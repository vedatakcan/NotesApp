package com.vedatakcan.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vedatakcan.notesapp.model.Note


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {

    // Veritabanı için DAO'yu (Data Access Object) döndüren soyut bir fonksiyon tanımlanır.
    abstract fun getNoteDao(): NoteDao

    companion object {
        // @Volatile: Bu değişkenin farklı iş parçacıkları (threads) arasında
        // tutarlı bir şekilde görünmesini sağlar.
        @Volatile
        private var instance: NoteDatabase? = null

        // LOCK: Veritabanı örneğinin yalnızca bir kez oluşturulmasını sağlamak için bir kilit nesnesi.
        private val LOCK = Any()

        // invoke operatörü, bir nesneyi bir fonksiyon gibi çağırmayı sağlar.
        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK) {
            // Eğer veritabanı örneği mevcut değilse, yeni bir örnek oluşturur.
            instance ?: createDatabase(context).also {
                // Yeni oluşturulan örnek instance'a atanır.
                instance = it
            }
        }

        // Veritabanını oluşturur ve geri döner.
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                // Uygulamanın context'ini alır (global context)
                context.applicationContext,
                // NoteDatabase sınıfı, veritabanının temelini oluşturur.
                NoteDatabase::class.java,
                // Veritabanının dosya adı "note_db" olarak tanımlanır.
                "note_db"
            ).build()
    }
}
