fun main() {
    var end = false
    val rulesData = mutableListOf<String>()
    val data = mutableListOf<String>()
    while (!end) {
        val input = readLine()
        val nonNull = input ?: ""
        if (nonNull.isBlank()) {
            end = true
        } else {
            rulesData += nonNull
        }
    }
    end = false
    while (!end) {
        val input = readLine()
        val nonNull = input ?: ""
        if (nonNull.isBlank()) {
            end = true
        } else {
            data += nonNull
        }
    }

    val rules = mutableMapOf<Int, MessageRule>()
    for (ruleString in rulesData) {
        val split = ruleString.split(" ")
        val number = split.first().substring(0, split.first().length - 1).toInt()
        var complete = false
        var options = mutableListOf<String>()
        var regulations = mutableListOf<List<Int>>()
        var currentRegulatnios = mutableListOf<Int>()
        for (segment in split) {
            val num = segment.toIntOrNull()
            when {
                num != null -> currentRegulatnios.add(num)
                segment == "|" -> {
                    regulations.add(currentRegulatnios)
                    currentRegulatnios = mutableListOf()
                }
                segment.startsWith("\"") -> {
                    complete = true
                    options.add(segment.substring(1, segment.length - 1))
                }
                else -> {}
            }
        }
        if (currentRegulatnios.isNotEmpty()) regulations.add(currentRegulatnios)
        rules[number] = MessageRule(number, true, options, regulations, complete)
    }

    rules[8] = MessageRule(8, false, mutableListOf(), mutableListOf(listOf(42), listOf(42, 8)), false)
    rules[11] = MessageRule(11, false, mutableListOf(), mutableListOf(listOf(42, 31), listOf(42, 11, 31)), false)

    val options42 = initRule(rules, 42)
    val options31 = initRule(rules, 31)

    var count8 = 0
    var count11 = 0
    for (string in data) {
        val after8 = match8(string, options42)
        for (opt8 in after8) {
            val after11 = match11(opt8, options42, options31)
            for (s in after11) {
                if (s.isEmpty()) count11++
            }
        }
        if (after8.isNotEmpty()) {
            count8 += after8.size
        }
    }

    println("RESULT  $count11")
}

private fun match8(string: String, options42: List<String>): List<String> {
    val options = mutableListOf<String>()
    var matched = true
    var substrings = mutableListOf(string)
    while (matched == true) {
        matched = false
        val newSubstrings = mutableListOf<String>()
        for (opt42 in options42) {
            for (substring in substrings) {
                if (substring.startsWith(opt42)) {
                    matched = true
                    newSubstrings.add(substring.removePrefix(opt42))
                }
            }
        }
        options.addAll(newSubstrings)
        substrings = newSubstrings
    }
    return options
}

private fun match11(string: String, options42: List<String>, options31: List<String>): List<String> {
    val options = mutableListOf<String>()
    var matched = true
    var substrings = mutableListOf(string)
    while (matched == true) {
        matched = false
        val newSubstrings = mutableListOf<String>()
        for (opt42 in options42) {
            for (opt31 in options31) {
                for (substring in substrings) {
                    if (substring.startsWith(opt42) && substring.endsWith(opt31)) {
                        matched = true
                        newSubstrings.add(substring.removePrefix(opt42).removeSuffix(opt31))
                    }
                }
            }
        }
        options.addAll(newSubstrings)
        substrings = newSubstrings
    }
    return options
}

private fun initRule(rules: MutableMap<Int, MessageRule>, key: Int): MutableList<String> {
    val rule = rules[key]!!
    if (rule.complete) {
        return rule.options
    } else {
        val options = mutableListOf<String>()
        for (regulation in rule.regulations) {
            var regulationOptions = mutableListOf("")
            for (subRule in regulation) {
                val regOptions = initRule(rules, subRule)
                val newOptions = mutableListOf<String>()
                for (option in regulationOptions) {
                    for (regOption in regOptions) {
                        newOptions.add(option + regOption)
                    }
                }
                regulationOptions = newOptions
            }
            options.addAll(regulationOptions)
        }
        rule.complete = true
        rule.options = options
        rules[key] = rule
        return options
    }
}

private class MessageRule(
        val number: Int,
        val finite: Boolean,
        var options: MutableList<String>,
        val regulations: List<List<Int>>,
        var complete: Boolean
) {
}