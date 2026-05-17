package com.wikicoding.grpc.spring_grpc_demo.client;

import com.wikicoding.grpc.spring_grpc_demo.HelloRequest;
import com.wikicoding.grpc.spring_grpc_demo.SimpleGrpc;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class GrpcClientService {
    private final SimpleGrpc.SimpleBlockingStub stub;

    public String getHello(String name) {
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        return stub.sayHello(request).getMessage();
    }
}
