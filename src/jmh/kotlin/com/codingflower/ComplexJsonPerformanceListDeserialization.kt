package com.codingflower

import argo.jdom.JdomParser
import com.beust.klaxon.Klaxon
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types.newParameterizedType
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.infra.Blackhole

open class ComplexJsonPerformanceListDeserialization : BenchmarkProperties() {
    private val jackson = jacksonObjectMapper()
    private val gson = Gson()
    private val kotlinx = Json
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val moshiJsonAdapter = moshi.adapter<List<ComplexJson>>(
        newParameterizedType(
            List::class.java,
            ComplexJson::class.java
        )
    )
    private val jdomParser = JdomParser()


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
    fun gsonComplex(bl: Blackhole) {
        val itemType = object : TypeToken<List<ComplexJson>>() {}.type
        val simpleJsons = gson.fromJson<List<ComplexJson>>(complexText, itemType)
        bl.consume(simpleJsons)
    }

    @Benchmark
    fun kotlinxComplex(bl: Blackhole) {
        val simpleJsons = kotlinx.decodeFromString<List<ComplexJson>>(complexText)
        bl.consume(simpleJsons)
    }

    @Benchmark
    fun klaxonComplex(bl: Blackhole) {
        val simpleJsons = Klaxon().parseArray<List<ComplexJson>>(complexText)
        bl.consume(simpleJsons)
    }

    @Benchmark
    fun moshiComplex(bl: Blackhole) {
        val simpleJsons = moshiJsonAdapter.fromJson(complexText)
        bl.consume(simpleJsons)
    }

    @Benchmark
    fun argoComplex(bl: Blackhole) {
        val simpleJsons = jdomParser.parse(complexText)
        bl.consume(simpleJsons)
    }
}