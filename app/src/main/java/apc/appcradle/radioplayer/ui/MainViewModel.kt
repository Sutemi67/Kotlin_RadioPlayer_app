package apc.appcradle.radioplayer.ui

import androidx.lifecycle.ViewModel
import apc.appcradle.radioplayer.domain.RepositoryInterface
import apc.appcradle.radioplayer.domain.models.Station

class MainViewModel(
    private val repository: RepositoryInterface
) : ViewModel() {

    fun getPlaylist(): List<Station> {
        return repository.getPlaylist()
    }

}
