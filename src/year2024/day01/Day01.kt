package year2024.day01

import println
import readInput

fun day01() {
    val input = readInput("year2024/day01/Day01")

    val leftList = mutableListOf<Int>()
    val rightList = mutableListOf<Int>()

    input.forEach { line ->
        val (left, right) = line.split("\\s+".toRegex()).map(String::toInt)
        leftList.add(left)
        rightList.add(right)
    }
    "Day 01: ${calculateTotalDistance(leftList, rightList)}".println()
    "Day 01 pt2: ${calculateSimilarityScore(leftList, rightList)}".println()
}

fun calculateTotalDistance(leftList: List<Int>, rightList: List<Int>) =
    leftList.sorted().zip(rightList.sorted()).sumOf { (l, r) -> kotlin.math.abs(l - r) }

fun calculateSimilarityScore(leftList: List<Int>, rightList: List<Int>): Int {
    val rightListCountMap = rightList.groupingBy { it }.eachCount()

    return leftList.sumOf { number ->
        val countInRightList = rightListCountMap[number] ?: 0
        number * countInRightList
    }
}