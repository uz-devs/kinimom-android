package org.codventure.kinimom.ui.authorization.agreement

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login_agreements.*
import org.codventure.kinimom.R
import org.codventure.kinimom.framework.extension.toast
import org.codventure.kinimom.ui.MainActivity


class AgreementFragment : Fragment(R.layout.fragment_login_agreements) {
    lateinit var lls: Array<LinearLayout> // checkbox parents where user clicks
    lateinit var ivs: Array<ImageView> // checkboxes
    lateinit var agreed: Array<Boolean>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lls = arrayOf(llTermsOfService, llPrivacyPolicy, llPersonalInfoCollection, llConsentToUsePersonalInfo, llConsentToThirdParty)
        ivs = arrayOf(ivTermsOfService, ivPrivacyPolicy, ivPersonalInfoCollection, ivConsentToUsePersonalInfo, ivConsentToThirdParty)
        agreed = arrayOf(false, false, false, false, false)

        llAgreeToAll.setOnClickListener { toggleAgreements() }

        lls.forEachIndexed { index, linearLayout ->
            linearLayout.setOnClickListener {
                agreed[index] = !agreed[index]
                updateAgreementCheckboxes()
            }
        }

        tvNext.setOnClickListener {
            if (agreed.contains(false)) {
                toast(getString(R.string.plz_agree_to_terms_and_conditions))
                return@setOnClickListener
            }
            (activity as MainActivity).openSurveyScreen()
        }

        ivTermsOfServiceMore.setOnClickListener {
            val agreementFragment = AgreementDetailFragment()
            agreementFragment.show(childFragmentManager, "AgreementDetailFragment")
        }

        ivPrivacyPolicyMore.setOnClickListener {
            val agreementFragment = AgreementDetailFragment()
            agreementFragment.show(childFragmentManager, "AgreementDetailFragment")
        }

        ivPersonalInfoCollectionMore.setOnClickListener {
            val agreementFragment = AgreementDetailFragment()
            agreementFragment.show(childFragmentManager, "AgreementDetailFragment")
        }

        ivConsentToUsePersonalInfoMore.setOnClickListener {
            val agreementFragment = AgreementDetailFragment()
            agreementFragment.show(childFragmentManager, "AgreementDetailFragment")
        }

        ivConsentToThirdPartyMore.setOnClickListener {
            val agreementFragment = AgreementDetailFragment()
            agreementFragment.show(childFragmentManager, "AgreementDetailFragment")
        }
    }

    private fun toggleAgreements() {
        if (agreed.contains(false)) {
            // agreed to some or none, then agree to all
            for (i in 0 until agreed.size)
                agreed[i] = true
        } else {
            // agreed to all, then disagree to all
            for (i in 0 until agreed.size)
                agreed[i] = false
        }
        updateAgreementCheckboxes()
    }

    private fun updateAgreementCheckboxes() {
        for (i in agreed.indices) {
            if (agreed[i]) {
                ivs[i].setImageResource(R.drawable.login_btn_agreement_allcheck_enable)
            } else {
                ivs[i].setImageResource(R.drawable.login_btn_agreement_check_disable)
            }
        }
        if (agreed.contains(false))
            ivAgreeToAll.setImageResource(R.drawable.login_btn_agreement_check_disable)
        else
            ivAgreeToAll.setImageResource(R.drawable.login_btn_agreement_allcheck_enable)
        updateNextButton()
    }

    private fun updateNextButton() {
        if (!agreed.contains(false)) {
            tvNext.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        } else {
            tvNext.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }
    }
}