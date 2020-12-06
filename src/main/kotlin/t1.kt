fun main() {
    val result = 2020
    var empty = false
    val data = mutableListOf<Int>()
    while (!empty) {
        val value = readLine()
        if (value.isNullOrBlank()) {
            empty = true
        } else {
            data.add(value.toInt())
        }
    }
    data.sort()
    var startPoint = 1
    var endPoint = data.size - 1
    var floatPoint = 0
    var found = false
    while (endPoint != 1 && !found) {
        val sum = data[startPoint] + data[endPoint]
        val subData = data.subList(0, startPoint)
        when {
            sum < result -> {
                val delta = result - sum
                when {
                    delta < subData[0] -> {
                        startPoint = 1
                        endPoint -= 1
                    }
                    delta >= subData[0] -> {
                        if (subData.contains(delta)) {
                            floatPoint = subData.indexOf(delta)
                            println("RESULT ${data[floatPoint] * data[startPoint] * data[endPoint]}")
                            found = true
                        } else {
                            startPoint += 1
                        }
                    }
                }
            }
            sum >= result -> {
                startPoint = 1
                endPoint -= 1
            }
        }
    }
    if (!found) println("NO RESULT")
}