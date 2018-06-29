package com.example.computer.noted;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoteListActivity extends AppCompatActivity{

    private Button mLogOutButton;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FloatingActionButton fabButton;

    private List<UserData> mUserData;

    private RecyclerView mRecyclerView;

    private NoteAdapter mAdapter;

//     private NoteAdapter.ItemClickListener itemClickListener;

    private FirebaseDatabase mFirebasedatabase;

    private DatabaseReference mDatabaseReference;

    private TextView note, category, date, time;


//    privateA AddDatabase mDb;

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
        mAdapter = new NoteAdapter(this, mUserData);


        mAuth = FirebaseAuth.getInstance();

        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(NoteListActivity.this));

//        mRecyclerView.setHasFixedSize(true);

//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);

//        prepareNoteData();

//        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
//        mRecyclerView.addItemDecoration(decoration);

        mLogOutButton = findViewById(R.id.logout_button);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(NoteListActivity.this, NotedActivity.class));
                }
            }
        };

        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });

        fabButton = findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteListActivity.this, AddNoteActivity.class));
            }
        });
        mFirebasedatabase = FirebaseDatabase.getInstance();
    }
//
    @Override
    protected void onResume() {
        super.onResume();

        note = findViewById(R.id.noteTextView);
        category = findViewById(R.id.categoryTextView);
        time = findViewById(R.id.timeTextView);
        date = findViewById(R.id.dateTextView);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("Noted");

//        String key = ref.push().getKey();
//        ref
//                .child(key)
//                .child("category")
//                .setValue("");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                try{
                    UserData userData = dataSnapshot.getValue(UserData.class);

                    userData.toMap();
                    mAdapter.addData(userData);

                }catch(NullPointerException e){
                    Toast.makeText(NoteListActivity.this, "Error casting Note", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        prepareNoteData();

    }
//
//    private void prepareNoteData() {
//        //mUserData.clear();
//        mDatabaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.app_name));
//
//        mDatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //mUserData.clear();
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    UserData userData = postSnapshot.getValue(UserData.class);
//                    UserData userData1 = new UserData(userData.getNote(), userData.getCategory(), userData.getUpdatedAtDate(), userData.getUpdatedAtTime());
//                    mUserData.add(userData1);
//                    mAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

}