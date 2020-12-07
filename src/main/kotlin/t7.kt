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

    val rules = mutableMapOf<String, Map<String, Int>>()
    for (row in data) {
        val split = row.split(",")
        val insides = mutableMapOf<String, Int>()
        val spaceSplit = split[0].split(" ")
        if (spaceSplit[4] != "no") {
            val name = "${spaceSplit[5]} ${spaceSplit[6]}"
            insides[name] = spaceSplit[4].toInt()
        }
        if (split.size > 1) {
            for (i in 1 until split.size) {
                val ruleSplit = split[i].split(" ")
                val ruleName = "${ruleSplit[2]} ${ruleSplit[3]}"
                insides[ruleName] = ruleSplit[1].toInt()
            }
        }
        val rowName = "${spaceSplit[0]} ${spaceSplit[1]}"
        rules[rowName] = insides
    }

    val counts = mutableMapOf<String, Int>()
    val containList = mutableMapOf<String, Boolean>()
    val result = findBagCount("shiny gold", rules, counts)
    for (row in rules.keys) {
        findIfContains("shiny gold", row, rules, containList)
    }
    var containCount = 0
    for (color in containList.keys) {
        if (containList[color] == true) {
            containCount += 1
        }
    }

    println("RESULT $result")
}

fun findIfContains(target: String, current: String, rules: Map<String, Map<String, Int>>, colors: MutableMap<String, Boolean>): Boolean {
    if (colors.containsKey(current)) {
        return colors[current] ?: error("not found counts $current")
    }
    if (rules.containsKey(current)) {
        val insides = rules[current] ?:error("not found rules $current")
        var contains = false
        for (rule in insides.keys) {
            contains = contains || rule == target
            contains = contains || findIfContains(target, rule, rules, colors)
        }
        colors[current] = contains
        return contains
    }
    error("not found counts rules $current")
}

fun findBagCount(name: String, rules: Map<String, Map<String, Int>>, counts: MutableMap<String, Int>): Int {
    if (counts.containsKey(name)) {
        return counts[name] ?: error("not found counts $name")
    }
    if (rules.containsKey(name)) {
        val insides = rules[name] ?:error("not found rules $name")
        var sum = 0
        for (rule in insides.keys) {
            sum += (insides[rule] ?: error("error cant happen")) * (findBagCount(rule, rules, counts) + 1)
        }
        counts[name] = sum
        return sum
    }
    error("not found counts rules $name")
}