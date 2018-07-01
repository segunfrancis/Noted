package com.example.computer.noted;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    public static final String PUT_NOTE = "put_note";
    public static final String PUT_CATEGORY = "put_category";
    public static final String PUT_DATE = "put_date";
    public static final String PUT_TIME = "put_time";

    private Context context;

    private final List<UserData> mUserData;


    DatabaseReference reference;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView noteView, categoryView, dateView, timeView;
        private ImageView mDeleteButton;


        public MyViewHolder(View view) {
            super(view);

            noteView = view.findViewById(R.id.noteTextView);
            dateView = view.findViewById(R.id.dateTextView);
            timeView = view.findViewById(R.id.timeTextView);
            categoryView = view.findViewById(R.id.categoryTextView);
            mDeleteButton = view.findViewById(R.id.deleteIconView);

        }
    }

    public NoteAdapter(Context context, List<UserData> userData) {
        this.context = context;
        this.mUserData = userData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    public void addData(UserData data) {
        //  mUserData.add(data);
        ArrayList<UserData> temp = new ArrayList<>();
        temp.add(data);
        temp.addAll(mUserData);
        mUserData.clear();
        mUserData.addAll(temp);
        temp.clear();
        notifyDataSetChanged();
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final UserData userData = mUserData.get(position);
        holder.noteView.setText(mUserData.get(position).getNote());
        holder.categoryView.setText(mUserData.get(position).getCategory());
        holder.dateView.setText(mUserData.get(position).getUpdatedAtDate());
        holder.timeView.setText(mUserData.get(position).getUpdatedAtTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DisplayNoteActivity.class);
                intent.putExtra(PUT_NOTE, userData.getNote());
                intent.putExtra(PUT_CATEGORY, userData.getCategory());
                intent.putExtra(PUT_DATE, userData.getUpdatedAtDate());
                intent.putExtra(PUT_TIME, userData.getUpdatedAtTime());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference("noted");
                reference.child(mUserData.get(position).getKey()).removeValue();
/*mUserData.get(position).getKey()*/
                mUserData.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mUserData.size());
                Toast.makeText(context, "Deleted " + mUserData.get(position).getKey(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mUserData.size();
    }
}