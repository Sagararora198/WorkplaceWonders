package com.example.workplacewonders
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.workplacewonders.data.model.Asset
import com.example.workplacewonders.data.model.Review
import com.example.workplacewonders.data.repository.AssetNReviewRepository
import com.example.workplacewonders.ui.viewmodel.EmployeeViewModel
import com.nhaarman.mockitokotlin2.eq
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EmployeeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: AssetNReviewRepository

    @InjectMocks
    private lateinit var viewModel: EmployeeViewModel

    @Mock
    private lateinit var assetsObserver: Observer<List<Asset>>

    @Mock
    private lateinit var assetObserver: Observer<Asset>

    @Mock
    private lateinit var reviewsObserver: Observer<List<Review>>

    @Captor
    private lateinit var captorAssets: ArgumentCaptor<(List<Asset>) -> Unit>

    @Captor
    private lateinit var captorAsset: ArgumentCaptor<(Asset) -> Unit>

    @Captor
    private lateinit var captorReviews: ArgumentCaptor<(List<Review>) -> Unit>

    @Before
    fun setup() {
        viewModel.assets.observeForever(assetsObserver)
        viewModel.asset.observeForever(assetObserver)
        viewModel.reviews.observeForever(reviewsObserver)
    }

    @Test
    fun fetchAssets_updatesAssetsLiveData() = runBlockingTest {
        val dummyAssets = listOf(Asset("1", "Asset 1"), Asset("2", "Asset 2"))

        Mockito.doNothing().`when`(repository).getAssets(capture(captorAssets))

        viewModel.fetchAssets()

        captorAssets.value.invoke(dummyAssets)

        Mockito.verify(assetsObserver).onChanged(dummyAssets)
    }

    @Test
    fun addReview_updatesReviewsLiveData() = runBlockingTest {
        val assetId = "1"
        val review = Review("Review 1")

        viewModel.addReview(assetId, review)

        val updatedReviews = listOf(review)
        Mockito.verify(reviewsObserver).onChanged(updatedReviews)
    }

    @Test
    fun fetchReviews_updatesReviewsLiveData() = runBlockingTest {
        val assetId = "1"
        val dummyReviews = listOf(Review("Review 1"), Review("Review 2"))

        Mockito.doNothing().`when`(repository).getReviews(eq(assetId), capture(captorReviews))

        viewModel.fetchReviews(assetId)

        captorReviews.value.invoke(dummyReviews)

        Mockito.verify(reviewsObserver).onChanged(dummyReviews)
    }

    @Test
    fun fetchAssetById_updatesAssetLiveData() = runBlockingTest {
        val assetId = "1"
        val dummyAsset = Asset(assetId, "Asset 1")

        Mockito.doNothing().`when`(repository).getAssetById(eq(assetId), capture(captorAsset))

        viewModel.fetchAssetById(assetId)

        captorAsset.value.invoke(dummyAsset)

        Mockito.verify(assetObserver).onChanged(dummyAsset)
    }

    private fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()
}