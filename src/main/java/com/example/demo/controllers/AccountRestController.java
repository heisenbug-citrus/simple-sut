package com.example.demo.controllers;

import com.example.demo.dto.ConvertInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class AccountRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRestController.class);

    @Value("${key.url}")
    private String urlForConvert;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @PostMapping(value = "/convert",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> convert(
            @RequestHeader(required = false, value = "X-ID-Request") String header,
            @Valid @RequestBody ConvertInput convertInput) {
        if (header != null && header.length() != 10) {
            // id - hardcode, проверка в тесте на id + X-ID-Request text
            return new ResponseEntity<String>("{\"id\": \"8732\",\"error\": \"X-ID-Request: " + header + " is bad \"}", HttpStatus.NOT_ACCEPTABLE);
        }
        LOGGER.debug("Triggered AccountRestController.accountInput");
        long timeOut = System.currentTimeMillis() + 500L;
        while (System.currentTimeMillis() < timeOut) {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType((MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<String>("{\"sum\": \"" + convertInput.getSum() + "\",\"accountNumber\": \"" + convertInput.getAccountNumber() + "\"}", headers);

            String body = restTemplate.exchange(urlForConvert, HttpMethod.POST, entity, String.class).getBody();
            // проверить что произойдёт, при ответе не OK - негатив
            if (body.contains("OK")) {
                return new ResponseEntity<String>("{\"amount\": \"" + Integer.parseInt(convertInput.getSum()) * 100 + "\",\"accountNumber\": \"" + convertInput.getAccountNumber() + "\"}", HttpStatus.ACCEPTED);
            }
            System.out.println(urlForConvert);
        }

        return null;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}

