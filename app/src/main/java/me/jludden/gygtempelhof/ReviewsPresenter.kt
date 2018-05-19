package me.jludden.gygtempelhof

import android.util.Log
import me.jludden.gygtempelhof.data.*
import me.jludden.gygtempelhof.data.ReviewsDataSource
import me.jludden.gygtempelhof.data.model.Review

class ReviewsPresenter(
        val reviewsRepo: ReviewsRepository,
        val reviewsView: ReviewsContract.View)
    : ReviewsContract.Presenter {

    override var currentFiltering = ReviewsFilterType.ALL_REVIEWS

    private var firstLoad = true

    init {
        reviewsView.presenter = this
    }



    override fun start() {
        loadReviews(false)
    }

    override fun loadReviews(forceUpdate: Boolean) {
        loadReviewsHelper(forceUpdate || firstLoad)
        firstLoad = false
    }

    override fun addNewReview() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //review added successfully
    override fun reviewAddedResult(requestCode: Int, resultCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun loadReviewsHelper(forceUpdate: Boolean, showLoadingUI: Boolean = false) {
        if (showLoadingUI) {
            reviewsView.setLoadingIndicator(true)
        }
        if (forceUpdate) {
            reviewsRepo.refreshReviews()
        }

        reviewsRepo.getReviews(object : ReviewsDataSource.LoadReviewsCallback {
            override fun onReviewsLoaded(tasks: List<Review>) {
                //todo apply filters? show which filter is active?
                reviewsView.setLoadingIndicator(false)

                reviewsView.showReviews(tasks)

            }

            override fun onDataNotAvailable(message: String?) {
                Log.e("jludden", "onDataNotAvailable $message")
                reviewsView.setLoadingIndicator(false)
                reviewsView.showLoadingError()
            }

        })
    }
}