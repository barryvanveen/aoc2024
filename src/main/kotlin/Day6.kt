package org.aoc

import org.aoc.day6.Direction

data class Guard(
    var pos: Pair<Int,Int>,
    var direction: Direction,
) {
    fun getRow(): Int {
        return pos.first
    }
    fun getCol(): Int {
        return pos.second
    }
    fun nextPos(): Pair<Int,Int> {
        return when (direction) {
            Direction.UP -> Pair(pos.first - 1, pos.second)
            Direction.RIGHT -> Pair(pos.first, pos.second + 1)
            Direction.DOWN -> Pair(pos.first + 1, pos.second)
            Direction.LEFT -> Pair(pos.first, pos.second - 1)
        }
    }
    fun stepForward() {
        pos = nextPos()
    }
    fun turnRight() {
        direction = when (direction) {
            Direction.UP -> Direction.RIGHT
            Direction.RIGHT -> Direction.DOWN
            Direction.DOWN -> Direction.LEFT
            Direction.LEFT -> Direction.UP
        }
    }
}

class Day6: Solver  {
    companion object {
        const val EMPTY = '.'
        const val GUARD = '^'
        const val OBSTACLE = '#'
    }

    var grid = mutableListOf<MutableList<Char>>()
    var guard: Guard? = null

    override fun solve(part: Int, example: Boolean): Unit {
        val input = readInput(6, example)

        var row = 0
        input.forEach {
            val line = it.toCharArray()
            var col = 0
            grid.add(row, mutableListOf())
            line.forEach { char ->
                grid[row].add(col, getType(char))
                if (char == '^') {
                    guard = Guard(Pair(row, col), Direction.UP)
                }
                col++
            }
            row++
        }

        printGrid()

        if (part == 1) {
            solvePart1(guard!!)
        } else {
            solvePart2(guard!!)
        }
    }

    private fun getType(i: Char): Char {
        return when (i) {
            '.' -> EMPTY
            '^' -> GUARD
            '#' -> OBSTACLE
            else -> throw RuntimeException("Invalid type: $i")
        }
    }

    private fun printGrid() {
        for (row in grid) {
            for (col in row) {
                print(col)
            }
            println()
        }
        println()
    }

    private fun solvePart1(guard: Guard) {
        do {
            grid[guard.pos.first][guard.pos.second] = GUARD

            val next = guard.nextPos()
            if (!withinBounds(next)) {
                break
            }

            val nextType = grid[next.first][next.second]
            when (nextType) {
                EMPTY -> guard.stepForward()
                GUARD -> guard.stepForward()
                OBSTACLE -> guard.turnRight()
            }
        } while (withinBounds(guard))

        println("Solution: : ${countGuards()}" )
    }

    private fun withinBounds(pos: Pair<Int, Int>): Boolean {
        return (pos.first >= 0 && pos.first < grid.size && pos.second >= 0 && pos.second < grid.first().size)
    }

    private fun withinBounds(guard: Guard): Boolean {
        val row = guard.getRow()
        val col = guard.getCol()
        return (row >= 0 && row < grid.size && col >= 0 && col < grid.first().size)
    }

    private fun countGuards(): Long {
        var total = 0L
        for (row in grid) {
            for (col in row) {
                if (col == GUARD) {
                    total++
                }
            }
        }
        return total
    }

    private fun solvePart2(guard: Guard) {
        var total = 0L

        val originalGuard = guard.copy()
        val originalPath = findPath(grid, guard).map { it.pos }.distinct()

        println("Obstacles to test: ${originalPath.size}")
        var tested = 0;

        originalPath.forEach { pos ->
            // place new obstacle
            val originalGridType = grid[pos.first][pos.second]
            grid[pos.first][pos.second] = OBSTACLE

            // make the guard start from its original position
            guard.pos = originalGuard.pos
            guard.direction = originalGuard.direction

            // walk path and see if it contains loop
            val newPath = findPath(grid, guard)
            if (containsLoop(newPath)) {
                total++
            }

            // remove obstacle
            grid[pos.first][pos.second] = originalGridType

            tested++
            if (tested % 100 == 0) println("Tested $tested obstacles")
        }

        println("Solution: $total")
    }

    private fun findPath(grid: List<List<Char>>, guard: Guard): List<Guard> {
        val path = mutableListOf<Guard>()

        do {
            path.add(guard.copy())

            if (containsLoop(path)) {
                break
            }

            val next = guard.nextPos()
            if (!withinBounds(next)) {
                break
            }

            val nextType = grid[next.first][next.second]
            when (nextType) {
                EMPTY -> guard.stepForward()
                GUARD -> guard.stepForward()
                OBSTACLE -> guard.turnRight()
            }
        } while (withinBounds(guard))

        return path
    }

    private fun containsLoop(path: List<Guard>): Boolean {
        val seen = mutableSetOf<Guard>()

        path.forEach {
            if (seen.contains(it)) {
                return true
            }
            seen.add(it)
        }

        return false
    }
}