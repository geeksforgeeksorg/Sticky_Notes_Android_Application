package org.geeksforgeeks.demo

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Bundle
import android.widget.EditText
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var mEditText: EditText
    private lateinit var save: FloatingActionButton

    // creating a new note
    private var note: StickyNote = StickyNote()

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)

        // getting the reference of the EditText
        mEditText = findViewById(R.id.editText)
        save = findViewById(R.id.floating_save_button)

        // retrieve the text from the saved file in
        // memory(if any) and set it to the edittext
        mEditText.setText(note.getStick(this))

        save.setOnClickListener {
            saveButton()
        }
    }

    // function to update the
    // Widget(Sticky Note) every time
    // user saves the note
    private fun updateWidget() {
        // the AppWidgetManager helps
        // us to manage all the
        // widgets from this app
        val appWidgetManager = AppWidgetManager.getInstance(this)


        // the RemoteViews class allows us to inflate a
        // layout resource file hierarchy and provides some
        // basic operations for modifying the content of the
        // inflated hierarchy
        val remoteViews = RemoteViews(this.packageName, R.layout.app_widget)


        // by using ComponentName class we can get specific
        // application component and to identify the
        // component we pass the package name and the class
        // name inside of that package
        val thisWidget = ComponentName(this, AppWidget::class.java)

        // update the text of the textview of the widget
        remoteViews.setTextViewText(R.id.appwidget_text, mEditText.text.toString())


        // finally us the AppWidgetManager instance to
        // update all the widgets
        appWidgetManager.updateAppWidget(thisWidget, remoteViews)
        finish()
    }

    // this function saves
    // the current status
    // of the EditText
    private fun saveButton() {
        // update the content of file stored in the memory
        note.setStick(mEditText.text.toString(), this)
        updateWidget()
        Toast.makeText(this, "Updated Successfully!!", Toast.LENGTH_SHORT).show()
    }
}