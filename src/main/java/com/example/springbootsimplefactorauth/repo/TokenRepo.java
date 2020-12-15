package com.example.springbootsimplefactorauth.repo;

import com.example.springbootsimplefactorauth.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long > {
   Token findByValue(String value);
}
