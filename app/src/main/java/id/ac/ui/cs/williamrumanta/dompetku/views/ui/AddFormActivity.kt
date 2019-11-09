package id.ac.ui.cs.williamrumanta.dompetku.views.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import id.ac.ui.cs.williamrumanta.dompetku.R
import kotlinx.android.synthetic.main.add_transaction_layout.*
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

class AddFormActivity : AppCompatActivity() {
    private lateinit var editTextAddAmount: EditText
    private lateinit var editTextAddName: EditText
    private lateinit var btnAddConfirm: Button
    private lateinit var radioGroup: RadioGroup
    private val calendar = Calendar.getInstance()
    private var radioSwitch = -1

    companion object {
        val RESULT_OK: Int = 2
        val EXTRA_NAME: String = "id.ac.ui.ac.williamrumanta.dompetku.EXTRA_NAME"
        val EXTRA_AMOUNT: String = "id.ac.ui.ac.williamrumanta.dompetku.EXTRA_AMOUNT"
        val EXTRA_DATETIME: String = "id.ac.ui.ac.williamrumanta.dompetku.EXTRA_DATETIME"
        val EXTRA_TYPE: String = "id.ac.ui.ac.williamrumanta.dompetku.EXTRA_TYPE"

        val TYPE_EARNING = 1
        val TYPE_SPENDING = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_transaction_layout)

        editTextAddAmount = findViewById(R.id.edit_text_add_amount)
        editTextAddName = findViewById(R.id.edit_text_add_name)
        btnAddConfirm = findViewById(R.id.btn_add_confirm)
        radioGroup = findViewById(R.id.radio_group)

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(year, monthOfYear, dayOfMonth)

                editTextDatePicker.setText("" + (Month.of(monthOfYear + 1)) + ", " + dayOfMonth + " " + year)
            },
            year,
            month,
            day
        )

        editTextDatePicker.setOnClickListener {
            dpd.show()
        }

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)


            editTextTimePicker.setText(SimpleDateFormat("HH:mm").format(cal.time))
        }

        editTextTimePicker.setOnClickListener {
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            Log.d("ASUU", "" + radio.text)
            if (radio.text.equals("Earning")) {
                radioSwitch = TYPE_EARNING
            } else if (radio.text.equals("Spending")) {
                radioSwitch = TYPE_SPENDING
            }
        }

        btnAddConfirm.setOnClickListener {
            saveTransaction()
        }

    }

    private fun saveTransaction() {
        var amountStr = editTextAddAmount.text.toString()
        if (amountStr.isEmpty()) {
            amountStr = "0"
        }

        val amount = amountStr.toDouble()

        val name = editTextAddName.text.toString()
        val datetime = calendar.timeInMillis

        Log.d("RADIO BUTTON", "" + radioSwitch)

        if (radioSwitch == -1) {
            Toast.makeText(
                this, "On button click : nothing selected",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (amount.compareTo(0.0) < 0 || name.trim().isEmpty()) {
            Toast.makeText(this, "Please input amount and name", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_NAME, name)
        data.putExtra(EXTRA_AMOUNT, amount)
        data.putExtra(EXTRA_DATETIME, datetime)
        data.putExtra(EXTRA_TYPE, radioSwitch)


        Log.d("data", "" + data)

        setResult(RESULT_OK, data)

        finish()
    }
}