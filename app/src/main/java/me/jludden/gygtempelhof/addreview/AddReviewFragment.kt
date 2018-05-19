package me.jludden.gygtempelhof.addreview

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import me.jludden.gygtempelhof.R
import kotlinx.android.synthetic.main.add_review_fragment.*

class AddReviewFragment : Fragment(), AddReviewContract.View {

    override lateinit var presenter: AddReviewContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.add_review_fragment, container, false)
        with(root.findViewById<Button>(R.id.add_review_post)) {
            setOnClickListener {
                presenter.postReview(
                        add_review_title.text.toString(),
                        add_review_message.text.toString(),
                        add_review_rating.rating.toString()
                )
            }
        }
        return root
    }

    override fun showInvalidReviewError() {
        Toast.makeText(context, "Please complete your review", Toast.LENGTH_SHORT).show()
    }

    //return to reviews list after posting
    override fun showReviewsList() {
        activity?.let {
            it.setResult(Activity.RESULT_OK)
            it.finish()
        }
    }

    companion object {
        fun newInstance() = AddReviewFragment()
    }
}
