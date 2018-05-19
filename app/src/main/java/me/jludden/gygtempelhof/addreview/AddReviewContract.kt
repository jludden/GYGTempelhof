package me.jludden.gygtempelhof.addreview

import me.jludden.gygtempelhof.data.model.Review
import me.jludden.gygtempelhof.reviews.BasePresenter
import me.jludden.gygtempelhof.reviews.BaseView

interface AddReviewContract {

    interface View : BaseView<Presenter> {

        fun showInvalidReviewError()

        fun showReviewsList()
    }

    interface Presenter : BasePresenter {
        fun postReview(title: String, message: String, rating: String)
    }

}