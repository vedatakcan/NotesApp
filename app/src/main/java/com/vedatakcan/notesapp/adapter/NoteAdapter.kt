package com.vedatakcan.notesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vedatakcan.notesapp.databinding.NoteLayoutBinding
import com.vedatakcan.notesapp.fragments.HomeFragmentDirections
import com.vedatakcan.notesapp.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    // ViewHolder sınıfı: RecyclerView öğelerini tutan sınıf
    class NoteViewHolder(val itemBinding: NoteLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root)

    // DiffUtil.ItemCallback: Liste öğelerinin karşılaştırılması için callback sınıfı
    private val differCallBack = object : DiffUtil.ItemCallback<Note>() {

        // areItemsTheSame: İki öğenin aynı olup olmadığını kontrol eder (ID ve içerik karşılaştırması)
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.noteDesc == newItem.noteDesc &&
                    oldItem.noteTitle == newItem.noteTitle
        }

        // areContentsTheSame: İki öğenin içeriklerinin tamamen aynı olup olmadığını kontrol eder
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    // AsyncListDiffer: Listeyi verimli şekilde güncellemek için kullanılır
    val differ = AsyncListDiffer(this, differCallBack)

    // onCreateViewHolder: RecyclerView için yeni ViewHolder oluşturur
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        // LayoutInflater ile XML layout dosyasını bağlar ve ViewHolder oluşturur
        return NoteViewHolder(
            NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    // getItemCount: Liste boyutunu döndürür (kaç öğe olduğunu)
    override fun getItemCount(): Int {
        return differ.currentList.size // AsyncListDiffer'in geçerli listesindeki öğe sayısını döndürür
    }

    // onBindViewHolder: Her bir öğe için veriyi bağlar
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        // Geçerli notu differ'dan alır
        val currentNote = differ.currentList[position]

        // ViewHolder'daki TextView'lere verileri bağlar
        holder.itemBinding.noteDesc.text = currentNote.noteDesc
        holder.itemBinding.noteTitle.text = currentNote.noteTitle

        // Öğeye tıklanma işlemi: EditNoteFragment'a geçiş yapar
        holder.itemView.setOnClickListener {
            // Geçiş için yönlendirme işlemi
            val direction = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote)
            it.findNavController().navigate(direction) // Yönlendirme
        }
    }
}
