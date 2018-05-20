package me.jludden.gygtempelhof.data.remote

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jludden.gygtempelhof.data.ReviewsDataSource
import me.jludden.gygtempelhof.data.model.Review
import me.jludden.gygtempelhof.data.model.ReviewResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.*
import java.util.*


class RemoteDataSource(val reviewsAPI: ReviewsAPI) : ReviewsDataSource {

    //Get all reviews from server. I pass in 500 as the count to bypass the default 100 return values
    override fun getReviews(callback: ReviewsDataSource.LoadReviewsCallback) {
        reviewsAPI.getNReviewsFromServer(500)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> callback.onReviewsLoaded(result.data) },
                        { error -> callback.onDataNotAvailable(error.message) })
    }

    //Mock post a review to the server. The response object has the newly created review's ID
    override fun postReview(review: Review, callback: ReviewsDataSource.PostReviewCallback) {
        Log.e("JLUDDEN", "POSTING REVIEW!!! ${review.title} ${review.rating} ${review.message}")

        //Mock Post Call - it should never fail
        postReviewToServerMock(review)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> callback.onReviewPosted(result.body()!!.id) },
                        { error -> callback.onReviewPostFailure(error.message) })
    }

    //Mock post response - always succeed and send back a random ID
    private fun postReviewToServerMock(review: Review): Observable<Response<ReviewsAPI.FakePostResponse>>
    {
        val id = Random().nextInt(900)
        return Observable.just(Response.success(ReviewsAPI.FakePostResponse(id)))
    }

    //unused - main repository will handle refreshing
    override fun refreshReviews() { }

    companion object {
        private var INSTANCE: RemoteDataSource? = null

        //return the singleton instance, creating it if necessary
        @JvmStatic fun getInstance(): RemoteDataSource {
            return INSTANCE
                    ?: RemoteDataSource(ReviewsAPI.create()).apply { INSTANCE = this }
        }
    }
}

