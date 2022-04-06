package com.oze.dev.db.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oze.dev.db.model.DeveloperData
import com.oze.dev.db.model.DeveloperInfo
import com.oze.dev.db.model.repo.DeveloperRepoResultItem

@Dao
interface DevelopersDao {

     @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertAll(devs: List<DeveloperInfo>?)

     @Query("SELECT * from developers")
     fun getDevelopersList(): LiveData<List<DeveloperInfo?>?>?

    @Query("SELECT * FROM developers")
     fun getAllDevelopersQQ(): MutableList<DeveloperInfo>

    @Query("SELECT * FROM developers")
     fun getAllDevelopers(): PagingSource<Int, DeveloperInfo>

    @Query("Update developers set isFavorite = :value where id = :id")
     fun updateFavouriteTable(id: Int,value:Boolean)

    @Query("SELECT * FROM developers where isFavorite = :value")
    fun getALlFavouriteDevs(value:Boolean): List<DeveloperInfo>
     //

    @Query("SELECT count(id) FROM developers")
      fun numberOfItemsInDB() : Int

    @Query("Update developers set isFavorite = :value where id = :id")
     fun deleteFromFavourite( id: Int,value:Boolean)

    @Query("Update developers set developerData = :bio where id = :id")
     fun updateDeveloperData(bio: DeveloperData,id:Int)

     @Insert(onConflict = OnConflictStrategy.IGNORE)
      fun addDeveloperData(bio: DeveloperData)

    @Query("SELECT * FROM developers where isFavorite = :value")
     fun getAllFavouriteDevelopers(value:Boolean): PagingSource<Int, DeveloperInfo>

    @Query("SELECT developerData FROM developers where id = :id")
     fun getLocalDeveloperData(id:Int): LiveData<DeveloperData>


    @Query("Update developers set developerRepoResult = :it where id = :id")
    fun updateDeveloperRepo(it: List<DeveloperRepoResultItem>, id:Int)


    @Query("Update developers set developerRepoResult = :it where id = :id")
    fun insertDeveloperRepo(it: List<DeveloperRepoResultItem>, id:Int)


    @Query("SELECT * FROM developers where id = :id")
    fun getDevelopersInfoById(id:Int): LiveData<DeveloperInfo>

}