package com.oze.dev.ui.fragments.developers

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.oze.dev.db.base.ResponseStatusCallbacks
import com.oze.dev.db.base.ResultCallBack
import com.oze.dev.db.model.DeveloperData
import com.oze.dev.db.model.DeveloperInfo
import com.oze.dev.db.model.FetchDataModel
import com.oze.dev.ui.fragments.developers.usecase.DeveloperUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeveloperViewModel  @Inject constructor(private  val developerUseCase: DeveloperUseCase, private  val repository : DeveloperRepository):ViewModel(){
    private var _usersList = MutableLiveData<ResponseStatusCallbacks<FetchDataModel>>()
    val usersResponse: LiveData<ResponseStatusCallbacks<FetchDataModel>>
        get() = _usersList

    private var pagination = 1
    private var searchUser: String = "lagos"
    private var updatedItems = arrayListOf<DeveloperInfo>()

    /*
* load next page
* */
    fun loadNextPageUsers() {
        pagination++
        fetchDevelopersFromRemoteServer(pagination, searchUser)
    }

     val  getAllPagesDevelopers = Pager(PagingConfig(pageSize = 20,prefetchDistance = 2)){
        repository.getAllDevelopers()
    }.flow.cachedIn(viewModelScope)


    val  getAllDevelopers = Pager(PagingConfig(pageSize = 20,prefetchDistance = 2)){
        DeveloperRepository.PostDataSourceSearch(repository)
    }.flow

    /*
      * Query to fetch Developers from server
      * */
    private fun fetchDevelopersFromRemoteServer(pagination: Int, search: String){
        _usersList.value = ResponseStatusCallbacks.loading(
            data = FetchDataModel(
                page = pagination,
                usersInfo = null
            )
        )


        viewModelScope.launch {
            try {
                developerUseCase(page = pagination, category = search)
                    .let { user ->
                        when (user) {
                            is ResultCallBack.CallException -> {
                                _usersList.postValue(
                                    ResponseStatusCallbacks.error(
                                        null,
                                        message = user.exception.toString()
                                    )
                                )
                            }
                            is ResultCallBack.Error -> {
                                _usersList.postValue(
                                    ResponseStatusCallbacks.error(
                                        null,
                                        message = user.error
                                    )
                                )
                            }
                            is ResultCallBack.Success -> {
                                user.data.collect { dataset ->
                                    dataset.usersInfo.let {
                                        if (it.isNotEmpty()) {
                                                repository.addAllDevelopers(it)

//                                            if (pagination == 1) {
//                                                updatedItems = arrayListOf()
//                                                updatedItems.addAll(it)
//                                            } else {
//                                                updatedItems.addAll(it)
//                                            }
//                                            _usersList.postValue(
//                                                ResponseStatusCallbacks.success(
//                                                    data = FetchDataModel(
//                                                        page = pagination,
//                                                        usersInfo = updatedItems
//                                                    ),
//                                                    "Users received"
//                                                )
//                                            )
                                        } else
                                            _usersList.value = ResponseStatusCallbacks.error(
                                                data = FetchDataModel(
                                                    page = pagination,
                                                    usersInfo = null
                                                ),
                                                if (pagination == 1) "Sorry no Users received" else "Sorry no more Users available"
                                            )
                                    }
                                }
                            }
                        }

                    }
            } catch (e: Exception) {
                _usersList.value = ResponseStatusCallbacks.error(null, e.message.toString())
            }
        }

    }

    /*
* Search function for searching lagos developers
* */
    fun searchDevelopersFromRemote() {
        pagination = 1
        fetchDevelopersFromRemoteServer(pagination, "lagos")
    }
    /*
   * Retry connection if internet is not available
   * */
    fun retryConnection() {
        fetchDevelopersFromRemoteServer(pagination, searchUser)
    }


}
