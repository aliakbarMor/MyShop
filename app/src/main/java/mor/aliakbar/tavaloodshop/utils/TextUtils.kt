package mor.aliakbar.tavaloodshop.utils

import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.Patterns

object TextUtils {

    fun formatPrice(price: Number, unitRelativeSizeFactor: Float = 0.7f): SpannableString {
        val currencyLabel = "تومان"
        val spannableString = SpannableString("$price $currencyLabel")
        spannableString.setSpan(
            RelativeSizeSpan(unitRelativeSizeFactor),
            spannableString.indexOf(currencyLabel),
            spannableString.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }

    fun convertEnglishNumberToPersianNumber(numbers: String): String {
        var faNumbers = numbers
        val mChars = arrayOf(
            arrayOf("0", "۰"),
            arrayOf("1", "۱"),
            arrayOf("2", "۲"),
            arrayOf("3", "۳"),
            arrayOf("4", "۴"),
            arrayOf("5", "۵"),
            arrayOf("6", "۶"),
            arrayOf("7", "۷"),
            arrayOf("8", "۸"),
            arrayOf("9", "۹")
        )
        for (num in mChars) {
            faNumbers = faNumbers.replace(num[0], num[1])
        }
        return faNumbers
    }

    fun CharSequence.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun CharSequence.validationIranianPhoneNumber(): Boolean {
        val phoneNumberPattern =
            Regex("(0|\\+98)?([ ]|,|-|[()]){0,2}9[1|2|3|4]([ ]|,|-|[()]){0,2}(?:[0-9]([ ]|,|-|[()]){0,2}){8}")
        return (this.isNotEmpty() && this.matches(phoneNumberPattern))
    }


}