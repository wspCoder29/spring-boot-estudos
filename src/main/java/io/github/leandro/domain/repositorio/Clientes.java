package io.github.leandro.domain.repositorio;

import io.github.leandro.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class Clientes {


    private static String INSERT = "insert into cliente (nome) values (?) ";
    private static String SELECT_ALL = "SELECT * FROM CLIENTE ";
    private static String UPDATE = "update cliente set nome = ? where id = ? ";
    private static String DELETE = "delete from cliente where id = ? ";


    @Autowired
   private JdbcTemplate jdbcTemplate;



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

            System.out.println("====Mostrando clientes====");
            //para mostrar todos os clientes gerados no console
            List<Cliente> todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);


            System.out.println("====Atualizando clientes====");
            //testa o método de atualização
            todosClientes.forEach(c -> {
                c.setNome(c.getNome()+" atualizado. (metodo editar nome ok)");
                clientes.atualizar(c);
            });

            System.out.println("====Mostrando clientes====");
            //Mostra todos os clientes
            todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("====Buscando clientes====");
                System.out.println("Encontrado");
                clientes.buscarPorNome("Juri").forEach(System.out::println);

//            System.out.println("deletando clientes");
//            clientes.obterTodos().forEach(c ->{
//                clientes.deletar(c);
//            });


            todosClientes = clientes.obterTodos();
            if(todosClientes.isEmpty()){
                System.out.println("Nenhum clientes encontrado");
            }else{
                todosClientes.forEach(System.out::println);
            }



        };
    }

    public Cliente salvar(Cliente cliente){
        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        return cliente;
    }


    //Atualizar
    public Cliente atualizar(Cliente cliente){
        jdbcTemplate.update(UPDATE, new Object[]{cliente.getNome(), cliente.getId()});
        return cliente;
    }


    public void deletar (Cliente cliente){
        deletar(cliente.getId());
    }


    //usado por deletar(Cliente cliente)
    public void deletar(Integer id){
        jdbcTemplate.update(DELETE, new Object[]{id});
    }


    //Buscar por nome
    public List<Cliente> buscarPorNome(String nome){
        return jdbcTemplate.query(SELECT_ALL.concat(" where nome like ? "),
                new Object[]{"%" + nome +"%"},
                obterClienteMapper());
    }



    //Lista todos os clientes no console
    public List<Cliente> obterTodos(){
        return jdbcTemplate.query(SELECT_ALL, obterClienteMapper());
    }

    private RowMapper<Cliente> obterClienteMapper() {

        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Cliente(resultSet.getInt("id"), resultSet.getString("nome"));
            }
        };
    }


}
