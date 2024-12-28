package com.vedatakcan.notesapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vedatakcan.notesapp.model.Note

// Room veritabanı için DAO (Data Access Object) sınıfıdır.
// Notlar (Note) üzerinde CRUD (Create, Read, Update, Delete) işlemlerini gerçekleştirmek ve
// SQL sorguları yazmak için kullanılır. Ayrıca, LiveData ile dinamik veri güncellemeleri sağlar.

@Dao
interface NoteDao {

    // Yeni bir not ekler veya ID çakışması durumunda mevcut notu günceller.
    // "suspend" ifadesi, bu fonksiyonun bir coroutine içinde çalıştırılmasını gerektirir.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    // Var olan bir notun içeriğini günceller.
    // "suspend" ifadesi, bu işlemin bir coroutine içinde yapılacağını belirtir.
    @Update
    suspend fun updateNote(note: Note)

    // Verilen notu veritabanından siler.
    // "suspend" ifadesi, bu işlemin bir coroutine içinde çalıştırılması gerektiğini ifade eder.
    @Delete
    suspend fun deleteNote(note: Note)

    // Tüm notları getirir ve ID'ye göre azalan sırayla sıralar.
    // Sonuçlar LiveData ile döner, böylece verilerdeki değişiklikler otomatik olarak UI'a yansır.
    @Query("SELECT * FROM NOTES ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    // Not başlığı veya açıklamasında verilen "query" ile eşleşen notları arar.
    // LiveData ile sonuçları dinamik bir şekilde döner, arama sonuçları değiştikçe UI güncellenir.
    @Query("SELECT * FROM NOTES WHERE noteTitle LIKE :query OR noteDesc LIKE :query")
    fun searchNote(query: String?): LiveData<List<Note>>
}
