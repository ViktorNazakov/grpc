package com.example.service;

import com.example.dto.LoginResponseDto;
import com.grpc.AuthServiceGrpc;
import com.grpc.LoginUserRequest;
import com.grpc.LoginUserResponse;
import com.grpc.RegisterUserRequest;
import com.grpc.RegisterUserResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class AuthClientService {

    @GrpcClient("cinema")
    private AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub;

    public String registerUser(String username, String password) {
        RegisterUserRequest request = RegisterUserRequest.newBuilder()
            .setUsername(username)
            .setPassword(password)
            .build();

        try {
            RegisterUserResponse response = authServiceBlockingStub.registerUser(request);
            if (response.getSuccess()) {
                return "Registration successful";
            } else {
                return "Registration failed";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public LoginResponseDto loginUser(String username, String password) {
        LoginUserRequest request = LoginUserRequest.newBuilder()
            .setUsername(username)
            .setPassword(password)
            .build();

        try {
            LoginUserResponse response = authServiceBlockingStub.loginUser(request);
            return LoginResponseDto.builder().token(response.getToken()).build();
        } catch (Exception e) {
            return LoginResponseDto.builder().message(e.getMessage()).build();
        }
    }
}

