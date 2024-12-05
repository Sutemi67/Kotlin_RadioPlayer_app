package apc.appcradle.radioplayer

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
    Station("Alternative metal","http://galnet.ru:8000/hard"),
    Station("101 Modern Rock","https://srv01.gpmradio.ru/stream/pro/aac/64/16?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJrZXkiOiI0ZjFlMjhjNzliMzY5MTM0OTk2ZGMyYWY2ZmY0NWUxOCIsIklQIjoiMjEyLjMuMTMwLjExMiIsIlVBIjoiTW96aWxsYS81LjAgKE1hY2ludG9zaDsgSW50ZWwgTWFjIE9TIFggMTBfMTVfNykgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzEzMS4wLjAuMCBTYWZhcmkvNTM3LjM2IiwiUmVmIjoiaHR0cHM6Ly8xMDEucnUvIiwidWlkX2NoYW5uZWwiOiIxNiIsInR5cGVfY2hhbm5lbCI6ImNoYW5uZWwiLCJ0eXBlRGV2aWNlIjoiUEMiLCJCcm93c2VyIjoiQ2hyb21lIiwiQnJvd3NlclZlcnNpb24iOiIxMzEuMC4wLjAiLCJTeXN0ZW0iOiJNYWMgT1MgWCBQdW1hIiwiZXhwIjoxNzMzNDY2ODQ3fQ.F7fvxSg7bgV8ruI_XMvSTcS4eO6lEyQxQ9pAyKUNzrU"),
    Station("Anime №1","https://relay.radionami.com/any-anime.ru"),
    Station("Anime ANISON","https://pool.anison.fm/AniSonFM(320)?nocache=0.18690744032766804"),
    Station("DFM","https://dfm.hostingradio.ru/dfm96.aacp")
)