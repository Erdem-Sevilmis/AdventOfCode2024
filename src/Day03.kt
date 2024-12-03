fun day03() {
    val input = readInput("day03")
    val data = input.joinToString("")
    val validPattern = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
    val controlPattern = Regex("""do\(\)|don't\(\)""")

    // Part 1: Sum all valid multiplications
    val totalSumPart1 = validPattern.findAll(data)
        .sumOf { matchResult ->
            val (x, y) = matchResult.destructured
            x.toInt() * y.toInt()
        }

    // Part 2: Handle do() and don't() instructions
    var enabled = true
    val totalSumPart2 = Regex("""mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""")
        .findAll(data)
        .sumOf { matchResult ->
            when {
                matchResult.value.startsWith("mul") && enabled -> {
                    val (x, y) = validPattern.matchEntire(matchResult.value)!!.destructured
                    x.toInt() * y.toInt()
                }
                matchResult.value == "do()" -> {
                    enabled = true
                    0
                }
                matchResult.value == "don't()" -> {
                    enabled = false
                    0
                }
                else -> 0
            }
        }

    "Day 03: $totalSumPart1".println()
    "Day 03 pt2: $totalSumPart2".println()
}

