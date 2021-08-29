package org.codventure.kinimom.ui.authorization.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.Profile
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import kotlinx.android.synthetic.main.fragment_login.*
import org.codventure.kinimom.AndroidApplication
import org.codventure.kinimom.R
import org.codventure.kinimom.framework.di.ApplicationComponent
import org.codventure.kinimom.framework.extension.toast
import org.codventure.kinimom.ui.MainActivity
import org.json.JSONObject

class LoginFragment : Fragment(R.layout.fragment_login), LoginView {
    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }

    private lateinit var presenter: LoginPresenter
    private lateinit var callbackManager: CallbackManager // facebook callback manager
    private lateinit var mOAuthLoginModule: OAuthLogin // naver login module
    private lateinit var mOAuthLoginHandler: OAuthLoginHandler // naver login handler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LoginPresenter(this)
        appComponent.inject(presenter)

        // region Facebook login
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                val profile = Profile.getCurrentProfile()
                if (profile != null)
                    presenter.signUpWithFacebook(profile)
            }

            override fun onCancel() {
                toast("Facebook login canceled")
            }

            override fun onError(error: FacebookException?) {
                toast("Facebook login error")
            }
        })
        llFacebookLogin.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(
                this,
                listOf("email", "public_profile")
            )
        }
        // endregion

        // region Naver login
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
            activity,
            getString(R.string.NAVER_OAUTH_CLIENT_ID),
            getString(R.string.NAVER_OAUTH_CLIENT_SECRET),
            getString(R.string.NAVER_OAUTH_CLIENT_NAME)
        )
        @SuppressLint("HandlerLeak")
        mOAuthLoginHandler = object : OAuthLoginHandler() {
            override fun run(success: Boolean) {
                if (success) {
                    val naverAccessToken = mOAuthLoginModule.getAccessToken(context)
                    val naverRefreshToken = mOAuthLoginModule.getRefreshToken(context)
                    val naverExpiresAt = mOAuthLoginModule.getExpiresAt(context)
                    val naverTokenType = mOAuthLoginModule.getTokenType(context)
                    val naverAuthState = mOAuthLoginModule.getState(context).toString()

                    // todo get profile with token
                    Thread {
                        val res = JSONObject(
                            mOAuthLoginModule.requestApi(
                                requireActivity(),
                                naverAccessToken,
                                "https://openapi.naver.com/v1/nid/me"
                            )
                        )
                        if (res.getString("resultcode") == "00")
                            presenter.signUpWithNaver(
                                mapOf(
                                    "id" to res.getJSONObject("response").getString("id"),
                                    "nickname" to res.getJSONObject("response").getString("nickname"),
                                    "profile_image" to res.getJSONObject("response").getString("profile_image"),
                                    "age" to res.getJSONObject("response").getString("age"),
                                    "gender" to res.getJSONObject("response").getString("gender"),
                                    "email" to res.getJSONObject("response").getString("email"),
                                    "mobile" to res.getJSONObject("response").getString("mobile"),
                                    "mobile_e164" to res.getJSONObject("response").getString("mobile_e164"),
                                    "name" to res.getJSONObject("response").getString("name"),
                                    "birthday" to res.getJSONObject("response").getString("birthday"),
                                    "birthyear" to res.getJSONObject("response").getString("birthyear")
                                )
                            )
                    }.start()
                    toast("Naver login successful")
                } else {
                    val errorCode = mOAuthLoginModule.getLastErrorCode(context).code
                    val errorDesc = mOAuthLoginModule.getLastErrorDesc(context)

                    // toast("errorCode: $errorCode, errorDesc: $errorDesc")
                    toast("Naver login failure")
                }
            }
        }
        llNaverLogin.setOnClickListener {
            mOAuthLoginModule.startOauthLoginActivity(activity, mOAuthLoginHandler)
        }
        // endregion

        // region Kakao login
        if (activity != null)
            KakaoSdk.init(requireActivity(), getString(R.string.KAKAO_NATIVE_APP_KEY))
        llKakaoLogin.setOnClickListener {
            if (activity != null)
                UserApiClient.instance.loginWithKakaoTalk(requireActivity()) { token, error ->
                    if (error != null) {
                        Log.e("LoginFragment", "Kakao app login failure : ${error.message}")

                        // maybe kakaotalk is not installed, open web browser
                        UserApiClient.instance.loginWithKakaoAccount(requireActivity()) { _token, _error ->
                            if (_error != null)
                                Log.e("LoginFragment", "Kakao web login failure : ${_error.message}")
                            else if (_token != null) {
                                Log.e("LoginFragment", "Kakao web login success : ${_token.accessToken}")

                                UserApiClient.instance.me { user, error ->
                                    if (user != null)
                                    // todo get profile with _token
                                        presenter.signUpWithKakao(
                                            mapOf(
                                                "id" to user.id.toString(),
                                                "email" to user.kakaoAccount?.email,
                                                "phone" to user.kakaoAccount?.phoneNumber,
                                                "nickname" to user.kakaoAccount?.profile?.nickname,
                                                "gender" to user.kakaoAccount?.gender.toString(),
                                                "profilePicUrl" to user.kakaoAccount?.profile?.thumbnailImageUrl
                                            )
                                        )
                                }
                            }
                        }
                    } else if (token != null) {
                        Log.e("LoginFragment", "Kakao app login success : ${token.accessToken}")

                        // todo get profile with token
                        presenter.signUpWithKakao(mapOf<String, String>("name" to "qobiljon"))
                    }
                }
        }
        // endregion
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun showSocialLoginFailed() {
        toast(getString(R.string.error_social_login_failed))
    }

    override fun openSurvey() {
        // user should agree to terms and conditions before survey
        (activity as MainActivity).openAgreementsScreen()
    }

    override fun openMain() {
        (activity as MainActivity).openMainScreen()
    }
}
