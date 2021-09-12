package org.codventure.kinimom.ui.authorization.survey

class SurveyResults(
    var is_pregnant: String = "",
    var expected_date: String = "",
    var nickname: String = "",
    var date_of_birth: String = "",
    var gender: String = "",
    var height: String = "",
    var weight: String = "",
    var weight_before: String = "",
    var period_end_date: String = "",
    var period_term: String = "",
    var period_cycle: String = ""
) {
    // suka, I had to do this:
    fun isPageCompleted(selectedPage: Int): Boolean {
        if (selectedPage == 0)
            return is_pregnant.isNotBlank()

        if (isPreparing()){
            return when (selectedPage) {
                1 -> nickname.isNotBlank() && date_of_birth.isNotBlank() && gender.isNotBlank()
                2 -> height.isNotBlank() && weight.isNotBlank()
                3 -> period_end_date.isNotBlank() && period_cycle.isNotBlank() && period_term.isNotBlank()
                else -> return false
            }
        }

        return when (selectedPage) {
            1 -> expected_date.isNotBlank()
            2 -> nickname.isNotBlank() && date_of_birth.isNotBlank() && gender.isNotBlank()
            3 -> height.isNotBlank() && weight.isNotBlank() && weight_before.isNotBlank()
            else -> return false
        }
    }

    fun isPreparing() = is_pregnant == "2"
}