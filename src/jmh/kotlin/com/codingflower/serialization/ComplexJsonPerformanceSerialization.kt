package com.codingflower.serialization

import com.beust.klaxon.Klaxon
import com.codingflower.BenchmarkProperties
import com.codingflower.ComplexJson
import com.codingflower.Friend
import com.codingflower.Name
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole


open class ComplexJsonPerformanceSerialization : BenchmarkProperties() {
    private val jackson = jacksonObjectMapper()
    private val gson = Gson()
    private val kotlinx = Json
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val moshiJsonAdapter = moshi.adapter(ComplexJson::class.java)


    private lateinit var complexJson: ComplexJson

    @Setup(Level.Trial)
    fun createSimpleObject() {
        complexJson = ComplexJson(
            id = "ID",
            index = 0,
            guid = "guid",
            isActive = true,
            picture = "picture",
            age = 20,
            eyeColor = "Blue",
            name = Name("John", "Dee"),
            company = "company",
            tags = listOf("Kotlin", "JMH", "Performance", "Json"),
            range = listOf(2, 3, 4),
            friends = listOf(
                Friend(1, "John"),
                Friend(2, "Stefan"),
                Friend(3, "Garry"),
                Friend(4, "Tom"),
                Friend(5, "Kimi")
            ),
            rating = mapOf(
                1 to "One",
                2 to "Two",
                3 to "Three",
                4 to "Four",
                5 to "Five",
            )
        )
    }

    @Benchmark
    fun jacksonComplex(bl: Blackhole) {
        val text = jackson.writeValueAsString(complexJson)
        bl.consume(text)
    }

    @Benchmark
    fun gsonComplex(bl: Blackhole) {
        val text = gson.toJson(complexJson)
        bl.consume(text)
    }

    @Benchmark
    fun kotlinComplex(bl: Blackhole) {
        val text = kotlinx.encodeToString(complexJson)
        bl.consume(text)
    }

    @Benchmark
    fun klaxonComplex(bl: Blackhole) {
        val text = Klaxon().toJsonString(complexJson)
        bl.consume(text)
    }

    @Benchmark
    fun moshiComplex(bl: Blackhole) {
        val text = moshiJsonAdapter.toJson(complexJson)
        bl.consume(text)
    }
}