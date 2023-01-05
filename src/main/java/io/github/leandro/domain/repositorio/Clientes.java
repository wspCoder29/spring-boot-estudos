package io.github.leandro.domain.repositorio;

import io.github.leandro.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class Clientes {


//    private static String INSERT = "insert into cliente (nome) values (?) ";
//    private static String SELECT_ALL = "SELECT * FROM CLIENTE ";
//    private static String UPDATE = "update cliente set nome = ? where id = ? ";
//    private static String DELETE = "delete from cliente where id = ? ";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;


    //Insere clientes na startup do H2
    @Bean
    public CommandLineRunner init (@Autowired Clientes clientes){
        //command line runner
        return args -> {


            System.out.println("====Salvando clientes====");
            clientes.salvar(new Cliente("Ada"));

            clientes.salvar(new Cliente("Mia"));

            clientes.salvar(new Cliente("Juri"));

            clientes.salvar(new Cliente("Kris"));

//            System.out.println("====Mostrando clientes====");
//            //para mostrar todos os clientes gerados no console
//            List<Cliente> todosClientes = clientes.obterTodos();
//            todosClientes.forEach(System.out::println);


//            System.out.println("====Atualizando clientes====");
//            //testa o método de atualização
//            todosClientes.forEach(c -> {
//                c.setNome(c.getNome()+" atualizado. (metodo editar nome ok)");
//                clientes.atualizar(c);
//            });
//
//            System.out.println("====Mostrando clientes====");
//            //Mostra todos os clientes
//            todosClientes = clientes.obterTodos();
//            todosClientes.forEach(System.out::println);
//
//            System.out.println("====Buscando clientes====");
//                System.out.println("Encontrado");
//                clientes.buscarPorNome("Juri").forEach(System.out::println);
//
////            System.out.println("deletando clientes");
////            clientes.obterTodos().forEach(c ->{
////                clientes.deletar(c);
////            });
//
//            todosClientes = clientes.obterTodos();
//            if(todosClientes.isEmpty()){
//                System.out.println("Nenhum clientes encontrado");
//            }else{
//                todosClientes.forEach(System.out::println);
//            }



        };
    }


    @Transactional
    public Cliente salvar(Cliente cliente){
        entityManager.persist(cliente);
        return cliente;
    }


    //Atualizar
    @Transactional
    public Cliente atualizar(Cliente cliente){
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar (Cliente cliente){
        if(!entityManager.contains(cliente)){
    cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }


    //usado por deletar(Cliente cliente)
    @Transactional
    public void deletar(Integer id){
        Cliente cliente = entityManager.find(Cliente.class, id);
        deletar(cliente);
    }


    //Buscar por nome
    @Transactional
    public List<Cliente> buscarPorNome(String nome){
        String jpql = " select c from Cliente c where c.nome = :nome ";
        entityManager.createQuery(jpql, Cliente.class);
        TypedQuery<Cliente> query = entityManager.createQuery (jpql, Cliente.class);
        query.setParameter("nome", "%"+nome+"%");
        return query.getResultList();
    }



    //Lista todos os clientes no console
    @Transactional
    public List<Cliente> obterTodos(){
        return entityManager
                .createQuery("", Cliente.class)
                .getResultList();
    }

}
