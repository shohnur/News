package all.news.dialogs

import all.news.R
import all.news.cache.MyCache
import all.news.utils.countryList
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.change_country_dialog.view.*

class ChangeCountryDialog(context: Context) : AlertDialog(context) {

    private var country: String? = null
    private var countries: ArrayList<String> = arrayListOf()

    init {

        for (i in countryList.indices) {
            if (MyCache.getCache().get() == countryList[i].code) {
                country = countryList[i].name
                countries.add(country!!)
                break
            }
        }
        for (i in countryList.indices) {
            if (countryList[i].name != country) {
                countries.add(countryList[i].name)
            }
        }

        val view = LayoutInflater.from(context).inflate(R.layout.change_country_dialog, null, false)
        view.country.setItems(countries)
        view.country.setOnItemSelectedListener { _, _, _, item ->
            country = item.toString()
        }

        setButton(BUTTON_POSITIVE, "Change") { dialog, _ ->
            for (i in countryList.indices) {
                if (country == countryList[i].name) {
                    MyCache.getCache().save(countryList[i].code)
                    onChangeClicked?.onChange(countryList[i].code)
                    break
                }
            }

            dialog.cancel()
        }
        setButton(BUTTON_NEGATIVE, "Cancel") { dialog, _ ->
            dialog.cancel()
        }

        setCancelable(false)
        setView(view)
    }

    interface OnChangeClicked {
        fun onChange(code: String)
    }

    var onChangeClicked: OnChangeClicked? = null

}
