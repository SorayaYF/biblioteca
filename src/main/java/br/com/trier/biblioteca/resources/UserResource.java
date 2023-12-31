package br.com.trier.biblioteca.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.biblioteca.domain.User;
import br.com.trier.biblioteca.domain.dto.UserDTO;
import br.com.trier.biblioteca.services.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserService service;

	@Secured({ "ROLE_USER" })
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findByID(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserDTO user) {
		return ResponseEntity.ok(service.insert(new User(user)).toDTO());
	}

	@Secured({ "ROLE_USER" })
	@GetMapping
	public ResponseEntity<List<UserDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((user) -> user.toDTO()).toList());
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
		User user = new User(userDTO);
		user.setId(id);
		return ResponseEntity.ok(service.update(user).toDTO());
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/name/{name}")
	public ResponseEntity<List<UserDTO>> findByNameStartsWithIgnoreCase(@PathVariable String name) {
		return ResponseEntity
				.ok(service.findByNameStartsWithIgnoreCase(name).stream().map((user) -> user.toDTO()).toList());
	}
}
