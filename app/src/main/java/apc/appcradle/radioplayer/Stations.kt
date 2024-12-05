package apc.appcradle.radioplayer

data class Station(
    val name: String,
    val url: String
)

val playlist = listOf<Station>(
    Station("Soundpark Deep","https://azuracast.radiotoolkit.com/radio/8010/radio"),
    Station("Like FM","https://pub0301.101.ru:8443/stream/air/mp3/256/219")
)