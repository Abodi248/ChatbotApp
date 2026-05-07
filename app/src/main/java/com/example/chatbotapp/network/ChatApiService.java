package com.example.chatbotapp.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatApiService {

    @POST("chat")
    Call<ChatResponse> sendMessage(@Body ChatRequest request);

    class ChatRequest {
        public String username;
        public String message;

        public ChatRequest(String username, String message) {
            this.username = username;
            this.message = message;
        }
    }

    class ChatResponse {
        public String response;
    }
}
