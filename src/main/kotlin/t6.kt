fun main() {
    var end = false
    val data = mutableListOf<List<String>>()
    var currentGroup = mutableListOf<String>()
    var prevIsBlank = false
    while (!end) {
        val line = readLine()
        val notnull = line ?: ""
        if (notnull.isBlank()) {
            if (prevIsBlank) {
                end = true
                continue
            } else {
                prevIsBlank = true
                data += currentGroup
                currentGroup = mutableListOf()
            }
        } else {
            prevIsBlank = false
            currentGroup.add(notnull)
        }
    }

    val count = mutableListOf<Int>()

    for (group in data) {
        var questions = mutableSetOf<Char>()
        for (c in group[0]) {
            questions.add(c)
        }
        for (line in group) {
            val updated = mutableSetOf<Char>()
            for (c in questions) {
                if (line.contains(c)) {
                    updated.add(c)
                }
            }
            questions = updated
        }
        count += questions.size
    }

    var sum = 0
    for (i in count) {
        sum += i
    }
    println("RESULT $sum")
}