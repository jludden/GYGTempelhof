package me.jludden.gygtempelhof.data

import android.content.Context
import me.jludden.gygtempelhof.data.local.LocalDataSource
import me.jludden.gygtempelhof.data.remote.RemoteDataSource

//Injection object provides an easy way to mock the data sources for unit testing
object Injection {
    @JvmStatic fun provideReviewsRepository(context: Context): ReviewsRepository {
        return ReviewsRepository.getInstance(LocalDataSource.getInstance(context), RemoteDataSource.getInstance())
    }
}
