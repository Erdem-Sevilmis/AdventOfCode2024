package year2024.day06

import println
import readInput

fun day06() {
    val input = readInput("year2024/day06/Day06")

    val distinctPositions = calculateDistinctPositions(input)
    println("Day 06: ${distinctPositions.size}")

    val loopCausingPositions = findLoopCausingPositions(input)
    println("Day 06 pt2: ${loopCausingPositions.size}")
}

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)
    fun isWithinGrid(grid: Array<CharArray>): Boolean =
        y in grid.indices && x in grid[y].indices
}

enum class Direction(val offset: Point) {
    UP(Point(0, -1)),
    RIGHT(Point(1, 0)),
    DOWN(Point(0, 1)),
    LEFT(Point(-1, 0));

    fun turnRight(): Direction = values()[(ordinal + 1) % values().size]
}

fun calculateDistinctPositions(map: List<String>): Set<Point> {
    val grid = map.parseGrid()
    val (startPosition, startDirection) = grid.findGuardStart()
    return grid.simulateGuardPath(startPosition, startDirection)
}

fun List<String>.parseGrid(): Array<CharArray> =
    map { it.toCharArray() }.toTypedArray()

fun Array<CharArray>.findGuardStart(): Pair<Point, Direction> {
    val directionMap = mapOf('^' to Direction.UP, '>' to Direction.RIGHT, 'v' to Direction.DOWN, '<' to Direction.LEFT)
    forEachIndexed { y, row ->
        row.forEachIndexed { x, cell ->
            if (cell in directionMap.keys) {
                this[y][x] = '.' // Replace guard marker with empty space
                return Point(x, y) to directionMap[cell]!!
            }
        }
    }
    throw IllegalArgumentException("Guard not found in the map.")
}

fun Array<CharArray>.simulateGuardPath(
    startPosition: Point,
    startDirection: Direction
): Set<Point> {
    val visitedPositions = mutableSetOf(startPosition)
    var currentPosition = startPosition
    var currentDirection = startDirection

    while (true) {
        val (newPosition, newDirection) = getNextMove(currentPosition, currentDirection) ?: break
        currentPosition = newPosition
        currentDirection = newDirection
        visitedPositions.add(currentPosition)
    }

    return visitedPositions
}

fun Array<CharArray>.getNextMove(
    position: Point,
    direction: Direction
): Pair<Point, Direction>? {
    val newPosition = position + direction.offset
    if (!newPosition.isWithinGrid(this)) return null // Guard has left the map

    return if (this[newPosition.y][newPosition.x] == '#') {
        // Obstacle: Turn right
        position to direction.turnRight()
    } else {
        // Move forward
        newPosition to direction
    }
}

fun findLoopCausingPositions(map: List<String>): Set<Point> {
    val grid = map.parseGrid()
    val (startPosition, startDirection) = grid.findGuardStart()
    val validPositions = mutableSetOf<Point>()

    grid.forEachIndexed { y, row ->
        row.forEachIndexed { x, cell ->
            val point = Point(x, y)
            if (cell == '.' && point != startPosition) {
                val gridCopy = grid.copy()
                gridCopy[y][x] = '#'
                if (gridCopy.doesGuardGetStuck(startPosition, startDirection)) {
                    validPositions.add(point)
                }
            }
        }
    }

    return validPositions
}

fun Array<CharArray>.doesGuardGetStuck(
    startPosition: Point,
    startDirection: Direction
): Boolean {
    val visitedStates = mutableSetOf<Pair<Point, Direction>>()
    var currentPosition = startPosition
    var currentDirection = startDirection

    while (true) {
        val state = currentPosition to currentDirection
        if (state in visitedStates) return true // Loop detected
        visitedStates.add(state)

        val nextMove = getNextMove(currentPosition, currentDirection) ?: return false
        currentPosition = nextMove.first
        currentDirection = nextMove.second
    }
}

fun Array<CharArray>.copy(): Array<CharArray> =
    map { it.copyOf() }.toTypedArray()
