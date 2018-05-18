package me.jludden.gygtempelhof.data

interface ReviewsDataSource {

    fun getReviews(callback: LoadReviewssCallback)

    fun postReview(review: Review)

    fun refreshReviews()

    interface LoadReviewssCallback {

        fun onReviewsLoaded(tasks: List<Review>)

        fun onDataNotAvailable()
    }

}