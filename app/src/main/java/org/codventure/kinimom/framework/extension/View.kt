package org.codventure.kinimom.framework.extension

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(s: String) {
    Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
}