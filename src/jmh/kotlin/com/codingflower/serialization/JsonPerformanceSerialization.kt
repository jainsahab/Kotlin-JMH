package com.codingflower.serialization

import com.beust.klaxon.Klaxon
import com.codingflower.BenchmarkProperties
import com.codingflower.SimpleJson
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole


open class JsonPerformanceSerialization : BenchmarkProperties() {
    private val jackson = jacksonObjectMapper()
    private val gson = Gson()
    private val kotlinx = Json
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val moshiJsonAdapter = moshi.adapter(SimpleJson::class.java)


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
    fun kotlinx(bl: Blackhole) {
        val text = kotlinx.encodeToString(simpleJson)
        bl.consume(text)
    }

    @Benchmark
    fun klaxon(bl: Blackhole) {
        val text = Klaxon().toJsonString(simpleJson)
        bl.consume(text)
    }

    @Benchmark
    fun moshi(bl: Blackhole) {
        val text = moshiJsonAdapter.toJson(simpleJson)
        bl.consume(text)
    }
}
