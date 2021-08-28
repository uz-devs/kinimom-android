package org.codventure.kinimom.core.interactors

import org.codventure.kinimom.core.data.KinimomRepository
import org.codventure.kinimom.core.data.request.CheckNicknameRequest
import javax.inject.Inject

/**
 * Created by abduaziz on 8/16/21 at 4:11 PM.
 */

class CheckNickname
@Inject constructor(
    private val repository: KinimomRepository){
    operator fun invoke(nickname: String): Boolean? {
        return repository.checkNickname(CheckNicknameRequest(nickname))
    }
}