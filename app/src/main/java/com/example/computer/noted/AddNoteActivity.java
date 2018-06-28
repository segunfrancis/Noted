package com.example.computer.noted;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {

    private EditText mEditText;
    private Spinner mSpinner;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference noteEntriesCloudEndPoint;
    private DatabaseReference categoryCloudEndPoint;
    private DatabaseReference dateCloudEndPoint;

    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        /**
         * Enabling disk persistence for offline functionality
         */
        // FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        /**
         * Setting App name to firebase database
         */

        noteEntriesCloudEndPoint = mFirebaseDatabase.child("noteEntries");
        categoryCloudEndPoint = mFirebaseDatabase.child("category");
        dateCloudEndPoint = mFirebaseDatabase.child("date");

        mEditText = findViewById(R.id.note_edittext);
        mSpinner = findViewById(R.id.category_spinner);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String note = mEditText.getText().toString();
        if (item.getItemId() == R.id.save) {
            if (note.isEmpty()) {
                Toast.makeText(AddNoteActivity.this, "Empty note not saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddNoteActivity.this, NoteListActivity.class));
            } else {
                onSaveItemClicked();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSaveItemClicked(){
        int id = getTaskId();
        String note = mEditText.getText().toString();
        String category = mSpinner.getSelectedItem().toString();
        String currentDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());

        /**
         * Setting App name to firebase database
         */
        mFirebaseDatabase.child("app_title").setValue(getString(R.string.app_name));

        // Creating new user node, which returns the unique key value
        // userId = mFirebaseDatabase.push().getKey();

         UserData userdata = new UserData(id, note, category, currentTime, currentDate);

        // pushing notes to userdata node using the userId
         mFirebaseDatabase.push().setValue(userdata);
         // finish();

        // noteEntriesCloudEndPoint.setValue(note);
        // categoryCloudEndPoint.setValue(category);
        Toast.makeText(AddNoteActivity.this, "Noted!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AddNoteActivity.this, NoteListActivity.class));
    }
}