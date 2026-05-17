package com.wikicoding.grpc.spring_grpc_demo.client;

import io.grpc.StatusRuntimeException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@AllArgsConstructor
public class HelloController {
    private final GrpcClientService grpcClientService;

    @GetMapping
    public ResponseEntity<Object> getHello(@RequestParam(value = "name") String name) {
        try {
            String response = grpcClientService.getHello(name);

            return ResponseEntity.ok(response);
        } catch (StatusRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
