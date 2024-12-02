package org.aoc

import kotlin.collections.windowed

enum class Direction {
    UP,
    DOWN,
}

class Day2: Solver  {
    override fun solve(part: Int, example: Boolean): Unit {
        val input = readInput(2, example)

        if (part == 1) {
            solvePart1(input)
        } else {
            solvePart2(input)
        }
    }

    private fun solvePart1(input: Collection<String>) {
        var total = input.count { line ->
            isValid(split(line))
        }

        println("Solution: $total")
    }

    private fun split(input: String) =
        input.split(Regex("""\W+""")).map { it.toInt() }

    private fun isValid(input: List<Int>): Boolean {
        require(input.size >= 2)

        val direction = if (input[0] <= input[1]) Direction.UP else Direction.DOWN

        val invalidParts = input.windowed(2, 1, false).count { window ->
            if (direction == Direction.UP && window[0] < window[1] && window[1] - window[0] <= 3) {
                return@count false
            } else if (direction == Direction.DOWN && window[0] > window[1] && window[0] - window[1] <= 3) {
                return@count false
            }
            return@count true
        }

        return invalidParts == 0
    }

    private fun solvePart2(input: Collection<String>) {
        val output = input.map { line ->
            val parts = split(line)
            Pair(
                parts,
                isValid(parts)
            )
        }

        val newOutput = output.filter { line ->
            line.second == false
        }.map { line ->
            Pair(
                line.first,
                permutationIsValid(line.first)
            )
        }

        println(newOutput)

        val total = output.count { line ->
            line.second == true
        } + newOutput.count { line ->
            line.second == true
        }

        println("Solution: $total")
    }

    private fun permutationIsValid(input: List<Int>): Boolean {
        val size = input.size

        for (i in 0..<size) {
            if (isValid(listWithoutIndex(input, i))) {
                return true
            }
        }

        return false
    }

    private fun listWithoutIndex(input: List<Int>, index: Int): List<Int> =
      input.filterIndexed { item, _ -> item != index }
}