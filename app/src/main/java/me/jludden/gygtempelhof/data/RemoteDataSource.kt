package me.jludden.gygtempelhof.data

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jludden.gygtempelhof.data.model.Review
import me.jludden.gygtempelhof.data.model.ReviewResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.http.*


class RemoteDataSource(val reviewsAPI: ReviewsAPI) : ReviewsDataSource {

    override fun getReviews(callback: ReviewsDataSource.LoadReviewsCallback) {

        //todo
        reviewsAPI.getAllReviewsFromServer(500)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> callback.onReviewsLoaded(result.data) },
                        { error -> callback.onDataNotAvailable(error.message) })
    }

    override fun postReview(review: Review, callback: ReviewsDataSource.PostReviewCallback ) {
        Log.e("JLUDDEN", "POSTING REVIEW!!! ${review.title} ${review.rating} ${review.message}")

        //todo

        callback.onReviewPosted()
    }

    //unused
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
    @Headers("User-Agent: GYGTempelhof")
    @GET("reviews.json")
    fun getReviewsFromServer(): Observable<ReviewResponse> //maxes out at 100 responses

    @Headers("User-Agent: GYGTempelhof")
    @GET("reviews.json")
    fun getAllReviewsFromServer(@Query("count") count: Int): Observable<ReviewResponse>

//    fun getAllReviewsFromServer(@Query("count") count: Int, @Query("page") page: Int,
//                             @Query("rating") rating: Int): Observable<ReviewResponse>

    @Headers("User-Agent: GYGTempelhof")
    @POST("reviews/new")
    fun postReviewToServer(@Body review: Review): Observable<ResponseBody>

    //&page=0&rating=0&sortBy=date_of_review&direction=DESC
//        @GET("https://www.getyourguide.com/berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/reviews.json?count=5&page=0&rating=0&sortBy=date_of_review&direction=DESC")


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