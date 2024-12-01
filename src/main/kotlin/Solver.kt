package org.aoc

import java.io.InputStream
import java.util.*

interface Solver {
    fun getFilename(day: Int, example: Boolean): String {
        if (example) {
            return "day${day}_example.txt"
        }
        return "day${day}.txt"
    }

    fun getResource(filename: String): InputStream =
        this.javaClass.getClassLoader().getResourceAsStream(filename)
            ?: throw RuntimeException("Could not find $filename")

    fun getLines(input: InputStream): Collection<String> {
        val lines = ArrayList<String>()

        val sc = Scanner(input)
        sc.useDelimiter("\\Z")

        while (sc.hasNextLine()) lines.add(sc.nextLine())

        return lines
    }

    fun readInput(day: Int, example: Boolean): Collection<String> {
        val filename = getFilename(day, example)
        val resource = getResource(filename)
        return getLines(resource)
    }

    fun solve(day: Int, part: Int, example: Boolean): Unit
}