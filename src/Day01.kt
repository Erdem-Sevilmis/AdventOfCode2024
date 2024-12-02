fun day01() {
    val input = readInput("day01")

    val leftList = mutableListOf<Int>()
    val rightList = mutableListOf<Int>()

    input.forEach { line ->
        val (left, right) = line.split("\\s+".toRegex()).map(String::toInt)
        leftList.add(left)
        rightList.add(right)
    }
    "Day 01: ${calculateTotalDistance(leftList, rightList)}".println()
}

fun calculateTotalDistance(leftList: List<Int>, rightList: List<Int>) =
    leftList.sorted().zip(rightList.sorted()).sumOf { (l, r) -> kotlin.math.abs(l - r) }
