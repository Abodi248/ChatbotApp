package com.example.chatbotapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatActivity extends AppCompatActivity {

    private ChatViewModel viewModel;
    private MessageAdapter adapter;
    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Window window = getWindow();
        window.setStatusBarColor(getColor(R.color.bg_deep));
        window.setNavigationBarColor(getColor(R.color.bg_deep));

        String username = getIntent().getStringExtra("username");

        TextView tvWelcome = findViewById(R.id.tv_welcome);
        tvWelcome.setText("Welcome " + username + "!");

        RecyclerView recyclerView = findViewById(R.id.recycler_messages);
        adapter = new MessageAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);   // newest messages at the bottom
        recyclerView.setLayoutManager(layoutManager);

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        viewModel.setUsername(username);

        viewModel.getMessages().observe(this, messages -> {
            adapter.setMessages(messages);
            recyclerView.scrollToPosition(messages.size() - 1);
        });

        etInput = findViewById(R.id.et_input);
        ImageButton btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(v -> {
            String text = etInput.getText().toString().trim();
            if (!TextUtils.isEmpty(text)) {
                viewModel.sendMessage(text);
                etInput.setText("");
            }
        });
    }
}
