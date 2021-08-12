package mor.aliakbar.tavaloodshop.utils

import android.app.Activity
import android.content.Intent
import android.view.View

object ActivityUtils {

    fun <T : Activity> Activity.startActivityWithoutExtra(clazz: Class<T>) {
        startActivity(Intent(this, clazz))
    }

    fun <T : Activity> Activity.startActivityWithIdExtra(clazz: Class<T>, id: Long) {
        startActivity(Intent(this, clazz).apply {
            putExtra(Variable.EXTRA_KEY_ID, id)
        })
    }


    fun Activity.backToPreviousPageListener() = View.OnClickListener {
        finish()
    }

    fun <T : Activity> Activity.backToAnyPageListener(clazz: Class<T>) = View.OnClickListener {
        startActivityWithoutExtra(clazz)
        finish()
    }

}