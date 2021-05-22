package com.chapter.one.clients.serviceImpl;

import com.chapter.one.clients.dto.ClientDTO;
import com.chapter.one.clients.entity.Client;
import com.chapter.one.clients.repository.ClientRepository;
import com.chapter.one.clients.service.ClientService;
import com.chapter.one.clients.serviceImpl.exception.NotFoundException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Override
    @Transactional
    public ClientDTO save(ClientDTO dto) {
        Client entity = new Client();
        convertDtoToEntity(dto, entity);
        entity = clientRepository.save(entity);

        return new ClientDTO(entity);
    }

    @Override
    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = clientRepository.getOne(id);
            convertDtoToEntity(dto, entity);
            clientRepository.save(entity);

            return new ClientDTO(entity);
        } catch (EntityNotFoundException e){
            throw new NotFoundException("Not found client id "+id);
        }
    }

    @Override
    public void delete(Long id) {
        try{
            clientRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new NotFoundException("Not found client id "+id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Optional<Client> object = clientRepository.findById(id);
        Client entity = object.orElseThrow(() -> new NotFoundException("Client not found"));
        return new ClientDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        Page<Client> list = clientRepository.findAll(pageRequest);
        Page<ClientDTO> listDto = list.map(x -> new ClientDTO(x));
        return listDto;
    }

    private void convertDtoToEntity(ClientDTO dto, Client entity){
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }
}
