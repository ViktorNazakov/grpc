package com.grpc.grpc.auth;

import com.grpc.AuthServiceGrpc;
import com.grpc.LoginUserRequest;
import com.grpc.LoginUserResponse;
import com.grpc.RegisterUserRequest;
import com.grpc.RegisterUserResponse;
import com.grpc.entity.User;
import com.grpc.grpc.auth.utils.JwtUtils;
import com.grpc.repository.UserRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@GrpcService
public class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthServiceImpl(JwtUtils jwtUtils, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(RegisterUserRequest request, StreamObserver<RegisterUserResponse> responseObserver) {
        String username = request.getUsername();
        String password = request.getPassword();

        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            responseObserver.onError(Status.ALREADY_EXISTS.withDescription("User already exists").asRuntimeException());
            return;
        }

        String hashedPassword = passwordEncoder.encode(password);

        User newUser = User.builder().username(username).password(hashedPassword).build();
        userRepository.save(newUser);

        RegisterUserResponse response = RegisterUserResponse.newBuilder()
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void loginUser(LoginUserRequest request, StreamObserver<LoginUserResponse> responseObserver) {
        String username = request.getUsername();
        String password = request.getPassword();

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            responseObserver.onError(Status.NOT_FOUND.withDescription("User not found").asRuntimeException());
            return;
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            responseObserver.onError(Status.UNAUTHENTICATED.withDescription("Invalid password").asRuntimeException());
            return;
        }

        String token = jwtUtils.generateToken(username);

        LoginUserResponse response = LoginUserResponse.newBuilder()
                .setToken(token)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
