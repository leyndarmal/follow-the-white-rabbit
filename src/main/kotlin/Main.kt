import kotlinx.coroutines.runBlocking

fun main() {

    initLettersCountHash(3)
    initAllowedLettersList()


    var candidateList: ArrayList<String> = ArrayList()
    var clue = getClue()
    var numberOfWords = getNumOfWords()

    var wordsTrie = getWordsTrie("resources/wordlist.dms", 3, 21)
    wordsTrie.numOfWords = numberOfWords
    wordsTrie.clue = clue
    wordsTrie.maxLength = clue.length + numberOfWords - 1
    wordsTrie.hash = "665e5bcb0c20062fe8abaaf4628bb154"

    wordsTrie.addCandidatesToList(wordsTrie.root(), clue, numberOfWords, "")


}
