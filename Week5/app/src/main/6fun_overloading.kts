
val a: Int = 1
val b: Int = 1
val c: Int = 1
val d: Int = 1

fun add(a: Int, b: Int): Int {
    return a+b
}

fun add(a: Int, b: Int, c: Int): Int {
//    val result: Int = a+b+c
    return a+b+c
}

fun add(a: String, b: String): String {
    return a+b
}

println(add(a,b))
println(add(a,b,c))
val e: String = "a"
val f: String = "b"
println(add(a,b))
println(add(e,f))