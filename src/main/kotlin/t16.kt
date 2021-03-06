fun main() {
    var end = false
    val data = mutableListOf<String>()
    while (!end) {
        val input = readLine()
        val nonNull = input ?: ""
        if (nonNull.isBlank() && data.last().isBlank()) {
            end = true
        } else {
            data += nonNull
        }
    }

    val rules = mutableListOf<Rule>()
    var state = ParseState.RULES
    var yourTicket: List<Int> = listOf()
    val tickets = mutableListOf<List<Int>>()

    for (line in data) {
        if (state != ParseState.END && line.isBlank()) {
            state = ParseState.values()[state.ordinal + 1]
            continue
        }
        when (state) {
            ParseState.RULES -> rules.add(parseRule(line))
            ParseState.YOUR_TICKET -> yourTicket = parseTicket(line)
            ParseState.NEARBY_TICKETS -> {
                val parsed = parseTicket(line)
                if (parsed.isNotEmpty()) tickets.add(parsed)
            }
            else -> {}
        }
    }

    val failedNumbers = mutableListOf<Int>()
    val validTickets = mutableListOf<List<Int>>()
    for (ticket in tickets) {
        var valid = true
        for (number in ticket) {
            var fits = false
            for (rule in rules) {
                fits = fits || rule.fits(number)
            }
            if (!fits) failedNumbers.add(number)
            valid = valid && fits
        }
        if (valid) validTickets.add(ticket)
    }


    val fieldMap = mutableListOf<MutableList<Rule>>()
    for (i in 0 until yourTicket.size) {
        val list = mutableListOf<Rule>()
        list.addAll(rules)
        fieldMap.add(list)
    }

    for (ticket in validTickets) {
        for (i in 0 until ticket.size) {
            val ticketRules = mutableListOf<Rule>()
            for (rule in fieldMap[i]!!) {
                if (rule.fits(ticket[i])) ticketRules.add(rule)
            }
            fieldMap[i] = ticketRules
        }
    }

    for (i in 0 until fieldMap.size) {
        for (fields in fieldMap) {
            if (fields.size == 1) {
                val exclusiveRule = fields.first()
                for (key in 0 until fieldMap.size) {
                    val ticketRules = fieldMap[key]
                    if (ticketRules.contains(exclusiveRule) && ticketRules.size > 1) {
                        ticketRules.remove(exclusiveRule)
                    }
                    fieldMap[key] = ticketRules
                }
            }
        }
    }

    var result = 1L
    for (i in 0 until fieldMap.size) {
        val rule = fieldMap[i].first()
        if (rule.name.contains("departure")) {
            result *= yourTicket[i]
        }
    }

    println("RESULT $result")
}

fun parseRule(line: String): Rule {
    var split = line.split(":")
    val name = split.first()
    split = split.last().split(" ")
    val ranges = mutableListOf<Range>()
    for (segment in split) {
        if (segment.contains("-")) {
            val nums = segment.split("-")
            ranges.add(Range(nums.first().toInt(), nums.last().toInt()))
        }
    }
    return Rule(name, ranges)
}

fun parseTicket(line: String): List<Int> {
    val numbers = mutableListOf<Int>()
    if (!line.contains("ticket")) {
        val split = line.split(",")
        for (segment in split) {
            numbers.add(segment.toInt())
        }
    }
    return numbers
}

class Range(val start: Int, val end: Int) {

    fun fits(number: Int): Boolean {
        return (start..end).contains(number)
    }
}

class Rule(val name: String, val ranges: List<Range>) {

    fun fits(number: Int): Boolean {
        var fits = false
        for (range in ranges) {
            fits = fits || range.fits(number)
        }
        return fits
    }

    override fun toString(): String {
        return name
    }
}

enum class ParseState {
    RULES,
    YOUR_TICKET,
    NEARBY_TICKETS,
    END
}