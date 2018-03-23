package com.timelesssoftware.fabbadge;

import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    NestedScrollView nestedScrollView;
    private FabBadge fabBadgeBtn;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nestedScrollView = findViewById(R.id.nested_scroll);
        fabBadgeBtn = findViewById(R.id.fab_badge_btn);
        button = findViewById(R.id.button);
        fabBadgeBtn.setBadgeNumber(5);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY < oldScrollY) {
                    fabBadgeBtn.show();
                } else {
                    fabBadgeBtn.hide();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabBadgeBtn.setFabBackgroundColor(Color.RED);
                fabBadgeBtn.setFabIconTint(Color.BLACK);
                fabBadgeBtn.setBadgeBackgroundColor(Color.BLACK);
                fabBadgeBtn.setBadgeTextColor(Color.WHITE);
                fabBadgeBtn.setFabIcon(getResources().getDrawable(R.drawable.ic_account_balance_black_24dp));
                fabBadgeBtn.setBadgeNumber(10);
            }
        });
    }
}
