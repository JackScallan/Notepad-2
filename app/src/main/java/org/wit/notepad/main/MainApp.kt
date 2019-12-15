package org.wit.notepad.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.notepad.models.NotepadJSONStore
import org.wit.notepad.models.NotepadStore

class MainApp : Application(), AnkoLogger {

  lateinit var notepads: NotepadStore

  override fun onCreate() {
    super.onCreate()
    notepads = NotepadJSONStore(applicationContext)
    info("Notepad started")
  }
}