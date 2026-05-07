package com.example.chatbotapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.chatbotapp.data.AppDatabase;
import com.example.chatbotapp.data.Message;
import com.example.chatbotapp.data.MessageDao;
import com.example.chatbotapp.network.ChatApiService;
import com.example.chatbotapp.network.RetrofitClient;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatViewModel extends AndroidViewModel {

    private final MessageDao messageDao;
    private String username;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        messageDao = AppDatabase.getInstance(application).messageDao();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LiveData<List<Message>> getMessages() {
        return messageDao.getMessagesForUser(username);
    }

    public void saveMessage(Message message) {
        Executors.newSingleThreadExecutor().execute(() -> messageDao.insert(message));
    }

    public void sendMessage(String userText) {
        Message userMsg = new Message(username, userText, true, System.currentTimeMillis());
        saveMessage(userMsg);

        ChatApiService.ChatRequest request = new ChatApiService.ChatRequest(username, userText);
        RetrofitClient.getService().sendMessage(request).enqueue(new Callback<ChatApiService.ChatResponse>() {
            @Override
            public void onResponse(@NonNull Call<ChatApiService.ChatResponse> call,
                                   @NonNull Response<ChatApiService.ChatResponse> response) {
                String botText = (response.isSuccessful() && response.body() != null)
                        ? response.body().response
                        : "Sorry, something went wrong.";

                Message botMsg = new Message(username, botText, false, System.currentTimeMillis());
                saveMessage(botMsg);
            }

            @Override
            public void onFailure(@NonNull Call<ChatApiService.ChatResponse> call, @NonNull Throwable t) {
                Message botMsg = new Message(username, "Error: " + t.getMessage(), false, System.currentTimeMillis());
                saveMessage(botMsg);
            }
        });
    }
}
