package com.seanrogandev.bebelink.generator.controller;

import com.seanrogandev.bebelink.generator.dto.GenerationRequest;
import com.seanrogandev.bebelink.generator.dto.GenerationResponse;
import com.seanrogandev.bebelink.generator.service.GeneratorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@AllArgsConstructor
@Controller
@Slf4j
public class GeneratorController {

    private final GeneratorService generatorService;
    /**
     * Generates a new shortened URL
     * @param generationRequest the long-form URL to be shortened
     * @return the generated URL and the URL expiration date
     */
    @PostMapping("generate")
    public ResponseEntity<GenerationResponse> generateNewUrl(@RequestBody GenerationRequest generationRequest) {
        try {
           return new ResponseEntity<>(generatorService.generate(generationRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves the long-form URL associated with a shortened URL
     * <p> This end point is called by the router service when a redirect is requested.
     * @param shortUrl the shortened URL used for redirect
     * @return the long-form URL aka origin URL
     */
    @GetMapping("/origin/{shortUrl}")
    public ResponseEntity<String> provideOrigin(@PathVariable("shortUrl") String shortUrl){
        try{
           Optional<String> originLookUp = generatorService.provideOrigin(shortUrl);
           if(originLookUp.isPresent()) return new ResponseEntity<>(originLookUp.get(), HttpStatus.OK);
        } catch(Exception e) {
            log.error("There was a problem fetching the origin for the shortened Url" + shortUrl + ". \n" + e.getMessage());
        }
            return new ResponseEntity<>("No origin found associated with that shortened url.", HttpStatus.NOT_FOUND);
    }


}
