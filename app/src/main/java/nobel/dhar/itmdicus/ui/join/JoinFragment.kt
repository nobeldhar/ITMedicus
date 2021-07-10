package nobel.dhar.itmdicus.ui.join

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import nobel.dhar.itmdicus.R
import nobel.dhar.itmdicus.SpliffApp
import nobel.dhar.itmdicus.databinding.JoinFragmentBinding
import javax.inject.Inject


class JoinFragment : Fragment(){

    @Inject
    lateinit var viewModel: JoinViewModel

    private lateinit var binding: JoinFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as SpliffApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = JoinFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getUIResponse.collect  {
                when(it){
                    is JoinViewModel.UIResponse.Join -> onJoinButtonClick()
                    is JoinViewModel.UIResponse.Login -> onLoginButtonCLick()
                }
            }
        }
    }




    private fun onLoginButtonCLick() {
        findNavController().navigate(JoinFragmentDirections.actionNavJoinToNavLogin())
    }

    private fun onJoinButtonClick() {
        findNavController().navigate(JoinFragmentDirections.actionNavJoinToNavRegistration())
    }

}