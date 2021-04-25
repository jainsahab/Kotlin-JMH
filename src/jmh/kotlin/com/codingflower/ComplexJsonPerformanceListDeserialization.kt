package com.codingflower

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.infra.Blackhole
import java.io.File

open class ComplexJsonPerformanceListDeserialization : BenchmarkProperties() {
    private val jackson = jacksonObjectMapper()
    private val gson = Gson()
    private val kotlinx = Json

    private lateinit var complexText: String

    @Setup(Level.Trial)
    fun loadFile() {
        complexText = Resources.getComplexListJson()
    }

    @Benchmark
    fun jackson(bl: Blackhole) {
        val simpleJsons = jackson.readValue<List<ComplexJson>>(complexText)
        bl.consume(simpleJsons)
    }

    @Benchmark
    fun gson(bl: Blackhole) {
        val itemType = object : TypeToken<List<ComplexJson>>() {}.type
        val simpleJsons = gson.fromJson<List<ComplexJson>>(complexText, itemType)
        bl.consume(simpleJsons)
    }

    @Benchmark
    fun kotlinx(bl: Blackhole) {
        val simpleJsons = kotlinx.decodeFromString<List<ComplexJson>>(complexText)
        bl.consume(simpleJsons)
    }
}