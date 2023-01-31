/**
 * =================================================
 * CLASS: ProductsListController
 * DESCRIPTION: Manages the logic to show all the
 *              products that are stored.
 * CREATE BY: GM
 * =================================================
 */
package com.minioguille.crudroom.controllers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.minioguille.crudroom.dao.DBClient;
import com.minioguille.crudroom.models.Category;
import com.minioguille.crudroom.models.Product;
import com.minioguille.crudroom.models.ProductsByCategory;
import com.minioguille.crudroom.views.IViewUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductsListController {

    private ExecutorService mExecutor;
    private Handler mHandler;

    private List<Product> mProducts;
    private List<Category> mCategories;
    private Product mCurrentProduct;
    private int mProductListSize;

    public ProductsListController(){

    }

    public void setProductsList(Context aContext, IViewUpdate aListener){

        mProducts = new ArrayList<>();
        mCategories = new ArrayList<>();

        mExecutor = Executors.newSingleThreadExecutor();
        mHandler = new Handler(Looper.getMainLooper());

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mProducts = DBClient.getmInstance(aContext).getDB().dataBaseActions().getAllProducts();

                mProductListSize = mProducts.size();
                for ( Product product:mProducts){
                    Category category = DBClient.getmInstance(aContext).getDB().dataBaseActions().getCategoryById(product.getmCategory());
                    mCategories.add(category);
                }

                mExecutor.shutdown();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mExecutor.isShutdown()){
                            aListener.onGetResult();
                        }
                    }
                });
            }
        });

    }

    public void deleteProduct( Context aContext, int aPosition, IViewUpdate aListener ){

        mExecutor = Executors.newSingleThreadExecutor();
        mHandler = new Handler(Looper.getMainLooper());

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mProducts.remove(aPosition);
                DBClient.getmInstance(aContext).getDB().dataBaseActions().deleteProduct(mCurrentProduct);
                mProductListSize = mProducts.size();
                mExecutor.shutdown();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mExecutor.isShutdown()){
                            aListener.onGetResult();
                        }
                    }
                });
            }
        });
    }

    public void setCurrentProduct(int aPosition){
        mCurrentProduct = mProducts.get(aPosition);
    }

    public Product getmCurrentProduct(){
        return mCurrentProduct;
    }

    public List<Product> getmProducts(){
        return mProducts;
    }

    public int getListProductSize(){
        return mProductListSize;
    }

    public String getCategoryName(int aPosition){
        return mCategories.get(aPosition).getmName();
    }
}
