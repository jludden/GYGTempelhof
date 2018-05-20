package me.jludden.gygtempelhof.reviews

import android.app.Activity
import android.util.Log
import me.jludden.gygtempelhof.addreview.AddReviewsActivity
import me.jludden.gygtempelhof.data.*
import me.jludden.gygtempelhof.data.ReviewsDataSource
import me.jludden.gygtempelhof.data.model.Review
import me.jludden.gygtempelhof.reviews.ReviewsFilterType.*

class ReviewsPresenter(
        val reviewsRepo: ReviewsRepository,
        val reviewsView: ReviewsContract.View)
    : ReviewsContract.Presenter {

    override var currentFiltering = ReviewsFilterType.ALL_REVIEWS

    init {
        reviewsView.presenter = this
    }

    override fun start() {
        loadReviews(false)
    }

    override fun loadReviews(forceUpdate: Boolean) {
        loadReviewsHelper(forceUpdate, showLoadingUI = true)
    }

    override fun addNewReview() {
        reviewsView.showAddReview()
    }

    override fun reviewAddedResult(requestCode: Int, resultCode: Int) {
        if (AddReviewsActivity.ADD_REVIEW ==
                requestCode && Activity.RESULT_OK == resultCode) {
            reviewsView.showSuccessfullyAddedMessage() //review added successfully
        }
    }

    private fun loadReviewsHelper(forceUpdate: Boolean, showLoadingUI: Boolean = false) {
        if (showLoadingUI) {
            reviewsView.setLoadingIndicator(true)
        }
        if (forceUpdate) {
            reviewsRepo.refreshReviews()
        }

        reviewsRepo.getReviews(object : ReviewsDataSource.LoadReviewsCallback {
            override fun onReviewsLoaded(reviews: List<Review>) {
                val reviewsToShow = ArrayList<Review>()
                for(review in reviews){
                    when(currentFiltering) {
                        ALL_REVIEWS -> reviewsToShow.add(review)
                        LOW_RATING -> if(review.rating.toFloat() <= 2) reviewsToShow.add(review)
                        HIGH_RATING -> if(review.rating.toFloat() >= 4) reviewsToShow.add(review)
                    }
                }

                reviewsView.setLoadingIndicator(false)
                reviewsView.showReviews(reviewsToShow)
            }

            override fun onDataNotAvailable(message: String?) {
                Log.e("jludden", "onDataNotAvailable $message")
                reviewsView.setLoadingIndicator(false)
                reviewsView.showLoadingError()
            }

        })
    }
}