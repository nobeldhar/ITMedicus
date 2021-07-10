package nobel.dhar.itmdicus.ui.cart

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import nobel.dhar.itmdicus.R
import nobel.dhar.itmdicus.SpliffApp
import nobel.dhar.itmdicus.data.local.CartTable
import nobel.dhar.itmdicus.data.local.CartWithProduct
import nobel.dhar.itmdicus.databinding.FragmentCartBinding
import nobel.dhar.itmdicus.databinding.FragmentHomeBinding
import nobel.dhar.itmdicus.ui.home.HomeViewModel
import nobel.dhar.itmdicus.ui.home.ProductAdapter
import nobel.dhar.itmdicus.ui.login.LoginViewModel
import nobel.dhar.itmdicus.utils.AppUtils
import nobel.dhar.itmdicus.utils.hide
import javax.inject.Inject

class CartFragment : Fragment(), CartAdapterLister {

    private lateinit var adapter: CartAdapter

    @Inject
    lateinit var viewModel: CartViewModel
    private lateinit var binding: FragmentCartBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as SpliffApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater)
        binding.viewModel = viewModel
        adapter = CartAdapter(this)
        binding.rcvCart.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCartItems().observe(viewLifecycleOwner, Observer {
            binding.pbCart.hide()

            viewModel.cartEmpty = it.isNullOrEmpty()

            calculateTotalPrice(it)
            adapter.submitList(it)

        })

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getUIResponse.collect {
                when (it) {
                    is CartViewModel.UIResponse.OnOrderPlaced ->{
                        AppUtils.showSuccessSnackBar("Order Placed Successfully", requireActivity())
                        findNavController().navigate(CartFragmentDirections.actionNavCartToNavOrderSuccess())
                    }
                    is CartViewModel.UIResponse.CartEmpty->
                        AppUtils.showErrorSnackbar("Cart Is Empty", requireActivity())

                }
            }
        }

    }

    private fun calculateTotalPrice(list: List<CartWithProduct>?) {
        var totalPrice = 0
        list?.forEach {
            totalPrice += it.cartTable.total_price
        }
        binding.tvTotalPrice.text = "$$totalPrice"
    }

    override fun onAddClicked(cartWithProduct: CartWithProduct) {
        val cart = cartWithProduct.cartTable
        val product = cartWithProduct.product
        viewModel.updateCartItem(
            CartTable(
                cart_id = cart.cart_id,
                product_id = cart.product_id,
                count = cart.count.plus(1),
                total_price = cart.total_price.plus(product.price)
            )
        )
    }

    override fun onMinusClicked(cartWithProduct: CartWithProduct) {
        val cart = cartWithProduct.cartTable
        val product = cartWithProduct.product

        if (cart.count > 1)
            viewModel.updateCartItem(
                CartTable(
                    cart_id = cart.cart_id,
                    product_id = cart.product_id,
                    count = cart.count.minus(1),
                    total_price = cart.total_price.minus(product.price)
                )
            )
    }


}