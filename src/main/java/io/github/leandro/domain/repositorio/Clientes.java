package io.github.leandro.domain.repositorio;

import io.github.leandro.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface Clientes extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNomeLike(String nome);
    boolean existsByNome(String nome);


}
