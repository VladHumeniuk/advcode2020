
fun main(args: Array<String>) {
    var end = false
    val data = mutableListOf<List<Char>>()
    while(!end) {
        val line = readLine()
        if (line?.isEmpty() != false) {
            end = true
            continue
        }
        val charlist = mutableListOf<Char>()
        for (c in line) {
            charlist.add(c)
        }
        data.add(charlist)
    }

    var border = false
    var count = 0
    var x = 0
    var y = 0
    while (!border) {
        border = x > (data[0].size - 1) || y > (data.size - 1)
        if (border) continue
        if(data[y][x] == '#') {
            count += 1
        }
        x += 1
        x %= data[0].size
        y += 2
    }
    println("RESAULT $count")
}