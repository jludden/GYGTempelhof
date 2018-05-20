package me.jludden.gygtempelhof.addreview

import me.jludden.gygtempelhof.data.ReviewsDataSource
import me.jludden.gygtempelhof.data.ReviewsRepository
import me.jludden.gygtempelhof.data.model.Review
import java.text.SimpleDateFormat
import java.util.*



class AddReviewPresenter(
        private val reviewsRepo: ReviewsRepository,
        private val addReviewView: AddReviewContract.View)
    : AddReviewContract.Presenter {

    init {
        addReviewView.presenter = this
    }

    override fun start() { }

    override fun postReview(title: String, message: String, rating: String) {
        val review = Review()
        review.title = title
        review.message = message
        review.date = todaysDate()
        review.rating = rating

        if(!review.isValid) addReviewView.showInvalidReviewError()
        else {
            reviewsRepo.postReview(review, object : ReviewsDataSource.PostReviewCallback {
                override fun onReviewPosted(id: Int) {
                    addReviewView.showReviewsList() //go back to reviews list activity
                }

                override fun onReviewPostFailure(message: String?) { //never called by mock Post API
                    addReviewView.showInvalidReviewError()
                }
            })
        }
    }

    //create a String with today's date
    private fun todaysDate() : String {
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("MMM dd, yyyy")
        return df.format(c)
    }
}