package com.codingflower

import com.beust.klaxon.Klaxon
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val moshiJsonAdapter = moshi.adapter(ComplexJson::class.java)

    private lateinit var complexText: String

    @Setup(Level.Trial)
    fun loadFile() {
        complexText = Resources.getComplexJson()
    }

    @Benchmark
    fun jacksonComplex(bl: Blackhole) {
        val simpleJson = jackson.readValue(complexText, ComplexJson::class.java)
        bl.consume(simpleJson)
    }

    @Benchmark
    fun gsonComplex(bl: Blackhole) {
        val simpleJson = gson.fromJson(complexText, ComplexJson::class.java)
        bl.consume(simpleJson)
    }

    @Benchmark
    fun kotlinxComplex(bl: Blackhole) {
        val simpleJson = kotlinx.decodeFromString<ComplexJson>(complexText)
        bl.consume(simpleJson)
    }

    @Benchmark
    fun klaxonComplex(bl: Blackhole) {
        val simpleJson = Klaxon().parse<ComplexJson>(complexText)
        bl.consume(simpleJson)
    }

    @Benchmark
    fun moshiComplex(bl: Blackhole) {
        val simpleJson = moshiJsonAdapter.fromJson(complexText)
        bl.consume(simpleJson)
    }
}