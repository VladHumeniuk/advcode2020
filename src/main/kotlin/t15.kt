fun main() {
    val numbersInput = readLine() ?: ""
    val numbers = mutableListOf<Int>()
    for (num in numbersInput.split(",")) {
        numbers.add(num.toInt())
    }

    val lastIndexMap = mutableMapOf<Int, Int>()
    for (i in 0 until numbers.size) {
        lastIndexMap[numbers[i]] = i
    }

    var index = numbers.size
    while (numbers.size < 30000000) {
        val prevNumber = numbers[index - 1]
        var current: Int
        if (lastIndexMap.keys.contains(prevNumber) && lastIndexMap[prevNumber] != index -1) {
            current = index - lastIndexMap[prevNumber]!! - 1
            lastIndexMap[prevNumber] = index - 1
        } else {
            current = 0
        }
        if (!lastIndexMap.keys.contains(current)) {
            lastIndexMap[current] = index
        }
        numbers.add(current)
        index++
    }

    println("RESULT ${numbers.last()} count ${numbers.size}")
}