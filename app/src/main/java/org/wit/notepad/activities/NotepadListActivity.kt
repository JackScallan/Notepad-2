package org.wit.notepad.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.activity_notepad_.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.notepad.R
import org.wit.notepad.main.MainApp
import org.wit.notepad.models.NotepadModel

class NotepadListActivity : AppCompatActivity(), NotepadListener {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notepad_)
    app = application as MainApp
    toolbarMain.title = title
    setSupportActionBar(toolbarMain)

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = NotepadAdapter(app.notepads.findAll(), this)
    loadNotepads()
  }

  private fun loadNotepads() {
    showNotepads( app.notepads.findAll())
  }

  fun showNotepads (notepads: List<NotepadModel>) {
    recyclerView.adapter = NotepadAdapter(notepads, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> startActivityForResult<NotepadActivity>(0)
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onNotepadClick(notepad: NotepadModel) {
    startActivityForResult(intentFor<NotepadActivity>().putExtra("notepad_edit", notepad), 0)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    loadNotepads()
    super.onActivityResult(requestCode, resultCode, data)
  }
}