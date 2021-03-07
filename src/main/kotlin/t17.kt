fun main() {
    var end = false
    val data = mutableListOf<String>()
    while (!end) {
        val input = readLine()
        val nonNull = input ?: ""
        if (nonNull.isBlank()) {
            end = true
        } else {
            data += nonNull
        }
    }

    var space = mutableListOf<List<List<List<Int>>>>()
    val z0 = mutableListOf<List<Int>>()
    for (line in data) {
        val y = mutableListOf<Int>()
        for (c in line) {
            y.add(if (c == '.') 0 else 1)
        }
        z0.add(y)
    }
    space.add(listOf(z0))

    for (i in 0 until 6) {
        val wdim = space.size
        val zdim = space.first().size
        val ydim = space.first().first().size
        val xdim = space.first().first().first().size
        val expandedSpace = mutableListOf<List<List<List<Int>>>>()
        for (w in -1..wdim) {
            val wspace = mutableListOf<List<List<Int>>>()
            for (z in -1..zdim) {
                val zspace = mutableListOf<List<Int>>()
                for (y in -1..ydim) {
                    val yspace = mutableListOf<Int>()
                    for (x in -1..xdim) {
                        yspace.add(if (!(0 until xdim).contains(x)
                                || !(0 until ydim).contains(y)
                                || !(0 until zdim).contains(z)
                                || !(0 until wdim).contains(w))
                                    0 else space[w][z][y][x])
                    }
                    zspace.add(yspace)
                }
                wspace.add(zspace)
            }
            expandedSpace.add(wspace)
        }

        val newSpace = mutableListOf<List<List<List<Int>>>>()
        for (w in expandedSpace.indices) {
            val wspace = mutableListOf<List<List<Int>>>()
            for (z in expandedSpace[w].indices) {
                val zspace = mutableListOf<List<Int>>()
                for (y in expandedSpace[w][z].indices) {
                    val yspace = mutableListOf<Int>()
                    for (x in expandedSpace[w][z][y].indices) {
                        val value = expandedSpace[w][z][y][x]
                        val active = calculateActiveAdjacent(expandedSpace, w, z, y, x)
                        yspace.add(if (value == 1 && (2..3).contains(active) || value == 0 && active == 3) 1 else 0)
                    }
                    zspace.add(yspace)
                }
                wspace.add(zspace)
            }
            newSpace.add(wspace)
        }
        space = newSpace
    }

    var result = 0
    for (w in space) {
        for (z in w) {
            for (y in z) {
                result += y.sum()
            }
        }
    }
    println("RESULT $result")
}

fun calculateActiveAdjacent(space: List<List<List<List<Int>>>>, w: Int, z: Int, y:Int, x: Int): Int {
    var count = 0
    for (dw in -1..1) {
        for (dz in -1..1) {
            for (dy in -1..1) {
                for (dx in -1..1) {
                    if (!(dz == 0 && dy == 0 && dx == 0 && dw == 0)
                            && space.indices.contains(w + dw)
                            && space.first().indices.contains(z + dz)
                            && space.first().first().indices.contains(y + dy)
                            && space.first().first().first().indices.contains(x + dx)) {
                        count += space[w + dw][z + dz][y + dy][x + dx]
                    }
                }
            }
        }
    }
    return count
}