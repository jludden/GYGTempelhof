package me.jludden.gygtempelhof.reviews

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.review_item.view.*
import kotlinx.android.synthetic.main.reviews_fragment.*
import me.jludden.gygtempelhof.data.model.Review
import me.jludden.gygtempelhof.R
import me.jludden.gygtempelhof.addreview.AddReviewsActivity

class ReviewsFragment : Fragment(), ReviewsContract.View {

    override lateinit var presenter: ReviewsContract.Presenter

    private val reviewsAdapter = ReviewsAdapter(ArrayList(0))

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.reviews_fragment, container, false)
        with(root) {
            findViewById<RecyclerView>(R.id.reviews_container)
                    .apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = reviewsAdapter
                    }

            findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
                    .apply {
                        setOnRefreshListener { presenter.loadReviews(true) }
                        setColorSchemeColors(
                                ContextCompat.getColor(context, R.color.colorPrimary),
                                ContextCompat.getColor(context, R.color.colorAccent),
                                ContextCompat.getColor(context, R.color.colorPrimaryDark)
                        )
                    }
        }

        // Set up floating action button
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.apply {
            setOnClickListener { presenter.addNewReview() }
        }

        return root
    }


    override fun setLoadingIndicator(active: Boolean) {
        with(swipe_refresh) {
            post { isRefreshing = active }
        }
    }

    override fun showReviews(reviews: List<Review>) {
        reviewsAdapter.reviewsList = reviews
        showMessage(getString(R.string.n_reviews_loaded_successfully, reviews.size))
    }

    private fun showMessage(message: String) {
        activity?.runOnUiThread({
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        })
    }

    override fun showLoadingError() {
        showMessage(getString(R.string.reviews_loaded_error))
    }

    override fun showAddReview() {
        val intent = Intent(context, AddReviewsActivity::class.java)
        startActivityForResult(intent, AddReviewsActivity.ADD_REVIEW)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.reviewAddedResult(requestCode, resultCode)
    }

    override fun showSuccessfullyAddedMessage() {
        showMessage(getString(R.string.review_posted_success))
    }

    override fun showFilteringPopUpMenu() {
        PopupMenu(requireContext(), requireActivity().findViewById(R.id.menu_filter)).apply {
            menuInflater.inflate(R.menu.menu_filters, menu)
            setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.high_rating -> presenter.currentFiltering = ReviewsFilterType.HIGH_RATING
                    R.id.low_rating -> presenter.currentFiltering = ReviewsFilterType.LOW_RATING
                    else -> presenter.currentFiltering = ReviewsFilterType.ALL_REVIEWS
                }
                presenter.loadReviews(false)
                true
            }
        }.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_filter -> true.also { showFilteringPopUpMenu() }
            R.id.menu_refresh -> true.also { presenter.loadReviews(true) }
            else -> super.onOptionsItemSelected(item)
        }
    }

    class ReviewsAdapter(reviews: List<Review>)
        : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var reviewsList: List<Review> = reviews
            set(reviews) {
                field = reviews
                notifyDataSetChanged()
            }

        fun ViewGroup.inflate(layoutRes: Int) : View = LayoutInflater.from(context).inflate(layoutRes, this, false)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
                = ReviewViewHolder(parent.inflate(R.layout.review_item))

        override fun getItemCount() = reviewsList.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
                (holder as ReviewViewHolder).bind(reviewsList[position])
    }

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(review: Review) = with(itemView) {
              review_title.text = review.title
              review_author.text = review.author
              review_rating.rating = review.rating.toFloat()
              review_date.text = review.date
              review_message.text = review.message
        }
    }

    companion object {
        fun newInstance() = ReviewsFragment()
    }
}