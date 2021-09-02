package org.codventure.kinimom.core.interactors

import org.codventure.kinimom.core.data.KinimomRepository
import org.codventure.kinimom.core.data.request.UserInfoSaveRequest
import org.codventure.kinimom.ui.authorization.survey.SurveyResults
import javax.inject.Inject

/**
 * Created by abduaziz on 8/17/21 at 7:22 PM.
 */

class UserInfoSave
@Inject constructor(
    private val repository: KinimomRepository
) {
    operator fun invoke(user_id: Long, surveyResults: SurveyResults): Boolean? {
        return repository.userInfoSave(
            UserInfoSaveRequest(
                user_id,
                "5",
                surveyResults.is_pregnant,
                surveyResults.nickname,
                surveyResults.expected_date,
                surveyResults.date_of_birth,
                surveyResults.gender,
                surveyResults.height,
                surveyResults.weight,
                surveyResults.weight_before,
                surveyResults.period_cycle,
                surveyResults.period_term,
                surveyResults.period_end_date
            )
        )
    }
}