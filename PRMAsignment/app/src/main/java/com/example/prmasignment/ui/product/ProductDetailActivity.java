package com.example.prmasignment.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.prmasignment.R;
import com.example.prmasignment.model.Product;
import com.example.prmasignment.ui.auth.SessionManager;
import com.example.prmasignment.utils.CartUtils;

public class ProductDetailActivity extends AppCompatActivity {

    private ProductViewModel viewModel;
    private Product currentProduct;

    private ImageView ivProductDetailImage;
    private TextView tvProductDetailName;
    private TextView tvProductDetailPrice;
    private TextView tvProductDetailDescription;
    private TextView tvProductDetailStock;
    private TextView tvProductDetailPetType;
    private TextView tvProductDetailLifeStage;
    private TextView tvProductDetailWeight;
    private TextView tvProductDetailSpecialNeeds;
    private LinearLayout layoutSpecialNeeds;
    private Button btnAddToCartDetail;
    private Button btnEditProductDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initViews();
        setupViewModel();
        setupObservers();

        int productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        if (productId != -1) {
            viewModel.fetchProductById(productId);
        } else {
            Toast.makeText(this, "Invalid product ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnAddToCartDetail.setOnClickListener(v -> {
            if (currentProduct != null) {
                CartUtils.addProductToCart(this, currentProduct.getProductId(), 1);
            }
        });

        btnEditProductDetail.setOnClickListener(v -> {
            if (currentProduct != null) {
                Intent intent = new Intent(this, ProductActivity.class);
                intent.putExtra("EDIT_PRODUCT_ID", currentProduct.getProductId());
                startActivity(intent);
                finish();
            }
        });
    }

    private void initViews() {
        ivProductDetailImage = findViewById(R.id.ivProductDetailImage);
        tvProductDetailName = findViewById(R.id.tvProductDetailName);
        tvProductDetailPrice = findViewById(R.id.tvProductDetailPrice);
        tvProductDetailDescription = findViewById(R.id.tvProductDetailDescription);
        tvProductDetailStock = findViewById(R.id.tvProductDetailStock);
        tvProductDetailPetType = findViewById(R.id.tvProductDetailPetType);
        tvProductDetailLifeStage = findViewById(R.id.tvProductDetailLifeStage);
        tvProductDetailWeight = findViewById(R.id.tvProductDetailWeight);
        tvProductDetailSpecialNeeds = findViewById(R.id.tvProductDetailSpecialNeeds);
        layoutSpecialNeeds = findViewById(R.id.layoutSpecialNeeds);
        btnAddToCartDetail = findViewById(R.id.btnAddToCartDetail);
        btnEditProductDetail = findViewById(R.id.btnEditProductDetail);
    }

    private void setupViewModel() {
        SessionManager sessionManager = new SessionManager(this);
        String token = sessionManager.getToken();

        ProductViewModelFactory factory = new ProductViewModelFactory(token);
        viewModel = new ViewModelProvider(this, factory).get(ProductViewModel.class);
    }

    private void setupObservers() {
        viewModel.productLiveData.observe(this, product -> {
            if (product != null) {
                currentProduct = product;
                displayProductDetails(product);
            } else {
                Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void displayProductDetails(Product product) {
        tvProductDetailName.setText(product.getName());
        tvProductDetailPrice.setText(String.format("$%.2f", product.getPrice()));
        tvProductDetailDescription.setText(product.getDescription());
        tvProductDetailStock.setText(String.valueOf(product.getStockQuantity()));
        tvProductDetailPetType.setText(product.getPetType() != null ? product.getPetType() : "N/A");
        tvProductDetailLifeStage.setText(product.getLifeStage() != null ? product.getLifeStage() : "N/A");
        tvProductDetailWeight.setText(String.format("%.1f kg", product.getWeightKg()));

        // Handle special needs
        if (product.getSpecialNeeds() != null && !product.getSpecialNeeds().isEmpty()) {
            tvProductDetailSpecialNeeds.setText(product.getSpecialNeeds());
            layoutSpecialNeeds.setVisibility(View.VISIBLE);
        } else {
            layoutSpecialNeeds.setVisibility(View.GONE);
        }

        // For now, use placeholder image. You can integrate with image loading library like Glide later
        ivProductDetailImage.setImageResource(R.drawable.ic_cart);

        // Enable/disable add to cart based on stock
        btnAddToCartDetail.setEnabled(product.getStockQuantity() > 0);
        if (product.getStockQuantity() <= 0) {
            btnAddToCartDetail.setText("Out of Stock");
        } else {
            btnAddToCartDetail.setText("Add to Cart");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
