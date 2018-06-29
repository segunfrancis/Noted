package com.example.computer.noted;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.firebase.ui.auth.ui.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<UserData> userData;

//    final private ItemClickListener mItemClickListener;

    protected Context context;

    public NoteAdapter(Context context, List<UserData> userData) {
        this.context = context;
        this.userData = userData;
    }


    public void addData(UserData data){
      //  userData.add(data);
        ArrayList<UserData> temp = new ArrayList<>();
        temp.add(data);
        temp.addAll(userData);
        userData.clear();
        userData.addAll(temp);
        temp.clear();
        notifyDataSetChanged();
    }


    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the note_layout to a view
        ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_layout, parent, false);
        viewHolder = new ViewHolder(view);
        return new ViewHolder(view);
    }


    public static class ViewHolder extends  RecyclerView.ViewHolder{

        protected View innerView;
        public ViewHolder(View itemView) {
            super(itemView);
            innerView = itemView;
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        View card = holder.innerView;


        TextView  noteView = card.findViewById(R.id.noteTextView);
        TextView  categoryView = card.findViewById(R.id.categoryTextView);
        TextView  dateView = card.findViewById(R.id.dateTextView);
        TextView timeView = card.findViewById(R.id.timeTextView);
        ImageView editIcon = card.findViewById(R.id.editIconView);


        noteView.setText(userData.get(position).getNote());
        categoryView.setText(userData.get(position).getCategory());
        dateView.setText(userData.get(position).getUpdatedAtDate());
        timeView.setText(userData.get(position).getUpdatedAtTime());

       UserData currentUserData = userData.get(position);

       String key  =currentUserData.getKey();

        final int currentPosition = position;
        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editNote = userData.get(position).getUpdatedAtTime();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query notesQuery = ref.orderByChild("note").equalTo(editNote);
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

    @Override
    public int getItemCount() {
        return userData.size();
    }
}
