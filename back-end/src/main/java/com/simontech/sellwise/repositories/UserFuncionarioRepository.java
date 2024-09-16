package com.simontech.sellwise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simontech.sellwise.domain.UserFuncionario;

@Repository
public interface UserFuncionarioRepository extends JpaRepository<UserFuncionario, Integer> {
    
}
