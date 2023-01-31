/**
 * =================================================
 * CLASS: DBProducts
 * DESCRIPTION: the db.
 * CREATE BY: GM
 * =================================================
 */
package com.minioguille.crudroom.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.minioguille.crudroom.models.Product;
import com.minioguille.crudroom.models.Category;

@Database(entities = {Product.class, Category.class}, version = 1)
public abstract class DBProducts extends RoomDatabase {
 public abstract IDBActions dataBaseActions();

}
