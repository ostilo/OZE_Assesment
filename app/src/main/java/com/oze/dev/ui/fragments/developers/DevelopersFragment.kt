package com.oze.dev.ui.fragments.developers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.oze.dev.R
import com.oze.dev.core.iinterface.IDeveloper
import com.oze.dev.databinding.FragmentDevelopersBinding
import com.oze.dev.db.adapters.DeveloperListAdapter
import com.oze.dev.db.base.ResponseStatus
import com.oze.dev.db.model.DeveloperInfo
import com.oze.dev.utils.gone
import com.oze.dev.utils.showSnackBar
import com.oze.dev.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class DevelopersFragment : Fragment(), IDeveloper {

    private var bi: FragmentDevelopersBinding? = null
    private val _binding get() = bi!!
    //private lateinit var bi : FragmentDevelopersBinding
    private val viewModel by viewModels<DeveloperViewModel>()
    private lateinit var adapter : DeveloperListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentDevelopersBinding.inflate(inflater,container,false).apply {
            bi = this
        }.root
    }

    private fun  setViewBasedOnRepo(){
        bi?.layoutShimmerLoading?.visible()
        adapter = DeveloperListAdapter(this)
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        bi?.productList?.adapter = adapter
        lifecycleScope.launch {
            adapter.loadStateFlow.distinctUntilChangedBy {
                it.refresh
            }.filter { it.refresh is LoadState.NotLoading }
                .collect{
                val lt = adapter.snapshot()
                    if(lt.size > 0 ){
                        bi?.layoutShimmerLoading?.gone()
                        bi?.productList?.visible()
                    }else{
                        bi?.layoutShimmerLoading?.visible()
                        bi?.productList?.gone()
                    }
            }

        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getAllDevelopers
                .collectLatest {
                bi?.productList?.adapter = adapter
                adapter.submitData(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         setViewBasedOnRepo()
         viewModel.searchDevelopersFromRemote()
         viewModel.usersResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    it.data?.apply {
                        bi?.layoutShimmerLoading?.gone()
                        bi?.productList?.visible()
                    }
                }
                ResponseStatus.ERROR -> {
                    setViewBasedOnRepo()
                    it.data?.let { item ->
                        if (item.page != 1) {
                            bi?.nestedScrollView?.showSnackBar(
                                message = it.message.toString()
                            )
                        }

                    } ?: run {
                        bi?.layoutShimmerLoading?.gone()
                        bi?.nestedScrollView?.showSnackBar(
                            message = getString(R.string.error_internet),
                            action = getString(R.string.retry)
                        ) {

                        }

                    }
                }
                ResponseStatus.LOADING -> {
                    it.data?.let { item ->
                        if (item.page == 1) {
                            bi?.layoutShimmerLoading?.visible()
                            bi?.productList?.gone()
                        } else
                            bi?.nestedScrollView?.showSnackBar(
                                message = getString(R.string.loading_users)
                            )
                    }
                }

            }
        })
/*
        * Checking scrollview scroll end
        * */
        bi?.productList?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadNextPageUsers()
                }
            }
        })
    }

    override fun openDeveloperDetails(deInfo: DeveloperInfo) {
       findNavController().navigate(DevelopersFragmentDirections.actionDevelopersFragmentToDeveloperDetailsFragment(
           deInfo.login,deInfo.id
       ))
    }


}