package com.example.lap6_ltmb;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Ví dụ: click vào logo ở màn 2 thì back về MainActivity
        ImageView iv = findViewById(R.id.iv_detail_logo);
        iv.setOnClickListener(v -> finishWithSlideBack());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Áp animation khi trở về MainActivity
        finishWithSlideBack();
    }

    private void finishWithSlideBack() {
        // đóng DetailActivity và trượt về phải
        overridePendingTransition(R.anim.activity_in_from_left, R.anim.activity_out_to_right);
    }
}
