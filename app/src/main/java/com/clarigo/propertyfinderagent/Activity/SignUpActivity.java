package com.clarigo.propertyfinderagent.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.TypeFactory;

public class SignUpActivity extends AppCompatActivity {

    private Button btn_sign_in, btn_agent, btn_forgot_pass;
    private ImageView img_back, img_icon;
    private TextView title_heading, tv_stuck, tv_privacy;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
        click_events();
    }

    private void init() {
        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_agent = findViewById(R.id.btn_agent);
        btn_forgot_pass = findViewById(R.id.btn_forgot_pass);
        img_back = findViewById(R.id.img_back);
        img_icon = findViewById(R.id.img_icon);
        title_heading = findViewById(R.id.title_heading);
        tv_stuck = findViewById(R.id.tv_stuck);
        tv_privacy = findViewById(R.id.tv_privacy);


        btn_forgot_pass.setTypeface(TypeFactory.getInstance(this).PoppinsBold());
    }

    private void click_events() {
        img_back.setOnClickListener(view -> finish());

        btn_agent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, Registration_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
