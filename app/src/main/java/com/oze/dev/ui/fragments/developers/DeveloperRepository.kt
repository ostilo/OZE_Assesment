package com.oze.dev.ui.fragments.developers

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oze.dev.db.local.DevelopersDao
import com.oze.dev.db.model.DeveloperInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DeveloperRepository @Inject constructor(private  val developersDao: DevelopersDao) {
     fun addAllDevelopers(it: List<DeveloperInfo>) {
         runBlocking {
            launch (Dispatchers.IO){
                var allFavourite = developersDao.getALlFavouriteDevs(true)
                if(!allFavourite.isNullOrEmpty()){
                    allFavourite.forEach { item ->
                        it.forEach {
                            if(item.id == it.id){
                                it.isFavorite = item.isFavorite
                            }
                        }
                    }
                }
                developersDao.insertAll(it)
            }
        }

    }
    fun getAllDevelopers() =  developersDao.getAllDevelopers()
    fun getAllDevelopersQQ() =  developersDao.getAllDevelopersQQ()


    class PostDataSourceSearch @Inject constructor(private  val repository: DeveloperRepository) : PagingSource<Int, DeveloperInfo>(){
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DeveloperInfo> {
            val currentLoadingPageKey = params.key ?: 0
            val prevKey = if (currentLoadingPageKey == 0) null else currentLoadingPageKey - 1
            var responseData = mutableListOf<DeveloperInfo>()
            runBlocking {
                launch (Dispatchers.IO){
                     responseData = repository.getAllDevelopersQQ()
                }
            }
            var resul = responseData
            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )

        }

        override fun getRefreshKey(state: PagingState<Int, DeveloperInfo>): Int? {
          return  state?.anchorPosition
        }
    }


}

