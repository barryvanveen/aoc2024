package org.aoc

class Day1: Solver  {
    override fun solve(day: Int, part: Int, example: Boolean): Unit {
        val input = readInput(day, example)

        val left: MutableList<Int> = mutableListOf()
        val right: MutableList<Int> = mutableListOf()

        for (line in input) {
            val parts = line.split(Regex("""\W+""")).map { it.toInt() }
            require(parts.size == 2)

            left.add(parts[0])
            right.add(parts[1])
        }

        if (part == 1) {
            solvePart1(left, right)
        } else {
            solvePart2(left, right)
        }
    }

    private fun solvePart1(left: MutableList<Int>, right: MutableList<Int>) {
        var total = 0L
        val size = left.size

        left.sort()
        right.sort()

        for (i in 0..<size) {
            if (left[i] == right[i])
                continue

            if (left[i] < right[i]) {
                total += (right[i] - left[i])
                continue
            }

            total += (left[i] - right[i])
        }

        println("Solution: $total")
    }

    private fun solvePart2(left: MutableList<Int>, right: MutableList<Int>) {
        var total = 0L

        val appearances = mutableMapOf<Int, Int>()

        right.forEach { i ->
            if (!appearances.containsKey(i)) {
                appearances[i] = right.count { it == i }
            }
        }

        left.forEach { i ->
            total += i * (appearances[i] ?: 0)
        }

        println("Solution: $total")
    }
}