package org.codventure.kinimom.ui.authorization.agreement

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_login_agreements_detail.*
import org.codventure.kinimom.R

class AgreementDetailFragment : DialogFragment(R.layout.fragment_login_agreements_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvConfirm.setOnClickListener {
            dismiss()
        }

        ivClose.setOnClickListener {
            dismiss()
        }
    }
}