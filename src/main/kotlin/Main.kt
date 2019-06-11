
fun main() {

    var hash = "665e5bcb0c20062fe8abaaf4628bb154"
    var clue = "poultryoutwitsants"
    var numberOfWords = 4
    var numOfSpaces = numberOfWords -1

    var maxLength = clue.length + numOfSpaces

    initLettersCountHash(clue, numOfSpaces)
    initAllowedLettersList(clue)

    var wordsTrie = getWordsTrie("resources/wordlist.dms", maxLength)

    wordsTrie.numOfWords = numberOfWords
    wordsTrie.clue = clue
    wordsTrie.maxLength = maxLength
    wordsTrie.hash = hash

    wordsTrie.assembleAndCheckCandidates(wordsTrie.root(), clue, numberOfWords, "")


}
