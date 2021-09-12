package org.codventure.kinimom.core.data.response

import org.codventure.kinimom.core.domain.Menstruation

class GetMenstruationResponse(
    val prev_month_menstruation: ArrayList<Menstruation>?,
    val current_month_menstruation: ArrayList<Menstruation>?,
    val next_month_menstruation: ArrayList<Menstruation>?
)
