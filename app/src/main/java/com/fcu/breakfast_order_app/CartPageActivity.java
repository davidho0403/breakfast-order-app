package com.fcu.breakfast_order_app;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CartPageActivity extends AppCompatActivity {

    ArrayList<CartClass> cartList = new ArrayList<>();

    public int total = 0;

    private Head cart_headTitle;
    private BackButton cart_back_button;
    public TextView cart_total_price;
    private LinearLayout cart_linearLayout;
    private Nav nav;
    private DatabaseHandler databaseHandler;
    private LinearLayout checkoutBtn;


    public CartItem createCartItem(String cartName, Integer cartPrice, Integer cartImage, Integer cartNumber) {
        CartItem cartItem = new CartItem(this);
        cartItem.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        cartItem.setCartName(cartName);
        cartItem.setCartPrice(cartPrice);
        cartItem.setCartImage(cartImage);
        cartItem.setCartNumber(cartNumber);

        return cartItem;
    }

    public void removeCartItem(View view, String productName) {

        cart_linearLayout.removeView(view);
        databaseHandler.removeProductFromCart(productName);
        fetchCartCount();
    }

    public void updateCartItemNumber(String productName, Integer count) {
        databaseHandler.updateProductCount(productName, count);
        fetchCartCount();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);
        databaseHandler = new DatabaseHandler(this);
        databaseHandler.open();

        cart_linearLayout = findViewById(R.id.cart_linearlayout);
        nav = findViewById(R.id.nav);

        cartList = databaseHandler.getCartProduct();


        for (CartClass item : cartList) {
            cart_linearLayout.addView(createCartItem(
                    item.getProductName(),
                    item.getPrice(),
                    item.getProductImage(),
                    item.getCount()));

            total = total + item.getPrice() * item.getCount();
        }

        cart_headTitle = findViewById(R.id.cart_headTitle);
        cart_headTitle.setHeadTitle("購物車");

        cart_back_button = findViewById(R.id.cart_back_button);
        cart_back_button.setLabel("繼續點餐");

        cart_total_price = findViewById(R.id.cart_total_price);
        cart_total_price.setText("總計:NT " + total);

        cart_back_button.setOnClickListener(v -> {
            onBackPressed();
        });

        checkoutBtn = findViewById(R.id.checkoutBtn);
        checkoutBtn.setOnClickListener(v -> {
            databaseHandler.moveAllCartToOrder();
            Intent intent = new Intent(this, OrderHistoryActivity.class);
            startActivity(intent);
        });
    }

    private void fetchCartCount() {
        databaseHandler = new DatabaseHandler(this);
        databaseHandler.open();

        int productCount = databaseHandler.getCartCount();
        nav.setCartCount(productCount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchCartCount();
    }
}