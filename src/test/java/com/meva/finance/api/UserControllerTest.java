package com.meva.finance.api;


import com.meva.finance.dto.UserDto;
import com.meva.finance.exception.CpfNotFoundException;
import com.meva.finance.exception.CpfValidateException;
import com.meva.finance.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userDto = new UserDto();
        userDto.setCpf("12345678901");
        userDto.setName("Test User");
    }

    @Test
    void registerUserShouldReturnOkWhenUserRegisteredSuccessfully() {
        // Chama o método no controller
        ResponseEntity<String> response = userController.registerUser(userDto);

        // Verifica se o status de resposta é OK (200) e a mensagem é "Usuario registrado"
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario registrado", response.getBody());
    }

    @Test
    void registerUserShouldReturnConflictWhenCpfAlreadyExists() {
        // Configura o mock para lançar CpfValidateException se o CPF já existe
        doThrow(new CpfValidateException("Cpf já existe")).when(userService).registerUser(userDto);

        // Chama o método no controller
        ResponseEntity<String> response = userController.registerUser(userDto);

        // Verifica se o status de resposta é CONFLICT (409) e a mensagem é "CPF já existe"
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Cpf já existe", response.getBody());
    }

    @Test
    void updateUserShouldReturnOkWhenUserUpdatedSuccessfully() {
        // Chama o método no controller
        ResponseEntity<String> response = userController.updateUser("12345678901", userDto);

        // Verifica se o status de resposta é OK (200) e a mensagem é "Cpf atualizado com sucesso"
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cpf atualizado com sucesso", response.getBody());
    }

    @Test
    void updateUser_ShouldReturnNotFound_WhenCpfNotFound() {
        // Configura o mock para lançar CpfNotFoundException se o CPF não for encontrado
        doThrow(new CpfNotFoundException("Cpf não encontrado.")).when(userService).updateUser("12345678901", userDto);

        // Chama o método no controller
        ResponseEntity<String> response = userController.updateUser("12345678901", userDto);

        // Verifica se o status de resposta é NOT_FOUND (404) e a mensagem é "CPF não encontrado"
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cpf não encontrado.", response.getBody());
    }

    @Test
    void searchUser_ShouldReturnOk_WhenUserFound() {
        // Configura o mock para retornar um UserDto quando o usuário é encontrado
        when(userService.searchUser("12345678901")).thenReturn(userDto);

        // Chama o método no controller
        ResponseEntity<UserDto> response = userController.searchUser("12345678901");

        // Verifica se o status de resposta é OK (200) e o corpo da resposta é o userDto esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void searchUser_ShouldReturnNotFound_WhenCpfNotFound() {
        // Configura o mock para lançar CpfNotFoundException se o CPF não for encontrado
        doThrow(new CpfNotFoundException("Cpf não encontrado.")).when(userService).searchUser("12345678901");

        // Chama o método no controller
        ResponseEntity<UserDto> response = userController.searchUser("12345678901");

        // Verifica se o status de resposta é NOT_FOUND (404) e o corpo da resposta é null
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void deleteUser_ShouldReturnOk_WhenUserDeletedSuccessfully() {
        // Chama o método no controller
        ResponseEntity<String> response = userController.deleteUser("12345678901");

        // Verifica se o status de resposta é OK (200) e a mensagem é "Usuario excluído com sucesso"
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario excluído com sucesso", response.getBody());
    }

    @Test
    void deleteUser_ShouldReturnNotFound_WhenCpfNotFound() {
        // Configura o mock para lançar CpfNotFoundException se o CPF não for encontrado
        doThrow(new CpfNotFoundException("Cpf não encontrado.")).when(userService).deleteUser("12345678901");

        // Chama o método no controller
        ResponseEntity<String> response = userController.deleteUser("12345678901");

        // Verifica se o status de resposta é NOT_FOUND (404) e a mensagem é "CPF não encontrado"
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cpf não encontrado.", response.getBody());
    }
}
