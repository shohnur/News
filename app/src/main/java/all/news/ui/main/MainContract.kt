package all.news.ui.main

import all.news.models.NewsData

interface MainContract {
    interface View {
        fun getData(data: NewsData)
        fun getError(e: String)
    }

    interface Presenter {
        fun loadData(pageSize: Int, country: String, category: String?)
    }
}