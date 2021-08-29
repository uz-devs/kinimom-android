package org.codventure.kinimom.core.data.response

import org.codventure.kinimom.core.domain.TestResultHusband
import org.codventure.kinimom.core.domain.TestResultWife

class TestLastOneResponse(
    val wife_test_last_one: ArrayList<TestResultWife>,
    val husband_test_last_one: ArrayList<TestResultHusband>
)
