package com.claudiusava.diningReviews.repository;

import com.claudiusava.diningReviews.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findAllBy(Sort username);

}
