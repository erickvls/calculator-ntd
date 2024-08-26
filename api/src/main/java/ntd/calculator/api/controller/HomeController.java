package ntd.calculator.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/home")
@RequiredArgsConstructor
public class HomeController {

    @GetMapping
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("Hello");
    }

}
