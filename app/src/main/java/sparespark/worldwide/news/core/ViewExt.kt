package sparespark.worldwide.news.core

import android.util.Patterns
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout
import sparespark.worldwide.news.R


fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.preventDoubleClick() {
    this.isEnabled = false
    this.postDelayed({ this.isEnabled = true }, 1000)
}

fun TextView.setArabicFont() {
    val typeface = ResourcesCompat.getFont(context, R.font.font_ar)
    this.typeface = typeface
}

fun TabLayout.updateColor(color: Int) {
    this.setBackgroundColor(ContextCompat.getColor(context, color))
}

fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()
