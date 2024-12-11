package year2024.day05

import println
import readInput

fun day05() {
    val input = readInput("year2024/day05/Day05")
    val splitIndex = input.indexOfFirst { it.isBlank() }
    val rulesInput = input.subList(0, splitIndex)
    val updatesInput = input.subList(splitIndex + 1, input.size)

    val rules = rulesInput.map { it.split("|").let { (x, y) -> x.toInt() to y.toInt() } }
    val updates = updatesInput.map { it.split(",").map(String::toInt) }

    fun filterRulesForUpdate(update: List<Int>, rules: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
        val updateSet = update.toSet()
        return rules.filter { (x, y) -> x in updateSet && y in updateSet }
    }
    fun isValidOrder(update: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
        val filteredRules = filterRulesForUpdate(update, rules)
        val indexMap = update.withIndex().associate { it.value to it.index }
        return filteredRules.all { (x, y) -> indexMap[x]!! < indexMap[y]!! }
    }

    fun sortUpdate(update: List<Int>, rules: List<Pair<Int, Int>>): List<Int> {
        val filteredRules = filterRulesForUpdate(update, rules)
        val dependencies = mutableMapOf<Int, MutableList<Int>>()
        filteredRules.forEach { (x, y) ->
            dependencies.computeIfAbsent(y) { mutableListOf() }.add(x)
        }

        val result = mutableListOf<Int>()
        val visited = mutableSetOf<Int>()

        fun visit(page: Int) {
            if (page !in visited) {
                visited.add(page)
                dependencies[page]?.forEach { visit(it) }
                result.add(page)
            }
        }

        update.forEach { visit(it) }
        return result.reversed()
    }

    val validUpdates = updates.filter { isValidOrder(it, rules) }
    val invalidUpdates = updates.filterNot { isValidOrder(it, rules) }

    val middleSumValid = validUpdates.sumOf { it[it.size / 2] }

    val correctedUpdates = invalidUpdates.map { sortUpdate(it, rules) }
    val middleSumInvalid = correctedUpdates.sumOf { it[it.size / 2] }

    "Day 05: $middleSumValid".println()
    "Day 05 pt2: $middleSumInvalid".println()
}
