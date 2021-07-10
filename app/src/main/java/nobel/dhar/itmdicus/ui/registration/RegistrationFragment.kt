package nobel.dhar.itmdicus.ui.registration

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import nobel.dhar.itmdicus.R
import nobel.dhar.itmdicus.SpliffApp
import nobel.dhar.itmdicus.databinding.FragmentRegistrationBinding
import nobel.dhar.itmdicus.utils.AppUtils
import nobel.dhar.itmdicus.utils.AppUtils.showErrorSnackbar
import nobel.dhar.itmdicus.utils.AppUtils.showSuccessSnackBar
import nobel.dhar.itmdicus.utils.Resource
import nobel.dhar.itmdicus.utils.hide
import nobel.dhar.itmdicus.utils.show
import javax.inject.Inject


class RegistrationFragment : Fragment() {

    @Inject
    lateinit var viewModel: RegistrationViewModel

    private lateinit var binding : FragmentRegistrationBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as SpliffApp).appComponent.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getUIResponse.collect  {
            when (it) {
                is RegistrationViewModel.UIResponse.ValidationError ->
                    showErrorSnackbar(it.error, requireActivity())

                is RegistrationViewModel.UIResponse.AlreadyMember ->
                    findNavController().
                    navigate(
                        RegistrationFragmentDirections.actionNavRegistrationToNavLogin()
                    )

                is RegistrationViewModel.UIResponse.RegisterClicked ->
                    AppUtils.hideKeyboard(requireActivity())
            }
        }
        }

        viewModel.getRegistrationResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.pbRegister.hide()
                    val response = it.data
                    if (response?.success?.token != null) {
                        showSuccessSnackBar("Registration Successful", requireActivity())
                        findNavController().navigate(
                            RegistrationFragmentDirections.actionNavRegistrationToNaviHome()
                        )
                    }
                }
                Resource.Status.ERROR -> {
                    binding.pbRegister.hide()
                    Log.d("TAG", "onActivityCreated: error " + it.isNetworkError)
                    it.isNetworkError?.let { it ->
                        if (it) {
                            Toast.makeText(context, "No Internet", Toast.LENGTH_LONG).show()
                        }
                    } ?: it.message?.let { it2 ->
                        showErrorSnackbar(it2, requireActivity())
                    }
                }

                Resource.Status.LOADING ->
                    binding.pbRegister.show()
            }
        })

    }




}