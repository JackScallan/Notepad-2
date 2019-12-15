package org.wit.notepad.models

interface NotepadStore {
  fun findAll(): List<NotepadModel>
  fun create(task: NotepadModel)
  fun update(task: NotepadModel)
  fun delete(task: NotepadModel)
}