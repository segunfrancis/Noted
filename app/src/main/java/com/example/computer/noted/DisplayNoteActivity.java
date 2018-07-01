package com.example.computer.noted;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import static com.example.computer.noted.NoteAdapter.PUT_CATEGORY;
import static com.example.computer.noted.NoteAdapter.PUT_DATE;
import static com.example.computer.noted.NoteAdapter.PUT_NOTE;
import static com.example.computer.noted.NoteAdapter.PUT_TIME;

public class DisplayNoteActivity extends AppCompatActivity {

    private int position;
    List<UserData> mUserData;
    NoteAdapter noteAdapter;

    TextView savedNote;
    TextView savedCategory;
    TextView savedDate;
    TextView savedTime;

    private Context context;

    String noteGotten;
    String categoryGotten;
    String dateGotten;
    String timeGotten;

    public static final String PUT_EXTRA_NOTE = "put_extra_note";
    public static final String PUT_EXTRA_CATEGORY = "put_extra_category";

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);

        savedNote = findViewById(R.id.savedNoteTextView);
        savedCategory = findViewById(R.id.savedCategoryTextView);
        savedDate = findViewById(R.id.savedDateTextView);
        savedTime = findViewById(R.id.savedTimeTextView);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            noteGotten = bundle.getString(PUT_NOTE);
            categoryGotten = bundle.getString(PUT_CATEGORY);
            dateGotten = bundle.getString(PUT_DATE);
            timeGotten = bundle.getString(PUT_TIME);
        }

        savedNote.setText(noteGotten);
        savedCategory.setText(categoryGotten);
        savedDate.setText(dateGotten);
        savedTime.setText(timeGotten);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView savedNote = findViewById(R.id.savedNoteTextView);
        TextView savedCategory = findViewById(R.id.savedCategoryTextView);
        int id = item.getItemId();
        if (id == R.id.edit) {
            Intent intent = new Intent(this, AddNoteActivity.class);
            intent.putExtra(PUT_EXTRA_NOTE, savedNote.getText().toString());
            intent.putExtra(PUT_EXTRA_CATEGORY, savedCategory.getText().toString());
            this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}