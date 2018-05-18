package me.jludden.gygtempelhof

import me.jludden.gygtempelhof.data.ReviewsRepository

class ReviewsPresenter(
        val reviewsRepo: ReviewsRepository,
        val reviewsView: ReviewsContract.View)
    : ReviewsContract.Presenter {

    override var currentFiltering: ReviewsFilterType
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun loadReviews(forceUpdate: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addNewReview() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}