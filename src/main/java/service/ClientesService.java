package service;

import model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ClientesRepository;

@Service
public class ClientesService {

    @Autowired
    private ClientesRepository repository;

    @Autowired
    public ClientesService (ClientesRepository repository){
        this.repository = repository;
    }


    public void salvarCliente(Cliente cliente){
        validarCliente(cliente);
        this.repository.persistir(cliente);

    }

    public void validarCliente(Cliente cliente){

    }


}
