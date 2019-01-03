package com.xbcai.myweb.jpa;

import com.xbcai.myweb.model.JpaUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SimpleUserRepository extends CrudRepository<JpaUser,Long> {

    JpaUser findByUsername(String username);

    Optional<JpaUser> findByUsername(Optional<String> username);

    List<JpaUser> findByLastname(String lastname);

    @Query("select u from JpaUser u where u.firstname=:firstname")
    List<JpaUser> findByFirstname(String firstname);

    @Query("select u from JpaUser u where u.firstname=:name or u.lastname=:name")
    List<JpaUser> findByFristnameOrLastname(String name);

    Long deleteByLastname(String lastname);

    /**
     * Returns a {@link Slice} counting a maximum number of {@link Pageable#getPageSize()} users matching given criteria
     * starting at {@link Pageable#getOffset()} without prior count of the total number of elements available.
     *
     * @param lastname
     * @param page
     * @return
     */
    Slice<JpaUser> findByLastnameOrderByUsernameAsc(String lastname, Pageable page);

    List<JpaUser> findTop2By(Sort sort);

    @Query("select u from JpaUser u where u.firstname=:#{#user.firstname} or u.lastname=:#{#user.lastname}")
    Iterable<JpaUser> findByFirstnameOrLastname(JpaUser user);
}
