package com.example.computer.noted;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {

//    private Button mLogOutButton;
    private ImageView mEditIconView;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FloatingActionButton fabButton;

    private RecyclerView mRecyclerView;

    private NoteAdapter mAdapter;

    private static final int RC_SIGN_IN = 234;

    private FirebaseDatabase mFirebasedatabase;

    private DatabaseReference mDatabaseReference;

    private TextView note, category, date, time;
    private ImageView deleteImage;

    private FirebaseAuth mAuth;
    private List<UserData> mUserData = new ArrayList<UserData>();

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        mUserData = new ArrayList<>(0);
        mAdapter = new NoteAdapter(getApplicationContext(), mUserData);


        mAuth = FirebaseAuth.getInstance();

        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(NoteListActivity.this));

        mRecyclerView.setHasFixedSize(true);

//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);


//        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
//        mRecyclerView.addItemDecoration(decoration);

//        mLogOutButton = findViewById(R.id.logout_button);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(NoteListActivity.this, NotedActivity.class));
                }
            }
        };

        fabButton = findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteListActivity.this, AddNoteActivity.class));
            }
        });
        mFirebasedatabase = FirebaseDatabase.getInstance();

        note = findViewById(R.id.noteTextView);
        category = findViewById(R.id.categoryTextView);
        time = findViewById(R.id.timeTextView);
        date = findViewById(R.id.dateTextView);
        deleteImage = findViewById(R.id.deleteIconView);

        mUserData.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference().child("Noted");

//        String key = ref.push().getKey();
//        ref
//                .child(key)
//                .child("category")
//                .setValue("");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUserData.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //get value from postSnapshot to journal object
                    UserData data = postSnapshot.getValue(UserData.class);
                    UserData userData = new UserData(data.getNote(), data.getCategory(), data.getUpdatedAtDate(), data.getUpdatedAtTime());
                    //add journal object to list used in recycler
                    mUserData.add(userData);

                    //notify data changed to recyclerView
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout){
                    mAuth.signOut();
                }
        return super.onOptionsItemSelected(item);
    }
}