package me.jludden.gygtempelhof.addreview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.jludden.gygtempelhof.R
import me.jludden.gygtempelhof.data.Injection

class AddReviewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_review_activity)

        val addReviewsFragment = supportFragmentManager.findFragmentById(R.id.add_review_content_frame)
                as AddReviewFragment? ?: AddReviewFragment.newInstance().also {
            supportFragmentManager.beginTransaction().replace(R.id.add_review_content_frame, it).commit()
        }

       //create the presenter
       AddReviewPresenter(Injection.provideReviewsRepository(applicationContext), addReviewsFragment)
    }

    companion object {
        const val ADD_REVIEW = 7
    }
}