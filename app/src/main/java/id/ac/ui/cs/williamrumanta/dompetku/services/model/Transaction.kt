package id.ac.ui.cs.williamrumanta.dompetku.services.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "transactions")

@TypeConverters(Converters::class)

data class Transaction(@PrimaryKey(autoGenerate = true) val id: Long?,
                       @ColumnInfo(name = "name") val name: String,
                       @ColumnInfo(name = "amount") val amount: Double,
                       @ColumnInfo(name = "type") val type: Int,
                       @ColumnInfo(name = "date") val date: Date?
) {
    constructor():this(null, "", 0.0, 0, null)
}