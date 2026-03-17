package com.orque.ims.client.service;

import com.orque.ims.client.entity.Client;
import com.orque.ims.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client updatedClient) {
        return clientRepository.findById(id).map(existingClient -> {
            existingClient.setName(updatedClient.getName());
            existingClient.setContactPerson(updatedClient.getContactPerson());
            existingClient.setEmail(updatedClient.getEmail());
            existingClient.setPhone(updatedClient.getPhone());
            existingClient.setAddress(updatedClient.getAddress());
            existingClient.setStatus(updatedClient.getStatus());
            existingClient.setNotes(updatedClient.getNotes());
            return clientRepository.save(existingClient);
        }).orElseThrow(() -> new RuntimeException("Client not found with id " + id));
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
