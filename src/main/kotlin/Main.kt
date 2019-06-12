

fun main() {

    log("Starting program")

    var filePath = "resources/wordlist.dms"
    var hash = "665e5bcb0c20062fe8abaaf4628bb154"
    var clue = "poultryoutwitsants"
    var numberOfWords = 4
    var numOfSpaces = numberOfWords - 1
    var maxLength = clue.length + numOfSpaces

    initLettersCountHash(clue, numOfSpaces)
    initAllowedLettersList(clue)

    var wordsTrie = getWordsTrie(filePath, maxLength, numberOfWords, clue, hash)
    wordsTrie.assembleAndCheckCandidates(wordsTrie.root(), clue, numberOfWords, "")


    log("Program finished")

}
