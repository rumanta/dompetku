package id.ac.ui.cs.williamrumanta.dompetku.services.model

import android.content.Context
import android.os.AsyncTask
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Transaction::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }


        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database"
            )
                .fallbackToDestructiveMigration()
                .addCallback(roomCallback)
                .build()

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDBAsyncTask(INSTANCE).execute()
            }
        }

        private class PopulateDBAsyncTask constructor(db: AppDatabase?) :
            AsyncTask<Void, Void, Void>() {
            private lateinit var transactionDao: TransactionDao

            init {
                if (db != null) {
                    transactionDao = db.transactionDao()
                }
            }

            override fun doInBackground(vararg voids: Void): Void? {
                transactionDao.insert(Transaction("Uang Kos", 500000.0, 1, 0))
                transactionDao.insert(Transaction("Nonton Joker", 50000.0, 2, 0))
                return null
            }
        }
    }
}
