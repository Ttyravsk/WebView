import java.security.MessageDigest

fun sha1(input: String): String {
    val digest = MessageDigest.getInstance("SHA-1")
    val bytes = digest.digest(input.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}

fun main() {
    val input = "Service"
    val expectedOutput = "e8c48c68ded02f66b836172e4bae3f38878b7a7d"
    val output = sha1(input)

    println("Input: $input")
    println("Expected Output: $expectedOutput")
    println("Output: $output")
    println("Match: ${output == expectedOutput}")
}