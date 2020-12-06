fun main() {
    val data = mutableListOf<String>()
    var end = false
    while (!end) {
        val row = readLine()
        if (row.isNullOrBlank()) {
            end = true
        } else {
            data.add(row)
        }
    }
    val results = mutableListOf<Int>()
    for (row in data) {
        results += process(row, 0, 0, 127, 0, 7)
    }
    results.sortDescending()
    val seats = 0..(127 * 8 + 7)
    val valid = mutableListOf<Int>()
    for (num in seats) {
        if (!results.contains(num)) {
            valid += num
        }
    }
    println("RESULT ${results[0]}\n")
    for (n in valid) {
        println("VALID $n")
    }
}

fun process(data: String, index: Int, lowR: Int, highR: Int, lowC: Int, highC: Int): Int {
    return if (index == data.length) {
        if (lowR == highR && lowC == highC) {
            lowR * 8 + lowC
        } else {
            -1
        }
    } else
        when (data[index]) {
            'F' -> process(data, index + 1, lowR, (lowR + highR) / 2, lowC, highC)
            'B' -> process(data, index + 1, (lowR + highR + 1) / 2, highR, lowC, highC)
            'R' -> process(data, index + 1, lowR, highR, (lowC + highC + 1) / 2, highC)
            'L' -> process(data, index + 1, lowR, highR, lowC, (lowC + highC) / 2)
            else -> -1
        }
}