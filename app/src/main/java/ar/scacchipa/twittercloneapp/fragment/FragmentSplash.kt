package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.FragmentSplashLayoutBinding
import ar.scacchipa.twittercloneapp.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentSplash: Fragment() {

    private var binding: FragmentSplashLayoutBinding? = null
    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashLayoutBinding.inflate(inflater)
        splashViewModel.splashWasSpent.observe(viewLifecycleOwner) { splashWasSpent ->
            if (splashWasSpent) {
                findNavController().navigate(R.id.action_fragmentSplash_to_fragmentLogin)
            }
        }
        activity?.window?.let { window ->
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                    View.SYSTEM_UI_FLAG_IMMERSIVE or
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.primary_background)
            window.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.primary_background)
        }
        splashViewModel.spendSplash()
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroyView()
        binding = null
    }
}


