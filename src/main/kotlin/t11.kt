fun main () {
    var end = false
    val data = mutableListOf<String>()
    while (!end) {
        val input = readLine()
        val nonNull = input ?: ""
        if (nonNull.isBlank()) {
            end = true
        } else {
            data += nonNull
        }
    }

    val stable = iterate(data)
    var count = 0
    for (line in stable) {
        for (place in line) {
            if (place == '#') count++
        }
    }
    println("RESULT OCCUPIED COUNT $count")
}

fun iterate(input: List<String>): List<String> {
    var changed = true
    var data = input
    var result = mutableListOf<String>()
    while (changed) {
        changed = false
        for (y in 0 until data.size) {
            var line = ""
            for (x in 0 until data[y].length) {
                val newChar = checkPlace(data, x, y)
                changed = changed || data[y][x] != newChar
                line += newChar
            }
            result.add(line)
        }
        data = result
        result = mutableListOf()
    }

    return data
}

fun checkPlace(data: List<String>, x: Int, y: Int): Char {
    val place = data[y][x]
    val adjacentOccupiedCount = calculateAdjancentOccupied(data, x, y)
    return when (place) {
        'L' -> {
            if (adjacentOccupiedCount == 0) '#' else 'L'
        }
        '#' -> {
            if (adjacentOccupiedCount >= 5) 'L' else '#'
        }
        else -> place
    }
}

fun calculateAdjancentOccupied(data: List<String>, x: Int, y: Int): Int {
    var result = 0
    for (i in -1..1) {
        for (j in -1..1) {
            if (i == 0 && j ==0) continue
            if (findPlaceInDirection(data, x, y, i, j) == '#') {
                result++
            }
        }
    }
    return result
}

fun findPlaceInDirection(data: List<String>, x: Int, y: Int, dx: Int, dy: Int): Char {
    var inBounds = true
    var xc = x
    var yc = y
    while (inBounds) {
        xc += dx
        yc += dy
        inBounds = xc >= 0 && yc >= 0 && yc < data.size && xc < data[yc].length
        if (!inBounds) continue
        val place = data[yc][xc]
        if (place != '.') return place
    }
    return '.'
}