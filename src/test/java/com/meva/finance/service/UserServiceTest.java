package com.meva.finance.service;

import com.meva.finance.dto.FamilyDto;
import com.meva.finance.dto.UserDto;
import com.meva.finance.exception.CpfNotFoundException;
import com.meva.finance.exception.CpfValidateException;
import com.meva.finance.exception.FamilyNotFoundException;
import com.meva.finance.model.Family;
import com.meva.finance.model.User;
import com.meva.finance.repository.FamilyRepository;
import com.meva.finance.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Declaração da classe de teste para UserService
class UserServiceTest {

    @Mock
    private UserRepository userRepository; // Cria um mock para o repositório de usuários

    @Mock
    private FamilyRepository familyRepository; // Cria um mock para o repositório de famílias

    @InjectMocks
    private UserService userService; // Injeta os mocks no serviço de usuário para realizar os testes

    private User user; // Declara uma instância de User para uso nos testes
    private Family family; // Declara uma instância de Family para uso nos testes
    private UserDto userDto; // Declara uma instância de UserDto para uso nos testes
    private FamilyDto familyDto; // Declara uma instância de FamilyDto para uso nos testes

    @BeforeEach
    public void setUp() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.initMocks(this);

        // Configura uma instância de Family para uso nos testes
        family = new Family(1L, "Morais");

        // Configura uma instância de User com atributos de teste
        user = new User(
                "676.390.570-00",
                "Thiago Arrais",
                "M",
                LocalDate.of(1990, 2, 25),
                "SP",
                "Cabreuva", family);

        // Configura uma instância de FamilyDto com atributos de teste
        familyDto = new FamilyDto(1L, "Morais");

        // Configura uma instância de UserDto com atributos de teste
        userDto = new UserDto("676.390.570-00",
                "Thiago Arrais",
                "M",
                LocalDate.of(1990, 2, 25),
                "SP",
                "Cabreuva", familyDto);
    }

    @Test
    void testRegisterUserSucess() {
        // Configura os mocks para simular que o CPF não existe e a família é encontrada
        when(userRepository.findByCpf(userDto.getCpf())).thenReturn(Optional.empty());
        when(familyRepository.findById(familyDto.getIdFamily())).thenReturn(Optional.of(family));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Executa o método registerUser e armazena o resultado
        User result = userService.registerUser(userDto);

        // Verifica se o resultado não é nulo e se o nome está correto
        assertNotNull(result);
        assertEquals("Thiago Arrais", result.getName());

        // Verifica se o método save foi chamado uma vez
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserCpfExists() {
        // Configura o mock para simular que o CPF já existe
        when(userRepository.findByCpf(userDto.getCpf())).thenReturn(Optional.of(user));

        // Verifica se uma exceção CpfValidateException é lançada ao tentar registrar o
        // usuário
        assertThrows(CpfValidateException.class, () -> userService.registerUser(userDto));
    }

    @Test
    void testRegisterUserFamilyNotFound() {
        // Configura os mocks para simular que o CPF não existe e a família não é
        // encontrada
        when(userRepository.findByCpf(userDto.getCpf())).thenReturn(Optional.empty());
        when(familyRepository.findById(familyDto.getIdFamily())).thenReturn(Optional.empty());

        // Verifica se uma exceção FamilyNotFoundException é lançada ao tentar registrar
        // o usuário
        assertThrows(FamilyNotFoundException.class, () -> userService.registerUser(userDto));
    }

    @Test
    void testUpdateUserSucess() {
        // Configura os mocks para simular que o usuário e a família existem
        when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.of(user));
        when(familyRepository.findById(familyDto.getIdFamily())).thenReturn(Optional.of(family));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Executa o método updateUser e armazena o resultado
        User result = userService.updateUser(user.getCpf(), userDto);

        // Verifica se o resultado não é nulo e se o nome está correto
        assertNotNull(result);
        assertEquals("Thiago Arrais", result.getName());

        // Verifica se o método save foi chamado uma vez
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUserCpfNotFound() {
        // Configura o mock para simular que o CPF não existe
        when(userRepository.findByCpf(userDto.getCpf())).thenReturn(Optional.empty());

        // Verifica se uma exceção CpfNotFoundException é lançada ao tentar atualizar o
        // usuário
        assertThrows(CpfNotFoundException.class, () -> userService.updateUser(userDto.getCpf(), userDto));
    }

    @Test
    void testSearchUserSuccess() {
        // Configura o mock para simular que o usuário existe
        when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.of(user));

        // Executa o método searchUser e armazena o resultado
        UserDto result = userService.searchUser(user.getCpf());

        // Verifica se o resultado não é nulo e se o nome está correto
        assertNotNull(result);
        assertEquals("Thiago Arrais", result.getName());
    }

    @Test
    void testSearchUserCpfNotFound() {
        // Configura o mock para simular que o CPF não existe
        when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.empty());

        // Verifica se uma exceção CpfNotFoundException é lançada ao tentar buscar o
        // usuário
        assertThrows(CpfNotFoundException.class, () -> userService.searchUser(user.getCpf()));
    }

    @Test
    void testDeleteUserSucess() {
        // Configura o mock para simular que o usuário existe
        when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.of(user));

        // Executa o método deleteUser
        userService.deleteUser(user.getCpf());

        // Verifica se o método delete foi chamado uma vez
        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    void testDeleteUserCpfNotFound() {
        // Configura o mock para simular que o CPF não existe
        when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.empty());

        // Verifica se uma exceção CpfNotFoundException é lançada ao tentar deletar o
        // usuário
        assertThrows(CpfNotFoundException.class, () -> userService.deleteUser(user.getCpf()));
    }
}
