package org.codventure.kinimom.core.data.response

import org.codventure.kinimom.core.domain.User

/**
 * Created by abduaziz on 7/13/21 at 10:55 PM.
 */

class SignUpResponse(
    val status: Boolean,
    val token: String?,
    val user: User?
)