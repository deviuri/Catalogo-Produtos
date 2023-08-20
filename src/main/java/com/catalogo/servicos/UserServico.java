package com.catalogo.servicos;

import com.catalogo.Infra.exceptions.DatabaseException;
import com.catalogo.Infra.exceptions.NoSuchElementException;
import com.catalogo.Infra.exceptions.ResourceNotFoundException;
import com.catalogo.dto.RoleDTO;
import com.catalogo.dto.UserDTO;
import com.catalogo.dto.UserInsertDTO;
import com.catalogo.entities.Role;
import com.catalogo.entities.User;
import com.catalogo.repository.RoleRepository;
import com.catalogo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;


@Service
public class UserServico {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPage(Pageable paginacao) {
        Page<User> user = repository.findAll(paginacao);

        return user.map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO buscarPorId(Long id) {
        Optional<User> ct = repository.findById(id);
        User user = ct.orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));

        return new UserDTO(user);
    }



    @Transactional
    public void deletarUser(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("User do id: " + id + " não foi encontrado");
        } catch (DataIntegrityViolationException | NoSuchElementException e) {
            throw new DatabaseException("User não existe em nosso Banco de Dados");
        }
    }
    @Transactional
    public UserDTO cadastrarUser(UserInsertDTO dto) {
        User user = new User();
        copyDtoToEntity(dto, user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user = repository.save(user);
        return new UserDTO(user);
    }
    @Transactional
    public UserDTO atualizarUser(Long id, UserDTO dto) {
        try {
            User entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UserDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Item do id: " + id + " não foi encontrado");
        } catch (DataIntegrityViolationException | java.util.NoSuchElementException e) {
            throw new DatabaseException("Violação de integridade");
        }
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        for (RoleDTO roleDto : dto.getRoles()) {
            Role role = roleRepository.getReferenceById(roleDto.getId());
            entity.getRoles().add(role);
        }
    }
}
