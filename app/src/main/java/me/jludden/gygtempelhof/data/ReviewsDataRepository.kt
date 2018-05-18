package me.jludden.gygtempelhof.data

import me.jludden.gygtempelhof.data.ReviewsDataSource.LoadReviewssCallback

//todo class ReviewsDataRepository(val remoteData: ReviewsDataSource, val localData: ReviewsDataSource) : ReviewsDataSource
class ReviewsDataRepository() : ReviewsDataSource {

    override fun getReviews(callback: LoadReviewssCallback){
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun postReview(review: Review) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshReviews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}