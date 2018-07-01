package com.example.computer.noted;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public  class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView noteView;
    public TextView categoryView;
    public TextView dateView;
    public TextView timeView;
    public ImageView deleteIcon;
    public View recyclerView;
    private List<UserData> mUserData;
    final private ItemClickListener mItemClickListener;

    public NoteViewHolder(View itemView,  List<UserData> userData, ItemClickListener listener) {
        super(itemView);
        this.mUserData = userData;
        mItemClickListener = listener;
        noteView = itemView.findViewById(R.id.noteTextView);
        categoryView = itemView.findViewById(R.id.categoryTextView);
        dateView = itemView.findViewById(R.id.dateTextView);
        timeView = itemView.findViewById(R.id.timeTextView);
        deleteIcon = itemView.findViewById(R.id.deleteIconView);
        recyclerView = itemView.findViewById(R.id.recyclerView);

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editNote = mUserData.get(getAdapterPosition()).getUpdatedAtTime();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query notesQuery = ref.orderByChild("noteView").equalTo(editNote);
                notesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    @Override
    public void onClick(View view) {
        int elementId = mUserData.get(getAdapterPosition()).getId();
        mItemClickListener.onItemClickListener(elementId);
    }
}