package nobel.dhar.itmdicus.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import nobel.dhar.itmdicus.BR
import nobel.dhar.itmdicus.data.local.Product
import nobel.dhar.itmdicus.databinding.ItemProductBinding


class ProductAdapter(private val listener: ProductAdapterListener) :
    ListAdapter<Product, ProductAdapter.MyViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class MyViewHolder(
        private val binding: ItemProductBinding,
        private val listener: ProductAdapterListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product){
            binding.setVariable(BR.product, product)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings();
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) =
            oldItem == newItem

    }
}