package com.thefull.m3uindexer

import com.thefull.m3uparser.M3UItem

data class SeriesItem(
    val serie: String,
    val serieNorm: String,
    val category: String,
    val poster: String
)

data class EpisodeMeta(
    val serieNorm: String,
    val season: Int,
    val episode: Int
)

data class SeriesIndexResult(
    val series: List<SeriesItem>,
    val seasons: List<SeriesSeason>,
    val episodes: List<EpisodeItem>,
    val episodesUrls: List<String>,
    val episodesMeta: List<EpisodeMeta>
)

class SeriesSeasonIndexer {

    fun index(list: List<M3UItem>): SeriesIndexResult {

        val parser = SeriesEpisodeParser()
        val parsed = parser.parse(list)

        val seriesMap = HashMap<String, SeriesItem>()
        val urls = mutableListOf<String>()
        val meta = mutableListOf<EpisodeMeta>()

        for (ep in parsed.episodes) {

            urls.add(ep.url)

            meta.add(
                EpisodeMeta(
                    serieNorm = ep.serieNorm,
                    season = ep.season,
                    episode = ep.episode
                )
            )

            if (!seriesMap.containsKey(ep.serieNorm)) {
                seriesMap[ep.serieNorm] = SeriesItem(
                    serie = ep.serie,
                    serieNorm = ep.serieNorm,
                    category = ep.category,
                    poster = ep.logo
                )
            }
        }

        return SeriesIndexResult(
            series = seriesMap.values.toList(),
            seasons = parsed.seriesSeasons,
            episodes = parsed.episodes,
            episodesUrls = urls,
            episodesMeta = meta
        )
    }
}
