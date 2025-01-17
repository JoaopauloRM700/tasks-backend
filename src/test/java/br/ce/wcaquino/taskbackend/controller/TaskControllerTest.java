package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;


public class TaskControllerTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskController controller;
    
    @Before
    public void Setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naoDeveSalvarSemDescricao (){

        Task todo = new Task();
        //todo.setTask("Descrição");
        todo.setDueDate(LocalDate.now());
        try {
            controller.save(todo);
            Assert.fail("Não deveria chegar aqui");
        } catch (Exception e) {
            Assert.assertEquals("Fill the task description", e.getMessage());
        }
        

    }

    @Test
    public void naoDeveSalvarSemData(){

        Task todo = new Task();
        todo.setTask("Descrição");
       // todo.setDueDate(LocalDate.now());
        try {
            controller.save(todo);
            Assert.fail("Não deveria chegar aqui");
        } catch (Exception e) {
            Assert.assertEquals("Fill the due date", e.getMessage());
        }



    }

    @Test
    public void naoDeveSalvarComDataPassada(){

        Task todo = new Task();
        todo.setTask("Descrição");
        
        todo.setDueDate(LocalDate.of(2021, 01, 01));
        try {
            controller.save(todo);
            Assert.fail("Não deveria chegar aqui");
        } catch (Exception e) {
            Assert.assertEquals("Due date must not be in past", e.getMessage());
        }

    }

    @Test
    public void DeveSalvarComSucesso () throws ValidationException{

        Task todo = new Task();
        todo.setTask("Descrição");
        todo.setDueDate(LocalDate.now());
        controller.save(todo);
        Mockito.verify(taskRepo).save(todo);

    }
}
