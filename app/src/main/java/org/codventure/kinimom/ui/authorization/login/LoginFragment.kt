package org.codventure.kinimom.ui.authorization.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
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
import org.codventure.kinimom.framework.extension.doAsync
import org.codventure.kinimom.framework.extension.toast
import org.codventure.kinimom.framework.extension.uiThread
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LoginPresenter(this)
        appComponent.inject(presenter)

        // region Facebook login
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    enableLoginButtons()
                    val profile = Profile.getCurrentProfile()
                    if (profile != null)
                        presenter.signUpWithFacebook(profile)
                }

                override fun onCancel() {
                    enableLoginButtons()
                    toast("Facebook login canceled")
                }

                override fun onError(error: FacebookException?) {
                    enableLoginButtons()
                    toast("Facebook login error")
                }
            })

        llFacebookLogin.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                    llFacebookLogin.setBackgroundResource(R.drawable.login_button_background_facebook_enabled)
                    tvFacebookLogin.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    false
                }
                MotionEvent.ACTION_UP -> {
                    llFacebookLogin.setBackgroundResource(R.drawable.login_button_background)
                    tvFacebookLogin.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    false
                }
                else -> false
            }
        }

        llFacebookLogin.setOnClickListener {
            disableLoginButtons()
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
                enableLoginButtons()

                if (success) {
                    val naverAccessToken = mOAuthLoginModule.getAccessToken(context)
                    val naverRefreshToken = mOAuthLoginModule.getRefreshToken(context)
                    val naverExpiresAt = mOAuthLoginModule.getExpiresAt(context)
                    val naverTokenType = mOAuthLoginModule.getTokenType(context)
                    val naverAuthState = mOAuthLoginModule.getState(context).toString()

                    // get profile with token
                    disableLoginButtons()
                    doAsync {
                        val res = JSONObject(
                            mOAuthLoginModule.requestApi(
                                requireActivity(),
                                naverAccessToken,
                                "https://openapi.naver.com/v1/nid/me"
                            ) ?: ""
                        )
                        uiThread {
                            enableLoginButtons()
                            if (res.getString("resultcode") == "00")
                                presenter.signUpWithNaver(
                                    mapOf(
                                        "id" to res.getJSONObject("response").getString("id"),
                                        "nickname" to res.getJSONObject("response")
                                            .getString("nickname"),
                                        "profile_image" to res.getJSONObject("response")
                                            .getString("profile_image"),
                                        "age" to res.getJSONObject("response").getString("age"),
                                        "gender" to res.getJSONObject("response").getString("gender"),
                                        "email" to res.getJSONObject("response").getString("email"),
                                        "mobile" to res.getJSONObject("response").getString("mobile"),
                                        "mobile_e164" to res.getJSONObject("response")
                                            .getString("mobile_e164"),
                                        "name" to res.getJSONObject("response").getString("name"),
                                        "birthday" to res.getJSONObject("response")
                                            .getString("birthday"),
                                        "birthyear" to res.getJSONObject("response")
                                            .getString("birthyear")
                                    )
                                )
                        }
                    }
                } else {
                    val errorCode = mOAuthLoginModule.getLastErrorCode(context).code
                    val errorDesc = mOAuthLoginModule.getLastErrorDesc(context)

                    // toast("errorCode: $errorCode, errorDesc: $errorDesc")
                    toast("Naver login failure")
                }
            }
        }
        llNaverLogin.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                    llNaverLogin.setBackgroundResource(R.drawable.login_button_background_naver_enabled)
                    tvNaverLogin.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    false
                }
                MotionEvent.ACTION_UP -> {
                    llNaverLogin.setBackgroundResource(R.drawable.login_button_background)
                    tvNaverLogin.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    false
                }
                else -> false
            }
        }
        llNaverLogin.setOnClickListener {
            disableLoginButtons()
            mOAuthLoginModule.startOauthLoginActivity(activity, mOAuthLoginHandler)
        }
        // endregion

        // region Kakao login
        llKakaoLogin.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                    llKakaoLogin.setBackgroundResource(R.drawable.login_button_background_kakao_enabled)
                    false
                }
                MotionEvent.ACTION_UP -> {
                    llKakaoLogin.setBackgroundResource(R.drawable.login_button_background)
                    false
                }
                else -> false
            }
        }
        llKakaoLogin.setOnClickListener {
            if (activity != null){
                disableLoginButtons()
                KakaoSdk.init(requireActivity(), getString(R.string.KAKAO_NATIVE_APP_KEY))
                UserApiClient.instance.loginWithKakaoTalk(requireActivity()) { token, error ->
                    enableLoginButtons()
                    if (error != null) {
                        Log.e("LoginFragment", "Kakao app login failure : ${error.message}")

                        // maybe kakaotalk is not installed, open web browser
                        disableLoginButtons()
                        UserApiClient.instance.loginWithKakaoAccount(requireActivity()) { _token, _error ->
                            enableLoginButtons()
                            if (_error != null)
                                Log.e(
                                    "LoginFragment",
                                    "Kakao web login failure : ${_error.message}"
                                )
                            else if (_token != null) {
                                Log.e(
                                    "LoginFragment",
                                    "Kakao web login success : ${_token.accessToken}"
                                )

                                disableLoginButtons()
                                UserApiClient.instance.me { user, error ->
                                    enableLoginButtons()
                                    if (user != null)
                                        // get profile with _token
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
        }
        // endregion
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun enableLoginButtons() {
        llNaverLogin.isEnabled = true
        llFacebookLogin.isEnabled = true
        llKakaoLogin.isEnabled = true
        flProgress.visibility = View.GONE
    }

    override fun disableLoginButtons() {
        llNaverLogin.isEnabled = false
        llFacebookLogin.isEnabled = false
        llKakaoLogin.isEnabled = false
        flProgress.visibility = View.VISIBLE
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
