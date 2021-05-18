package com.codingflower.serialization

import com.beust.klaxon.Klaxon
import com.codingflower.BenchmarkProperties
import com.codingflower.SimpleJson
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole


open class JsonPerformanceListSerialization : BenchmarkProperties() {
    private val jackson = jacksonObjectMapper()
    private val gson = Gson()
    private val kotlinx = Json
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val moshiJsonAdapter = moshi.adapter<List<SimpleJson>>(
        Types.newParameterizedType(
            List::class.java,
            SimpleJson::class.java
        )
    )


    private val simpleJsons: List<SimpleJson> = (1..1000).map { SimpleJson("www.codingflower$it.com") }.toList()

    @Benchmark
    fun jackson(bl: Blackhole) {
        val text = jackson.writeValueAsString(simpleJsons)
        bl.consume(text)
    }

    @Benchmark
    fun gson(bl: Blackhole) {
        val text = gson.toJson(simpleJsons)
        bl.consume(text)
    }

    @Benchmark
    fun kotlinx(bl: Blackhole) {
        val text = kotlinx.encodeToString(simpleJsons)
        bl.consume(text)
    }

    @Benchmark
    fun klaxon(bl: Blackhole) {
        val text = Klaxon().toJsonString(simpleJsons)
        bl.consume(text)
    }

    @Benchmark
    fun moshi(bl: Blackhole) {
        val text = moshiJsonAdapter.toJson(simpleJsons)
        bl.consume(text)
    }
}