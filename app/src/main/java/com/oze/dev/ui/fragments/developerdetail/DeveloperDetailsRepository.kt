package com.oze.dev.ui.fragments.developerdetail

import androidx.lifecycle.LiveData
import com.oze.dev.db.local.DevelopersDao
import com.oze.dev.db.model.DeveloperData
import com.oze.dev.db.model.DeveloperInfo
import com.oze.dev.db.model.repo.DeveloperRepoResult
import com.oze.dev.db.model.repo.DeveloperRepoResultItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DeveloperDetailsRepository@Inject constructor(private  val developersDao: DevelopersDao) {

    fun updateDeveloperData(data: DeveloperData) {
        runBlocking {
            launch (Dispatchers.IO){
                developersDao.updateDeveloperData(data,data.id)
            }
        }

    }

    fun getLocalDeveloperData(id:Int):  LiveData<DeveloperData> {
        return   developersDao.getLocalDeveloperData(id)
    }

    fun updateDeveloperRepo(it: List<DeveloperRepoResultItem>,id:Int) {
        runBlocking {
            launch (Dispatchers.IO){
                developersDao.updateDeveloperRepo(it,id)
            }
        }

    }


    fun getLocalDeveloperRepo(id: Int): LiveData<DeveloperInfo> {
        return   developersDao.getDevelopersInfoById(id)
    }
    fun addRemoveFavourite(devInfo: DeveloperInfo, value: Boolean) {
        runBlocking {
            launch (Dispatchers.IO){
                developersDao.updateFavouriteTable(devInfo.id,value)
            }
        }

    }

}