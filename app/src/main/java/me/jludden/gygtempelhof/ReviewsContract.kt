package me.jludden.gygtempelhof

import me.jludden.gygtempelhof.data.Review

interface ReviewsContract {

    interface View : BaseView<Presenter> {

        var isActive: Boolean

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

    }

}

//todo
enum class ReviewsFilterType {

    ALL_REVIEWS,

    PAST_MONTH,

    LOW_RATING


}


interface BaseView<T> {

    var presenter: T
}

interface BasePresenter {

    fun start()

}