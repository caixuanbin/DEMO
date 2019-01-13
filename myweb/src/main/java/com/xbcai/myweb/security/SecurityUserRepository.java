package com.xbcai.myweb.security;

import com.xbcai.myweb.model.SecurityUser;
import org.springframework.data.repository.CrudRepository;

public interface SecurityUserRepository extends CrudRepository<SecurityUser,Long> {
    public SecurityUser findByUsername(String username);
}
