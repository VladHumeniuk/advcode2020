fun main(args: Array<String>) {
    var end = false
    val data = mutableListOf<Row>()
    while (!end) {
        val line = readLine()
        if (line.isNullOrEmpty()) {
            end = true
            continue
        }
        val split = line.split(" ")
        val range = split[0].split("-")
        val ln = range[0].toInt()
        val hn = range[1].toInt()
        val symbol = split[1].removeSuffix(":")
        val l = split[2]
        if (ln != null && hn != null && symbol != null && l != null) {
            data.add(Row(ln, hn, symbol[0], l))
        } else {
            println("FAILED TO PARSE $line")
        }
    }

    var count = 0
    for (row in data) {
        if (row.match()) count += 1
    }
    println("RESULT $count")
}

class Row(val lowNum: Int, val highNum: Int, val symbol: Char, val line: String) {

    fun match(): Boolean {
        val lt = line[lowNum - 1] == symbol
        val ht = line[highNum - 1] == symbol
        return (lt || ht) && !(lt && ht)
    }
}