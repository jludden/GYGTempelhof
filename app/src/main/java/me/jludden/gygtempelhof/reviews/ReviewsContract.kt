package me.jludden.gygtempelhof.reviews

import me.jludden.gygtempelhof.data.model.Review

interface ReviewsContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showReviews(reviews: List<Review>)

        fun showLoadingError()

        fun showAddReview()

        fun showSuccessfullyAddedMessage()

        fun showFilteringPopUpMenu()

    }


    interface Presenter : BasePresenter {

        var currentFiltering: ReviewsFilterType

        fun loadReviews(forceUpdate: Boolean)

        fun addNewReview()

        fun reviewAddedResult(requestCode: Int, resultCode: Int)
    }
}

enum class ReviewsFilterType {

    ALL_REVIEWS,

    LOW_RATING,

    HIGH_RATING

}


interface BaseView<T> {

    var presenter: T
}

interface BasePresenter {

    fun start()

}