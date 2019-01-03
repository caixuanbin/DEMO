package com.xbcai.myweb.controller;

import com.xbcai.myweb.jpa.SimpleUserRepository;
import com.xbcai.myweb.model.JpaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
public class JpaController {
    @Autowired
    private SimpleUserRepository repository;
    @GetMapping("/queryJpaUsers")
    public List<JpaUser> queryJpaUsers(){
        return repository.findByFristnameOrLastname("cai");
    }
    @GetMapping("/queryPage")
    public Slice<JpaUser> queryPage(){
        return repository.findByLastnameOrderByUsernameAsc("bin",
                PageRequest.of(3,8,new Sort(ASC,"id")));
    }
}
