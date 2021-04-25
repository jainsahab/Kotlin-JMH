package com.codingflower

import kotlinx.serialization.Serializable

@Serializable
data class SimpleJson(
    val website: String
)

@Serializable
data class ComplexJson(
    val id: String,
    val index: Int,
    val guid: String,
    val isActive: Boolean,
    val picture: String,
    val age: Int,
    val eyeColor: String,
    val name: Name,
    val company: String,
    val tags: List<String>,
    val range: List<Int>,
    val friends: List<Friend>,
    val rating: Map<Int, String>
)

@Serializable
data class Friend(
    val id: Int,
    val name: String
)

@Serializable
data class Name(
    val first: String,
    val last: String
)