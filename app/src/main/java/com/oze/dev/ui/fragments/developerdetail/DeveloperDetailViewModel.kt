package com.oze.dev.ui.fragments.developerdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oze.dev.db.base.ResponseStatusCallbacks
import com.oze.dev.db.base.ResultCallBack
import com.oze.dev.db.model.DeveloperData
import com.oze.dev.db.model.DeveloperInfo
import com.oze.dev.db.model.FetchDataModel
import com.oze.dev.db.model.repo.DeveloperRepoResultItem
import com.oze.dev.ui.fragments.developerdetail.usecase.SpecificDeveloperUseCase
import com.oze.dev.ui.fragments.developerdetail.usecase.SpecificSpecificDeveloperReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject


@HiltViewModel
class DeveloperDetailViewModel @Inject constructor(
    private val specificDeveloperUseCase: SpecificDeveloperUseCase,
    private val specificDeveloperReposUseCase: SpecificSpecificDeveloperReposUseCase,
    private val repository: DeveloperDetailsRepository
): ViewModel() {

    private val _userReposList = MutableLiveData<ResponseStatusCallbacks<FetchDataModel>>()
    val userReposResponse: LiveData<ResponseStatusCallbacks<FetchDataModel>>
        get() = _userReposList

    private val _selectedUser = MutableLiveData<ResponseStatusCallbacks<DeveloperData>>()
    val selectedUserResponse: LiveData<ResponseStatusCallbacks<DeveloperData>>
        get() = _selectedUser

    private var pagination = 1
    private var selectedUser = StringUtils.EMPTY
    private var selectedUserID = 0

    private var updatedItems = arrayListOf<DeveloperRepoResultItem>()

    fun getLocalDeveloperData(id:Int) = repository.getLocalDeveloperData(id)

    fun getLocalDeveloperRepo(id:Int) = repository.getLocalDeveloperRepo(id)

    /* Observed function for initiate searching */
    private fun fetchReposFromRemoteServer(pagination: Int, user: String,id: Int) {
        _userReposList.value = ResponseStatusCallbacks.loading(
            data = FetchDataModel(
                page = pagination,
                usersInfo = null
            )
        )
        viewModelScope.launch {
            try {
                specificDeveloperReposUseCase(page = pagination, user = user).let { user ->
                    when (user) {
                        is ResultCallBack.CallException -> {
                            _userReposList.postValue(
                                ResponseStatusCallbacks.error(
                                    null,
                                    message = user.exception.toString()
                                )
                            )
                        }
                        is ResultCallBack.Error -> {
                            _userReposList.postValue(
                                ResponseStatusCallbacks.error(
                                    null,
                                    message = user.error
                                )
                            )
                        }
                        is ResultCallBack.Success -> {
                            user.data.collect { dataset ->
                                dataset.let {
                                    if (it.isNotEmpty()) {
                                            updatedItems.addAll(it)
                                            repository.updateDeveloperRepo(updatedItems,selectedUserID)
                                    } else
                                        _userReposList.value = ResponseStatusCallbacks.error(
                                            data = FetchDataModel(
                                                page = pagination,
                                                usersInfo = null
                                            ),
                                            if (pagination == 1) "Sorry no Repo received" else "Sorry no more Repos available"
                                        )
                                }
                            }
                        }
                    }

                }
            } catch (e: Exception) {
                _userReposList.value = ResponseStatusCallbacks.error(null, e.message.toString())
            }
        }
    }


    fun getDeveloperData(user: String,id: Int) {
        _selectedUser.value = ResponseStatusCallbacks.loading(null)
        viewModelScope.launch {
            try {
                selectedUser = user
                selectedUserID = id
                specificDeveloperUseCase(user).let {
                    when (it) {
                        is ResultCallBack.CallException -> {
                        }
                        is ResultCallBack.Error -> {
                            _selectedUser.value = ResponseStatusCallbacks.error(
                                data = null,
                                message = it.error
                            )
                        }
                        is ResultCallBack.Success -> {
                            repository.updateDeveloperData(it.data)
                        }
                    }
                }
            } catch (e: Exception) {
                _selectedUser.value = ResponseStatusCallbacks.error(null, e.message.toString())
            }
        }
    }
    /*load next page*/
    fun loadNextPageRepos() {
        pagination++
        fetchReposFromRemoteServer(pagination, selectedUser,selectedUserID)
    }

    fun getUserRepos(user: String,id: Int) {
        fetchReposFromRemoteServer(pagination, user,id)

    }
    /*Retry connection if internet is not available*/
    fun retryConnection() {
        fetchReposFromRemoteServer(pagination, selectedUser,selectedUserID)
    }

    fun clearView() {
        selectedUser = StringUtils.EMPTY
    }
    fun addRemoveFavourite(devInfo: DeveloperInfo, value: Boolean) {
        viewModelScope.launch {
            repository.addRemoveFavourite(devInfo,value)

        }
    }
}