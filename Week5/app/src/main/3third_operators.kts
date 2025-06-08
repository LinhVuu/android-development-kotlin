fun main() {
    var subject: String = "Mobile Development"
    val studentId: Int = 123456
    val ranNum: Int = 500
    val subjectCode: String = "TECH4300"
    val subjectFull: String = subjectCode + " " + subject

    subject += subjectCode
    println(subjectFull)
    println(subject)
    if (ranNum > 600)
        println(true)
    else
        println(false)
    println(ranNum > 600)

}

main()

//• Arithmetic: +, -, *, /, %
//• Assignment: =, a= (where a is an arithmetic operator)
//• Comparison: ==, !=, >, >=, <, <=
//• Logical: &&, ||, !
//• Increment/Decrement: ++, --
//• Elvis: ?:
//• Safe call: ?.
//• Range: ..