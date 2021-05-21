package com.example.jpsubmission2.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 *  Toast extension function for activity
 *  To use it simply call showToast("hello")
 */
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

/**
 *  Toast extension function for fragment
 *  To use it simply call showToast("hello")
 */
fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

/**
 *  how to use in fragment.
 *   - Make sure you create an Id for the fragment then simply call binding.id.
 *  Eg. the Id is android:id="@+id/root_Layout"
 *   - Make sure you create string resource already in the res - values - strings.xml
 *  binding.rootlayout.snack(R.string.message)
 */
fun View.snack(@StringRes message: Int, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
}

/**
 * Set an onclick listener
 */
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener { block(it as T) }

/**
 * Extension method to set OnClickListener for long press on a view.
 */
fun <T : View> T.longClick(block: (T) -> Boolean) = setOnLongClickListener { block(it as T) }