package me.jludden.gygtempelhof.data

import me.jludden.gygtempelhof.data.ReviewsDataSource.LoadReviewssCallback

//todo class ReviewsRepository(val remoteData: ReviewsDataSource, val localData: ReviewsDataSource) : ReviewsDataSource
class ReviewsRepository() : ReviewsDataSource {

    override fun getReviews(callback: LoadReviewssCallback){
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun postReview(review: Review) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshReviews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {
        private var INSTANCE: ReviewsRepository? = null

        //return the singleton instance, creating it if necessary
        @JvmStatic fun getInstance(): ReviewsRepository {
            return INSTANCE ?: ReviewsRepository().apply { INSTANCE = this }
        }
    }

}