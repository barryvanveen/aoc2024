package org.aoc

import kotlin.text.split

class Day5: Solver  {
    var rules: MutableMap<Int, MutableList<Int>> = mutableMapOf()
    var updates: MutableList<List<Int>> = mutableListOf()

    override fun solve(part: Int, example: Boolean): Unit {
        val input = readInput(5, example)

        var processingRules = true;
        input.forEach {
            if (it == "") {
                processingRules = false
            } else if (processingRules) {
                var parts = it.split("|")
                require(parts.size == 2)

                val key = parts[0].toInt()
                val value = parts[1].toInt()
                if (rules.containsKey(key)) {
                    rules.get(key)!!.add(value)
                } else {
                    rules.put(key, mutableListOf(value))
                }
            } else {
                updates.add(it.split(",").map { it.toInt() })
            }
        }

        if (part == 1) {
            solvePart1()
        } else {
            solvePart2()
        }
    }

    private fun solvePart1() {
        var total = 0L

        updates.forEach { update ->
            if (isValid(update) == null) {
                total += (update[update.size / 2])
            }
        }

        println("Solution: $total")
    }

    private fun isValid(input: List<Int>): Pair<Int, Int>? {
        for (i in 1..<input.size) {
            val current = input[i]
            val rules = rules.get(current) ?: emptyList()
            for (j in 0..<i) {
                val seenit = input[j]
                if (rules.contains(seenit)) {
                    return Pair(i, j)
                }
            }
        }

        return null
    }

    private fun solvePart2() {
        var total = 0L

        var invalidUpdates: MutableList<List<Int>> = mutableListOf()
        updates.forEach { update ->
            if (isValid(update) != null) {
                invalidUpdates.add(update)
            }
        }

        val correctedUpdates = invalidUpdates.map { update ->
            var swap = isValid(update)
            var outcome = update
            while (swap != null) {
                outcome = swap(outcome, swap.first, swap.second)
                swap = isValid(outcome)
            }

            return@map outcome
        }

        correctedUpdates.forEach { update ->
            total += (update[update.size / 2])
        }

        println("Solution: $total")
    }

    private fun swap(input: List<Int>, i: Int, j: Int): List<Int> {
        val parts = input.toMutableList()
        val temp = parts[i]
        parts[i] = parts[j]
        parts[j] = temp
        return parts.toList()
    }
}