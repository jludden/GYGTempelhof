package me.jludden.gygtempelhof

import android.content.Context
import me.jludden.gygtempelhof.data.ReviewsRepository

//todo move to separate folders for different build variants
//todo will need to provide ReviewsDataRepo with the local and remote data repos
object Injection {
    @JvmStatic fun provideReviewsRepository(context: Context): ReviewsRepository {
        return ReviewsRepository.getInstance()
    }
}
