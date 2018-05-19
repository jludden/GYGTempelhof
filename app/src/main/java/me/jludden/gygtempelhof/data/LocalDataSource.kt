package me.jludden.gygtempelhof.data

import android.content.Context
import me.jludden.gygtempelhof.data.model.Review

class LocalDataSource private constructor(context: Context) : ReviewsDataSource {

    override fun postReview(review: Review, callback: ReviewsDataSource.PostReviewCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getReviews(callback: ReviewsDataSource.LoadReviewsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //unused
    override fun refreshReviews() { }

    companion object {
        private var INSTANCE: LocalDataSource? = null

        //return the singleton instance, creating it if necessary
        @JvmStatic fun getInstance(context: Context): LocalDataSource {
            return INSTANCE ?: LocalDataSource(context).apply { INSTANCE = this }
        }
    }
}