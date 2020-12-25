package all.news.cache

import android.content.Context
import android.content.SharedPreferences

class MyCache private constructor(context: Context) {

    companion object {
        private var cache: MyCache? = null
        private var preferences: SharedPreferences? = null


        fun init(context: Context) {
            if (cache == null)
                cache = MyCache(context)
        }

        fun getCache(): MyCache = cache!!
    }

    init {
        preferences = context.getSharedPreferences("cache", Context.MODE_PRIVATE)
    }

    fun save(s: String) {
        preferences!!.edit().putString("code", s).apply()
    }

    fun get(): String? = preferences!!.getString("code", null)
}