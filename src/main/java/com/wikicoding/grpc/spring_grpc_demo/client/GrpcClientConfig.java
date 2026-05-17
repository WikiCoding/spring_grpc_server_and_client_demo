package com.wikicoding.grpc.spring_grpc_demo.client;

import com.wikicoding.grpc.spring_grpc_demo.SimpleGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {
    @Bean
    SimpleGrpc.SimpleBlockingStub stub() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        return SimpleGrpc.newBlockingStub(channel);
    }
}
