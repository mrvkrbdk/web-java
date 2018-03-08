package com.wstore.Repository;

import org.springframework.data.repository.CrudRepository;

import com.wstore.Domain.security.Role;

public interface RoleRepository extends CrudRepository<Role,Long> {
 Role findByname(String name);
}
