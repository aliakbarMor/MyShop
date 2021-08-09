package mor.aliakbar.tavaloodshop.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import mor.aliakbar.tavaloodshop.model.dataclass.Product
import mor.aliakbar.tavaloodshop.model.source.ProductLocalSource

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val productDao: ProductLocalSource


}