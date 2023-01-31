package com.minioguille.crudroom.views;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.minioguille.crudroom.R;
import com.minioguille.crudroom.controllers.ProductDetailController;
import com.minioguille.crudroom.databinding.ActivityProductDetailBinding;

import java.util.ArrayList;

public class UIProductDetail extends UICustom implements AdapterView.OnItemSelectedListener {

    private static final int ADD_PRODUCT_MODE = 0;
    private static final int EDIT_PRODUCT_MODE = 1;

    private ActivityProductDetailBinding mBinding;

    private TextView mTextTitle;
    private EditText mEditProductName;
    private EditText mEditProductDescription;
    private EditText mEditProductPrice;
    private Spinner mSpinnerCategories;
    private Button mBtnCreate;
    private Button mBtnCancel;

    private ProductDetailController mController;
    private ArrayList<TextInputLayout> mFormFields;
    private ArrayList<TextInputLayout> mEmptyFields;

    private int mCurrentViewMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setView();

        linkComponents();
        init();
        setEvents();

    }

    @Override
    public void onBackPressed(){
        showConfirmBackMessage();
    }

    @Override
    protected void setView() {
        mBinding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        View activityView = mBinding.getRoot();
        setContentView(activityView);
    }

    @Override
    protected void init(){

        mController = new ProductDetailController();
        Intent intent = getIntent();
        if ( intent.getExtras()!=null){
            mController.setmEditableProduct(intent,"product_for_edit");
            mCurrentViewMode = EDIT_PRODUCT_MODE;
        }else
            mCurrentViewMode = ADD_PRODUCT_MODE;

        mFormFields = new ArrayList<>();
        mEmptyFields = new ArrayList<>();
        ConstraintLayout constraintLayout = mBinding.clUIPDContainer;
        int totalChild = constraintLayout.getChildCount();
        for ( int i = 0; i < totalChild; i++){
            View child = constraintLayout.getChildAt(i);
            if ( child instanceof TextInputLayout){
                mFormFields.add((TextInputLayout) child);
            }
        }

        mController.getCategories(this, new IViewUpdate() {
            @Override
            public void onGetResult() {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UIProductDetail.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, mController.getmCategoriesName());
                mBinding.spUIPDCategories.setAdapter(adapter);
                setViewMode();
            }
        });
    }

    @Override
    protected void linkComponents() {
        mTextTitle = mBinding.txUIPDTittle;
        mSpinnerCategories = mBinding.spUIPDCategories;
        mEditProductName = mBinding.edUIPDName;
        mEditProductDescription = mBinding.edUIPDDescription;
        mEditProductPrice = mBinding.edUIPDPrice;
        mBtnCreate = mBinding.btnUIPDCreate;
        mBtnCancel = mBinding.btnUIPDCancel;

        mSpinnerCategories.setOnItemSelectedListener(this);
    }

    @Override
    protected void setEvents() {

        mBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restoreField();
                if ( !checkForEmptyFields()){
                    String name = mEditProductName.getEditableText().toString();
                    String description = mEditProductDescription.getEditableText().toString();
                    float price = Float.parseFloat(mEditProductPrice.getEditableText().toString());

                    int categorySelected = mSpinnerCategories.getSelectedItemPosition()+1;

                    if ( mCurrentViewMode == EDIT_PRODUCT_MODE){
                        mController.updateProductDetail(UIProductDetail.this, name, description, price,categorySelected, new IViewUpdate() {
                            @Override
                            public void onGetResult() {
                                showProductCreateOrEditedOk(getString(R.string.dialog_product_edited));
                            }
                        });
                    }else{
                        mController.createProductDetail(UIProductDetail.this, name, description, price,categorySelected, new IViewUpdate() {
                            @Override
                            public void onGetResult() {
                                showProductCreateOrEditedOk(getString(R.string.dialog_new_product_created));
                            }
                        });
                    }


                }else{
                    showErrorMessage();
                }
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmBackMessage();
            }
        });
    }

    private void setViewMode(){
        if ( mCurrentViewMode == EDIT_PRODUCT_MODE){
            mTextTitle.setText(getString(R.string.product_detail_title_edit));
            mBtnCreate.setText(getString(R.string.product_confirm_changes));

            mEditProductName.setText(mController.getmEditableProduct().getmName());
            mEditProductDescription.setText(mController.getmEditableProduct().getmDescription());
            mSpinnerCategories.setSelection(mController.getmEditableProduct().getmCategory()-1);

        }else{
            mTextTitle.setText(getString(R.string.product_detail_title_new));
            mBtnCreate.setText(getString(R.string.product_create));
        }
    }


    private boolean checkForEmptyFields(){
        boolean theFirst = false;
        for (TextInputLayout field : mFormFields){
            if ( field.getEditText().getText().toString().isEmpty()){
                mEmptyFields.add(field);
                if ( !theFirst) {
                    field.requestFocus();
                    theFirst = true;
                }
            }
        }

        return mEmptyFields.size() != 0;
    }

    private void showError(){
        for ( TextInputLayout field : mEmptyFields){
            field.setError(" ");
            field.setErrorEnabled(true);
        }
    }

    private void restoreField(){
        for (TextInputLayout field : mEmptyFields)
            field.setErrorEnabled(false);
        mEmptyFields.clear();
    }

    private void goTo(){
        Intent intent = new Intent(this, UIProductsList.class);
        startActivity(intent);
        finish();
    }

    private void showErrorMessage(){
        AlertDialog.Builder dialog = new AlertDialog.Builder( this );
        dialog.setTitle(getString(R.string.dialog_title_form_incomplete));
        dialog.setMessage(R.string.dialog_message_form_incomplete).setPositiveButton(getString(R.string.dialog_button_acept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showError();
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                showError();
            }
        });
        dialog.create();
        dialog.show();
    }

    private void showProductCreateOrEditedOk(String aMessage){
        AlertDialog.Builder dialog = new AlertDialog.Builder( this );
        dialog.setMessage(aMessage).setPositiveButton(getString(R.string.dialog_button_acept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                goTo();
            }
        });
        dialog.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void showConfirmBackMessage(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getString(R.string.dialog_message_doubt)).setPositiveButton(getString(R.string.dialog_button_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton(getString(R.string.button_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}