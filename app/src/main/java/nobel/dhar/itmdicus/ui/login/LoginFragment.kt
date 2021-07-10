package nobel.dhar.itmdicus.ui.login

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import nobel.dhar.itmdicus.R
import nobel.dhar.itmdicus.SpliffApp
import nobel.dhar.itmdicus.databinding.FragmentLoginBinding
import nobel.dhar.itmdicus.ui.registration.RegistrationFragmentDirections
import nobel.dhar.itmdicus.ui.registration.RegistrationViewModel
import nobel.dhar.itmdicus.utils.AppUtils
import nobel.dhar.itmdicus.utils.AppUtils.showErrorSnackbar
import nobel.dhar.itmdicus.utils.AppUtils.showSuccessSnackBar
import nobel.dhar.itmdicus.utils.Resource
import nobel.dhar.itmdicus.utils.hide
import nobel.dhar.itmdicus.utils.show
import javax.inject.Inject

class LoginFragment : Fragment() {



    @Inject
    lateinit var viewModel: LoginViewModel
    private lateinit var binding : FragmentLoginBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as SpliffApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getUIResponse.collect  {
                when (it) {
                    is LoginViewModel.UIResponse.ValidationError ->
                        showErrorSnackbar(it.error,requireActivity())

                    is LoginViewModel.UIResponse.LoginClicked ->
                        AppUtils.hideKeyboard(requireActivity())
                }
            }
        }

        viewModel.getLoginResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.pbLogin.hide()
                    val response = it.data
                    if (response?.success?.token != null) {
                        showSuccessSnackBar("Login Successful",requireActivity())
                        findNavController().navigate(
                            LoginFragmentDirections.actionNavLoginToNaviHome()
                        )
                    }
                }
                Resource.Status.ERROR -> {
                    binding.pbLogin.hide()
                    Log.d("TAG", "onActivityCreated: error " + it.isNetworkError)
                    it.isNetworkError?.let { it ->
                        if (it) {
                            Toast.makeText(context, "No Internet", Toast.LENGTH_LONG).show()
                        }
                    } ?: it.message?.let { it2 ->
                        showErrorSnackbar(it2,requireActivity())
                    }
                }

                Resource.Status.LOADING ->
                    binding.pbLogin.show()
            }
        })
    }


}