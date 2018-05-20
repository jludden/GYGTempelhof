package me.jludden.gygtempelhof.data

import me.jludden.gygtempelhof.data.ReviewsDataSource.LoadReviewsCallback
import me.jludden.gygtempelhof.data.model.Review

class ReviewsRepository(
        val localDataSource: ReviewsDataSource,
        val remoteDataSource: ReviewsDataSource) : ReviewsDataSource {

    var cacheIsDirty = false
    var cachedReviews:  ArrayList<Review> = ArrayList() //in-memory cache

    //Get reviews from one of the 3 options - remote server call, local persistent database,
    // or in-memory cache
    override fun getReviews(callback: LoadReviewsCallback) {
        when {
            cacheIsDirty -> getReviewsFromRemote(callback)
            cachedReviews.isEmpty() -> getReviewsFromLocal(callback)
            else -> callback.onReviewsLoaded(cachedReviews)
        }
    }

    //First post the review to the remote web server
    //After the server response we can add it to the cache and local db
    override fun postReview(review: Review, callback: ReviewsDataSource.PostReviewCallback) {
        remoteDataSource.postReview(review, object : ReviewsDataSource.PostReviewCallback {
            override fun onReviewPosted(id: Int) {
                review.review_id = id
                cachedReviews.add(review)
                saveReviewToLocal(review)
                callback.onReviewPosted(id)
            }

            override fun onReviewPostFailure(message: String?) {
                callback.onReviewPostFailure(message)
            }
        })
    }

    //mark the local cache as dirty - next GetReviews() call will be to the remote data source
    override fun refreshReviews() {
        cacheIsDirty = true
    }

    //helper function to populate tasks from the remote data source
    //we need to also save the tasks to the local db and in-memory cache
    private fun getReviewsFromRemote(callback: LoadReviewsCallback) {
        remoteDataSource.getReviews(object : LoadReviewsCallback{
            override fun onReviewsLoaded(reviews: List<Review>) {
                updateCache(reviews)
                updateLocalDataSource(reviews)
                callback.onReviewsLoaded(reviews)
            }

            override fun onDataNotAvailable(message: String?) {
                callback.onDataNotAvailable(message)
            }
        })
    }

    //helper function to populate tasks from the local db
    //we also need to save to the in-memory db
    private fun getReviewsFromLocal(callback: LoadReviewsCallback) {
        localDataSource.getReviews(object : LoadReviewsCallback{
            override fun onReviewsLoaded(reviews: List<Review>) {
                updateCache(reviews)
                callback.onReviewsLoaded(reviews)
            }

            override fun onDataNotAvailable(message: String?) {
                callback.onDataNotAvailable(message)
            }
        })
    }

    private fun updateCache(reviews: List<Review>) {
        cachedReviews = ArrayList(reviews)
        cacheIsDirty = false
    }

    private fun updateLocalDataSource(reviews: List<Review>) {
        localDataSource.refreshReviews()
        for (review in reviews) {
            saveReviewToLocal(review)
        }
    }

    private fun saveReviewToLocal(review: Review) {
        localDataSource.postReview(review, object : ReviewsDataSource.PostReviewCallback{
            override fun onReviewPosted(id: Int) { }
            override fun onReviewPostFailure(message: String?) { } // never called
        })
    }

    companion object {
        private var INSTANCE: ReviewsRepository? = null

        //return the singleton instance, creating it if necessary
        @JvmStatic fun getInstance(localDataSource: ReviewsDataSource, remoteDataSource: ReviewsDataSource): ReviewsRepository {
            return INSTANCE ?: ReviewsRepository(localDataSource, remoteDataSource).apply { INSTANCE = this }
        }
    }

}