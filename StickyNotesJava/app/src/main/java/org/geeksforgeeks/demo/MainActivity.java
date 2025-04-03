package org.geeksforgeeks.demo;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private EditText mEditText;

    // Creating a new note
    private final StickyNote note = new StickyNote();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting the reference of the EditText
        mEditText = findViewById(R.id.editText);
        FloatingActionButton save = findViewById(R.id.floating_save_button);

        // Retrieve the text from the saved file in memory (if any) and set it to the EditText
        mEditText.setText(note.getStick(this));

        save.setOnClickListener(view -> saveButton());
    }

    // Function to update the Widget (Sticky Note) every time the user saves the note
    private void updateWidget() {
        // The AppWidgetManager helps us manage all the widgets from this app
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        // The RemoteViews class allows us to inflate a layout resource file hierarchy
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.app_widget);

        // By using ComponentName class, we can get a specific application component
        ComponentName thisWidget = new ComponentName(this, AppWidget.class);

        // Update the text of the TextView of the widget
        remoteViews.setTextViewText(R.id.appwidget_text, mEditText.getText().toString());

        // Finally, use the AppWidgetManager instance to update all the widgets
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
        finish();
    }

    // This function saves the current status of the EditText
    private void saveButton() {
        // Update the content of the file stored in memory
        note.setStick(mEditText.getText().toString(), this);
        updateWidget();
        Toast.makeText(this, "Updated Successfully!!", Toast.LENGTH_SHORT).show();
    }
}