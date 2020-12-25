package all.news.ui.main

import all.news.R
import all.news.adapter.DataAdapter
import all.news.adapter.DataAdapter2
import all.news.cache.MyCache
import all.news.dialogs.ChangeCountryDialog
import all.news.dialogs.SortByCategoryDialog
import all.news.models.Article
import all.news.models.NewsData
import all.news.network.Client
import all.news.network.NetworkService
import all.news.ui.news.NewsActivity
import all.news.utils.OnNewsSelected
import all.news.utils.apiKey
import all.news.utils.onNewsSelected
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), MainContract.View {

    private var presenter = Presenter(this)
    private var toggle: ActionBarDrawerToggle? = null
    private val adapter: DataAdapter = DataAdapter()
    private var total: Int? = null
    private var category: String? = null


    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        toggle = ActionBarDrawerToggle(
            this, drawerLayout,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle!!)
        toggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        navigation_view.setNavigationItemSelectedListener { p0 ->
            if (p0.itemId == R.id.category) {
                val dialog = SortByCategoryDialog(this, category)
                dialog.setTitle("Choose category")
                dialog.onChangeClicked = object : SortByCategoryDialog.OnChangeClicked {
                    override fun onChange(category: String?) {
                        adapter.data.clear()
                        total = 0
                        this@MainActivity.category = category
                        presenter.loadData(10, MyCache.getCache().get()!!, category)
                        adapter.notifyDataSetChanged()
                    }
                }
                dialog.show()
            }
            if (p0.itemId == R.id.change_country) {
                val dialog = ChangeCountryDialog(this)
                dialog.setTitle("Choose country")
                dialog.onChangeClicked = object : ChangeCountryDialog.OnChangeClicked {
                    override fun onChange(code: String) {
                        adapter.data.clear()
                        total = 0
                        presenter.loadData(10, MyCache.getCache().get()!!, category)
                        adapter.notifyDataSetChanged()
                    }
                }
                dialog.show()
            }
            drawerLayout.closeDrawer(Gravity.LEFT)
            false
        }
        presenter.loadData(10, MyCache.getCache().get()!!, category)
        list.adapter = adapter
        adapter.onPageEnded = object : DataAdapter.OnPageEnded {
            override fun onPageEnded(size: Int) {
                if (size + 10 <= total!!) {
                    presenter.loadData(size + 10, MyCache.getCache().get()!!, category)
                } else {
                    presenter.loadData(total!!, MyCache.getCache().get()!!, category)
                }
            }
        }
        onNewsSelected = object : OnNewsSelected {
            override fun onNewsSelected(data: Article) {
                val intent = Intent(this@MainActivity, NewsActivity::class.java)
                intent.putExtra("url", data.url)
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle!!.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getData(data: NewsData) {
        adapter.data = data.articles
        total = data.totalResults.toInt()
    }

    override fun getError(e: String) {
        Toast.makeText(this, e, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val manager =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val search: SearchView = menu!!.findItem(R.id.searchV1).actionView as SearchView

        search.setSearchableInfo(manager.getSearchableInfo(componentName))

        search.setOnCloseListener {

            true
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                list.visibility = View.VISIBLE
                list2.visibility = View.INVISIBLE
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {

                if (query != null) run {
                    list.visibility = View.GONE
                    list2.visibility = View.VISIBLE
                    val client = NetworkService.getRetrofit().create(Client::class.java)
                    client.getSearchingData(query, 10, apiKey).enqueue(object : Callback<NewsData> {
                        override fun onFailure(call: Call<NewsData>, t: Throwable) {
                            getError(t.localizedMessage!!)
                        }

                        override fun onResponse(
                            call: Call<NewsData>,
                            response: Response<NewsData>
                        ) {
                            if (response.isSuccessful && response.body()!!.articles != null) {
                                val adapter = DataAdapter2()
                                list2.adapter = adapter
                                adapter.data = response.body()!!.articles
                            }
                        }
                    })
                }
                if (query == "") {
                    list.visibility = View.VISIBLE
                    list2.visibility = View.INVISIBLE
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


}