package me.jludden.gygtempelhof

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.reviews_activity.*

class ReviewsActivity : AppCompatActivity() {

    private val SAVED_FILTER_KEY = "SAVED_FILTER_KEY"

    private lateinit var reviewsPresenter: ReviewsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reviews_activity)
        setSupportActionBar(toolbar)

  /*      fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
*/
        val reviewsFragment = supportFragmentManager.findFragmentById(R.id.content_frame)
            as ReviewsFragment? ?: ReviewsFragment.newInstance().also {
             supportFragmentManager.beginTransaction().replace(R.id.content_frame, it).commit()
        }

        //create the presenter
        reviewsPresenter = ReviewsPresenter(Injection.provideReviewsRepository(applicationContext),
                reviewsFragment).apply {
            if (savedInstanceState != null) {
                currentFiltering = savedInstanceState.getSerializable(SAVED_FILTER_KEY)
                        as ReviewsFilterType
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState.apply {
            putSerializable(SAVED_FILTER_KEY, reviewsPresenter.currentFiltering)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

}
