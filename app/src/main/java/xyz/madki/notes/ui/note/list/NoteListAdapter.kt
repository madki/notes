package xyz.madki.notes.ui.note.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.Bind
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import xyz.madki.notes.R
import xyz.madki.notes.data.Note
import xyz.madki.notes.ui.base.PerActivity
import java.util.*
import javax.inject.Inject

@PerActivity
class NoteListAdapter @Inject constructor(): RecyclerView.Adapter<NoteListAdapter.Holder>() {
    var notes: List<Note> = ArrayList()

    fun setData(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return Holder(view)
    }

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        @Bind(R.id.iv_photo) lateinit var ivPhoto: ImageView
        @Bind(R.id.tv_title) lateinit var tvTitle: TextView
        @Bind(R.id.tv_text) lateinit var tvText: TextView

        init {
            ButterKnife.bind(this, view)
        }

        fun bind(note: Note) {
            Glide.with(ivPhoto.context).load(note.photo).into(ivPhoto)
            tvTitle.text = note.title
            tvText.text = note.text
        }
    }
}


