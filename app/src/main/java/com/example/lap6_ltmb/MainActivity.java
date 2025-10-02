// app/src/main/java/vn/uit/lap6_ltmb/MainActivity.java
package com.example.lap6_ltmb;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.animation.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnFadeInXml, btnFadeInCode, btnFadeOutXml, btnFadeOutCode,
            btnBlinkXml, btnBlinkCode, btnZoomInXml, btnZoomInCode, btnZoomOutXml, btnZoomOutCode,
            btnRotateXml, btnRotateCode, btnMoveXml, btnMoveCode, btnSlideUpXml, btnSlideUpCode,
            btnBounceXml, btnBounceCode, btnCombineXml, btnCombineCode;

    private ImageView ivUitLogo;
    private Animation.AnimationListener animationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewsByIds();
        initVariables();

        // --- XML animations ---
        handleClickAnimationXml(btnFadeInXml,  R.anim.anim_fade_in);
        handleClickAnimationXml(btnFadeOutXml, R.anim.anim_fade_out);
        handleClickAnimationXml(btnBlinkXml,   R.anim.anim_blink);
        handleClickAnimationXml(btnZoomInXml,  R.anim.anim_zoom_in);
        handleClickAnimationXml(btnZoomOutXml, R.anim.anim_zoom_out);
        handleClickAnimationXml(btnRotateXml,  R.anim.anim_rotate);
        handleClickAnimationXml(btnMoveXml,    R.anim.anim_move);
        handleClickAnimationXml(btnSlideUpXml, R.anim.anim_slide_up);
        handleClickAnimationXml(btnBounceXml,  R.anim.anim_bounce);

        // Combine (XML): ví dụ kết hợp zoom_in trước rồi rotate sau (chạy nối tiếp bằng code)
        btnCombineXml.setOnClickListener(v -> {
            Animation a1 = AnimationUtils.loadAnimation(this, R.anim.anim_zoom_in);
            a1.setAnimationListener(new SimpleEndListener(() -> {
                Animation a2 = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
                a2.setAnimationListener(animationListener);
                ivUitLogo.startAnimation(a2);
            }));
            ivUitLogo.startAnimation(a1);
        });

        // --- CODE animations ---
        handleClickAnimationCode(btnFadeInCode,  createFadeIn());
        handleClickAnimationCode(btnFadeOutCode, createFadeOut());
        handleClickAnimationCode(btnBlinkCode,   createBlink());
        handleClickAnimationCode(btnZoomInCode,  createZoomIn());
        handleClickAnimationCode(btnZoomOutCode, createZoomOut());
        handleClickAnimationCode(btnRotateCode,  createRotate());
        handleClickAnimationCode(btnMoveCode,    createMove());
        handleClickAnimationCode(btnSlideUpCode, createSlideUp());
        handleClickAnimationCode(btnBounceCode,  createBounce());

        // Combine (CODE): ví dụ phóng to rồi xoay
        btnCombineCode.setOnClickListener(v -> {
            AnimationSet set = new AnimationSet(true);
            set.setInterpolator(new LinearInterpolator());
            set.setFillAfter(true);

            ScaleAnimation zoom = new ScaleAnimation(
                    1f, 3f, 1f, 3f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );
            zoom.setDuration(1000);

            RotateAnimation rotate = new RotateAnimation(
                    0, 360,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );
            rotate.setDuration(600);
            rotate.setRepeatCount(2);
            rotate.setRepeatMode(Animation.RESTART);

            set.addAnimation(zoom);
            set.addAnimation(rotate);
            set.setAnimationListener(animationListener);

            ivUitLogo.startAnimation(set);
        });
    }

    private void findViewsByIds() {
        ivUitLogo = findViewById(R.id.iv_uit_logo);

        btnFadeInXml   = findViewById(R.id.btn_fade_in_xml);
        btnFadeInCode  = findViewById(R.id.btn_fade_in_code);
        btnFadeOutXml  = findViewById(R.id.btn_fade_out_xml);
        btnFadeOutCode = findViewById(R.id.btn_fade_out_code);
        btnBlinkXml    = findViewById(R.id.btn_blink_xml);
        btnBlinkCode   = findViewById(R.id.btn_blink_code);
        btnZoomInXml   = findViewById(R.id.btn_zoom_in_xml);
        btnZoomInCode  = findViewById(R.id.btn_zoom_in_code);
        btnZoomOutXml  = findViewById(R.id.btn_zoom_out_xml);
        btnZoomOutCode = findViewById(R.id.btn_zoom_out_code);
        btnRotateXml   = findViewById(R.id.btn_rotate_xml);
        btnRotateCode  = findViewById(R.id.btn_rotate_code);
        btnMoveXml     = findViewById(R.id.btn_move_xml);
        btnMoveCode    = findViewById(R.id.btn_move_code);
        btnSlideUpXml  = findViewById(R.id.btn_slide_up_xml);
        btnSlideUpCode = findViewById(R.id.btn_slide_up_code);
        btnBounceXml   = findViewById(R.id.btn_bounce_xml);
        btnBounceCode  = findViewById(R.id.btn_bounce_code);
        btnCombineXml  = findViewById(R.id.btn_combine_xml);
        btnCombineCode = findViewById(R.id.btn_combine_code);
    }

    private void initVariables() {
        animationListener = new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) { }
            @Override public void onAnimationEnd(Animation animation) {
                Toast.makeText(getApplicationContext(), "Animation Stopped", Toast.LENGTH_SHORT).show();
            }
            @Override public void onAnimationRepeat(Animation animation) { }
        };
    }

    /** Bắt click cho nút dùng XML */
    private void handleClickAnimationXml(Button btn, int animResId) {
        btn.setOnClickListener(v -> {
            Animation anim = android.view.animation.AnimationUtils.loadAnimation(this, animResId);
            anim.setAnimationListener(animationListener);
            ivUitLogo.startAnimation(anim);
        });
    }

    /** Bắt click cho nút dùng CODE */
    private void handleClickAnimationCode(Button btn, Animation anim) {
        btn.setOnClickListener(v -> {
            anim.setAnimationListener(animationListener);
            ivUitLogo.startAnimation(anim);
        });
    }

    // ====== Các animation tạo bằng CODE ======

    private Animation createFadeIn() {
        AlphaAnimation a = new AlphaAnimation(0f, 1f);
        a.setDuration(1000);
        a.setFillAfter(true);
        return a;
    }
    private Animation createFadeOut() {
        AlphaAnimation a = new AlphaAnimation(1f, 0f);
        a.setDuration(1000);
        a.setFillAfter(true);
        return a;
    }
    private Animation createBlink() {
        AlphaAnimation a = new AlphaAnimation(0f, 1f);
        a.setDuration(300);
        a.setRepeatMode(Animation.REVERSE);
        a.setRepeatCount(3);
        return a;
    }
    private Animation createZoomIn() {
        ScaleAnimation s = new ScaleAnimation(
                1f, 3f, 1f, 3f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        s.setDuration(1000);
        s.setFillAfter(true);
        return s;
    }
    private Animation createZoomOut() {
        ScaleAnimation s = new ScaleAnimation(
                1f, 0.5f, 1f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        s.setDuration(1000);
        s.setFillAfter(true);
        return s;
    }
    private Animation createRotate() {
        RotateAnimation r = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        r.setDuration(600);
        r.setRepeatMode(Animation.RESTART);
        r.setRepeatCount(2);
        return r;
    }
    private Animation createMove() {
        TranslateAnimation t = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0.75f,
                Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, 0f
        );
        t.setDuration(800);
        t.setFillAfter(true);
        return t;
    }
    private Animation createSlideUp() {
        // Mô phỏng slide-up bằng thu hẹp theo trục Y
        ScaleAnimation s = new ScaleAnimation(
                1f, 1f, 1f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1f
        );
        s.setDuration(500);
        s.setFillAfter(true);
        return s;
    }
    private Animation createBounce() {
        // Bounce đơn giản: scale Y từ 0 -> 1 với BounceInterpolator
        ScaleAnimation s = new ScaleAnimation(
                1f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1f
        );
        s.setDuration(500);
        s.setFillAfter(true);
        s.setInterpolator(new BounceInterpolator());
        return s;
    }

    /** Helper nhỏ để bắt sự kiện kết thúc cho chuỗi animation */
    private static class SimpleEndListener implements Animation.AnimationListener {
        private final Runnable onEnd;
        SimpleEndListener(Runnable onEnd) { this.onEnd = onEnd; }
        @Override public void onAnimationStart(Animation animation) {}
        @Override public void onAnimationEnd(Animation animation) { if (onEnd != null) onEnd.run(); }
        @Override public void onAnimationRepeat(Animation animation) {}
        private Animation createFadeIn() {
            AlphaAnimation a = new AlphaAnimation(0f, 1f);
            a.setDuration(1000);
            a.setFillAfter(true);
            return a;
        }

        private Animation createFadeOut() {
            AlphaAnimation a = new AlphaAnimation(1f, 0f);
            a.setDuration(1000);
            a.setFillAfter(true);
            return a;
        }

        private Animation createBlink() {
            AlphaAnimation a = new AlphaAnimation(0f, 1f);
            a.setDuration(300);
            a.setRepeatMode(Animation.REVERSE);
            a.setRepeatCount(3);
            return a;
        }

        private Animation createZoomIn() {
            ScaleAnimation s = new ScaleAnimation(
                    1f, 3f, 1f, 3f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );
            s.setDuration(1000);
            s.setFillAfter(true);
            return s;
        }

        private Animation createZoomOut() {
            ScaleAnimation s = new ScaleAnimation(
                    1f, 0.5f, 1f, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );
            s.setDuration(1000);
            s.setFillAfter(true);
            return s;
        }

        private Animation createRotate() {
            RotateAnimation r = new RotateAnimation(
                    0, 360,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );
            r.setDuration(600);
            r.setRepeatMode(Animation.RESTART);
            r.setRepeatCount(2);
            return r;
        }

        private Animation createMove() {
            TranslateAnimation t = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, 0f,
                    Animation.RELATIVE_TO_PARENT, 0.75f,
                    Animation.RELATIVE_TO_PARENT, 0f,
                    Animation.RELATIVE_TO_PARENT, 0f
            );
            t.setDuration(800);
            t.setFillAfter(true);
            t.setInterpolator(new LinearInterpolator());
            return t;
        }

        private Animation createSlideUp() {
            // Giả lập slide-up bằng thu nhỏ theo trục Y
            ScaleAnimation s = new ScaleAnimation(
                    1f, 1f, 1f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 1f
            );
            s.setDuration(500);
            s.setFillAfter(true);
            return s;
        }

        private Animation createBounce() {
            ScaleAnimation s = new ScaleAnimation(
                    1f, 1f, 0f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 1f
            );
            s.setDuration(500);
            s.setFillAfter(true);
            s.setInterpolator(new BounceInterpolator());
            return s;
        }
    }
    }

