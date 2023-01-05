package io.github.leandro;

import io.github.leandro.domain.entity.Cliente;
import io.github.leandro.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Autowired
    @Qualifier("applicationName")
    private String applicationName;

    @GetMapping("/hello")
    public String hello(){
        return applicationName;
    }

    @Bean
    public CommandLineRunner init (@Autowired Clientes clientes){
        //command line runner
        return args -> {


            System.out.println("====Salvando clientes====");
            clientes.save(new Cliente("Ada"));

            clientes.save(new Cliente("Mia"));

            clientes.save(new Cliente("Juri"));

            clientes.save(new Cliente("Kris"));

            System.out.println("====Mostrando clientes====");
            List<Cliente> todosClientes = clientes.findAll();
            todosClientes.forEach(System.out::println);


            System.out.println("====Atualizando clientes====");
            //testa o método de atualização
            todosClientes.forEach(c -> {
                c.setNome(c.getNome()+" atualizado. (metodo editar nome ok)");
                clientes.save(c);
            });


            System.out.println("====Mostrando clientes====");
            todosClientes = clientes.findAll();
            todosClientes.forEach(System.out::println);


            System.out.println("====Buscando clientes====");
            System.out.println("Encontrado");
            clientes.findByNomeLike("Juri").forEach(System.out::println);



            System.out.println("deletando clientes");
            clientes.findAll().forEach(c->{
                clientes.delete(c);
            });

            todosClientes = clientes.findAll();
            if(todosClientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado");
            }else{
                todosClientes.forEach(System.out::println);
            }
        };
    }

    public static void main(String[] args) {
        //run para rodar a applicação rececebe a classe e os args
        SpringApplication.run(VendasApplication.class, args);


    }
}
