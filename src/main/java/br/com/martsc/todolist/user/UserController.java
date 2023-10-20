package br.com.martsc.todolist.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * Repositório de usuários
     */
    @Autowired
    private IUserRepository userRepository;

    /**
     * Retorna todos os usuários criados
     * @return List<User>
     */
    @GetMapping("/")
    public List<UserModel> list() {
        var users = userRepository.findAll();
        return users;
    }

    /**
     * Retorna um único usuário pelo seu username
     * @param user
     * @return User
     */
    @GetMapping("/{username}")
    public UserModel find(@PathParam("username") UserModel user) {
        var userSearch = userRepository.findByUsername(user.getUsername());
        return userSearch;
    }

    /**
     * Adiciona um novo usuário
     * @param user
     */
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody UserModel user) {
        var userSearch = this.userRepository.findByUsername(user.getUsername());

        if(userSearch != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe.");
        }

        var passwordHashed = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(passwordHashed);

        var userCreated = this.userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

}
