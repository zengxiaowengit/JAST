package org.qiuer

fun main(args: Array<String>) {
    println("Hello World!")
    listOf(1, 2, 3).map {
        it * 3
    }.forEach {
        println(it)
    }

    var str = """World？
        | I'm Here.
    """.trimMargin().apply {
        println(this)
    }

    str = "Hello $str".apply {
        println(this)
    }
}