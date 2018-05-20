package me.jludden.gygtempelhof.data

import me.jludden.gygtempelhof.data.model.Review

interface ReviewsDataSource {

    fun getReviews(callback: LoadReviewsCallback)

    fun postReview(review: Review, callback: PostReviewCallback)

    fun refreshReviews()

    interface LoadReviewsCallback {

        fun onReviewsLoaded(reviews: List<Review>)

        fun onDataNotAvailable(message: String?)
    }

    interface PostReviewCallback {

        fun onReviewPosted(id: Int)

        fun onReviewPostFailure(message: String?)
    }

}