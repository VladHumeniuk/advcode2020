import java.util.*

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

    val expressions = mutableListOf<List<String>>()
    val results = mutableListOf<Long>()
    for (line in data) {
        val split = line.split(" ")
        val expression = mutableListOf<String>()
        for (segment in split) {
            expression.addAll(parseExpression(segment))
        }
        expressions.add(expression)
    }

    for (expression in expressions) {
        results.add(evaluateExpression(expression).toLong())
    }

    println("RESULT ${results.sum()}")
}

fun parseExpression(segment: String): List<String> {
    val expression = mutableListOf<String>()
    when {
        segment.startsWith("(") -> {
            expression.add("(")
            expression.addAll(parseExpression(segment.substring(1)))
        }
        segment.endsWith(")") -> {
            expression.addAll(parseExpression(segment.substring(0, segment.length - 1)))
            expression.add(")")
        }
        else -> expression.add(segment)
    }
    return expression
}

fun evaluateExpression(expression: List<String>): Long {
    val actions = listOf("+", "*")
    val numberStack = Stack<Long>()
    val actionStack = Stack<String>()
    var index = expression.size - 1
    while (index >= 0) {
        val segment = expression[index]
        val number = segment.toLongOrNull()
        when {
            number != null -> {
                numberStack.push(number)
                index--
                if (actionStack.size > 0 && actionStack.peek() == "+") {
                    actionStack.pop()
                    val one = numberStack.pop()
                    val two = numberStack.pop()
                    numberStack.push(one + two)
                }
            }
            actions.contains(segment) -> {
                actionStack.push(segment)
                index--
            }
            segment == ")" -> {
                var close = 1
                var open = 0
                var parIndex = index - 1
                while (close != open) {
                    when (expression[parIndex]) {
                        "(" -> open++
                        ")" -> close++
                        else -> {}
                    }
                    parIndex--
                }
                val subExpression = expression.subList(parIndex + 2, index)
                numberStack.push(evaluateExpression(subExpression))
                index = parIndex
                if (actionStack.size > 0 && actionStack.peek() == "+") {
                    actionStack.pop()
                    val one = numberStack.pop()
                    val two = numberStack.pop()
                    numberStack.push(one + two)
                }
            }
            else -> {}
        }
    }

    while (actionStack.size > 0) {
        val action = actionStack.pop()
        val one = numberStack.pop()
        val two = numberStack.pop()
        numberStack.push(if (action == "+") one + two else one * two)
    }
    return numberStack.pop()
}