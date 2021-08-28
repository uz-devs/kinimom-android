package org.codventure.kinimom.ui.authorization.survey

/**
 * Created by abduaziz on 8/15/21 at 4:21 PM.
 */
class SurveyResults(
    var is_pregnant: String = "",
    var expected_date: String = "",
    var nickname: String = "",
    var date_of_birth: String = "",
    var gender: String = "",
    var height: String = "",
    var weight: String = "",
    var weight_before: String = ""
) {
    // suka, I had to do this:
    fun isPageCompleted(selectedPage: Int): Boolean {
        return when (selectedPage) {
            0 -> is_pregnant.isNotBlank()
            1 -> expected_date.isNotBlank()
            2 -> nickname.isNotBlank() && date_of_birth.isNotBlank() && gender.isNotBlank()
            3 -> height.isNotBlank() && weight.isNotBlank() && weight_before.isNotBlank()
            else -> return false
        }
    }
}