package me.jludden.gygtempelhof.data

import me.jludden.gygtempelhof.data.ReviewsDataSource.LoadReviewsCallback
import me.jludden.gygtempelhof.data.model.Review

//todo class ReviewsRepository(val remoteData: ReviewsDataSource, val localData: ReviewsDataSource) : ReviewsDataSource
class ReviewsRepository(val localDataSource: ReviewsDataSource, val remoteDataSource: ReviewsDataSource) : ReviewsDataSource {

    var cacheIsDirty = false
    var cachedReviews:  ArrayList<Review> = ArrayList()

    override fun getReviews(callback: LoadReviewsCallback){

        if(!cacheIsDirty && !cachedReviews.isEmpty()) {
            callback.onReviewsLoaded(cachedReviews)
            return
        }

        remoteDataSource.getReviews(callback)
    }

    override fun postReview(review: Review) {
        //todo add to cache, but is ui up to date?
        cachedReviews.add(review)
        remoteDataSource.postReview(review)
    }

    override fun refreshReviews() {
        cacheIsDirty = true
    }


    companion object {
        private var INSTANCE: ReviewsRepository? = null

        //return the singleton instance, creating it if necessary
        @JvmStatic fun getInstance(localDataSource: ReviewsDataSource, remoteDataSource: ReviewsDataSource): ReviewsRepository {
            return INSTANCE ?: ReviewsRepository(localDataSource, remoteDataSource).apply { INSTANCE = this }
        }
    }

}