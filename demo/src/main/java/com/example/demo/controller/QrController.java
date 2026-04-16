package com.example.demo.controller;

import com.example.demo.service.QrService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qr")
public class QrController {

    private final QrService service;

    public QrController(QrService service) {
        this.service = service;
    }

    @GetMapping("/static")
    public String createStatic() {
        return service.generateStatic();
    }

    @GetMapping("/dynamic")
    public String createDynamic() {
        return service.generateDynamic();
    }

    @GetMapping("/timed")
    public String createTimed() {
        return service.generateTimed();
    }

    @PostMapping("/validate")
    public String validate(@RequestBody String id) throws Exception {
        return service.validate(id);
    }
}