package nobel.dhar.itmdicus.ui.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import nobel.dhar.itmdicus.R
import nobel.dhar.itmdicus.SpliffApp
import nobel.dhar.itmdicus.utils.SharedPrefsHelper
import javax.inject.Inject


class SplashFragment : Fragment() {

    @Inject
    lateinit var prefsHelper: SharedPrefsHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as SpliffApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=  inflater.inflate(R.layout.splash_fragment, container, false)
        Handler(Looper.getMainLooper()).postDelayed({

            val navDirections: NavDirections =
                if (prefsHelper.hasUser()) {
                    SplashFragmentDirections.actionNavSplashToNaviHome()
                } else
                    SplashFragmentDirections.actionNavSplashToNavJoin()

            findNavController().navigate(navDirections)
        }, 2000)
        return view
    }
}