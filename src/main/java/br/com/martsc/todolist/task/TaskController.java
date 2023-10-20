package br.com.martsc.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.martsc.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    /**
     * Repositório de tarefas
     */
    @Autowired
    private ITaskRepository taskRepository;
    
    /**
     * Lista as tarefas
     */
    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        
        // Obtém id de usuário da requisição
        var idUser = request.getAttribute("idUser");

        // Obtém todas as tarefas relacionadas ao usuário de ID informado
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);

        // Retorna todas as tarefas encontradas para o usuário em questão
        return tasks;
    }

    /**
     * Adiciona uma nova tarefa
     */
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody TaskModel task, HttpServletRequest request) {

        // Obtém a data/hora atual
        var currentDate = LocalDateTime.now();
        
        // Verifica se a data atual se encontra depois da data de início/término da tarefa 
        if (currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())) {
            // Retorna um status BAD REQUEST (código 400) e o corpo informando o problema
            return ResponseEntity.badRequest()
            .body("A data de início/término deve ser maior que a data atual.");
        }

        // Verifica se a data de início da tarefa se encontra depois da data de término da tarefa
        if (task.getStartAt().isAfter(task.getEndAt())) {
            // Retorna um status BAD REQUEST (código 400) e um corpo informando o problema
            return ResponseEntity.badRequest()
            .body("A data de início deve ser menor que a data de término");
        }

        // Obtém o id de usuário que foi passado pelo atributo na requisição
        var idUser = request.getAttribute("idUser");
        
        // Seta para a tarefa o id do usuário associado
        task.setIdUser((UUID) idUser);
        
        // Salva a tarefa no repositório de tarefas
        var taskSearch = this.taskRepository.save(task);

        // Retorna um status OK (200) e as informações da tarefa registrada no corpo da requisição
        return ResponseEntity.ok().body(taskSearch);

    }

    /**
     * Altera uma tarefa
     * @param task
     * @param id
     * @param request
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody TaskModel task, @PathVariable UUID id, HttpServletRequest request) {
        var taskSearch = this.taskRepository.findById(id).orElse(null);

        if(taskSearch == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Tarefa não encontrada.");
        }

        var idUser = request.getAttribute("idUser");

        if(!task.getIdUser().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Usuário não tem permissão par alterar essa tarefa.");
        }

        Utils.copyNonNullProperties(taskSearch, task);

        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);     
    }
    
}
