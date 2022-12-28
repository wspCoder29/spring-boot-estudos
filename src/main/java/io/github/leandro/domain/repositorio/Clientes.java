package io.github.leandro.domain.repositorio;

import io.github.leandro.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



@Repository
public class Clientes {

    @Autowired
   private JdbcTemplate jdbcTemplate;



    public Cliente salvar(Cliente cliente){

        return null;

    }






}
