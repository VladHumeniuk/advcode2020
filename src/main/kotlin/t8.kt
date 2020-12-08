fun main() {
    var end = false
    val data = mutableListOf<String>()
    while (!end) {
        val line = readLine()
        val nonNull = line ?: ""
        if (nonNull.isBlank()) {
            end = true
        } else {
            data += nonNull
        }
    }

    var result = 0

    for (i in 0 until data.size) {
        val row = data[i]
        val command = row.subSequence(0, 3).toString()
        if (command != "acc") {
            val copy = ArrayList(data)
            val oppositeCommand = if (command == "jmp") "nop" else "jmp"
            copy[i] = copy[i].replace(command, oppositeCommand)
            val res = run(copy, 0, mutableSetOf())
            if (!res.looped) {
                result = res.accumulated
                break
            }
        }
    }

    println("RESULT $result")
}

fun run(data: List<String>, row: Int, visited: MutableSet<Int>): Result {
    if (visited.contains(row)) {
        return Result(0, true)
    }
    if (row < 0 || row >= data.size) {
        return Result(0, false)
    }
    visited += row
    val split = data[row].split(" ")
    val num = split[1].toInt()
    return when (split[0]) {
        "nop" -> {
            run(data, row + 1, visited)
        }
        "jmp" -> {
            run(data, row + num, visited)
        }
        "acc" -> {
            run(data, row + 1, visited).apply {
                accumulated += num
            }
        }
        else -> {
            error("invalid command")
        }
    }
}

class Result(
        var accumulated: Int,
        var looped: Boolean
)