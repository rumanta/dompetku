package id.ac.ui.cs.williamrumanta.dompetku.services.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import id.ac.ui.cs.williamrumanta.dompetku.services.model.AppDatabase
import id.ac.ui.cs.williamrumanta.dompetku.services.model.Transaction
import id.ac.ui.cs.williamrumanta.dompetku.services.model.TransactionDao

class TransactionRepository {
    private var transactionDao: TransactionDao
    private var allTransactions: LiveData<List<Transaction>>

    constructor(application: Application) {
        val db: AppDatabase = AppDatabase.getInstance(application)
        transactionDao = db.transactionDao()
        allTransactions = transactionDao.getAll()

    }

    fun insert(transaction: Transaction) {
        InsertTransactionAsync(transactionDao).execute(transaction)
    }

    fun update(transaction: Transaction) {
        UpdateTransactionAsync(transactionDao).execute(transaction)
    }

    fun delete(transaction: Transaction) {
        DeleteTransactionAsync(transactionDao).execute(transaction)
    }

    fun getAllTransactions() : LiveData<List<Transaction>> {
        return allTransactions;
    }

    companion object {
        private class InsertTransactionAsync constructor(private val transactionDao: TransactionDao) :
            AsyncTask<Transaction, Void, Void>() {

            override fun doInBackground(vararg transactions: Transaction): Void? {
                transactionDao.insert(transactions[0])
                return null;
            }
        }

        private class UpdateTransactionAsync constructor(private val transactionDao: TransactionDao) :
            AsyncTask<Transaction, Void, Void>() {

            override fun doInBackground(vararg transactions: Transaction): Void? {
                transactionDao.update(transactions[0])
                return null;
            }
        }

        private class DeleteTransactionAsync constructor(private val transactionDao: TransactionDao) :
            AsyncTask<Transaction, Void, Void>() {

            override fun doInBackground(vararg transactions: Transaction): Void? {
                transactionDao.delete(transactions[0])
                return null;
            }
        }
    }
}
