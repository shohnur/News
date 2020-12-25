package all.news.utils

import all.news.models.Article

interface OnNewsSelected {
    fun onNewsSelected(data: Article)
}

var onNewsSelected: OnNewsSelected? = null

class CountryData(var name: String, var code: String)

const val apiKey = "4ac82706bcde43189cd17b2516a2ebbf"

const val baseUri = "http://newsapi.org/v2/"

val countryList = arrayListOf(
    CountryData("Argentina", "ar"),
    CountryData("Australia", "au"),
    CountryData("Belgium", "be"),
    CountryData("Canada", "ca"),
    CountryData("China", "cn"),
    CountryData("France", "fr"),
    CountryData("Russia", "ru"),
    CountryData("United States America", "us")
)

val categoryList = arrayListOf(
    "business",
    "entertainment",
    "general",
    "health",
    "science",
    "sports",
    "technology"
)