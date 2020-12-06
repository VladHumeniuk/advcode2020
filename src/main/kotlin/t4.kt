fun main()  {
    var end = false
    val data = mutableListOf<String>()
    var current = ""
    var blank = false
    while(!end) {
        val line = readLine()
        val nonNull = line ?: ""
        if (nonNull.isBlank()) {
            if (blank) {
                end = true
            } else {
                data.add(current)
                current = ""
            }
            blank = true
        } else {
            blank = false
            if (current.length > 0) current += " "
            current += nonNull.replace('\n',  ' ')
        }
    }

    val fields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid")

    var count = 0
    for (entry in data) {
        val present = mutableSetOf<String>()
        val split = entry.split(" ")
        for (segment in split) {
            val substring = segment.substring(0, 3)
            val name = segment.substring(0, 3)
            val value = segment.substring(4)
            if (segment.length > 4 && segment[3] == ':' && fields.contains(substring) && validate(name, value)) {
                present += substring
            }
        }
        val hasCid = present.contains("cid")
        val diff = fields.size - present.size
        if (diff == 0 || (diff == 1 && !hasCid) ) {
            count += 1
        }
    }
    println("RESULT $count")
}

fun validate(tag: String, value: String): Boolean {
    val eyeColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    val cm = "cm"
    val inch = "in"
    return when (tag) {
        "byr" -> {
            try {
                val int = value.toInt()
                int in 1920..2002
            } catch (e: Exception) {
                false
            }
        }
        "iyr" -> {
            try {
                val int = value.toInt()
                int in 2010..2020
            } catch (e: Exception) {
                false
            }
        }
        "eyr" -> {
            try {
                val int = value.toInt()
                int in 2020..2030
            } catch (e: Exception) {
                false
            }
        }
        "hgt" -> {
            try {
                val length = value.length
                val unit = value.substring(length - 2)
                val num = value.substring(0, length - 2)
                val long = num.toLong()
                when (unit) {
                    cm -> {
                        long in 150..193
                    }
                    inch -> {
                        long in 59..76
                    }
                    else -> {
                        false
                    }
                }
            } catch (e: Exception) {
                false
            }
        }
        "hcl" -> {
            var valid = value.startsWith("#")
            val substr = value.replace("#", "")
            valid = valid && substr.length == 6 && substr.toLowerCase().none { it !in 'a'..'f' && it !in '0'..'9'  }
            valid
        }
        "ecl" -> {
            eyeColors.contains(value)
        }
        "pid" -> {
            try {
                val long = value.toLong()
                value.length == 9
            } catch (e : Exception) {
                false
            }
        }
        "cid" -> {
            true
        }
        else -> false
    }
}