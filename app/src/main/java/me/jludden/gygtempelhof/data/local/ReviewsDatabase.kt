package me.jludden.gygtempelhof.data.local

import android.arch.persistence.room.*
import android.content.Context

//Database that contains the reviews table
@Database(entities = [(ReviewRow::class)], version = 1)
abstract class ReviewsDatabase : RoomDatabase() {
    abstract fun reviewDao(): ReviewDao

    companion object {
        private var INSTANCE: ReviewsDatabase? = null

        @JvmStatic fun getInstance(context: Context): ReviewsDatabase {
            return INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                    ReviewsDatabase::class.java,"TempelhofReviews.db").build()
                    .apply { INSTANCE = this }
        }
    }
}

//Entity models a database table
@Entity(tableName = "reviews")
data class ReviewRow (
        @PrimaryKey
        @ColumnInfo(name = "review_id")
        val id: Int,
        val title: String,
        val message: String,
        val date: String,
        val rating: Float
)

//Data Access Object provides a way to get entities from the database and persist changes back
@Dao
interface ReviewDao {
    @Query("SELECT * FROM reviews")
    fun getReviews(): List<ReviewRow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReview(review: ReviewRow)

    @Query("DELETE FROM reviews")
    fun deleteAllReviews()
}