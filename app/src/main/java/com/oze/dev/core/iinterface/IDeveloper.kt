package com.oze.dev.core.iinterface

import com.oze.dev.db.model.DeveloperInfo

interface IDeveloper {
    fun openDeveloperDetails(deInfo: DeveloperInfo)
}