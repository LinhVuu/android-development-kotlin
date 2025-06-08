fun main() {
    val subject: String = "Mobile Development"
    val studentId: Int = 123456
    val numbers: IntArray = intArrayOf(1,2,3)
    val studentNames = arrayOf("Anh", "Linh", "Ford", "Mazda")
    println("$studentId")
    println("My first program in Kotlin")
    println("Hi $studentId, Welcome to $subject")
//    for i in numbers:
//        println(int)
    println("$numbers")
    println("$studentNames")

    for (x in numbers) {
        println(x)
    }

    for (x in studentNames) {
        print(x)
    }

}

main()