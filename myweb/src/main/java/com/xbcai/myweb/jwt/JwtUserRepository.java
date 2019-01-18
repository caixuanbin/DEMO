package com.xbcai.myweb.jwt;

import com.xbcai.myweb.model.JwtUser;
import org.springframework.data.repository.CrudRepository;

public interface JwtUserRepository extends CrudRepository<JwtUser,Long> {
    public JwtUser findByUsername(String username);
}
