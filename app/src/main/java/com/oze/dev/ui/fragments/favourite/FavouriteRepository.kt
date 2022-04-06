package com.oze.dev.ui.fragments.favourite

import com.oze.dev.db.local.DevelopersDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class FavouriteRepository @Inject constructor(private  val developersDao: DevelopersDao) {

    fun getAllFavouriteDevelopers() = developersDao.getAllFavouriteDevelopers(true)
    fun deleteFromFavourite(id: Int) {
        runBlocking {
            launch (Dispatchers.IO){
                developersDao.deleteFromFavourite(id,false)
            }
        }
    }

}