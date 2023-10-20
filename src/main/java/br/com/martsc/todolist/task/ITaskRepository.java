package br.com.martsc.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    
    /**
     * Busca pelo id de usuário associado a esta tarefa
     * @param idUser
     * @return
     */
    List<TaskModel> findByIdUser(UUID idUser);
}
