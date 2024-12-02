fun day02() {
    val input = readInput("day02")

    val reports = input.map { line ->
        line.split("\\s+".toRegex()).map(String::toInt)
    }
    "Day 02: ${countSafeReports(reports)}".println()
}

fun isSafeReport(levels: List<Int>): Boolean {
    if (levels.size < 2) return true

    val differences = levels.zipWithNext { a, b -> b - a }
    val allIncreasing = differences.all { it in 1..3 }
    val allDecreasing = differences.all { it in -3..-1 }

    return allIncreasing || allDecreasing
}

fun countSafeReports(reports: List<List<Int>>): Int {
    return reports.count { isSafeReport(it) }
}