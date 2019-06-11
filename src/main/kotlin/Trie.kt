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




}


//https://www.baeldung.com/trie-java prefixtree