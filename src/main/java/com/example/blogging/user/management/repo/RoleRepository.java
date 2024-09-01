package com.example.blogging.user.management.repo;

import com.example.blogging.user.management.model.Role;
import com.example.blogging.user.management.model.user.projections.RoleView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Query(nativeQuery = true,value = "select id,role_name from roles where role_name=:name")
    Optional<RoleView> findByRolename(@Param("name") String name);
}
