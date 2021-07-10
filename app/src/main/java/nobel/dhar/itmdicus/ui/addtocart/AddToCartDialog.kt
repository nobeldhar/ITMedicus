package nobel.dhar.itmdicus.ui.addtocart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import nobel.dhar.itmdicus.SpliffApp
import nobel.dhar.itmdicus.data.local.Product
import nobel.dhar.itmdicus.databinding.FragmentAddToCartDialogBinding
import nobel.dhar.itmdicus.ui.login.LoginViewModel
import nobel.dhar.itmdicus.utils.AppUtils
import javax.inject.Inject

class AddToCartDialog(val product: Product) : DialogFragment() {

    @Inject
    lateinit var viewModel: AddToCartDialogViewModel
    private lateinit var binding : FragmentAddToCartDialogBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as SpliffApp).appComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddToCartDialogBinding.inflate(inflater)

        binding.viewModel = viewModel
        viewModel.product = product

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getUIResponse.collect  {
                when (it) {
                    is AddToCartDialogViewModel.UIResponse.OnAddClicked ->
                        binding.tvCount.text = it.count

                    is AddToCartDialogViewModel.UIResponse.OnMinusClicked ->
                        binding.tvCount.text = it.count

                    is AddToCartDialogViewModel.UIResponse.CartUpdated ->{
                        AppUtils.showSuccessSnackBar("Added To Cart", requireActivity())
                        dismiss()
                    }
                }
            }
        }
    }



}