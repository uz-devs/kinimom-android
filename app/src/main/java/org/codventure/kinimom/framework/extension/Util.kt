package org.codventure.kinimom.framework.extension

/**
 * Created by abduaziz on 8/20/21 at 4:12 PM.
 */


fun String.removeChars(): String{
    var res = ""
    for (c in this)
        if (!c.isDigit())
            res += c
    return res
}