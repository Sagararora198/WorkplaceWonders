package com.example.workplacewonders

import com.example.workplacewonders.data.repository.AuthRepository
import com.example.workplacewonders.ui.viewmodel.LoginViewModel
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @Mock
    private lateinit var authRepository: AuthRepository

    private lateinit var viewModel: LoginViewModel

    @Captor
    private lateinit var callbackCaptor: ArgumentCaptor<(Boolean, String?) -> Unit>
    @Before
    fun setup() {
        viewModel = LoginViewModel(authRepository)
    }

    @Test
    fun login_successful() = runBlockingTest {
        val email = "test@example.com"
        val password = "password"
        val role = "admin"


        viewModel.login(email, password) { success, role -> }

        Mockito.verify(authRepository).login(eq(email), eq(password), capture(callbackCaptor))

        callbackCaptor.value.invoke(true, role)

        val callback = mock<(Boolean, String?) -> Unit>()
        callback(true, role)

        verify(callback).invoke(true, role)
    }





    @Test
    fun login_failure() = runBlockingTest {
        val email = "test@example.com"
        val password = "password"
        viewModel.login(email, password) { success, role -> }

        Mockito.verify(authRepository).login(eq(email), eq(password), capture(callbackCaptor))

        callbackCaptor.value.invoke(false, null)

        val callback = mock<(Boolean, String?) -> Unit>()
        callback(false, null)

        verify(callback).invoke(false, null)
    }

    @Test
    fun logout_callsRepository() {
        viewModel.logout()

        verify(authRepository).logout()
    }
}