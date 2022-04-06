package com.oze.dev.core.iinterface

import com.oze.dev.db.model.DeveloperInfo

interface IFavourite {
    fun deleteFavourite(id:Int, name:String)
    fun openDevDetails(developerInfo: DeveloperInfo)
}