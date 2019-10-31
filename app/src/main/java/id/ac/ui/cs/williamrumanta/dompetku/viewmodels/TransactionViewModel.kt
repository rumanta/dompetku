package id.ac.ui.cs.williamrumanta.dompetku.viewmodels

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.ui.cs.williamrumanta.dompetku.services.model.Transaction
import id.ac.ui.cs.williamrumanta.dompetku.services.repository.TransactionRepository

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TransactionRepository
    private val allTransactions: LiveData<List<Transaction>>

    init {
        repository = TransactionRepository(application)
        allTransactions = repository.getAllTransactions()
    }

    fun insert(transaction: Transaction) {
        repository.insert(transaction)
    }

    fun update(transaction: Transaction) {
        repository.update(transaction)
    }

    fun delete(transaction: Transaction) {
        repository.delete(transaction)
    }

    fun getAllTransactions(): LiveData<List<Transaction>> {
        return allTransactions
    }
}
