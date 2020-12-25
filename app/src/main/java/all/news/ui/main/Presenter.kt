package all.news.ui.main

import all.news.models.NewsData
import all.news.network.Client
import all.news.network.NetworkService
import all.news.utils.apiKey
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Presenter(var view: MainContract.View) : MainContract.Presenter {

    private var client: Client? = null

    override fun loadData(pageSize: Int, country: String, category: String?) {
        client = NetworkService.getRetrofit().create(Client::class.java)
        val data = if (category == null) {
            client!!.getTops(country, pageSize, apiKey)
        } else {
            client!!.getTopsByCategory(country, pageSize, category, apiKey)
        }
        data.enqueue(object : Callback<NewsData> {
            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                view.getError(t.localizedMessage!!)
            }

            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {

                if (response.isSuccessful && response.body()!!.articles != null) {
                    view.getData(response.body()!!)
                } else {
                    Log.i("myLog", response.code().toString())
                }
            }
        })
    }


}