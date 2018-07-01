package com.example.computer.noted;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
    private RecyclerView mRecyclerView;
    private NoteAdapter mRecyclerViewAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private List<UserData> allUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        String editNote = getIntent().getStringExtra(DisplayNoteActivity.PUT_EXTRA_NOTE);
        String editCategory = getIntent().getStringExtra(DisplayNoteActivity.PUT_EXTRA_CATEGORY);
        mEditText = findViewById(R.id.note_edittext);
        mSpinner = findViewById(R.id.category_spinner);
        mEditText.setText(editNote, TextView.BufferType.EDITABLE);
//        mSpinner.setSelected(R.id);

        allUserData = new ArrayList<UserData>();

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference(getString(R.string.app_name));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mEditText = findViewById(R.id.note_edittext);
        mSpinner = findViewById(R.id.category_spinner);
        String note = mEditText.getText().toString();
        if (item.getItemId() == R.id.save) {
            if (note.isEmpty()) {
                Toast.makeText(AddNoteActivity.this, "Empty Note not saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddNoteActivity.this, NoteListActivity.class));
            } else {
//                onSaveItemClicked();

                String noted = mEditText.getText().toString();
                String category = mSpinner.getSelectedItem().toString();
                String currentDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());

                UserData userdata = new UserData(noted, category, currentTime, currentDate);

                // pushing notes to userdata node using the userId
                String key = mFirebaseDatabase.push().getKey();
                mFirebaseDatabase.child(key).setValue(userdata);
                mFirebaseDatabase.child(key).child("key").setValue(key);
//          finish();

                Toast.makeText(AddNoteActivity.this, "Noted!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddNoteActivity.this, NoteListActivity.class));
                finish();

            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllUserData(DataSnapshot dataSnapshot) {
        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            String notes = singleSnapshot.getValue(String.class);
            String notes1 = singleSnapshot.getValue(String.class);
            String notes2 = singleSnapshot.getValue(String.class);
            String notes3 = singleSnapshot.getValue(String.class);
            allUserData.add(new UserData(notes, notes1, notes2, notes3));
            mRecyclerView = findViewById(R.id.recyclerView);
            mRecyclerViewAdapter = new NoteAdapter(AddNoteActivity.this, allUserData);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
        }
    }

    private void noteDeletion(DataSnapshot dataSnapshot) {
        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            String notes = singleSnapshot.getValue(String.class);
            for (int i = 0; i < allUserData.size(); i++) {
                if (allUserData.get(i).getNote().equals(notes)) {
                    allUserData.remove(i);
                }
            }
        }
    }

    public void onSaveItemClicked() {
        int id = getTaskId();
        String note = mEditText.getText().toString();
        String category = mSpinner.getSelectedItem().toString();
        String currentDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());

        /**
         * Setting App name to firebase database
         */

        UserData userdata = new UserData(note, category, currentTime, currentDate);

        // pushing notes to userdata node using the userId
        mFirebaseDatabase.push().setValue(userdata);

        Toast.makeText(AddNoteActivity.this, "Noted!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AddNoteActivity.this, NoteListActivity.class));
        finish();
    }

}