package org.codventure.kinimom.framework.extension

import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment

/**
 * Created by abduaziz on 8/11/21 at 8:06 PM.
 */

fun Fragment.toast(s: String) {
    Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
}