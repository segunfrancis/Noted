package com.example.computer.noted;



public class UserData {

    public int id;
    public String note;
    public String category;
    public String updatedAtDate;
    public String updatedAtTime;

    // Default constructor required for calls to DataSnapshot.getValue(UserDate.class)
    public UserData() {

    }

    public UserData(int id, String note, String category, String updatedAtTime, String updatedAtDate) {
        this.id = id;
        this.note = note;
        this.category = category;
        this.updatedAtTime = updatedAtTime;
        this.updatedAtDate = updatedAtDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUpdatedAtDate() {
        return updatedAtDate;
    }

    public void setUpdatedAtDate(String updatedAtDate) {
        this.updatedAtDate = updatedAtDate;
    }

    public String getUpdatedAtTime() {
        return updatedAtTime;
    }

    public void setUpdatedAtTime(String updatedAtTime) {
        this.updatedAtTime = updatedAtTime;
    }
}