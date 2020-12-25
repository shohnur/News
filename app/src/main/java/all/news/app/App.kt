package all.news.app

import all.news.cache.MyCache
import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MyCache.init(this)

        if (MyCache.getCache().get() == null) {
            MyCache.getCache().save("ru")
        }
    }
}