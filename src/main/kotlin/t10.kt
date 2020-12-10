fun main() {
    var end = false
    val data = mutableListOf<Long>()
    while (!end) {
        val input = readLine()
        val nonNull = input ?: ""
        if (nonNull.isBlank()) {
            end = true
        } else {
            data += nonNull.toLong()
        }
    }

    val sortedList = data.sorted()
    val device = sortedList.last() + 3
    val outlet = 0L

    val sorted = mutableListOf<Long>().apply {
        add(outlet)
        addAll(sortedList)
        add(device)
        sort()
    }
    var oneCount = 0
    var threeCount = 0
    for (i in 1 until sorted.size) {
        val diff = sorted[i] - sorted[i - 1]
        if (diff == 1L) {
            oneCount += 1
        }
        if (diff == 3L) {
            threeCount += 1
        }
        if (diff > 3L) {
            break
        }
    }

    val waysCount = mutableMapOf<Int, Long>()
    val res = countConnections(sorted, 0, waysCount)

    println("RESULT $res")
}

fun countConnections(data: List<Long>, current: Int, waysCount: MutableMap<Int, Long>): Long {
    if (waysCount.containsKey(current)) {
        return waysCount[current]!!
    }
    if (current == data.size - 1) {
        waysCount[current] = 1
        return 1
    }
    var count = 0L
    if (current < (data.size - 1) && (data[current + 1] - data[current]) < 4) {
        count += countConnections(data, current + 1, waysCount)
    }
    if (current < (data.size - 2) && (data[current + 2] - data[current]) < 4) {
        count += countConnections(data, current + 2, waysCount)
    }
    if (current < (data.size - 3) && (data[current + 3] - data[current]) < 4) {
        count += countConnections(data, current + 3, waysCount)
    }
    waysCount[current] = count
    return count
}