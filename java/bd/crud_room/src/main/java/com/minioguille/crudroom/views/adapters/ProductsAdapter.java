package com.minioguille.crudroom.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minioguille.crudroom.R;
import com.minioguille.crudroom.controllers.ProductsListController;
import com.minioguille.crudroom.views.UIProductsList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductItem> {

    private UIProductsList.IProductsListener mProductListener;
    private ProductsListController mController;

    public ProductsAdapter(ProductsListController aController, UIProductsList.IProductsListener aListener){
        mController = aController;
        mProductListener = aListener;
    }

    @NonNull
    @Override
    public ProductItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_detail,parent,false);
        return new ProductItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItem holder, int position) {
        if ( mController.getmProducts().size() > 0){
            mController.setCurrentProduct(position);
            holder.getTxProductName().setText(mController.getmCurrentProduct().getmName());
            holder.getTxProductDescription().setText(mController.getmCurrentProduct().getmDescription());
            holder.getTxProductPrice().setText(String.valueOf(mController.getmCurrentProduct().getmPrice()));
            holder.getTxProductCategory().setText(mController.getCategoryName(position));

            holder.setEvents(position, mProductListener);
        }
    }

    @Override
    public int getItemCount() {
        return mController.getListProductSize();
    }

    public static class ProductItem extends RecyclerView.ViewHolder {

        private TextView txProductName;
        private TextView txProductDescription;
        private TextView txProductPrice;
        private TextView txProductCategory;
        private Button btnProductDelete;
        private Button btnProductEdit;
        private View vView;

        public ProductItem(@NonNull View itemView) {
            super(itemView);
            vView = itemView;

            linkComponents();

        }

        private void linkComponents(){
            txProductName = vView.findViewById(R.id.txUIPLTitle);
            txProductDescription = vView.findViewById(R.id.txUIPLDescription);
            txProductPrice = vView.findViewById(R.id.txUIPLPrice);
            txProductCategory = vView.findViewById(R.id.txUIPLCategory);
            btnProductDelete = vView.findViewById(R.id.btnUIPLDelete);
            btnProductEdit = vView.findViewById(R.id.btnUIPLEdit);
        }

        private void setEvents(int aItemPos, UIProductsList.IProductsListener aListener) {
            btnProductEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aListener.onProductEdit(aItemPos);
                }
            });

            btnProductDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aListener.onProductDelete(aItemPos);
                }
            });

        }

        public TextView getTxProductName() {
            return txProductName;
        }

        public TextView getTxProductDescription() {
            return txProductDescription;
        }

        public TextView getTxProductPrice() {
            return txProductPrice;
        }

        public TextView getTxProductCategory() {
            return txProductCategory;
        }

    }
}
