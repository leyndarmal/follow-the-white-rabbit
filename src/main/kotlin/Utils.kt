import java.io.File
import java.math.BigInteger
import java.security.MessageDigest


var allowed: HashSet<Char> = HashSet()

var lettersCount: MutableMap<Char, Int> = HashMap()
const val MAX_LENGTH_2_SPACES = 20
const val MAX_LENGTH_3_SPACES = 21
const val CLUE = "poultryoutwitsants"
const val NUM_OF_WORDS = 4


internal fun getWordsTrie(fileName: String, numOfSpaces: Int, maxLength: Int): Trie {

    var trie = Trie()

    File(fileName).forEachLine {
        if (isValid(it, maxLength)) {
            trie.insert(it)
        }
    }
    return trie
}


fun getNumOfWords(): Int {
    return NUM_OF_WORDS
}

fun getClue(): String {
    return CLUE
}

fun getLettersCountMap(): MutableMap<Char, Int> {
    return lettersCount
}

fun initLettersCountHash(numOfSpaces: Int) {

    CLUE.forEach {
        lettersCount[it] = (lettersCount[it] ?: 0) + 1
    }

    lettersCount[' '] = numOfSpaces
}


fun initAllowedLettersList() {
    allowed.addAll(CLUE.toCharArray().asList())
    allowed.add(' ')
}

data class Quad(
    val first: Int,
    val second: Int,
    val third: Int,
    val forth: Int
)


fun generateAllTriplets(sum: Int): List<Triple<Int, Int, Int>> {

    var bla: ArrayList<Triple<Int, Int, Int>> = ArrayList()

    var tripletsList: ArrayList<Triple<Int, Int, Int>> = ArrayList()
    for (i in 1..10) {
        for (j in i..10) {
            for (k in j..10) {
                if (i + j + k === sum) {
                    bla.add(Triple(i, j, k))
                }
            }
        }
    }
    return bla
}

fun generateAllQuad(sum: Int): List<Quad> {

    var quad: ArrayList<Quad> = ArrayList()

    for (i in 1..10) {
        var currentTriple = generateAllTriplets(sum - i)
        currentTriple.forEach {
            quad.add(Quad(i, it.first, it.second, it.third))
        }
    }
    return quad
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


fun getWord(hash: String, wordsList: List<String>): String {
    val md = MessageDigest.getInstance("MD5")
    var i = 0
    while (i < wordsList.size) {
        val md = MessageDigest.getInstance("MD5")
        var myHash = BigInteger(1, md.digest(wordsList[i].toByteArray())).toString(16).padStart(32, '0')
        if (myHash == hash) {
            return wordsList[i]
        } else {
            i++
        }
    }
    return "I am sorry, I couldn't find"

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