package com.example.computer.noted;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserData {

    public int id;
    private String key;
    private String note;
    private String category;
    private String updatedAtDate;
    private String updatedAtTime;


    public UserData() {
    }

    public UserData(String note, String category, String updatedAtTime, String updatedAtDate) {
        this("", note,category,updatedAtTime, updatedAtDate);
    }

    public UserData(String key, String note, String category, String updatedAtDate, String updatedAtTime) {
        this.key = key;
        this.note = note;
        this.category = category;
        this.updatedAtDate = updatedAtDate;
        this.updatedAtTime = updatedAtTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserData(int id, String key, String note, String category, String updatedAtDate, String updatedAtTime) {
        this.id = id;
        this.key = key;
        this.note = note;
        this.category = category;
        this.updatedAtDate = updatedAtDate;
        this.updatedAtTime = updatedAtTime;

    }

        @Exclude
        public Map<String, Object> toMap () {
            HashMap<String, Object> result = new HashMap<>();
            result.put("key", key);
            result.put("noteView", note);
            result.put("category", category);
            result.put("updatedAtTime", updatedAtTime);
            result.put("updatedAtDate", updatedAtDate);

            return result;
        }

        public String getKey () {
            return key;
        }

        public void setKey (String key){
            this.key = key;
        }

        public String getNote () {
            return note;
        }

        public void setNote (String note){
            this.note = note;
        }

        public String getCategory () {
            return category;
        }

        public void setCategory (String category){
            this.category = category;
        }

        public String getUpdatedAtDate () {
            return updatedAtDate;
        }

        public void setUpdatedAtDate (String updatedAtDate){
            this.updatedAtDate = updatedAtDate;
        }

        public String getUpdatedAtTime () {
            return updatedAtTime;
        }

        public void setUpdatedAtTime (String updatedAtTime){
            this.updatedAtTime = updatedAtTime;
        }
}