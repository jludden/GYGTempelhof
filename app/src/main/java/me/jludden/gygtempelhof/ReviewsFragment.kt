package me.jludden.gygtempelhof

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import me.jludden.gygtempelhof.data.Review

class ReviewsFragment : Fragment(), ReviewsContract.View {

    override lateinit var presenter: ReviewsContract.Presenter
    lateinit var recyclerView: RecyclerView
    lateinit var viewAdapter: ReviewsAdapter

    override var isActive: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun setLoadingIndicator(active: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showReviews(reviews: List<Review>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoadingError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAddReview() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSuccessfullyAddedMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showFilteringPopUpMenu() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }




    class ReviewsAdapter(reviews: List<Review>)
        : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getItemCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    companion object {
        fun newInstance() = ReviewsFragment()
    }
}