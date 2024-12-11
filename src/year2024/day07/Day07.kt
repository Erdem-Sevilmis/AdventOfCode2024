package year2024.day07

import println
import readInput
fun day07() {
    val input = readInput("year2024/day07/Day07")

    // Part 1: Original calibration result (without '||')
    val originalResult = calculateTotalCalibration(input, availableOperators = listOf('+', '*'))
    "Day 07: $originalResult".println()

    // Part 2: New calibration result (with '||')
    val newResult = calculateTotalCalibration(input, availableOperators = listOf('+', '*', '|'))
    "Day 07 pt2: $newResult".println()
}

fun calculateTotalCalibration(input: List<String>, availableOperators: List<Char>): Long {
    return input.sumOf { line ->
        val (testValue, numbers) = parseLine(line)
        if (canMatchTestValue(testValue, numbers, availableOperators)) testValue else 0L
    }
}

fun parseLine(line: String): Pair<Long, List<Long>> {
    val (testValueStr, numbersStr) = line.split(": ")
    val testValue = testValueStr.toLong()
    val numbers = numbersStr.split(" ").map { it.toLong() }
    return testValue to numbers
}

fun canMatchTestValue(testValue: Long, numbers: List<Long>, availableOperators: List<Char>): Boolean {
    val operatorCombinations = generateOperatorCombinations(numbers.size - 1, availableOperators)
    return operatorCombinations.any { operators ->
        evaluateExpression(numbers, operators) == testValue
    }
}

fun generateOperatorCombinations(size: Int, operators: List<Char>): List<List<Char>> {
    if (size == 0) return listOf(emptyList())
    return operators.flatMap { op ->
        generateOperatorCombinations(size - 1, operators).map { listOf(op) + it }
    }
}

fun evaluateExpression(numbers: List<Long>, operators: List<Char>): Long {
    var result = numbers[0]
    for (i in operators.indices) {
        result = when (operators[i]) {
            '+' -> result + numbers[i + 1]
            '*' -> result * numbers[i + 1]
            '|' -> concatenateNumbers(result, numbers[i + 1])
            else -> result
        }
    }
    return result
}

fun concatenateNumbers(left: Long, right: Long): Long {
    return (left.toString() + right.toString()).toLong()
}
