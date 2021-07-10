package nobel.dhar.itmdicus.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import nobel.dhar.itmdicus.R
import nobel.dhar.itmdicus.SpliffApp
import nobel.dhar.itmdicus.data.local.Product
import nobel.dhar.itmdicus.databinding.FragmentHomeBinding
import nobel.dhar.itmdicus.ui.addtocart.AddToCartDialog
import nobel.dhar.itmdicus.utils.*
import nobel.dhar.itmdicus.worker.TokenRefreshWorker
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeFragment : Fragment(), SearchView.OnQueryTextListener, ProductAdapterListener {

    private lateinit var adapter: ProductAdapter

    @Inject
    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var prefsHelper: SharedPrefsHelper

    private lateinit var binding: FragmentHomeBinding

    private lateinit var searchView: SearchView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as SpliffApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        setHasOptionsMenu(true)

        adapter = ProductAdapter(this)
        binding.rcvProduct.adapter = adapter

        initWorker()

        return binding.root
    }

    private fun initWorker() {

        prefsHelper.getExpiryDate()?.let {

            val timeDiff = getTimeDifference(it)

            Log.d("TAG", "initWorker: timeDiff $timeDiff ")

            val tokenRefreshWork = PeriodicWorkRequestBuilder<TokenRefreshWorker>(
                timeDiff, TimeUnit.MICROSECONDS
            ).build()

            WorkManager.getInstance(requireContext())
                .enqueueUniquePeriodicWork(
                "TokenRefreshWork",
                ExistingPeriodicWorkPolicy.KEEP,
                tokenRefreshWork
            )
        } ?: run {


            val tokenRefreshWork = PeriodicWorkRequestBuilder<TokenRefreshWorker>(
                24, TimeUnit.HOURS
            ).build()

            WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
                "TokenRefreshWork",
                ExistingPeriodicWorkPolicy.KEEP,
                tokenRefreshWork
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.productsResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.pbHome.hide()

                    adapter.submitList(it.data)
                }

                Resource.Status.LOADING ->
                    binding.pbHome.show()
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                goToCart()
                true
            }
            R.id.action_logout -> {
                logoutUser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logoutUser() {
        viewModel.logoutUser().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.pbHome.hide()
                    val response = it.data
                    if (response?.success?.equals("true") == true) {

                        AppUtils.showSuccessSnackBar("Logout Successful", requireActivity())

                        WorkManager.getInstance(requireContext())
                            .cancelUniqueWork("TokenRefreshWork")

                        viewModel.dropCartTable()

                        prefsHelper.clearAllData()

                        findNavController().navigate(
                            HomeFragmentDirections.actionNaviHomeToNavSplash()
                        )
                    }
                }
                Resource.Status.ERROR -> {
                    binding.pbHome.hide()
                    Log.d("TAG", "onActivityCreated: error " + it.isNetworkError)
                    it.isNetworkError?.let { it ->
                        if (it) {
                            Toast.makeText(context, "No Internet", Toast.LENGTH_LONG).show()
                        }
                    } ?: it.message?.let { it2 ->
                        AppUtils.showErrorSnackbar(it2, requireActivity())
                    }
                }

                Resource.Status.LOADING ->
                    binding.pbHome.show()
            }
        })

    }

    private fun goToCart() {
        findNavController().navigate(HomeFragmentDirections.actionNaviHomeToNavCart())
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.onSearchQueryChanged(newText)
        return true
    }

    override fun onAddClicked(product: Product) {
        val addToCartDialog = AddToCartDialog(product)
        addToCartDialog.show(parentFragmentManager, "Home Fragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
    }


    private fun getTimeDifference(expiredDate: String): Long {

        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()

        try {

            val sourceFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ssssss")
            sourceFormat.timeZone = TimeZone.getTimeZone("UTC+3")
            val parsed = sourceFormat.parse(expiredDate)

            val tz = TimeZone.getTimeZone("Asia/Dhaka")
            val destFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            destFormat.timeZone = tz
            val result = destFormat.format(parsed)


            dueDate.time = destFormat.parse(result)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return dueDate.timeInMillis.minus(currentDate.timeInMillis)
    }

}