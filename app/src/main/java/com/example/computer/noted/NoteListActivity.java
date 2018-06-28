package com.example.computer.noted;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class NoteListActivity extends AppCompatActivity implements NoteAdapter.ItemClickListener{

    private Button mLogOutButton;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FloatingActionButton fabButton;

    private RecyclerView mRecyclerView;

    private NoteAdapter mAdapter;

    private FirebaseDatabase mFirebasedatabase;


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

        mRecyclerView = findViewById(R.id.recyclerViewNotes);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(NoteListActivity.this));

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new NoteAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClickListener(int itemId) {

    }
}