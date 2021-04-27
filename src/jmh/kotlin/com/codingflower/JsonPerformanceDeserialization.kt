package com.codingflower

import com.beust.klaxon.Klaxon
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.infra.Blackhole


open class JsonPerformanceDeserialization : BenchmarkProperties() {
    private val jackson = jacksonObjectMapper()
    private val gson = Gson()
    private val kotlinx = Json

    private lateinit var simpleText: String

    @Setup(Level.Trial)
    fun loadFile() {
        simpleText = Resources.getSmallJson()
    }

    @Benchmark
    fun jackson(bl: Blackhole) {
        val simpleJson = jackson.readValue(simpleText, SimpleJson::class.java)
        bl.consume(simpleJson)
    }

    @Benchmark
    fun gson(bl: Blackhole) {
        val simpleJson = gson.fromJson(simpleText, SimpleJson::class.java)
        bl.consume(simpleJson)
    }

    @Benchmark
    fun kotlinx(bl: Blackhole) {
        val simpleJson = kotlinx.decodeFromString<SimpleJson>(simpleText)
        bl.consume(simpleJson)
    }

    @Benchmark
    fun kotlinxSerializer(bl: Blackhole) {
        val simpleJson = kotlinx.decodeFromString(SimpleJson.serializer(), simpleText)
        bl.consume(simpleJson)
    }

    @Benchmark
    fun klaxon(bl: Blackhole) {
        val simpleJson = Klaxon().parse<SimpleJson>(simpleText)
        bl.consume(simpleJson)
    }
}

