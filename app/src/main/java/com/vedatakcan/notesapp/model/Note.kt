package com.vedatakcan.notesapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// Room veritabanında bir tabloyu temsil eden sınıf
@Entity(tableName = "notes") // Bu sınıfın veritabanında "notes" adlı bir tabloyu temsil ettiğini belirtir
@Parcelize // Bu sınıfın Parcelable olmasını sağlar, nesneleri Android'de taşımak için kullanılır
data class Note(
    @PrimaryKey(autoGenerate = true) // Bu alanı tablo için birincil anahtar olarak işaretler ve otomatik artırılır
    val id: Int, // Notun benzersiz kimliği
    val noteTitle: String, // Notun başlığını saklayan alan
    val noteDesc: String // Notun açıklamasını saklayan alan
) : Parcelable // Bu sınıfın Parcelable arayüzünü uyguladığını belirtir
