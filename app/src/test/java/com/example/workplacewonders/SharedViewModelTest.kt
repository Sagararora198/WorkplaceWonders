package com.example.workplacewonders

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.workplacewonders.ui.viewmodel.SharedViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SharedViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SharedViewModel

    @Mock
    private lateinit var userIdObserver: Observer<String>

    @Before
    fun setup() {
        viewModel = SharedViewModel()
        viewModel.userId.observeForever(userIdObserver)
    }

    @Test
    fun setUserId_updatesUserIdLiveData() {
        val userId = "test_user_id"
        viewModel.setUserId(userId)

        verify(userIdObserver).onChanged(userId)
    }
}