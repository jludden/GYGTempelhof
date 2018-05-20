package me.jludden.gygtempelhof.data.local

import android.content.Context
import kotlinx.coroutines.experimental.launch
import me.jludden.gygtempelhof.data.ReviewsDataSource
import me.jludden.gygtempelhof.data.model.Review


class LocalDataSource private constructor(val reviewDao: ReviewDao) : ReviewsDataSource {

    var dbIsDirty = false

    //Save a review to the on-disk database
    override fun postReview(review: Review, callback: ReviewsDataSource.PostReviewCallback) {
        launch { postReviewAsync(review, callback) }
    }

    //update the database in the background
    private fun postReviewAsync(review: Review,  callback: ReviewsDataSource.PostReviewCallback) {
        if (dbIsDirty) { //clear existing rows from the table
            reviewDao.deleteAllReviews()
            dbIsDirty = false
        }

        reviewDao.insertReview(createTableRow(review))
        callback.onReviewPosted(review.review_id)
    }

    //Get reviews from the on-disk database
    override fun getReviews(callback: ReviewsDataSource.LoadReviewsCallback) {
       launch { getReviewsAsync(callback) }
    }

    private fun getReviewsAsync(callback: ReviewsDataSource.LoadReviewsCallback) {
        val reviews = reviewDao.getReviews()
        if (reviews.isNotEmpty()) {
            callback.onReviewsLoaded(reviews.map { row -> mapRowToReview(row) })
        } else {
            callback.onDataNotAvailable("No local data found")
        }
    }

    //helper function to map from the Room Database Review rows to the Retrofit Review data class
    private fun mapRowToReview(row: ReviewRow): Review {
        return Review().apply {
            review_id = row.id
            title = row.title
            message = row.message
            date = row.date
            rating = row.rating.toString()
        }
    }

    //helper function to map a Retrofit Review to a Room Review Table Row
    private fun createTableRow(review: Review): ReviewRow {
        return with(review) {
            me.jludden.gygtempelhof.data.local.ReviewRow(
                    review_id,
                    title,
                    message,
                    date,
                    rating.toFloat()
            )
        }
    }

    //mark the db as dirty. It will be cleared the next time reviews are added
    override fun refreshReviews() {
        dbIsDirty = true
    }

    companion object {
        private var INSTANCE: LocalDataSource? = null

        //return the singleton instance, creating it if necessary
        @JvmStatic fun getInstance(context: Context): LocalDataSource {
            return INSTANCE ?: LocalDataSource(ReviewsDatabase.getInstance(context).reviewDao())
                    .apply { INSTANCE = this }
        }
    }
}
