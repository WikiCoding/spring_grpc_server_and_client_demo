# Grpc Server
1. Had to change `server.port` to `9090` so the tomcat server exposes the correct port and the server is reachable.
2. Define the proto file with the correct `package_name` and mark `target/generated-sources` as generated sources in the IDE.
```protobuf
syntax = "proto3";

option java_multiple_files = true;
option java_package = "com.wikicoding.grpc.spring_grpc_demo";
option java_outer_classname = "HelloWorldProto";

// The greeting service definition.
service Simple {
  // Sends a greeting
  rpc SayHello(HelloRequest) returns (HelloReply) {}
  rpc StreamHello(HelloRequest) returns (stream HelloReply) {}
}

// The request message containing the user's name.
message HelloRequest {
  string name = 1;
}

// The response message containing the greetings
message HelloReply {
  string message = 1;
}
```
3. Then when creating the service, extend `<service_name>Grpc.<service_name>ImplBase` like this:
```java
@Service
@Slf4j
public class GrpcServerService extends SimpleGrpc.SimpleImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {}

    @Override
    public void streamHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {}
}
```

# Grpc Client
1. Create the BlockingStub like this:
```java
@Bean
SimpleGrpc.SimpleBlockingStub stub() {
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
    return SimpleGrpc.newBlockingStub(channel);
}
```
It's named after the service name, in this case is `SimpleBlockingStub`.
2. It's possible to also make the calls via cli with something like `grpcurl -d '{"name":"Hi"}' -plaintext localhost:9090 Simple.SayHello`