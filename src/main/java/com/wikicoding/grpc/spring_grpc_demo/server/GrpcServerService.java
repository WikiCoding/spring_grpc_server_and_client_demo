package com.wikicoding.grpc.spring_grpc_demo.server;

import com.wikicoding.grpc.spring_grpc_demo.HelloReply;
import com.wikicoding.grpc.spring_grpc_demo.HelloRequest;
import com.wikicoding.grpc.spring_grpc_demo.SimpleGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GrpcServerService extends SimpleGrpc.SimpleImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        log.info("Received request: {}", request.getName());
        if (request.getName().startsWith("error")) {
            throw new IllegalArgumentException("Bad name: " + request.getName());
        }

        if (request.getName().startsWith("internal")) {
            throw new RuntimeException("Internal error");
        }

        HelloReply reply = HelloReply.newBuilder()
                .setMessage("Hello " + request.getName())
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void streamHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        log.info("Received request: {}", request.getName());

        int count = 0;
        while (count < 10) {
            HelloReply reply = HelloReply.newBuilder()
                    .setMessage("Hello " + request.getName() + " " + count)
                    .build();

            responseObserver.onNext(reply);
            count++;

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                responseObserver.onError(e);
                return;
            }
        }

        responseObserver.onCompleted();
    }
}
