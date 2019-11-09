package id.ac.ui.cs.williamrumanta.dompetku.views.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import id.ac.ui.cs.williamrumanta.dompetku.R
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.ac.ui.cs.williamrumanta.dompetku.services.model.Transaction
import id.ac.ui.cs.williamrumanta.dompetku.viewmodels.TransactionViewModel
import id.ac.ui.cs.williamrumanta.dompetku.views.adapter.TransactionAdapter
import id.ac.ui.cs.williamrumanta.dompetku.views.ui.AddFormActivity.Companion.RESULT_OK

class TransactionFragment : Fragment() {
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var adapter: TransactionAdapter

    companion object {
        val ADD_FORM_REQUEST: Int = 1

        fun newInstances(): TransactionFragment {
            return TransactionFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.transaction_layout, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        val btnAddTransaction =
            rootView.findViewById<FloatingActionButton>(R.id.btn_add_transaction)

        btnAddTransaction.setOnClickListener {
            val intent = Intent(activity, AddFormActivity::class.java)
            activity?.startActivityForResult(intent, ADD_FORM_REQUEST)
        }

        val activity = activity as Context
        recyclerView.setLayoutManager(LinearLayoutManager(activity))
        recyclerView.setHasFixedSize(true)

        this.adapter = TransactionAdapter()
        recyclerView.setAdapter(this.adapter)

        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel::class.java)

        val transactionObserver = Observer<List<Transaction>> { newTransactions ->
            adapter.setTransactions(newTransactions)
        }

        transactionViewModel.getAllTransactions().observe(this, transactionObserver)

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("Masuk", "woi")

        if (requestCode == ADD_FORM_REQUEST && resultCode == RESULT_OK) {
            Log.d("asu", "req")
            if (data != null) {
                val name = data.getStringExtra(AddFormActivity.EXTRA_NAME)
                val amount = data.getDoubleExtra(AddFormActivity.EXTRA_AMOUNT, 0.0)
                val type = data.getIntExtra(AddFormActivity.EXTRA_TYPE, 1)
                val datetime = data.getLongExtra(AddFormActivity.EXTRA_DATETIME, 0)

                val transaction = Transaction(name, amount, type, datetime)
                transactionViewModel.insert(transaction)

                Log.d("berhasil", "asu")

                Toast.makeText(activity, "Transaction saved!", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("gagal", "asu")

                Toast.makeText(activity, "There is null transaction!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.d("anjing", "asu")

            Toast.makeText(activity, "Transaction is not saved!", Toast.LENGTH_SHORT).show()
        }
    }

}
