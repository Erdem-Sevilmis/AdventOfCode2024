fun day04() {
    val input = readInput("day04")
    val target = "XMAS"
    val directions = listOf(
        0 to 1,    // Right
        1 to 0,    // Down
        1 to 1,    // Down-right
        1 to -1,   // Down-left
        0 to -1,   // Left
        -1 to 0,   // Up
        -1 to -1,  // Up-left
        -1 to 1    // Up-right
    )

    fun isValid(x: Int, y: Int, dx: Int, dy: Int, word: String): Boolean {
        for (i in word.indices) {
            val nx = x + i * dx
            val ny = y + i * dy
            if (nx !in input.indices || ny !in input[nx].indices || input[nx][ny] != word[i]) return false
        }
        return true
    }

    fun countOccurrences(): Int {
        var count = 0
        for (x in input.indices) {
            for (y in input[x].indices) {
                for ((dx, dy) in directions) {
                    if (isValid(x, y, dx, dy, target)) {
                        count++
                    }
                }
            }
        }
        return count
    }

    val totalOccurrences = countOccurrences()
    "Day 04: $totalOccurrences".println()
}
