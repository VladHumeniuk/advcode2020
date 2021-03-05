import java.lang.Exception

fun main() {
    val earliestDepart = (readLine() ?: "-1").toInt()
    val busesInput = readLine() ?: ""
    val buses = mutableListOf<Int>()
    val split = busesInput.split(",")
    for (number in split) {
        if (number != "x") buses += number.toInt()
    }
    val busMap = mutableMapOf<Int, Int>()
    for (bus in buses) {
        busMap[bus] = split.indexOf(bus.toString())
    }

    var busRemainders = mutableMapOf<Int, Int>()
    for (bus in busMap.keys) {
        val remainder = busMap[bus]!! % bus
        busRemainders[bus] = if (remainder >= 0) remainder else remainder + bus
    }
    busRemainders = busRemainders.toSortedMap()

    val keys = busRemainders.keys.toList()
    for (i in (busRemainders.keys.size - 2) downTo 0) {
        for (j in i downTo 0) {
            busRemainders[keys[j]] = findMultiplierToFitRemainder(keys[i + 1], keys[j], busRemainders[keys[i + 1]]!!, busRemainders[keys[j]]!!)
        }
    }

    val busList = mutableListOf(0L)

    for (i in 0 until busRemainders.size) {
        val mul = keys[i] * busList.last()
        val rem = busRemainders[keys[i]]!!
        busList += mul + rem
    }

    val number = busList.last()

    println("RESULT $number")
}

fun part1(earliestDepart: Int, buses: List<Int>): Int {
    val timeDiff = mutableMapOf<Int, Int>()
    for (bus in buses) {
        timeDiff[bus] = bus - earliestDepart % bus
    }
    val resultMap = timeDiff.toSortedMap(compareBy { timeDiff[it] })
    val earliestBus = resultMap.keys.first()

    return earliestBus * resultMap[earliestBus]!!
}

fun findMultiplierToFitRemainder(a: Int, b: Int, ra: Int, rb: Int): Int {
    for (i in 0 until b) {
        if ((i * a + ra - rb) % b == 0) return i
    }
    throw Exception("MultiplierToFitRemainder not found $a $b $ra $rb")
}