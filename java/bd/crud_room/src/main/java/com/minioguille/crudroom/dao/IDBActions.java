/**
 * =================================================
 * INTERFACE: IDBActions
 * DESCRIPTION: Defines the methods that execute
 *              db's queries.
 * CREATE BY: GM
 * =================================================
 */
package com.minioguille.crudroom.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.minioguille.crudroom.models.Category;
import com.minioguille.crudroom.models.Product;
import com.minioguille.crudroom.models.ProductsByCategory;

import java.util.List;

@Dao
public interface IDBActions {
    @Insert
    void insertProduct(Product aProduct);
    @Delete
    void deleteProduct(Product aProduct);
    @Update
    void updateProduct(Product aProduct);

    @Query("SELECT * FROM products")
    List<Product> getAllProducts();

    @Query("SELECT * FROM products WHERE id = :aProductId")
    Product getProductById(int aProductId);

    @Query("SELECT * FROM categories")
    List<Category> getAllCategories();

    @Query("SELECT * FROM categories WHERE id = :aCategoryId")
    Category getCategoryById(int aCategoryId);

}
