package br.com.martsc.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_users")
public class UserModel {
    
    /**
     * Identificador de usuário
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    /**
     * Nome de usuário do usuário - valor deve ser único
     */
    @Column(unique = true)
    private String username;

    /**
     * Nome do usuário
     */
    private String name;

    /**
     * Senha do usuário
     */
    private String password;

    /**
     * Data de criação do usuário
     */
    @CreationTimestamp
    private LocalDateTime createdAt;
    
}
