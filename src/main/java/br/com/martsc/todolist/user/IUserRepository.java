package br.com.martsc.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    
    /**
     * Busca o usuário pelo seu username
     * @return User
     */
    UserModel findByUsername(String username);
}
