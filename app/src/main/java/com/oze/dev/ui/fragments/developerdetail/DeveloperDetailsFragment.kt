package com.oze.dev.ui.fragments.developerdetail

import android.annotation.SuppressLint
import android.media.AudioDeviceInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.oze.dev.R
import com.oze.dev.databinding.FragmentDeveloperDetailsBinding
import com.oze.dev.db.adapters.GenericListAdapter
import com.oze.dev.db.base.ResponseStatus
import com.oze.dev.db.model.DeveloperInfo
import com.oze.dev.db.model.repo.DeveloperRepoResultItem
import com.oze.dev.utils.CONSTANTS
import com.oze.dev.utils.gone
import com.oze.dev.utils.showSnackBar
import com.oze.dev.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeveloperDetailsFragment : Fragment() {
  private lateinit var bi : FragmentDeveloperDetailsBinding
    private val viewModel by viewModels<DeveloperDetailViewModel>()
    private val userLoginName: String by lazy {
        arguments?.getString(CONSTANTS.INTENT_EXTRAS.DEVELOPER_DATA) as String
    }
    private val userLoginID: Int by lazy {
        arguments?.getInt(CONSTANTS.INTENT_EXTRAS.DEVELOPER_ID) as Int
    }
    private lateinit var deviceInfo: DeveloperInfo

    private lateinit var adapter: GenericListAdapter<DeveloperRepoResultItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // IInitializing DataBinding
        return  FragmentDeveloperDetailsBinding.inflate(inflater,container,false).apply {
            bi= this
            viewModel.getDeveloperData(user = userLoginName,id= userLoginID)
            viewModel.getUserRepos(user = userLoginName,id=userLoginID)
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
       * Initiating recyclerview
       * */
        callingRecyclerView()

        /*
        * Checking scrollview scroll end
        * */
        bi.repoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadNextPageRepos()
                }
            }
        })

        viewModel.getLocalDeveloperData(userLoginID).observe(viewLifecycleOwner,{
            it?.let { item ->
                /*Passing data to view*/
                 bi.setVariable(BR.selectedUser, item)
            }
        })

        viewModel.getLocalDeveloperRepo(userLoginID)?.observe(viewLifecycleOwner,{
            it?.let{item ->
                if(!item.developerRepoResult.isNullOrEmpty()){
                    deviceInfo = it
                    bi.favorite.visibility = View.VISIBLE
                    bi.favorite.isChecked = item.isFavorite
                    adapter.productItems = item.developerRepoResult as ArrayList<DeveloperRepoResultItem>
                    bi.layoutShimmerLoading.gone()
                    bi.repoList.visible()
                    setFavouriteCheck()
                }
            }
        })
        /*
               * Fetch Repo list
               * */
        viewModel.userReposResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                ResponseStatus.ERROR -> {
                    it.data?.let { item ->
                        if (item.page == 1) {
                            bi.noItemAvailable.visible()
                            bi.layoutShimmerLoading.gone()
                            bi.repoList.gone()
                        } else
                            bi.nestedScrollView.showSnackBar(
                                message = it.message.toString()
                            )
                    } ?: run {
                        bi.layoutShimmerLoading.gone()
                        bi.nestedScrollView.showSnackBar(
                            message = getString(R.string.error_internet),
                            action = getString(R.string.retry)
                        ) {
                            viewModel.retryConnection()
                        }

                    }
                }
                ResponseStatus.LOADING -> {
                    it.data?.let { item ->
                        if (item.page == 1) {
                            bi.layoutShimmerLoading.visible()
                            bi.repoList.gone()
                            bi.noItemAvailable.gone()
                        } else
                            bi.nestedScrollView.showSnackBar(
                                message = getString(R.string.loading_repos)
                            )
                    }
                }
            }

        })
    }

    private fun setFavouriteCheck(){
        bi.favorite.setOnCheckedChangeListener { compoundButton, b ->
            addRemoveFavourite(deviceInfo,value = b)
        }
    }

    private fun addRemoveFavourite(devInfo: DeveloperInfo, value: Boolean) {
        viewModel.addRemoveFavourite(devInfo,value)
    }

    /*
   * Initialize recyclerView with onClickListener
   * */
    @SuppressLint("ResourceType")
    private fun callingRecyclerView() {
        adapter = GenericListAdapter(R.layout.repo_view) { item, position ->
        }
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        bi.repoList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearView()

    }
}