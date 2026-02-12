package com.thefull.m3uindexer

import com.thefull.m3uparser.M3UItem
import java.text.Normalizer

class SeriesEpisodeParser {

    fun parseEpisodes(list: List<M3UItem>): List<EpisodeItem> {

        val out = mutableListOf<EpisodeItem>()

        for (item in list) {

            val (season, episode) = extractEpisode(item.name)
            if (season == 0 && episode == 0) continue

            val serie = guessSerieName(item.name)
            val serieNorm = normalizeSerieName(serie)

            val epTitle = "S" + season.toString().padStart(2,'0') +
                    "E" + episode.toString().padStart(2,'0')

            out.add(
                EpisodeItem(
                    serie = serie,
                    serieNorm = serieNorm,
                    season = season,
                    episode = episode,
                    title = epTitle,
                    category = item.group,
                    logo = item.logo,
                    url = item.url
                )
            )
        }

        return out
    }

    private fun extractEpisode(name: String): Pair<Int,Int> {

        val r1 = Regex("""S\s*(\d+)\s*E\s*(\d+)""", RegexOption.IGNORE_CASE)
        val r2 = Regex("""(\d+)\s*x\s*(\d+)""", RegexOption.IGNORE_CASE)

        r1.find(name)?.let {
            return it.groupValues[1].toInt() to it.groupValues[2].toInt()
        }

        r2.find(name)?.let {
            return it.groupValues[1].toInt() to it.groupValues[2].toInt()
        }

        return 0 to 0
    }

    private fun guessSerieName(name: String): String {
        var x = name
        x = x.replace(Regex("""S\d+\s*E\d+""", RegexOption.IGNORE_CASE), "")
        x = x.replace(Regex("""\d+\s*x\s*\d+""", RegexOption.IGNORE_CASE), "")
        x = x.replace(Regex("""\s+"""), " ")
        return x.trim()
    }

    private fun normalizeSerieName(s: String): String {
        var x = s.lowercase()
        x = Normalizer.normalize(x, Normalizer.Form.NFD)
            .replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
        x = x.replace(Regex("[^a-z0-9]+"), "_")
        return x.trim('_')
    }
}
