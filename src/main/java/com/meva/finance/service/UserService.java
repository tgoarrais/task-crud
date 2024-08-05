package com.meva.finance.service;

import com.meva.finance.dto.UserDto;
import com.meva.finance.exception.CpfNotFoundException;
import com.meva.finance.exception.CpfValidateException;
import com.meva.finance.exception.FamilyNotFoundException;
import com.meva.finance.model.Family;
import com.meva.finance.model.User;
import com.meva.finance.repository.FamilyRepository;
import com.meva.finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
public class UserService {

    // Injeta as dependências dos repositórios de usuário
    private final UserRepository userRepository;
    // Injeta as dependências dos repositórios de familia
    private final FamilyRepository familyRepository;

    // Construtor que recebe as dependências dos repositórios
    @Autowired
    public UserService(UserRepository userRepository, FamilyRepository familyRepository) {
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
    }

    // Método para resgistrar um novo usuário
    @Transactional
    public User registerUser(UserDto userDto) {
        // Converte UserDto para User
        User user = userDto.converterToUser();
        // Valida e configura a família do usuário
        user.setFamily(validateFamily(userDto));
        // Salva o usuário no banco de dados
        return userRepository.save(user);
    }

    private Family validateFamily(UserDto userDto) throws CpfValidateException, FamilyNotFoundException {
        // Verifica se o CPF já existe no repositório de usuários.
        if (userRepository.findByCpf(userDto.getCpf()).isPresent()) {
            throw new CpfValidateException(userDto.getCpf()); // Lança exceção se o CPF já existir.
        }

        if (userDto.getFamilyDto().getIdFamily() == null){
            throw new FamilyNotFoundException(userDto.getFamilyDto().getIdFamily());
        }

        // Verifica se o ID da família é zero, se for, cria uma nova família
        if (userDto.getFamilyDto().getIdFamily() == 0) {
            // Salva e retorna a nova família.
            return familyRepository.save(userDto.getFamilyDto().converterFamily());
        }
        
        // Tenta encontrar a família pelo ID, se não encontrar, lança exceção
        return familyRepository.findById(userDto.getFamilyDto().getIdFamily())
                .orElseThrow(() -> new FamilyNotFoundException(userDto.getFamilyDto().getIdFamily()));
    }

    @Transactional
    public User updateUser(String cpf, UserDto userDto) throws CpfNotFoundException, FamilyNotFoundException{
            User existUser = userRepository.findByCpf(cpf).orElseThrow(() -> new CpfNotFoundException(cpf));
            if (!familyRepository.findById(userDto.getFamilyDto().getIdFamily()).isPresent()){
                throw new FamilyNotFoundException(userDto.getFamilyDto().getIdFamily());
            }

            updateValues(existUser, userDto);
            return userRepository.save(existUser);
        }

    @Transactional
    public UserDto searchUser(String cpf) throws CpfNotFoundException {
        User existUser = userRepository.findByCpf(cpf).orElseThrow(() -> new CpfNotFoundException(cpf));
        return new UserDto(existUser);
    }

    @Transactional
    public void deleteUser(String cpf) throws CpfNotFoundException {
        User existUser = userRepository.findByCpf(cpf).orElseThrow(() -> new CpfNotFoundException(cpf));
        userRepository.delete(existUser);
    }

    private void updateValues(User existUser, UserDto userDto) {
        existUser.setName(getUpdateValue(existUser.getName(), userDto.getName()));
        existUser.setGenre(getUpdateValue(existUser.getGenre(), userDto.getGenre()));
        existUser.setState(getUpdateValue(existUser.getState(), userDto.getState()));
        existUser.setCity(getUpdateValue(existUser.getCity(), userDto.getCity()));

        LocalDate birth = userDto.getBirth();
        if (birth != null){
            existUser.setBirth(birth);
        }
    }

    private String getUpdateValue(String currentValue, String newValue) {
        return (newValue != null && !newValue.isEmpty()) ? newValue : currentValue;
    }

}
