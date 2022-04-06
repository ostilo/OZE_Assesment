package com.oze.dev.ui.fragments.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private  val  repository: FavouriteRepository) : ViewModel(){
    fun deleteFromFavourite(id: Int) {
        repository.deleteFromFavourite(id)
    }


    val  getAllFavouriteDevelopers = Pager(PagingConfig(pageSize = 20,prefetchDistance = 2)){
        repository.getAllFavouriteDevelopers()
    }.flow.cachedIn(viewModelScope)
}