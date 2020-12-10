fun main() {
    var end = false
    val data = mutableListOf<Long>()
    while (!end) {
        val row = readLine()
        val nonNull = row ?: ""
        if (nonNull.isBlank()) {
            end = true
        } else {
            data += nonNull.toLong()
        }
    }

    var index = 25
    while (index < data.size) {
        if (!validate(data, index)) {
            break
        }
        index += 1
    }

    val desired = 50047984L
    var startIndex = 0
    var length = 1
    var found = false
    while (!found && (startIndex + length) < index) {
        val subList = data.subList(startIndex, startIndex + length)
        val sum = subList.sum()
        when {
            sum == desired -> found = true
            sum < desired -> length += 1
            sum > desired -> {
                startIndex += 1
                length = 1
            }
        }
    }

    val sl = data.subList(startIndex, startIndex + length).sorted()
    println("RESULT ${sl[0] + sl[sl.size - 1]}")
}

fun validate(data: List<Long>, index: Int): Boolean {
    val startIndex = (index - 25).coerceAtLeast(0)
    val subList = data.subList(startIndex, index).sorted()
    var low = 0
    var high = subList.size - 1
    var found = false
    while (low != high && !found) {
        val sum = subList[low] + subList[high]
        when {
            sum == data[index] -> found = true
            sum > data[index] -> high -= 1
            sum < data[index] -> low += 1
        }
    }
    return found
}