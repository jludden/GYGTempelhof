package me.jludden.gygtempelhof.data

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jludden.gygtempelhof.data.model.Review
import me.jludden.gygtempelhof.data.model.ReviewResponse
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
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
    override fun postReview(review: Review, callback: ReviewsDataSource.PostReviewCallback ) {
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
            return INSTANCE ?: RemoteDataSource(ReviewsAPI.create()).apply { INSTANCE = this }
        }
    }
}

//define the interface for sending and retrieving reviews from the server
interface ReviewsAPI {

    //Get N (count) reviews from server
    @Headers("User-Agent: GYGTempelhof")
    @GET("reviews.json")
    fun getNReviewsFromServer(@Query("count") count: Int): Observable<ReviewResponse>


    //Unused - how the post api might look
    @Headers("User-Agent: GYGTempelhof")
    @POST("reviews/new")
    fun postReviewToServer(@Body review: Review): Observable<Response<FakePostResponse>>

    //the response object should contain the the ID of the new review, at a minimum
    data class FakePostResponse(val id: Int)

    companion object {
        fun create(): ReviewsAPI {

            val builder = OkHttpClient.Builder()

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.networkInterceptors().add(httpLoggingInterceptor)
            val myclient = builder.build()


            val retrofit = Retrofit.Builder()
                    .client(myclient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://www.getyourguide.com/berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/")
                    .build()

            return retrofit.create(ReviewsAPI::class.java)
        }
    }

}