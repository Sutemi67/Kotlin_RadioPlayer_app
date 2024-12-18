package apc.appcradle.radioplayer.domain

import apc.appcradle.radioplayer.domain.models.Station

interface RepositoryInterface {
    fun getPlaylist(): List<Station>
}