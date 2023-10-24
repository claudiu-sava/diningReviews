package com.claudiusava.diningReviews.repository;

import java.util.Optional;

import com.claudiusava.diningReviews.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByRoleName(String name);
}
