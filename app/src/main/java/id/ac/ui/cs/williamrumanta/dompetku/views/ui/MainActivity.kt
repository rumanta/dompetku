package id.ac.ui.cs.williamrumanta.dompetku.views.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import id.ac.ui.cs.williamrumanta.dompetku.R
import id.ac.ui.cs.williamrumanta.dompetku.services.model.Transaction
import id.ac.ui.cs.williamrumanta.dompetku.viewmodels.TransactionViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var transactionViewModel : TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel::class.java)

        val transactionObserver = Observer<List<Transaction>> { newList ->
            Toast.makeText(this, "onChanged", Toast.LENGTH_SHORT).show()
        }

        transactionViewModel.getAllTransactions().observe(this, transactionObserver)

    }
}
