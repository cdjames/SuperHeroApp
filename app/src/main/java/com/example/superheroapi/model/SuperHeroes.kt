package com.example.superheroapi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuperHero(
    val response: String? = null,
    val id: String,
    val name: String,
    @SerialName("powerstats")
    val powerStats: PowerStats? = null,
    val biography: Biography? = null,
    val appearance: Appearance? = null,
    val work: Work? = null,
    val connections: Connections? = null,
    val image: Image
)

@Serializable
data class SuperHeroSearch(
    val response: String,
    @SerialName("results-for")
    val resultsFor: String,
    val results: List<SuperHero>
)

@Serializable
data class PowerStats(
    val intelligence: String,
    val strength: String,
    val speed: String,
    val aliases: List<String>,
    val durability: String,
    val power: String,
    val combat: String,
)

@Serializable
data class Biography(
    @SerialName("full-name")
    val fullName: String,
    @SerialName("alter-egos")
    val alterEgos: String,
    val aliases: List<String>,
    @SerialName("place-of-birth")
    val placeOfBirth: String,
    @SerialName("first-appearance")
    val firstAppearance: String,
    val publisher: String,
    val alignment: String,
)

@Serializable
data class Appearance(
    val gender: String,
    val race: String,
    val height: List<String>,
    val weight: List<String>,
    val eyeColor: String,
    val hairColor: String,
)

@Serializable
data class Work(
    val occupation: String,
    val base: String
)

@Serializable
data class Connections(
    val groupAffiliation: String,
    val relatives: String
)

@Serializable
data class Image(val url: String)