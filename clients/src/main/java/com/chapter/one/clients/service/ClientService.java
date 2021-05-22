package com.chapter.one.clients.service;


import com.chapter.one.clients.dto.ClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ClientService {

    ClientDTO save(ClientDTO dto);

    ClientDTO update(Long id, ClientDTO dto);

    void delete(Long id);

    ClientDTO findById(Long id);

    Page<ClientDTO> findAllPaged(PageRequest pageRequest);
}
