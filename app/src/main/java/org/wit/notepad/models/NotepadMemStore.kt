package org.wit.notepad.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
  return lastId++
}

class NotepadMemStore : NotepadStore, AnkoLogger {

  val notepads = ArrayList<NotepadModel>()

  override fun findAll(): List<NotepadModel> {
    return notepads
  }

  override fun create(notepad: NotepadModel) {
    notepad.id = getId()
    notepads.add(notepad)
    logAll()
  }

  override fun update(notepad: NotepadModel) {
    var foundTask: NotepadModel? = notepads.find { p -> p.id == notepad.id }
    if (foundTask != null) {
      foundTask.title = notepad.title
      foundTask.description = notepad.description
      foundTask.selection = notepad.selection
      foundTask.image = notepad.image
      foundTask.lat = notepad.lat
      foundTask.lng = notepad.lng
      foundTask.zoom = notepad.zoom
      logAll();
    }
  }

  override fun delete(notepad: NotepadModel) {
    notepads.remove(notepad)
  }

  fun logAll() {
    notepads.forEach { info("${it}") }
  }
}