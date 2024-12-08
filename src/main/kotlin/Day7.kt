package org.aoc

import com.github.michaelbull.itertools.product
import kotlin.math.pow

class Day7: Solver  {
    companion object {
        const val PLUS = '+'
        const val TIMES = '*'
        const val CONCAT = '|'
    }

    override fun solve(part: Int, example: Boolean): Unit {
        var input = readInput(7, example)

        var calibrations: MutableList<Pair<Long,List<Int>>> = mutableListOf()

        input.forEachIndexed { i, line ->
            val parts = line.split(": ")
            val result = parts[0].toLong()
            val pieces = parts[1].split(" ").map { it.toInt() }.toList()
            calibrations.add(Pair(result, pieces))
        }

        if (part == 1) {
            solvePart1(calibrations.toList())
        } else {
            solvePart2(calibrations.toList())
        }
    }

    private fun solvePart1(calibrations: List<Pair<Long,List<Int>>>) {
        var total = 0L

        val max = calibrations.maxOf { it.second.size - 1 }

        val permutations: MutableMap<Int, List<List<Char>>> = mutableMapOf()
        for (i in 1..max) {
            permutations[i] = getOperatorPermutations(listOf(PLUS, TIMES), i)
        }

        calibrations.forEach { item ->
            run loop@ {
                permutations[item.second.size-1]!!.forEach { permutation ->
                    item.second.map { it.toLong() }.reduceIndexed { index, acc, element ->
                        if (permutation[index-1] == PLUS) acc + element
                        else if (permutation[index-1] == TIMES) acc * element
                        else throw RuntimeException("Don't know how to continue $index, $acc, $element, '${permutation[index-1]}'")
                    }.also {
                        if (it == item.first) {
                            total += item.first
//                            println("Found solution for $item with $permutation")
                            return@loop
                        }
                    }
                }
//                println("Found no solution for $item")
            }
        }

        println("Solution: $total" )
    }

    private fun getOperatorPermutations(chars: List<Char>, numOperators: Int): List<List<Char>> {
        val operators = mutableListOf<List<Char>>()

        for (i in 0..<numOperators) {
            operators.add(chars)
        }

        return operators
            .product()
            .toList()
    }

    private fun solvePart2(calibrations: List<Pair<Long,List<Int>>>) {
        var total = 0L

        val max = calibrations.maxOf { it.second.size - 1 }

        val permutations: MutableMap<Int, List<List<Char>>> = mutableMapOf()
        for (i in 1..max) {
            permutations[i] = getOperatorPermutations(listOf(PLUS, TIMES, CONCAT), i)
        }

        calibrations.forEach { item ->
            run loop@ {
                permutations[item.second.size-1]!!.forEach { permutation ->
                    item.second.map { it.toLong() }.reduceIndexed { index, acc, element ->
                        if (permutation[index-1] == PLUS) acc + element
                        else if (permutation[index-1] == TIMES) acc * element
                        else if (permutation[index-1] == CONCAT) (acc * 10.0.pow(element.toString().length)).toLong() + element
                        else throw RuntimeException("Don't know how to continue $index, $acc, $element, '${permutation[index-1]}'")
                    }.also {
                        if (it == item.first) {
                            total += item.first
//                            println("Found solution for $item with $permutation")
                            return@loop
                        }
                    }
                }
//                println("Found no solution for $item")
            }
        }

        println("Solution: $total" )
    }
}