package com.charly.test.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.charly.test.entidades.Banco;

@Repository
public interface BancoRepositorio extends JpaRepository<Banco, Long> {

}
