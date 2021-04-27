package com.codingflower

import com.beust.klaxon.Klaxon
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole


open class JsonPerformanceListDeserialization : BenchmarkProperties() {
    private val jackson = jacksonObjectMapper()
    private val gson = Gson()
    private val kotlinx = Json

    private lateinit var text: String

    @Setup(Level.Trial)
    fun loadFile() {
        text = Resources.getSmallListJson()
    }

    @Benchmark
    fun jackson(bl: Blackhole) {
        val simpleJsons = jackson.readValue<List<SimpleJson>>(text)
        bl.consume(simpleJsons)
    }

    @Benchmark
    fun gson(bl: Blackhole) {
        val itemType = object : TypeToken<List<SimpleJson>>() {}.type
        val simpleJsons = gson.fromJson<List<SimpleJson>>(text, itemType)
        bl.consume(simpleJsons)
    }

    @Benchmark
    fun kotlinx(bl: Blackhole) {
        val simpleJsons = kotlinx.decodeFromString<List<SimpleJson>>(text)
        bl.consume(simpleJsons)
    }
    @Benchmark
    fun kotlinxSerializer(bl: Blackhole) {
        val simpleJson = kotlinx.decodeFromString(ListSerializer(SimpleJson.serializer()), text)
        bl.consume(simpleJson)
    }

    @Benchmark
    fun klaxon(bl: Blackhole) {
        val simpleJsons = Klaxon().parseArray<List<SimpleJson>>(text)
        bl.consume(simpleJsons)
    }
}