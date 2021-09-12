package org.codventure.kinimom.core.data.response

import org.codventure.kinimom.core.domain.User

class SignUpResponse(
    val status: Boolean,
    val token: String?,
    val user: User?
)