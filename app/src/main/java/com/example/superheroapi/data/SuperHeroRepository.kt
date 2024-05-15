package com.example.superheroapi.data

import com.example.superheroapi.model.SuperHero
import com.example.superheroapi.model.SuperHeroSearch
import com.example.superheroapi.network.SuperHeroApiService

interface SuperHeroRepository {
    suspend fun getHero(apiKey: String, id: String): SuperHero
}

class SuperHeroNetworkRepo(
    private val superHeroApiService: SuperHeroApiService
) : SuperHeroRepository {
    override suspend fun getHero(apiKey: String, id: String): SuperHero {
        return superHeroApiService.getHero(apiKey, id)
    }
}
