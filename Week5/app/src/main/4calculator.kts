//fun main(input_num1, input_num2, input_operator) {
fun main() {

    println("Enter number 1:")
    val input_num1 = readLine()
    println("Enter number 2:")
    val input_num2 = readLine()
    println("Enter operator:")
    val input_operator = readLine()
    var subject: String = "Mobile Development"
    val studentId: Int = 12345
    val ranNum: Double = 111111111.0
    val subjectCode: String = "TECH4300"
    val subjectFull: String = subjectCode + " " + subject

    println(ranNum + studentId)
    subject += subjectCode
    println(subjectFull)
    println(subject)
    if (ranNum > 600)
        println(true)
    else
        println(false)
    println(ranNum > 600)



}


//main(input_num1, input_num2, input_operator)
main()
//• Arithmetic: +, -, *, /, %
//• Assignment: =, a= (where a is an arithmetic operator)
//• Comparison: ==, !=, >, >=, <, <=
//• Logical: &&, ||, !
//• Increment/Decrement: ++, --
//• Elvis: ?:
//• Safe call: ?.
//• Range: ..