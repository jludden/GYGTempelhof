package me.jludden.gygtempelhof.addreview

import me.jludden.gygtempelhof.data.ReviewsRepository
import me.jludden.gygtempelhof.data.model.Review

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
        review.rating = rating

        if(!review.isValid) addReviewView.showInvalidReviewError()
        else {
            reviewsRepo.postReview(review)
            addReviewView.showReviewsList() //go back to reviews list activity
        }
    }

}