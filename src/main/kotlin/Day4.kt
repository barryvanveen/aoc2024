package org.aoc

class Day4: Solver  {
    override fun solve(part: Int, example: Boolean): Unit {
        val input = readInput(4, example)

        if (part == 1) {
            solvePart1(input)
        } else {
            solvePart2(input)
        }
    }

    private fun solvePart1(input: Collection<String>) {
        var total = 0L

        var grid = input.toTypedArray()
        var allLines: MutableList<String> = mutableListOf()

        // rows
        grid.forEach {
            allLines.add(it)
        }

        // cols
        var rows = input.size
        var columns = input.first().length
        for (col in 0..<columns) {
            var thisCol = ""
            for (row in 0..<rows) {
                thisCol += grid[row][col]
            }
            allLines.add(thisCol)
        }

        // diagonals (top left to bottom right)
        var startCol = columns-1;
        var startRow = 0;
        while (startCol > 0) {
            var col = startCol
            var row = startRow
            var thisDiagonal = ""
            while (col < columns && row < rows) {
                thisDiagonal += grid[row][col]
                col++
                row++
            }
            allLines.add(thisDiagonal)
            startCol--
        }
        while (startRow < rows) {
            var col = startCol
            var row = startRow
            var thisDiagonal = ""
            while (col < columns && row < rows) {
                thisDiagonal += grid[row][col]
                col++
                row++
            }
            allLines.add(thisDiagonal)
            startRow++
        }

        // diagonals (top left to bottom right)
        startCol = 0;
        startRow = 0;
        while (startCol < columns-1) {
            var col = startCol
            var row = startRow
            var thisDiagonal = ""
            while (col >= 0 && row < rows) {
                thisDiagonal += grid[row][col]
                col--
                row++
            }
            allLines.add(thisDiagonal)
            startCol++
        }
        while (startRow < rows) {
            var col = startCol
            var row = startRow
            var thisDiagonal = ""
            while (col >= 0 && row < rows) {
                thisDiagonal += grid[row][col]
                col--
                row++
            }
            allLines.add(thisDiagonal)
            startRow++
        }

        // search allLines for occurrences of XMAS
        allLines.forEach {
            total += countXmas(it)
            total += countXmas(it.reversed())
        }

        println("Solution: $total")
    }

    private fun countXmas(input: String): Int {
        val xmas = Regex("""XMAS""")
        val findings = xmas.findAll(input)
        return findings.count()
    }

    private fun solvePart2(input: Collection<String>) {
        var total = 0L

        var grid = input.toTypedArray()
        var rows = input.size
        var columns = input.first().length

        for (col in 0..<columns-2) {
            for (row in 0..<rows-2) {
                var diagonal1 = "" + grid[row][col] + grid[row+1][col+1] + grid[row+2][col+2]
                var diagonal2 = "" + grid[row+2][col] + grid[row+1][col+1] + grid[row][col+2]

                if ((diagonal1 == "MAS" || diagonal1 == "SAM") && (diagonal2 == "MAS" || diagonal2 == "SAM")) {
                    total++
                }
            }
        }

        println("Solution: $total")
    }
}