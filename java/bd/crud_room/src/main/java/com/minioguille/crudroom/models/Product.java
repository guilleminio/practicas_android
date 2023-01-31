package com.minioguille.crudroom.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(tableName = "products", foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "id",
        childColumns = "product_category",
        onDelete = ForeignKey.SET_NULL))
public class Product implements Serializable {
    public @PrimaryKey (autoGenerate = true) int id;
    @NotNull
    public @ColumnInfo(name = "product_name" ) String mName;
    @NotNull
    public @ColumnInfo(name = "product_description") String mDescription;
    @NotNull
    public @ColumnInfo(name = "product_price") float mPrice;
    @NotNull
    public @ColumnInfo(name = "product_category", index = true) int mCategory;

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

    public float getmPrice() {
        return mPrice;
    }

    public void setmPrice(float mPrice) {
        this.mPrice = mPrice;
    }

    public int getmCategory() {
        return mCategory;
    }

    public void setmCategory(int mCategory) {
        this.mCategory = mCategory;
    }

}
