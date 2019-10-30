package id.ac.ui.cs.williamrumanta.dompetku.services.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Transaction::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    private var instance: AppDatabase? = null


    @Synchronized
    fun getInstance(context: Context): AppDatabase {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase::class.java, "transaction_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        return instance as AppDatabase
    }

}