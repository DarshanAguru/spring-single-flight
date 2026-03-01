package com.darshan.springsingleflight.demo.service;

import com.darshan.springsingleflight.demo.entity.MockBlogEntity;
import com.darshan.springsingleflight.demo.repos.MockBlogRepository;
import com.darshan.springsingleflight.demo.dto.ResponseDTO;
import com.darshan.springsingleflight.demo.mappers.BlogResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {

    @Autowired
    private MockBlogRepository repository;

    @Autowired
    private BlogResponseMapper mapper;

    public List<ResponseDTO> getAllData(){
        try{
            ResponseDTO response = new ResponseDTO();
            List<MockBlogEntity> result =  repository.findAll();
            if(result.isEmpty()) {
                throw new NullPointerException("No data Found");
            }

            return result.stream().map(mapper::toDto).toList();
        } catch(Exception e)
        {
            System.out.println(e.getMessage() + " : " + e);
            return null;
        }
    }

    public ResponseDTO getDataById(Long id)
    {
        try{
            ResponseDTO response = new ResponseDTO();
            MockBlogEntity result =  repository.findById(id).orElse(null);
            if(result == null) {
                throw new NullPointerException("No data Found");
            }
            response.setId(result.getId());
            response.setTitle(result.getTitle());
            response.setBody(result.getBody());
            response.setDate(result.getDate());
            return response;
        } catch(Exception e)
        {
            System.out.println(e.getMessage() + " : " + e);
            return null;
        }

    }

}
