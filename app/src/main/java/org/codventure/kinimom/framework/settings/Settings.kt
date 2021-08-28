package org.codventure.kinimom.framework.settings

import android.content.Context
import org.codventure.kinimom.core.domain.SocialUser
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by abduaziz on 8/16/21 at 6:49 PM.
 */

@Singleton
class Settings
@Inject constructor(private val context: Context) {

    private val USER_ID = "user_id"
    private val USER_TOKEN = "token"

    private val SOCIAL_TYPE = "SOCIAL_TYPE"
    private val SOCIAL_ID = "SOCIAL_ID"
    private val SOCIAL_NAME = "SOCIAL_NAME"
    private val SOCIAL_PHONE = "SOCIAL_PHONE"
    private val SOCIAL_PHOTO = "SOCIAL_PHOTO"

    fun isUserLoggedIn(): Boolean {
        if (getToken().isBlank()) return false
        if (getUserId() == -1L) return false
        return true
    }

    fun saveUserId(id: Long) {
        Prefs.save(context, USER_ID, id)
    }

    fun getUserId() = Prefs.get(context, USER_ID, -1L)

    fun saveToken(token: String) {
        Prefs.save(context, USER_TOKEN, token)
    }

    fun getToken(): String {
        return "Bearer ${Prefs.get(context, USER_TOKEN, "")}"
    }

    fun saveSocialLoginCredentials(
        social_login_type: String,
        social_id: String,
        social_name: String,
        social_phone: String,
        social_photo: String
    ) {
        Prefs.save(context, SOCIAL_TYPE, social_login_type)
        Prefs.save(context, SOCIAL_ID, social_id)
        Prefs.save(context, SOCIAL_NAME, social_name)
        Prefs.save(context, SOCIAL_PHONE, social_phone)
        Prefs.save(context, SOCIAL_PHOTO, social_photo)
    }

    fun getSocialLoginCredentials(): SocialUser {
        return SocialUser(
            Prefs.get(context, SOCIAL_TYPE, ""),
            Prefs.get(context, SOCIAL_ID, ""),
            Prefs.get(context, SOCIAL_NAME, ""),
            Prefs.get(context, SOCIAL_PHONE, ""),
            Prefs.get(context, SOCIAL_PHOTO, "")
        )
    }
}