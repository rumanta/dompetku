package id.ac.ui.cs.williamrumanta.dompetku.services.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")

data class Transaction(
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "amount") var amount: Double = 0.0,
    @ColumnInfo(name = "type") var type: Int = 1,
    @ColumnInfo(name = "datetime") var date: Long = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}