package org.wit.notepad.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_notepad.view.*
import org.wit.notepad.R
import org.wit.notepad.helpers.readImageFromPath
import org.wit.notepad.models.NotepadModel

interface NotepadListener {
  fun onNotepadClick(notepad: NotepadModel)
}

class NotepadAdapter constructor(private var notepads: List<NotepadModel>,
                              private val listener: NotepadListener) : RecyclerView.Adapter<NotepadAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_notepad, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val notepad = notepads[holder.adapterPosition]
    holder.bind(notepad, listener)
  }

  override fun getItemCount(): Int = notepads.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(notepad: NotepadModel, listener: NotepadListener) {
      itemView.notepadTitle.text = notepad.title
      itemView.description.text = notepad.description
      itemView.NotepadSelection.text = notepad.selection
      itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, notepad.image))
      itemView.setOnClickListener { listener.onNotepadClick(notepad) }
    }
  }
}