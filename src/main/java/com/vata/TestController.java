package com.vata;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @PostMapping("/fake-image")
    public ResponseEntity<String> fake() throws InterruptedException {
        Thread.sleep(8000);
        return ResponseEntity.ok("OK");
    }
}
