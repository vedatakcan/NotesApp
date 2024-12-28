package com.vedatakcan.notesapp.repository

import com.vedatakcan.notesapp.database.NoteDatabase
import com.vedatakcan.notesapp.model.Note

// Repository sınıfı, uygulamanın veritabanı işlemleri ile diğer katmanları (örneğin, ViewModel) arasında bir köprü görevi görür.
// Bu yapı, veri işleme mantığını bir yerde toplar ve veri kaynağına (örneğin, Room veritabanı, API, vb.) erişimi soyutlar.
// Repository sayesinde, veritabanı ile doğrudan etkileşime gerek kalmadan gerekli işlemleri ViewModel veya diğer sınıflardan çağırabilirsiniz.
// Ayrıca, veri kaynağı değiştiğinde (örneğin, Room yerine bir API kullanımı), diğer katmanlarda kod değişikliği yapmadan sadece repository sınıfını güncelleyerek uygulamayı adapte edebilirsiniz.
// Bu, kodun yeniden kullanılabilirliğini, sürdürülebilirliğini ve temizliğini artırır.

class NoteRepository(private val db: NoteDatabase) { // Veritabanı örneğini alır

    // Coroutine içinde çalıştırılarak veritabanına not ekler
    suspend fun insertNote(note: Note) = db.getNoteDao().insertNote(note)

    // Coroutine içinde çalıştırılarak veritabanından not siler
    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)

    // Coroutine içinde çalıştırılarak veritabanında not günceller
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)

    // Tüm notları sıralanmış şekilde getirir. LiveData döner, UI otomatik güncellenir.
    fun getAllNotes() = db.getNoteDao().getAllNotes()

    // Kullanıcının belirttiği başlık veya açıklama içeriğine göre not arar
    fun searchNote(query: String?) = db.getNoteDao().searchNote(query)
}
