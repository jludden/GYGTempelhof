package me.jludden.gygtempelhof.reviews

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.review_item.view.*
import me.jludden.gygtempelhof.data.model.Review

import kotlinx.android.synthetic.main.reviews_fragment.*
import me.jludden.gygtempelhof.R
import me.jludden.gygtempelhof.addreview.AddReviewsActivity

class ReviewsFragment : Fragment(), ReviewsContract.View {

    override lateinit var presenter: ReviewsContract.Presenter

    private val reviewsAdapter = ReviewsAdapter(ArrayList(0))

    override var isActive: Boolean = true //todo is this used

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
//        reviews_container.visibility = View.VISIBLE
//        no_reviews_message.visibility = View.GONE
        Toast.makeText(context, "${reviews.size} Reviews loaded successfully", Toast.LENGTH_SHORT).show()
    }

    //todo possibly need to differentiate failed to load vs no reviews
    override fun showLoadingError() {
        no_reviews_message.apply {
            visibility = View.VISIBLE
            text = "No reviews loaded"
        }

        Toast.makeText(context, "Error loading reviews", Toast.LENGTH_SHORT).show()
    }

    override fun showAddReview() {
        val intent = Intent(context, AddReviewsActivity::class.java)
        startActivityForResult(intent, AddReviewsActivity.ADD_REVIEW)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.reviewAddedResult(requestCode, resultCode)
    }

    override fun showSuccessfullyAddedMessage() {
        Toast.makeText(context, "Review successfully posted!", Toast.LENGTH_SHORT).show()
        //todo - should i make sure it's visible? should be datrepo..
    }

    override fun showFilteringPopUpMenu() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.menu_refresh -> true.also { presenter.loadReviews(true) } //todo doesn't work yet
            else -> super.onOptionsItemSelected(item)
        }
    }


    class ReviewsAdapter(reviews: List<Review>)
        : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var reviewsList: List<Review> = reviews
            set(tasks) {
                field = tasks
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