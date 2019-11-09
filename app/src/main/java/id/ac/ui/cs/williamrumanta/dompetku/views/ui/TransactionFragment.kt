package id.ac.ui.cs.williamrumanta.dompetku.views.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import id.ac.ui.cs.williamrumanta.dompetku.R
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import id.ac.ui.cs.williamrumanta.dompetku.views.ui.AddEditFormActivity.Companion.RESULT_OK

class TransactionFragment : Fragment() {
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var adapter: TransactionAdapter

    companion object {
        val ADD_FORM_REQUEST: Int = 1
        val EDIT_FORM_REQUEST: Int = 2


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

        val textViewTotalAmount = rootView.findViewById<TextView>(R.id.text_view_total_amount)


        btnAddTransaction.setOnClickListener {
            val intent = Intent(activity, AddEditFormActivity::class.java)
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

//        val totalAmount = this.adapter.getTotalAmount()

        textViewTotalAmount.setText("Rp " + "550000")

        adapter.setOnItemClickListener(object: TransactionAdapter.OnItemClickListener {
            override fun onItemClick(transaction: Transaction) {
                val intent = Intent(activity, AddEditFormActivity::class.java)
                intent.putExtra(AddEditFormActivity.EXTRA_ID, transaction.id)
                intent.putExtra(AddEditFormActivity.EXTRA_NAME, transaction.name)
                intent.putExtra(AddEditFormActivity.EXTRA_AMOUNT, transaction.amount)
                intent.putExtra(AddEditFormActivity.EXTRA_TYPE, transaction.type)
                intent.putExtra(AddEditFormActivity.EXTRA_DATETIME, transaction.date)

                startActivityForResult(intent, EDIT_FORM_REQUEST)
            }
        })

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_FORM_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                val name = data.getStringExtra(AddEditFormActivity.EXTRA_NAME)
                val amount = data.getDoubleExtra(AddEditFormActivity.EXTRA_AMOUNT, 0.0)
                val type = data.getIntExtra(AddEditFormActivity.EXTRA_TYPE, 1)
                val datetime = data.getLongExtra(AddEditFormActivity.EXTRA_DATETIME, 0)

                val transaction = Transaction(name, amount, type, datetime)
                transactionViewModel.insert(transaction)

                Toast.makeText(activity, "Transaction saved!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "There is null transaction!", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == EDIT_FORM_REQUEST && resultCode == RESULT_OK) {

            val minusOne = -1

            if (data != null) {
                val id = data.getLongExtra(AddEditFormActivity.EXTRA_ID, minusOne.toLong())

                if (id == minusOne.toLong()) {
                    Toast.makeText(activity, "Transaction is failed to update", Toast.LENGTH_SHORT).show()
                    return
                }

                val name = data.getStringExtra(AddEditFormActivity.EXTRA_NAME)
                val amount = data.getDoubleExtra(AddEditFormActivity.EXTRA_AMOUNT, 0.0)
                val type = data.getIntExtra(AddEditFormActivity.EXTRA_TYPE, 1)
                val datetime = data.getLongExtra(AddEditFormActivity.EXTRA_DATETIME, 0)

                val transaction = Transaction(name, amount, type, datetime)
                transaction.id = id

                transactionViewModel.update(transaction)
            }
        }
    }
}
