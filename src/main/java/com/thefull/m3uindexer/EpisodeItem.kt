package com.thefull.m3uindexer

data class EpisodeItem(
    val serie: String,
    val serieNorm: String,
    val season: Int,
    val episode: Int,
    val title: String,
    val category: String,
    val logo: String,
    val url: String
)
