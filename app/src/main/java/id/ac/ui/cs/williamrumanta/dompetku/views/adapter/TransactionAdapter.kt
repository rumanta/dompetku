package id.ac.ui.cs.williamrumanta.dompetku.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.williamrumanta.dompetku.R
import id.ac.ui.cs.williamrumanta.dompetku.services.model.Transaction
import java.text.DecimalFormat

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionHolder>() {
    internal lateinit var listener: OnItemClickListener

    val dec = DecimalFormat("#,###.00")

    var transactions = arrayListOf<Transaction>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false)
        return TransactionHolder(itemView)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

//    fun getTotalAmount(): String {
//        val amount = transactions.fold(0.0) { sum, transaction ->
//            sum + transaction.amount
//        }
//        return dec.format(amount)
//    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val currentTransaction = transactions.get(position)
        holder.textViewName.setText(currentTransaction.name)

        val amount = dec.format(currentTransaction.amount)

        holder.textViewAmount.setText(amount)
    }

    fun setTransactions(newTransactions: List<Transaction>?) {
        transactions = newTransactions as ArrayList<Transaction>
         notifyDataSetChanged()
    }

    inner class TransactionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val textViewName: TextView
        internal val textViewAmount: TextView

        init {
            textViewName = itemView.findViewById(R.id.text_view_name)
            textViewAmount = itemView.findViewById(R.id.text_view_amount)
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val position = adapterPosition
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(transactions.get(position))
                    }

                }
            })
        }
    }

    interface OnItemClickListener {
        fun onItemClick(transaction: Transaction)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}

