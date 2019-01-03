package com.xbcai.myweb.jpa;

import com.xbcai.myweb.model.JpaUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@Rollback(false)
public class SimpleUserRepositoyTsts {
    @Autowired
    private SimpleUserRepository repository;
    private JpaUser jpaUser;
    @Before
    public void setUp(){
        System.out.println("SimpleUserRepositoyTsts->setUp()");
        Random r = new Random();
        jpaUser = new JpaUser();
        jpaUser.setUsername("caixuanbin"+r.nextInt(100));
        jpaUser.setFirstname("cai");
        jpaUser.setLastname("bin");
    }
    @Test
    public void findSavedUserById(){
        jpaUser = repository.save(jpaUser);
        assertThat(repository.findByLastname("bin")).contains(jpaUser);
    }
    @Test
    public void findByFirstnameOrLastname(){
        jpaUser = repository.save(jpaUser);
        assertThat(repository.findByFristnameOrLastname("bin")).contains(jpaUser);
    }
    @Test
    public void saveAll(){
        JpaUser user1 = new JpaUser();
        user1.setLastname("lisi");
        user1.setFirstname("zhangsan");
        user1.setUsername("lisi");
        repository.saveAll(Arrays.asList(jpaUser,user1));
    }
    @Test
    public void deleteByLastname(){
        repository.deleteByLastname("lisi");
    }
    @Test
    public void useOptionalAsReturnAndParameterType(){
       assertThat(repository.findByUsername(Optional.of("caixuanbin"))).isEmpty();
    }
    @Test
    public void userSliceToLoadContent(){
        repository.deleteAll();
        int totalNumberUsers = 110;
        List<JpaUser> source = new ArrayList<JpaUser>(totalNumberUsers);
        for(int i=1;i<=totalNumberUsers;i++){
            JpaUser user = new JpaUser();
            user.setLastname(this.jpaUser.getLastname());
            user.setUsername(this.jpaUser.getUsername()+"-"+
                    String.format("%03d",i));
            user.setFirstname(this.jpaUser.getFirstname());
            source.add(user);
        }
        repository.saveAll(source);

        Slice<JpaUser> users =
                repository.findByLastnameOrderByUsernameAsc(this.jpaUser.getLastname(),
                        PageRequest.of(1,5));
        System.out.println(users);
        assertThat(users).containsAll(source.subList(5,10));
    }
    @Test
    public void findTop2ByWithSort(){
        List<JpaUser> resultAsc = repository.findTop2By(new Sort(ASC,"username"));
        System.out.println(resultAsc);
        resultAsc = repository.findTop2By(new Sort(DESC,"username"));
        System.out.println(resultAsc);
    }
    @Test
    public void findByFirstnameOrLastnameUsingSpEL(){
        JpaUser user = new JpaUser();
        user.setFirstname("cai2");
        user.setLastname("bin15");
        Iterable<JpaUser> users = repository.findByFirstnameOrLastname(user);
        System.out.println(users);
    }
}
