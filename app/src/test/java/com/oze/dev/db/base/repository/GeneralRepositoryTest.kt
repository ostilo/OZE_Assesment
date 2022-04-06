package com.oze.dev.db.base.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.oze.dev.MainCoroutinesRule
import com.oze.dev.MockTestUtil
import com.oze.dev.core.AuthApi
import com.oze.dev.core.auth.remote.ApiResponse
import com.oze.dev.db.base.GeneralRepository
import com.oze.dev.db.base.ResultCallBack
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class GeneralRepositoryTest {

    // Subject under test
    private lateinit var repository: GeneralRepository

    @MockK
    private lateinit var authApi: AuthApi

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test getspecificuser() returns single user`() = runBlocking {
        // Given
        repository = GeneralRepository(authApi)
        val searchUser = MockTestUtil.createSingleUser()

        // When
        coEvery { authApi.getSpecificUserData(any()) }.returns(ApiResponse.create(response = Response.success(searchUser)))

        // Invoke
        val usersFlow = repository.getSpecificDeveloper("ostilo")

        // Then
        MatcherAssert.assertThat(usersFlow, CoreMatchers.notNullValue())

        val result = (usersFlow as ResultCallBack.Success).data
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(result.login, CoreMatchers.`is`("alimohamadi17"))
    }

    @Test
    fun `test searchUser returns list of users `() = runBlocking {
        //Given
        repository = GeneralRepository(authApi)
        val searchUser = MockTestUtil.createUsers()

        //When
        coEvery {
            authApi.getSearchDeveloperData(any(), any())
        }.returns(ApiResponse.create(response = Response.success(searchUser)))

//        //Invoke
        val usersFlow = repository.getSpecificDeveloper("ostilo")
//
        //Then
//        MatcherAssert.assertThat(usersFlow, CoreMatchers.notNullValue())
//
//        val result = (usersFlow as ResultCallBack.Success)
//        val users = result.data
//        MatcherAssert.assertThat(users, CoreMatchers.notNullValue())
//        MatcherAssert.assertThat(users.usersInfo, CoreMatchers.notNullValue())
//        MatcherAssert.assertThat(
//            users.usersInfo.size,
//            CoreMatchers.`is`(searchUser.usersInfo.size)
//        )

    }

    @Test
    fun `test searchUserRepos returns list of users repos`() = runBlocking {
        //Given
        repository = GeneralRepository(authApi)
        val searchUser = MockTestUtil.createRepos()

        //When
        coEvery {
            authApi.getSpecificDeveloperReposData(any(), any())
        }.returns(ApiResponse.create(response = Response.success(searchUser)))

        //Invoke
        val usersFlow = repository.getSpecificDeveloperRepos(1, "Ali")

        //Then
        MatcherAssert.assertThat(usersFlow, CoreMatchers.notNullValue())

        val result = (usersFlow as ResultCallBack.Success).data
        val users = result.first()
        MatcherAssert.assertThat(users, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(users, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            users.size,
            CoreMatchers.`is`(searchUser.size)
        )

    }

    @After
    fun tearDown() {

    }
}