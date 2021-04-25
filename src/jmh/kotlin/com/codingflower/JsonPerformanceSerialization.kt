package com.codingflower

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole


open class JsonPerformanceSerialization : BenchmarkProperties() {
    private val jackson = jacksonObjectMapper()
    private val gson = Gson()
    private val kotlinx = Json

    private lateinit var simpleJson: SimpleJson

    @Setup(Level.Trial)
    fun createSimpleObject() {
        simpleJson = SimpleJson("www.codingflower.com")
    }

    @Benchmark
    fun jackson(bl: Blackhole) {
        val text = jackson.writeValueAsString(simpleJson)
        bl.consume(text)
    }

    @Benchmark
    fun gson(bl: Blackhole) {
        val text = gson.toJson(simpleJson)
        bl.consume(text)
    }

    @Benchmark
    fun kotlin(bl: Blackhole) {
        val text = kotlinx.encodeToString(simpleJson)
        bl.consume(text)
    }
}
