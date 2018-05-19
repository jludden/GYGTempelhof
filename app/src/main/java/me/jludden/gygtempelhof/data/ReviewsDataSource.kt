package me.jludden.gygtempelhof.data

import me.jludden.gygtempelhof.data.model.Review

interface ReviewsDataSource {

    fun getReviews(callback: LoadReviewsCallback)

    fun postReview(review: Review)

    fun refreshReviews()

    interface LoadReviewsCallback {

        fun onReviewsLoaded(tasks: List<Review>)

        fun onDataNotAvailable(message: String?)
    }

}