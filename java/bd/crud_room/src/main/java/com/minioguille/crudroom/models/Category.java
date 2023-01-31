package com.minioguille.crudroom.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "categories")
public class Category {
    public @PrimaryKey(autoGenerate = true) int id;
    @NotNull
    public @ColumnInfo(name = "category_name") String mName;
    @NotNull
    public @ColumnInfo(name = "category_description") String mDescription;

    public int getmId() {
        return id;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
