package org.wit.notepad.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.notepad.helpers.*
import java.util.*

val JSON_FILE = "notepads.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<NotepadModel>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class NotepadJSONStore : NotepadStore, AnkoLogger {

  val context: Context
  var notepads = mutableListOf<NotepadModel>()

  constructor (context: Context) {
    this.context = context
    if (exists(context, JSON_FILE)) {
      deserialize()
    }
  }

  override fun findAll(): MutableList<NotepadModel> {
    return notepads
  }

  override fun create(notepad: NotepadModel) {
    notepad.id = generateRandomId()
    notepads.add(notepad)
    serialize()
  }
  
  override fun update(notepad: NotepadModel) {
    var foundNotepad: NotepadModel? = notepads.find { p -> p.id == notepad.id }
    if (foundNotepad != null) {
      foundNotepad.title = notepad.title
      foundNotepad.description = notepad.description
      foundNotepad.selection = notepad.selection
      foundNotepad.image = notepad.image
      foundNotepad.lat = notepad.lat
      foundNotepad.lng = notepad.lng
      foundNotepad.zoom = notepad.zoom
    }
    serialize()
  }

  override fun delete(notepad: NotepadModel) {
    notepads.remove(notepad)
    serialize()
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(notepads, listType)
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    notepads = Gson().fromJson(jsonString, listType)
  }
}
