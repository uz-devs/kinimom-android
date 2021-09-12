package org.codventure.kinimom.core.interactors

import org.codventure.kinimom.core.data.KinimomRepository
import org.codventure.kinimom.core.data.request.CheckNicknameRequest
import javax.inject.Inject

class CheckNickname
@Inject constructor(
    private val repository: KinimomRepository
) {
    operator fun invoke(nickname: String): Boolean? {
        return repository.checkNickname(CheckNicknameRequest(nickname))
    }
}