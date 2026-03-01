package com.darshan.springsingleflight.demo.controller;

import com.darshan.springsingleflight.annotation.SingleFlight;
import com.darshan.springsingleflight.demo.dto.ResponseDTO;
import com.darshan.springsingleflight.demo.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
public class ReaderController {


    @Autowired
    private ReaderService readerService;


    @SingleFlight(key = "'blogs'")
    @GetMapping("/blogs")
    public ResponseEntity<List<ResponseDTO>> getAllBlogs()
    {
        try {
            List<ResponseDTO> res = readerService.getAllData();
            if (res == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(res);
        }catch(Exception e)
        {
            System.out.println(e.getMessage() + " : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @SingleFlight(key = "#id")
    @GetMapping("/blog/{id}")
    public ResponseEntity<ResponseDTO> getBlogById(@PathVariable Long id){

        try {
            ResponseDTO res = readerService.getDataById(id);
            if (res == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(res);
        }catch(Exception e)
        {
            System.out.println(e.getMessage() + " : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
