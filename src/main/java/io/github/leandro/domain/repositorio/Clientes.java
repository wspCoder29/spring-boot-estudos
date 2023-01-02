package io.github.leandro.domain.repositorio;

import io.github.leandro.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



@Repository
public class Clientes {


    private static String INSERT = "insert into cliente (nome) values (?) ";

    @Autowired
   private JdbcTemplate jdbcTemplate;

    @Bean
    public CommandLineRunner init (@Autowired Clientes clientes){
        //command line runner
        return args -> {
            Cliente cliente = new Cliente();
            cliente.setNome("Leandro");
            clientes.salvar(cliente);
        };
    }

    
    public Cliente salvar(Cliente cliente){
        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        return cliente;
    }






}
