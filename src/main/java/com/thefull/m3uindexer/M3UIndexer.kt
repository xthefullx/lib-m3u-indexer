package com.thefull.m3uindexer

import com.thefull.m3uparser.M3UParser
import java.net.URL

class M3UIndexer {

    fun indexFromUrl(
        url: String,
        dbWriter: IndexDatabaseWriter
    ): IndexResult {

        val content = URL(url).readText()

        val parser = M3UParser()
        val result = parser.parse(content)

        dbWriter.insertLive(result.live)

        return IndexResult(
            liveCount = result.live.size,
            movieCount = result.movies.size,
            seriesCount = result.series.size
        )
    }
}
