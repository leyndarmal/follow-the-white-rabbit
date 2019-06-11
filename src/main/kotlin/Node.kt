import java.util.HashMap

internal class Node {
    val children: MutableMap<Char, Node> = HashMap()
    var isEndOfWord: Boolean = false
    var letters: String = ""
}