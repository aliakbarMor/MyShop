package mor.aliakbar.tavaloodshop.base

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.view.auth.AuthActivity
import mor.aliakbar.tavaloodshop.services.exception.AppException
import mor.aliakbar.tavaloodshop.services.exception.ExceptionType
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

interface BaseView {

    val rootView: CoordinatorLayout?
    val viewContext: Context?

    fun showProgressbar(mustShow: Boolean) {
        rootView?.let { coordinatorLayout ->
            viewContext?.let { context ->
                var loadingView = coordinatorLayout.findViewById<View>(R.id.loadingView)
                if (loadingView == null && mustShow) {
                    loadingView = LayoutInflater.from(context)
                        .inflate(R.layout.view_loading, coordinatorLayout, false)
                    coordinatorLayout.addView(loadingView)
                }
                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showError(appException: AppException) {
        rootView?.let { coordinatorLayout ->
            viewContext?.let {
                when (appException.exceptionType) {
                    ExceptionType.SIMPLE -> showSnackBar(
                        appException.exceptionMessage
                            ?: it.getString(appException.resourceStringMessage)
                    )
                    ExceptionType.AUTH -> {
                        it.startActivity(Intent(it, AuthActivity::class.java))
                        Toast.makeText(it, appException.exceptionMessage, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                    }
                }
            }
        }
    }

    fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        rootView?.let {
            Snackbar.make(it, message, duration).show()
        }
    }

    fun showEmptyState(layoutResId: Int, rootViewId: Int): View? {
        rootView?.let {
            viewContext?.let { context ->
                var cartEmptyState = it.findViewById<View>(rootViewId)
                if (cartEmptyState == null) {
                    cartEmptyState = LayoutInflater.from(context).inflate(layoutResId, it, false)
                    it.addView(cartEmptyState)
                }
                cartEmptyState.visibility = View.VISIBLE
                return cartEmptyState
            }
        }
        return null
    }


}