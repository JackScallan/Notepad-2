package org.wit.notepad.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_notepad.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.notepad.R
import org.wit.notepad.helpers.readImage
import org.wit.notepad.helpers.readImageFromPath
import org.wit.notepad.helpers.showImagePicker
import org.wit.notepad.main.MainApp
import org.wit.notepad.models.NotepadModel

class NotepadActivity : AppCompatActivity(), AnkoLogger {

  var notepad = NotepadModel()
  lateinit var app: MainApp
  val IMAGE_REQUEST = 1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_notepad)
    //to create variable to hold spinner
    var manager = arrayOf<String>("Icebox","In Progress","Completed")
    var NotepadSelection = findViewById(R.id.NotepadSelection) as Spinner
    var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,manager)
    NotepadSelection.adapter = adapter
    //Listener
    NotepadSelection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
      override fun onItemSelected(adapterView:AdapterView<*>,view: View, i: Int, l: Long)
      {
        Toast.makeText(this@NotepadActivity, manager[i], Toast.LENGTH_SHORT).show()      }

    override fun onNothingSelected(adapterView: AdapterView<*>){

    }}
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("Notepad Activity started..")

    app = application as MainApp
    var edit = false

    if (intent.hasExtra("notepad_edit")) {
      edit = true
      notepad = intent.extras.getParcelable<NotepadModel>("notepad_edit")
      notepadTitle.setText(notepad.title)
      description.setText(notepad.description)
      NotepadSelection.setSelection(adapter.getPosition(notepad.selection))
      notepadImage.setImageBitmap(readImageFromPath(this, notepad.image))
      if (notepad.image != null) {
        chooseImage.setText(R.string.change_notepad_image)
      }
      btnAdd.setText(R.string.save_notepad)
    }

    btnAdd.setOnClickListener() {
      notepad.title = notepadTitle.text.toString()
      notepad.description = description.text.toString()
      notepad.selection = NotepadSelection.selectedItem.toString()
      if (notepad.title.isEmpty()) {
        toast(R.string.enter_notepad_title)
      } else {
        if (edit) {
          app.notepads.update(notepad.copy())
        } else {
          app.notepads.create(notepad.copy())
        }
      }
      info("add Button Pressed: $notepadTitle")
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }
    btnAdd2.setOnClickListener() {
      notepad.title = notepadTitle.text.toString()
      notepad.description = description.text.toString()
      if (notepad.title.isEmpty()) {
        toast(R.string.enter_notepad_title)
      } else {
        if (edit) {
          app.notepads.delete(notepad.copy())
        }
      }
      info("add Button Pressed: $notepadTitle")
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }
    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }
  }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
      menuInflater.inflate(R.menu.menu_notepad, menu)
      return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
      when (item?.itemId) {
        R.id.item_cancel -> {
          finish()
        }
      }
      return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
      super.onActivityResult(requestCode, resultCode, data)
      when (requestCode) {
        IMAGE_REQUEST -> {
          if (data != null) {
            notepad.image = data.getData().toString()
            notepadImage.setImageBitmap(readImage(this, resultCode, data))
            chooseImage.setText(R.string.change_notepad_image)
          }
        }

      }
    }
  }