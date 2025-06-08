val findSumLm = {a: Int, b: Int, c: Int, d: Int -> a + b + c + d}
val findSubtractionLm: (Int, Int) -> Int = {a, b -> a - b}
val findMultiLm = {a: Int, b: Int -> a * b}
val findDivLm = {a: Int, b: Int -> a / b}

val a: Int = 1
val b: Int = 1
val c: Int = 1
val d: Int = 1

fun main(a: Int, b: Int) {

    println(findSumLm(a, b, c, d))
    println(findSubtractionLm(a, b))
    println(findMultiLm(a, b))
    println(findDivLm(a, b))
}

main(a,b)