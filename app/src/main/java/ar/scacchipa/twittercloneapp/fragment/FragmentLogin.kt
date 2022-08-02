package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.DialogLoginErrorBinding
import ar.scacchipa.twittercloneapp.databinding.FragmentLoginLayoutBinding
import ar.scacchipa.twittercloneapp.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentLogin : Fragment() {

    private var mainBinding: FragmentLoginLayoutBinding? = null
    private var errorBinding: DialogLoginErrorBinding? = null
    private var errorDialog: AlertDialog? = null

    private val viewModel: LoginViewModel by viewModel()
    private val args: FragmentLoginArgs by navArgs()

    private var readArgs = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainBinding = FragmentLoginLayoutBinding.inflate(inflater)
        mainBinding?.buttonLogin?.setOnClickListener{
            viewModel.onClickLoginButton()
        }

        createDialog(inflater)

        viewModel.navToFragAuthWeb.observe(viewLifecycleOwner) { mustNavToLoginAuthWeb ->
            if (mustNavToLoginAuthWeb) {
                viewModel.onNavToAuthWeb()
                findNavController().navigate(
                    FragmentLoginDirections.actionFragmentLoginToFragmentLoginAuthWebDialog()
                )
            }
        }

        viewModel.mustShowErrorMsg.observe(viewLifecycleOwner) { mustShowErrorMsg ->
            if (mustShowErrorMsg) {
                this.showErrorDialog()
            } else {
                this.hideErrorDialog()
            }
        }

        if (readArgs and args.showErrorMsg) {
            viewModel.showErrorMsg()
            readArgs = false
        }

        return mainBinding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainBinding = null
        errorBinding = null
        errorDialog = null
    }

    private fun createDialog(inflater: LayoutInflater) {
        errorBinding = DialogLoginErrorBinding.inflate(inflater)
        errorDialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
            .setView(errorBinding?.root)
            .setCancelable(false)
            .create()
    }

    private fun showErrorDialog() {
        errorDialog?.show()
        errorDialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        errorBinding?.okLoginErrorButton?.setOnClickListener {
            viewModel.onClickMsgButton()
        }

        mainBinding?.root?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.secondary_background))
    }

    private fun hideErrorDialog() {
        errorDialog?.hide()
        mainBinding?.root?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary_background))
    }
}