package nobel.dhar.itmdicus.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import nobel.dhar.itmdicus.BR
import nobel.dhar.itmdicus.data.local.CartWithProduct
import nobel.dhar.itmdicus.data.local.Product
import nobel.dhar.itmdicus.databinding.ItemCartBinding


class CartAdapter(private val listener: CartAdapterLister) :
    ListAdapter<CartWithProduct, CartAdapter.MyViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class MyViewHolder(
        private val binding: ItemCartBinding,
        private val listener: CartAdapterLister
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cart: CartWithProduct){
            binding.setVariable(BR.cart, cart)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings();
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CartWithProduct>() {
        override fun areItemsTheSame(oldItem: CartWithProduct, newItem: CartWithProduct) =
            oldItem == newItem


        override fun areContentsTheSame(
            oldItem: CartWithProduct,
            newItem: CartWithProduct
        ) = oldItem.cartTable.cart_id == newItem.cartTable.cart_id


    }
}