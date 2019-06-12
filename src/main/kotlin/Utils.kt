import java.io.File
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


var allowed: HashSet<Char> = HashSet()
var lettersCount: MutableMap<Char, Int> = HashMap()
var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss z")


internal fun getWordsTrie(fileName: String, maxLength: Int, numOfWords: Int, clue: String, hash: String): Trie {

    log("Filtering words and building words trie")

    var trie = Trie(numOfWords, clue, hash)

    File(fileName).forEachLine {
        if (isValid(it, maxLength)) {
            trie.insert(it)
        }
    }

    log("Finished filtering words and building words trie")
    return trie
}


fun initLettersCountHash(clue: String, numOfSpaces: Int) {

    clue.forEach {
        lettersCount[it] = (lettersCount[it] ?: 0) + 1
    }

    lettersCount[' '] = numOfSpaces
}


fun initAllowedLettersList(clue: String) {
    allowed.addAll(clue.toCharArray().asList())
    allowed.add(' ')
}


fun isValid(word: String, maxLength: Int): Boolean {
    if (word.length > maxLength) {
        return false
    }
    var i = 0
    while (i < word.length) {
        if (!allowed.contains(word[i]) && word[i] != ' ') {
            return false
        }
        i++
    }
    return frequencyCheck(word, lettersCount)
}


fun frequencyCheck(word: String, lettersCount: MutableMap<Char, Int>): Boolean {
    var i = 0
    while (i < word.length) {
        if (getFrequency(word, word[i]) > lettersCount.getOrDefault(word[i], 0)) {
            return false
        }
        i++
    }
    return true
}

fun getFrequency(word: String, letter: Char): Int {
    var frequency = 0

    for (i in 0..word.length - 1) {
        if (letter == word[i]) {
            ++frequency
        }
    }
    return frequency
}

fun log(message: String){
    println("${ZonedDateTime.now().format(formatter)}: $message\n")

}
