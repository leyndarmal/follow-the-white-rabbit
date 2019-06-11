import java.math.BigInteger
import java.security.MessageDigest
import kotlin.system.exitProcess
import kotlinx.coroutines.*


internal class Trie {
    private val root: Node
    private var counter: Int = 0
    var numOfWords: Int = 0
    var clue: String = ""
    var maxLength: Int = 0
    val md = MessageDigest.getInstance("MD5")
    var hash: String = ""

    val isEmpty: Boolean
        get() = (root == null || root.children?.isEmpty())

    init {
        root = Node()
    }

    fun insert(word: String) {
        counter++
        var current = root

        for (i in 0 until word.length) {
//            current?.children?.putIfAbsent(word[i], Node())
            current = current?.children?.getOrPut(word[i], { Node() })
        }
        current!!.isEndOfWord = true
        current!!.letters = word

    }

    fun root(): Node {
        return root
    }

    fun counter(): Int {
        return counter
    }


    fun containsNode(word: String): Boolean {
        var current = root

        for (i in 0 until word.length) {
            val char = word[i]
            val node = current?.children?.get(char) ?: return false
            current = node
        }
        return current!!.isEndOfWord
    }


    fun addCandidatesToList(
        startNode: Node, phrase: String, numOfWords: Int, tempString: String
    ) {
        var currentNode = startNode


        for (child in currentNode.children) {
            var indexToRemove = phrase.indexOf(child.key)

            if (indexToRemove === -1) {
                continue
            }
            var isWord: Boolean = child.value.isEndOfWord
            var newPhrase = phrase.removeRange(indexToRemove, indexToRemove + 1)

            if (isWord) { //use the current word if possible
                var newTempString =
                    if (numOfWords == this.numOfWords) "${child.value.letters}" else "$tempString ${child.value.letters}"
                if (newTempString.length < this.maxLength && numOfWords > 1) {
                    addCandidatesToList(root, newPhrase, numOfWords - 1, newTempString)

                }

                if (newTempString.length === this.maxLength) {
                    if (isValid(newTempString, this.maxLength)) {

                        var myHash =
                            BigInteger(1, md.digest(newTempString.toByteArray())).toString(16).padStart(32, '0')
                        if (myHash == hash) {
                            println(newTempString)
                            exitProcess(1)
                        }

                    }

                }
            }

            addCandidatesToList(child.value, newPhrase, numOfWords, tempString)

        }
    }


}


//https://www.baeldung.com/trie-java prefixtree