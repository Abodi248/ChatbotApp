package com.example.chatbotapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window window = getWindow();
        window.setStatusBarColor(getColor(R.color.bg_deep));
        window.setNavigationBarColor(getColor(R.color.bg_deep));

        EditText etUsername = findViewById(R.id.et_username);
        etUsername.setHintTextColor(getColor(R.color.text_hint));

        Button btnGo = findViewById(R.id.btn_go);

        btnGo.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
    }
}
