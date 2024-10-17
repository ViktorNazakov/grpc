package com.grpc.grpc.auth;

import com.grpc.grpc.auth.utils.Constants;
import io.grpc.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@GrpcGlobalServerInterceptor
@Component
public class AuthInterceptor implements ServerInterceptor{
    private static final Set<String> IGNORED_ROUTES = new HashSet<>();

    static {
        IGNORED_ROUTES.add("AuthService/RegisterUser");
        IGNORED_ROUTES.add("AuthService/LoginUser");
//        IGNORED_ROUTES.add("CinemaService/CreateActor");
//        IGNORED_ROUTES.add("CinemaService/GetActor");
//        IGNORED_ROUTES.add("CinemaService/DeleteActor");
//        IGNORED_ROUTES.add("CinemaService/CreateMovie");
//        IGNORED_ROUTES.add("CinemaService/GetMovie");
//        IGNORED_ROUTES.add("CinemaService/DeleteMovie");
//        IGNORED_ROUTES.add("CinemaService/CreateTVSeries");
//        IGNORED_ROUTES.add("CinemaService/GetTVSeries");
//        IGNORED_ROUTES.add("CinemaService/DeleteTVSeries");
//        IGNORED_ROUTES.add("CinemaService/UpdateTVSeries");
//        IGNORED_ROUTES.add("CinemaService/UpdateMovie");
//        IGNORED_ROUTES.add("CinemaService/UpdateActor");
    }

    private JwtParser parser = Jwts.parser().setSigningKey(Constants.JWT_SIGNING_KEY);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String fullMethodName = serverCall.getMethodDescriptor().getFullMethodName();

        if (IGNORED_ROUTES.contains(fullMethodName)) {
            return serverCallHandler.startCall(serverCall, metadata);
        }

        String value = metadata.get(Constants.AUTHORIZATION_METADATA_KEY);

        Status status;
        if (value == null) {
            status = Status.UNAUTHENTICATED.withDescription("Authorization token is missing");
        } else if (!value.startsWith(Constants.BEARER_TYPE)) {
            status = Status.UNAUTHENTICATED.withDescription("Unknown authorization type");
        } else {
            try {
                String token = value.substring(Constants.BEARER_TYPE.length()).trim();
                Jws<Claims> claims = parser.parseClaimsJws(token);
                Context ctx = Context.current().withValue(Constants.CLIENT_ID_CONTEXT_KEY, claims.getBody().getSubject());
                return Contexts.interceptCall(ctx, serverCall, metadata, serverCallHandler);
            } catch (Exception e) {
                status = Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e);
            }
        }

        serverCall.close(status, metadata);
        return new ServerCall.Listener<>() {
            // noop
        };
    }
}
