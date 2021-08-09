package mor.aliakbar.tavaloodshop.customview.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.databinding.ViewToolbarBinding


class AppToolbar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {


    var onBackButtonClickListener: View.OnClickListener? = null
        set(value) {
            field = value
            findViewById<ImageView>(R.id.backImageViewItemToolbar).setOnClickListener(
                onBackButtonClickListener
            )
        }

    init {
        ViewToolbarBinding.inflate(LayoutInflater.from(context), this, true)

        setupTitleTextView(context, attrs)
    }


    private fun setupTitleTextView(context: Context, attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val getCustomView =
                context.obtainStyledAttributes(attributeSet, R.styleable.TitleToolbar)

            val title = getCustomView.getString(R.styleable.TitleToolbar_toolbar_text)

            if (!title.isNullOrEmpty()) {
                findViewById<TextView>(R.id.toolbarTextViewItemToolbar).text = title
            }

            getCustomView.recycle()
        }
    }
}