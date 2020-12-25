package all.news.dialogs

import all.news.R
import all.news.utils.categoryList
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.change_country_dialog.view.*

class SortByCategoryDialog(context: Context, private var currentCategory: String?) :
    AlertDialog(context) {
    private var categories = arrayListOf<String>()

    init {
        if (currentCategory == null) {
            categories = categoryList
        } else {
            categories.add(currentCategory!!)
            for (i in categoryList.indices) {
                if (currentCategory != categoryList[i])
                    categories.add(categoryList[i])
            }
        }

        val view = LayoutInflater.from(context).inflate(R.layout.change_country_dialog, null, false)
        view.country.setItems(categories)
        view.country.setOnItemSelectedListener { _, _, _, item ->
            currentCategory = item.toString()
        }

        setButton(BUTTON_POSITIVE, "Change") { dialog, _ ->

            onChangeClicked?.onChange(currentCategory)

            dialog.cancel()
        }
        setButton(BUTTON_NEGATIVE, "Cancel") { dialog, _ ->
            dialog.cancel()
        }

        setCancelable(false)
        setView(view)

    }

    interface OnChangeClicked {
        fun onChange(category: String?)
    }

    var onChangeClicked: OnChangeClicked? = null
}