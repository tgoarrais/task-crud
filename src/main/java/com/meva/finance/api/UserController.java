
package com.meva.finance.api;

import com.meva.finance.dto.UserDto;
import com.meva.finance.exception.CpfNotFoundException;
import com.meva.finance.exception.CpfValidateException;
import com.meva.finance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController // Indica que essa classe é um controlador REST, que responde a solicitações
				// HTTP.
@RequestMapping("/users") // Define o caminho base para todas as solicitações HTTP que serão tratadas por
							// essa classe.
@RequiredArgsConstructor // Cria um construtor com base nos campos finais não inicializados
public class UserController {

	private final UserService userService;

	// Método para registrar um novo usuário.
	@PostMapping("/register") // Mapeia solicitações HTTP POST para o caminho /register.
	public ResponseEntity<String> registerUser(@RequestBody @Valid UserDto userDto) {
		try {
			// Chama o serviço para registrar o usuário.
			userService.registerUser(userDto);
			return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado");
		} catch (CpfValidateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

	@PutMapping("/update/{cpf}")
	public ResponseEntity<String> updateUser(@PathVariable String cpf, @RequestBody UserDto userDto) {
		try {
			userService.updateUser(cpf, userDto);
			return ResponseEntity.ok("Cpf atualizado com sucesso");
		} catch (CpfNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/search/{cpf}")
	public ResponseEntity<UserDto> searchUser(@PathVariable String cpf) {
		try {
			UserDto userDto = userService.searchUser(cpf);
			return ResponseEntity.ok(userDto);
		} catch (CpfNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@DeleteMapping("/delete/{cpf}")
	public ResponseEntity<String> deleteUser(@PathVariable String cpf) {
		try {
			userService.deleteUser(cpf);
			return ResponseEntity.ok("Usuario excluído com sucesso");
		} catch (CpfNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

}
