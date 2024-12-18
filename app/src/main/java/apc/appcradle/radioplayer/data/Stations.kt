package apc.appcradle.radioplayer.data

data class Station(
    val name: String,
    val url: String
)

val playlist = listOf<Station>(
    Station("Soundpark Deep","https://azuracast.radiotoolkit.com/radio/8010/radio"),
    Station("Like FM","https://pub0301.101.ru:8443/stream/air/mp3/256/219"),
    Station("Цой","https://pub0302.101.ru:8443/stream/pro/aac/64/103"),
    Station("Relax Fm","https://pub0301.101.ru:8443/stream/air/mp3/256/200"),
    Station("Радио Energy","https://pub0301.101.ru:8443/stream/air/mp3/256/99"),
    Station("Наше Радио","https://nashe1.hostingradio.ru/nashe-256"),
    Station("Наше Панки Хой!","https://nashe1.hostingradio.ru/nashe-256"),
    Station("Юмор FM","https://pub0301.101.ru:8443/stream/air/mp3/256/102"),
    Station("Record DEEP","https://radiorecord.hostingradio.ru/deep96.aacp"),
    Station("Record LoFi House","https://radiorecord.hostingradio.ru/lofihouse96.aacp"),
    Station("Record Phonk","https://radiorecord.hostingradio.ru/phonk96.aacp"),
    Station("Record Rap Classics","https://radiorecord.hostingradio.ru/rapclassics96.aacp"),
    Station("Record Reggae","https://radiorecord.hostingradio.ru/reggae32.aacp"),
    Station("Record Rock","https://radiorecord.hostingradio.ru/rock96.aacp"),
    Station("Record Workout","https://radiorecord.hostingradio.ru/workout32.aacp"),
    Station("101 Modern Rock","https://srv01.gpmradio.ru/stream/pro/aac/64/16?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJrZXkiOiI0ZjFlMjhjNzliMzY5MTM0OTk2ZGMyYWY2ZmY0NWUxOCIsIklQIjoiMjEyLjMuMTMwLjExMiIsIlVBIjoiTW96aWxsYS81LjAgKE1hY2ludG9zaDsgSW50ZWwgTWFjIE9TIFggMTBfMTVfNykgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzEzMS4wLjAuMCBTYWZhcmkvNTM3LjM2IiwiUmVmIjoiaHR0cHM6Ly8xMDEucnUvIiwidWlkX2NoYW5uZWwiOiIxNiIsInR5cGVfY2hhbm5lbCI6ImNoYW5uZWwiLCJ0eXBlRGV2aWNlIjoiUEMiLCJCcm93c2VyIjoiQ2hyb21lIiwiQnJvd3NlclZlcnNpb24iOiIxMzEuMC4wLjAiLCJTeXN0ZW0iOiJNYWMgT1MgWCBQdW1hIiwiZXhwIjoxNzMzNDY2ODQ3fQ.F7fvxSg7bgV8ruI_XMvSTcS4eO6lEyQxQ9pAyKUNzrU"),
    Station("101 Irish Rock","https://srv12.gpmradio.ru:8443/stream/pro/aac/64/321?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJrZXkiOiI0ZjFlMjhjNzliMzY5MTM0OTk2ZGMyYWY2ZmY0NWUxOCIsIklQIjoiMjEyLjMuMTMwLjExMiIsIlVBIjoiTW96aWxsYS81LjAgKE1hY2ludG9zaDsgSW50ZWwgTWFjIE9TIFggMTBfMTVfNykgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzEzMS4wLjAuMCBTYWZhcmkvNTM3LjM2IiwiUmVmIjoiaHR0cHM6Ly8xMDEucnUvIiwidWlkX2NoYW5uZWwiOiIzMjEiLCJ0eXBlX2NoYW5uZWwiOiJjaGFubmVsIiwidHlwZURldmljZSI6IlBDIiwiQnJvd3NlciI6IkNocm9tZSIsIkJyb3dzZXJWZXJzaW9uIjoiMTMxLjAuMC4wIiwiU3lzdGVtIjoiTWFjIE9TIFggUHVtYSIsImV4cCI6MTczMzUxNDIwM30.tYNNZy_tUrGEJf8M5IddNh9HsUt_5SGqRkspEFf5ME0"),
    Station("101 Brit Pop","https://srv12.gpmradio.ru:8443/stream/pro/aac/64/206?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJrZXkiOiI0ZjFlMjhjNzliMzY5MTM0OTk2ZGMyYWY2ZmY0NWUxOCIsIklQIjoiMjEyLjMuMTMwLjExMiIsIlVBIjoiTW96aWxsYS81LjAgKE1hY2ludG9zaDsgSW50ZWwgTWFjIE9TIFggMTBfMTVfNykgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzEzMS4wLjAuMCBTYWZhcmkvNTM3LjM2IiwiUmVmIjoiaHR0cHM6Ly8xMDEucnUvIiwidWlkX2NoYW5uZWwiOiIyMDYiLCJ0eXBlX2NoYW5uZWwiOiJjaGFubmVsIiwidHlwZURldmljZSI6IlBDIiwiQnJvd3NlciI6IkNocm9tZSIsIkJyb3dzZXJWZXJzaW9uIjoiMTMxLjAuMC4wIiwiU3lzdGVtIjoiTWFjIE9TIFggUHVtYSIsImV4cCI6MTczMzUxNDMyNH0.ICtQETh-Y6tfTgqOh8J4e-cFguIgxhBeA57qSCKMWdw"),
    Station("Route 101","https://srv12.gpmradio.ru:8443/stream/pro/aac/64/185?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJrZXkiOiI0ZjFlMjhjNzliMzY5MTM0OTk2ZGMyYWY2ZmY0NWUxOCIsIklQIjoiMjEyLjMuMTMwLjExMiIsIlVBIjoiTW96aWxsYS81LjAgKE1hY2ludG9zaDsgSW50ZWwgTWFjIE9TIFggMTBfMTVfNykgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzEzMS4wLjAuMCBTYWZhcmkvNTM3LjM2IiwiUmVmIjoiaHR0cHM6Ly8xMDEucnUvIiwidWlkX2NoYW5uZWwiOiIxODUiLCJ0eXBlX2NoYW5uZWwiOiJjaGFubmVsIiwidHlwZURldmljZSI6IlBDIiwiQnJvd3NlciI6IkNocm9tZSIsIkJyb3dzZXJWZXJzaW9uIjoiMTMxLjAuMC4wIiwiU3lzdGVtIjoiTWFjIE9TIFggUHVtYSIsImV4cCI6MTczMzUxNDM4MH0.bcHz6rZUVAcGVL89fI4yPm6hTzbrEsoYpqx921d4OCA"),
    Station("Anime №1","https://relay.radionami.com/any-anime.ru"),
    Station("Anime ANISON","https://pool.anison.fm/AniSonFM(320)?nocache=0.18690744032766804"),
    Station("DFM","https://dfm.hostingradio.ru/dfm96.aacp")
)