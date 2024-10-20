package com.grpc.grpc.auth.utils;

import io.grpc.Context;
import io.grpc.Metadata;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class Constants {

    public static final String JWT_SIGNING_KEY = "wAtEr";
    public static final String BEARER_TYPE = "Bearer";

    public static final Integer EXPIRATION_TIME = 120000;

    public static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY = Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);
    public static final Context.Key<String> CLIENT_ID_CONTEXT_KEY = Context.key("clientId");

    private Constants() {
        throw new AssertionError();
    }
}
