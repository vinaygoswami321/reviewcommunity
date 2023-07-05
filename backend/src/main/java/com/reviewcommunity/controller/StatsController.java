package com.reviewcommunity.controller;

import com.reviewcommunity.service.impl.StatsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/stats")
public class StatsController {

     @Autowired
     private StatsServiceImpl statsService;

     /*
     *  Retrieves stats : number of users registered, number of products and number of approved reviews
     * */
     @GetMapping("")
     public ResponseEntity<?> getStats(){
         Map<String,Object> responseBody = new HashMap<>();
         responseBody.put("Stats",statsService.getStats());
         return new ResponseEntity<>(responseBody, HttpStatus.OK);
     }
}

