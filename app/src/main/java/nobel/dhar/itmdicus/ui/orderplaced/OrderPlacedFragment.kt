package nobel.dhar.itmdicus.ui.orderplaced

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import nobel.dhar.itmdicus.R
import nobel.dhar.itmdicus.databinding.FragmentOrderPlacedBinding

class OrderPlacedFragment : Fragment() {

    private lateinit var binding: FragmentOrderPlacedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderPlacedBinding.inflate(inflater)
        binding.btnContinueShopping.setOnClickListener {
            findNavController().navigate(
                OrderPlacedFragmentDirections.actionNavOrderSuccessToNaviHome()
            )
        }
        return binding.root
    }
}