package com.codingflower

object Resources {

    fun getSmallJson() = readFile("small.json")
    fun getComplexJson() = readFile("complex.json")
    fun getSmallListJson() = readFile("small_list.json")
    fun getComplexListJson() = readFile("complex_list.json")

    private fun readFile(fileName: String): String {
        return this.javaClass.classLoader.getResourceAsStream(fileName).bufferedReader().use { it.readText() }
    }

}