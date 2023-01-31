package com.minioguille.crudroom.views;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.minioguille.crudroom.R;
import com.minioguille.crudroom.controllers.ProductsListController;
import com.minioguille.crudroom.databinding.ActivityProductsListBinding;
import com.minioguille.crudroom.views.adapters.ProductsAdapter;

public class UIProductsList extends UICustom {

    private ActivityProductsListBinding mBinding;
    private ProductsAdapter mProductAdapter;
    private ProductsListController mController;
    private RecyclerView mRvList;
    private FloatingActionButton mFBtnAddProduct;

    public interface IProductsListener{
        void onProductEdit(int aIndex);
        void onProductDelete( int aIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setView();

        linkComponents();
        init();
        setEvents();
    }

    @Override
    protected void setView() {
        mBinding = ActivityProductsListBinding.inflate(getLayoutInflater());
        View activityView = mBinding.getRoot();
        setContentView(activityView);
    }

    @Override
    protected void init(){

        mController = new ProductsListController();
        mController.setProductsList(this, new IViewUpdate() {
            @Override
            public void onGetResult() {
                mRvList.setLayoutManager(new LinearLayoutManager(UIProductsList.this,LinearLayoutManager.VERTICAL,false));
                mProductAdapter = new ProductsAdapter(mController, new IProductsListener() {
                    @Override
                    public void onProductEdit(int aIndex) {
                        mController.setCurrentProduct(aIndex);
                        Intent intent = new Intent(UIProductsList.this,UIProductDetail.class);
                        intent.putExtra("product_for_edit",mController.getmCurrentProduct());
                        startActivity(intent);
                    }

                    @Override
                    public void onProductDelete(int aIndex) {
                        dialogConfirmDelete(aIndex);
                    }
                });
                mRvList.setAdapter(mProductAdapter);
            }
        });

    }

    @Override
    protected void linkComponents() {
        mRvList = mBinding.rvUIPList;
        mFBtnAddProduct = mBinding.fbUIPLAddProduct;
    }

    @Override
    protected void setEvents() {

        mFBtnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UIProductsList.this, UIProductDetail.class);
                startActivity(intent);
            }
        });
    }

    private void dialogConfirmDelete(int aProductIndex ){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title_form_incomplete));
        builder.setMessage(getString(R.string.dialog_message_confirm_delete)).setPositiveButton(getString(R.string.dialog_button_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mController.deleteProduct(UIProductsList.this, aProductIndex, new IViewUpdate() {
                    @Override
                    public void onGetResult() {
                        mProductAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
            }
        });
        builder.create();
        builder.show();

    }
}