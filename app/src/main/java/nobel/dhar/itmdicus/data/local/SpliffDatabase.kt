package nobel.dhar.itmdicus.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters



@Database(entities = [Product::class, CartTable::class], version = 1, exportSchema = true)
abstract class SpliffDatabase : RoomDatabase() {

    abstract fun spliffDao(): SpliffDao

}