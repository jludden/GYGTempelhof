package me.jludden.gygtempelhof.data.remote

import io.reactivex.Observable
import me.jludden.gygtempelhof.data.model.Review
import me.jludden.gygtempelhof.data.model.ReviewResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

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
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://www.getyourguide.com/berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/")
                    .build()

            return retrofit.create(ReviewsAPI::class.java)
        }
    }

}