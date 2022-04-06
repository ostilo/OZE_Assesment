package com.oze.dev.ui.fragments.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.oze.dev.core.iinterface.IFavourite
import com.oze.dev.databinding.FragmentFavouriteBinding
import com.oze.dev.db.adapters.MainListSearchAdapter
import com.oze.dev.db.model.DeveloperInfo
import com.oze.dev.utils.gone
import com.oze.dev.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteFragment : Fragment(), IFavourite {

    private lateinit var bi : FragmentFavouriteBinding
    lateinit var  favAdapter : MainListSearchAdapter
    private val viewModel by viewModels<FavouriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentFavouriteBinding.inflate(inflater,container,false).apply {
            bi = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewBasedOnRepo()
    }

    private fun  setViewBasedOnRepo(){
        favAdapter = MainListSearchAdapter(this)
        lifecycleScope.launch {
            favAdapter.loadStateFlow.distinctUntilChangedBy {
                it.refresh
            }.collect{
                val loist = favAdapter.snapshot()
                if(loist.size == 0){
                    bi.layoutEmptyFavorite.visible()
                    bi.productList.gone()
                }else{
                    bi.layoutEmptyFavorite.gone()
                    bi.productList.visible()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getAllFavouriteDevelopers.collectLatest {
                if(it != PagingData.empty<DeveloperInfo>()){
                    bi.layoutEmptyFavorite.gone()
                    bi.productList.visible()
                }else{
                    bi.layoutEmptyFavorite.visible()
                    bi.productList.gone()
                }
                bi.productList.adapter = favAdapter
                favAdapter.submitData(it)
            }
        }
    }

    override fun deleteFavourite(id: Int,name:String) {
        val dialog: AlertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Delete Favourite")
            .setMessage("Are you sure you want to delete $name, from your favourite list.")
            .setPositiveButton("Delete"
            ) { dialog, which -> redirectToDelete(id) }
            .setNegativeButton("Cancel"
            ) { dialog, which -> dialog.dismiss() }.create()
        dialog.show()
    }

    override fun openDevDetails(developerInfo: DeveloperInfo) {
        findNavController().navigate(FavouriteFragmentDirections.actionFavouriteFragmentToDeveloperDetailsFragment(developerInfo.login,developerInfo.id))
    }

    private fun redirectToDelete(id: Int) {
        viewModel.deleteFromFavourite(id)
    }
}