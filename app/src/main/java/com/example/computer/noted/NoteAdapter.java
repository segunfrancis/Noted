package com.example.computer.noted;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<UserData> mUserData;

    final private ItemClickListener mItemClickListener;

    private Context mContext;

    public NoteAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the note_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.activity_note_list, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        UserData userData = mUserData.get(position);
        int id = userData.getId();
        String note = userData.getNote();
        String category = userData.getCategory();
        String date = userData.getUpdatedAtDate();
        String time = userData.getUpdatedAtTime();

        //Set values
        holder.noteView.setText(note);
        holder.categoryView.setText(category);
        holder.dateView.setText(date);
        holder.timeView.setText(time);
    }

    @Override
    public int getItemCount() {
        if (mUserData == null) {
            return 0;
        }
        return mUserData.size();
    }

    public void setNotes(List<UserData> userData) {
        mUserData = userData;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView noteView;
        TextView categoryView;
        TextView dateView;
        TextView timeView;

        public NoteViewHolder(View itemView) {
            super(itemView);

            noteView = itemView.findViewById(R.id.noteTextView);
            categoryView = itemView.findViewById(R.id.categoryTextView);
            dateView = itemView.findViewById(R.id.dateTextView);
            timeView = itemView.findViewById(R.id.timeTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mUserData.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);

        }
    }
}
