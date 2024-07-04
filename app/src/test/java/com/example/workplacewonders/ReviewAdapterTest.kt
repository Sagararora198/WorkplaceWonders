package com.example.workplacewonders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.example.workplacewonders.data.model.Review
import com.example.workplacewonders.databinding.ItemreviewBinding
import com.example.workplacewonders.ui.adapter.ReviewAdapter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ReviewAdapterTest {

    @Mock
    private lateinit var parent: ViewGroup

    @Mock
    private lateinit var inflater: LayoutInflater

    @Mock
    private lateinit var binding: ItemreviewBinding

    private lateinit var adapter: ReviewAdapter

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        `when`(parent.context).thenReturn(mock(Context::class.java))
        `when`(LayoutInflater.from(parent.context)).thenReturn(inflater)
        `when`(inflater.inflate(R.layout.itemreview, parent, false)).thenReturn(mock(View::class.java))

        val reviews = listOf(
            Review("user1", 5f, "Great!"),
            Review("user2", 4f, "Good"),
            Review("user3", 3f, "Average")
        )
        adapter = ReviewAdapter(reviews)
    }

    @Test
    fun getItemCount_returnsCorrectSize() {
        val expectedSize = 3
        assertEquals(expectedSize, adapter.itemCount)
    }

    @Test
    fun onCreateViewHolder_inflatesView() {
        val viewHolder = adapter.onCreateViewHolder(parent, 0)
        verify(inflater).inflate(R.layout.itemreview, parent, false)

    }

    @Test
    fun onBindViewHolder_bindsData() {
        val review = Review("user1", 5f, "Great!")
        val viewHolder = adapter.onCreateViewHolder(parent, 0) as ReviewAdapter.ReviewViewHolder

        `when`(binding.userName).thenReturn(mock(TextView::class.java))
        `when`(binding.ratingBar).thenReturn(mock(RatingBar::class.java))
        `when`(binding.comment).thenReturn(mock(TextView::class.java))

        viewHolder.bind(review)

        verify(binding.userName).text = review.userId
        verify(binding.ratingBar).rating = review.rating
        verify(binding.comment).text = review.comment
    }
}