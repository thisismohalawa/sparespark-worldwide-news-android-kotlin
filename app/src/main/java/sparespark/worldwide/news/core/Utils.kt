package sparespark.worldwide.news.core

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import java.util.*

var vibrantLightColorList = arrayOf(
    ColorDrawable(Color.parseColor("#ffeead")),
    ColorDrawable(Color.parseColor("#93cfb3")),
    ColorDrawable(Color.parseColor("#fd7a7a")),
    ColorDrawable(Color.parseColor("#faca5f")),
    ColorDrawable(Color.parseColor("#1ba798")),
    ColorDrawable(Color.parseColor("#6aa9ae")),
    ColorDrawable(Color.parseColor("#ffbf27")),
    ColorDrawable(Color.parseColor("#d93947"))
)

fun getRandomDrawableColor(): ColorDrawable {
    val idx = Random().nextInt(vibrantLightColorList.size)
    return vibrantLightColorList.get(idx)
}

fun isDeviceLanguageArabic(): Boolean {
    return Locale.getDefault().language == "ar"
}

fun getCountry(): String {
    val locale = Locale.getDefault()
    val country = locale.country.toString()
    return country.lowercase(Locale.getDefault())
}

const val TWO_SEC_DUR: Long = 2000
fun actionPostDelayed(action: (() -> Unit)? = null) {
    Handler(Looper.getMainLooper()).postDelayed({
        action?.let { it() }
    }, TWO_SEC_DUR)
}