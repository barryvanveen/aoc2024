package org.aoc

class Day3: Solver  {
    override fun solve(part: Int, example: Boolean): Unit {
        val input = readInput(3, example)

        if (part == 1) {
            solvePart1(input)
        } else {
            solvePart2(input)
        }
    }

    private fun solvePart1(input: Collection<String>) {
        var total = 0L

        val regex = Regex("""mul\(([0-9]{1,3}),([0-9]{1,3})\)""")

        input.forEach { memory ->
            val found = regex.findAll(memory)
            println(found.count())
            found.forEach {
                println(it.groupValues)
                total += (it.groupValues[1].toLong() * it.groupValues[2].toLong())
            }
        }

        println("Solution: $total")
    }

    private fun solvePart2(input: Collection<String>) {
        var total = 0L

        val doDontRegex = Regex("""((do|don't)\(\))""")
        val mulRegex = Regex("""mul\(([0-9]{1,3}),([0-9]{1,3})\)""")
        val memoryRanges = mutableListOf<String>()

        input.forEach { memory ->
            val line = "do()${memory}don't()"
            val dosAndDonts = doDontRegex.findAll(line)

            var start: Int? = 0
            dosAndDonts.forEach {
                it.groups.forEach { group ->
                    if (start != null && group?.value == "don't()") {
                        val end = group.range.last + 1
                        memoryRanges.add(line.substring(start!!, end))
                        start = null
                    }
                    if (start == null && group?.value == "do()") {
                        start = group.range.first
                    }
                }
            }
        }

        memoryRanges.forEach { memory ->
            val found = mulRegex.findAll(memory)
            found.forEach {
                total += (it.groupValues[1].toLong() * it.groupValues[2].toLong())
            }
        }

        println("Solution: $total")
    }
}