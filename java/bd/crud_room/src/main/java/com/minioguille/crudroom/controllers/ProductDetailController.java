/**
 * =================================================
 * CLASS: ProductDetailController
 * DESCRIPTION: Manages the logic to show, create and
 *              edit a product.
 * CREATE BY: GM
 * =================================================
 */
package com.minioguille.crudroom.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.minioguille.crudroom.dao.DBClient;
import com.minioguille.crudroom.models.Category;
import com.minioguille.crudroom.models.Product;
import com.minioguille.crudroom.views.IViewUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductDetailController {

    private ArrayList<String> mCategoriesName;
    private List<Category> mCategories;

    private ExecutorService mExecutor ;
    private Handler mHandler;
    private Product mEditableProduct;

    public ProductDetailController(){
        mCategoriesName = new ArrayList<>();
    }


    public void getCategories(Context aContext, IViewUpdate aViewUpdate){
        mExecutor = Executors.newSingleThreadExecutor();
        mHandler = new Handler(Looper.getMainLooper());
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {

                mCategories = DBClient.getmInstance(aContext).getDB().dataBaseActions().getAllCategories();
                mExecutor.shutdown();
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        if ( mExecutor.isShutdown()){
                            for(Category category: mCategories){
                                mCategoriesName.add(category.getmName());
                            }
                            aViewUpdate.onGetResult();
                        }
                    }
                });
            }
        });
    }

    public void createProductDetail(Context aContext, String aName, String aDescription, float aPrice, int aCategoryId, IViewUpdate aViewUpdate ){
        Product product = new Product();
        product.setmName(aName);
        product.setmDescription(aDescription);
        product.setmPrice(aPrice);
        product.setmCategory(aCategoryId);

        mExecutor = Executors.newSingleThreadExecutor();
        mHandler = new Handler(Looper.getMainLooper());
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {

                DBClient.getmInstance(aContext).getDB().dataBaseActions().insertProduct(product);
                mExecutor.shutdown();
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        if ( mExecutor.isShutdown()){
                            aViewUpdate.onGetResult();
                        }
                    }
                });
            }
        });

    }

    public void updateProductDetail(Context aContext, String aName, String aDescription, float aPrice,int aCategoryId, IViewUpdate aViewUpdate){
        mExecutor = Executors.newSingleThreadExecutor();
        mHandler = new Handler(Looper.getMainLooper());
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mEditableProduct.setmName(aName);
                mEditableProduct.setmDescription(aDescription);
                mEditableProduct.setmPrice(aPrice);
                mEditableProduct.setmCategory(aCategoryId);
                DBClient.getmInstance(aContext).getDB().dataBaseActions().updateProduct(mEditableProduct);
                mExecutor.shutdown();
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        if ( mExecutor.isShutdown()){
                            aViewUpdate.onGetResult();
                        }
                    }
                });
            }
        });


    }

    public ArrayList<String> getmCategoriesName(){
        return mCategoriesName;
    }

    public void setmEditableProduct(Intent aIntent, String aKey){
        mEditableProduct = (Product) aIntent.getSerializableExtra("product_for_edit");
    }

    public Product getmEditableProduct(){
        return mEditableProduct;
    }
}
