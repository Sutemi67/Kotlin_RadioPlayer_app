package apc.appcradle.radioplayer.data

import apc.appcradle.radioplayer.domain.RepositoryInterface
import apc.appcradle.radioplayer.domain.models.Station

class Repository() : RepositoryInterface {

    override fun getPlaylist(): List<Station> = playlist

    private val playlist = listOf<Station>(
        Station("Like FM", "https://pub0301.101.ru:8443/stream/air/mp3/256/219"),
        Station("Relax Fm", "https://pub0301.101.ru:8443/stream/air/mp3/256/200"),
        Station("Радио Energy", "https://pub0301.101.ru:8443/stream/air/mp3/256/99"),
        Station("Наше Радио", "https://nashe1.hostingradio.ru/nashe-256"),
        Station("Наше Панки Хой!", "https://nashe1.hostingradio.ru/nashe-256"),
        Station("Юмор FM", "https://pub0301.101.ru:8443/stream/air/mp3/256/102"),
        Station("Deep Ambient", "https://srv01.gpmradio.ru/stream/trust/mp3/128/173?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJrZXkiOiI0ZjFlMjhjNzliMzY5MTM0OTk2ZGMyYWY2ZmY0NWUxOCIsIklQIjoiMjEyLjMuMTMwLjU0IiwiVUEiOiJNb3ppbGxhLzUuMCAoTWFjaW50b3NoOyBJbnRlbCBNYWMgT1MgWCAxMF8xNV83KSBBcHBsZVdlYktpdC81MzcuMzYgKEtIVE1MLCBsaWtlIEdlY2tvKSBDaHJvbWUvMTMxLjAuMC4wIFNhZmFyaS81MzcuMzYiLCJSZWYiOiJodHRwczovLzEwMS5ydS8iLCJ1aWRfY2hhbm5lbCI6IjE3MyIsInR5cGVfY2hhbm5lbCI6ImNoYW5uZWwiLCJ0eXBlRGV2aWNlIjoiUEMiLCJCcm93c2VyIjoiQ2hyb21lIiwiQnJvd3NlclZlcnNpb24iOiIxMzEuMC4wLjAiLCJTeXN0ZW0iOiJNYWMgT1MgWCBQdW1hIiwiZXhwIjoxNzM1MDI3NDQwfQ.OuY4D11Br-0gdq_8gIL3TSfDUzcIfu5crqVGpt_B4aQ"),

        Station(
            "Route 101",
            "https://srv12.gpmradio.ru:8443/stream/pro/aac/64/185?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJrZXkiOiI0ZjFlMjhjNzliMzY5MTM0OTk2ZGMyYWY2ZmY0NWUxOCIsIklQIjoiMjEyLjMuMTMwLjExMiIsIlVBIjoiTW96aWxsYS81LjAgKE1hY2ludG9zaDsgSW50ZWwgTWFjIE9TIFggMTBfMTVfNykgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzEzMS4wLjAuMCBTYWZhcmkvNTM3LjM2IiwiUmVmIjoiaHR0cHM6Ly8xMDEucnUvIiwidWlkX2NoYW5uZWwiOiIxODUiLCJ0eXBlX2NoYW5uZWwiOiJjaGFubmVsIiwidHlwZURldmljZSI6IlBDIiwiQnJvd3NlciI6IkNocm9tZSIsIkJyb3dzZXJWZXJzaW9uIjoiMTMxLjAuMC4wIiwiU3lzdGVtIjoiTWFjIE9TIFggUHVtYSIsImV4cCI6MTczMzUxNDM4MH0.bcHz6rZUVAcGVL89fI4yPm6hTzbrEsoYpqx921d4OCA"
        ),
        Station("Anime №1", "https://relay.radionami.com/any-anime.ru"),
        Station("Anime ANISON", "https://pool.anison.fm/AniSonFM(320)?nocache=0.18690744032766804"),
        Station("DFM", "https://dfm.hostingradio.ru/dfm96.aacp")
    )
}