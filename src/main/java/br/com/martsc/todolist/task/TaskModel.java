package br.com.martsc.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
    
    /**
     * Identificador da tarefa
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    
    /**
     * Descrição da tarefa
     */
    private String description;
    
    /**
     * Título da tarefa (tamanho máximo de 50 caractéres)
     */
    @Column(length = 50)
    private String title;
    
    /**
     * Data de início da tarefa
     */
    private LocalDateTime startAt;

    /**
     * Data de término da tarefa
     */
    private LocalDateTime endAt;

    /**
     * Prioridade da tarefa
     */
    private String priority;

    /**
     * Identificador do usuário no qual a tarefa está associada
     */
    private UUID idUser;

    /**
     * Data de criação da tarefa
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

}
