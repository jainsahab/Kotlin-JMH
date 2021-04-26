package com.codingflower

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.infra.Blackhole

open class ComplexJsonPerformanceDeserialization : BenchmarkProperties() {
    private val jackson = jacksonObjectMapper()
    private val gson = Gson()
    private val kotlinx = Json

    private lateinit var complexText: String

    @Setup(Level.Trial)
    fun loadFile() {
        complexText = Resources.getComplexJson()
    }

    @Benchmark
    fun jackson(bl: Blackhole) {
        val simpleJson = jackson.readValue(complexText, ComplexJson::class.java)
        bl.consume(simpleJson)
    }

    @Benchmark
    fun gson(bl: Blackhole) {
        val simpleJson = gson.fromJson(complexText, ComplexJson::class.java)
        bl.consume(simpleJson)
    }

    @Benchmark
    fun kotlinx(bl: Blackhole) {
        val simpleJson = kotlinx.decodeFromString<ComplexJson>(complexText)
        bl.consume(simpleJson)
    }
}