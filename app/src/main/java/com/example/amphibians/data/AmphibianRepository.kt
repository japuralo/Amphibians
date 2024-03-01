package com.example.amphibians.data

import com.example.amphibians.model.Amphibian
import com.example.amphibians.network.AmphibiansApiService

interface AmphibianRepository
{
    suspend fun getAmphibians(): List<Amphibian>
}

class DefaulAmphibiansRepository(
    private val amphibiansApiService: AmphibiansApiService
): AmphibianRepository
{
    override suspend fun getAmphibians(): List<Amphibian> =
        amphibiansApiService.getAmphibians()
}