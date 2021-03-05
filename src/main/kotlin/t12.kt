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

    var wx = 10.0
    var wy = 1.0
    var sx = 0.0
    var sy =0.0
    for (line in data) {
        val direction = line[0]
        val distance = line.substring(1).toInt()
        when (direction) {
            'R' -> {
                val angle = kotlin.math.PI * distance / 180
                val currentAngle = kotlin.math.atan(wy / wx)
                wx = wx * kotlin.math.cos(currentAngle - angle) / kotlin.math.cos(currentAngle)
                wy = wy * kotlin.math.sin(currentAngle - angle) / kotlin.math.sin(currentAngle)
            }
            'L' -> {
                val angle = kotlin.math.PI * distance / 180
                val currentAngle = kotlin.math.atan(wy / wx)
                wx = wx * kotlin.math.cos(angle + currentAngle) / kotlin.math.cos(currentAngle)
                wy = wy * kotlin.math.sin(angle + currentAngle) / kotlin.math.sin(currentAngle)
            }
            'N' -> wy += distance
            'S' -> wy -= distance
            'E' -> wx += distance
            'W' -> wx -= distance
            'F' -> {
                sx += wx * distance
                sy += wy * distance
            }
            else -> {}
        }
        println("$line $wx $wy $sx $sy")
    }

    val result = kotlin.math.abs(sx) + kotlin.math.abs(sy)
    println("MANHATTAN DISTANCE $result")
}