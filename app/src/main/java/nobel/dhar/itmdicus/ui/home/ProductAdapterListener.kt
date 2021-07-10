package nobel.dhar.itmdicus.ui.home

import nobel.dhar.itmdicus.data.local.Product

interface ProductAdapterListener {
    fun onAddClicked(product: Product)
}