package com.minioguille.crudroom.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ProductsByCategory {
    @Embedded public Category mCategory;
    @Relation( parentColumn = "mId",
               entityColumn = "product_category"
    )
    public List<Product> mProducts;
}


