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

    val memMap = mutableMapOf<ULong, ULong>()
    var mask = ""

    for (line in data) {
        val split = line.split(" ")
        if (split.first() == "mask") {
            mask = split.last()
        } else {
            val address = split.first().replace("mem[", "").replace("]", "").toULong()
            val number = split.last().toULong()

            var addressString = address.toString(2)
            val diff = mask.length - addressString.length
            if (diff > 0) {
                addressString = '0'.toString().repeat(diff) + addressString
            }
            var addressDecoder = ""
            for (i in (mask.length - 1) downTo 0) {
                val addressIndex = addressString.length - mask.length + i
                addressDecoder = when (mask[i]) {
                    '0' -> addressString[addressIndex] + addressDecoder
                    else -> mask[i] + addressDecoder
                }
            }

            val addresses = generateAddresses(addressDecoder, 0)
            for (address in addresses) {
                memMap[address] = number
            }
        }
    }

    var result = 0L
    for (value in memMap.values) {
        result += value.toLong()
    }
    println("RESULT $result")
}

fun generateAddresses(addressDecoder: String, index: Int): List<ULong> {
    val result = mutableListOf<ULong>()
    when {
        index >= addressDecoder.length -> {
            result.add(addressDecoder.toULong(2))
        }
        addressDecoder[index] == 'X' -> {
            val before = addressDecoder.substring(0, index)
            val after = addressDecoder.substring(index + 1)
            result.addAll(generateAddresses(before + "0" + after, index + 1))
            result.addAll(generateAddresses(before + "1" + after, index + 1))
        }
        else -> {
            result.addAll(generateAddresses(addressDecoder, index + 1))
        }
    }
    return result
}