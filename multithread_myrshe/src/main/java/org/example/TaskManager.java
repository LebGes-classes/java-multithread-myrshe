package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    private Map<String, Employee> employees;
    private Map<String, Task> tasks = new HashMap<>();
    private ExecutorService executorService;

    public TaskManager(Map<String, Employee> employees) {
        this.employees = employees;
        this.executorService = Executors.newFixedThreadPool(employees.size());

    }

    public void assignTasks() {
        for (Map.Entry<String, Employee> entry : employees.entrySet()) {
            Employee employee = entry.getValue();
            String employeeName = entry.getKey();
            executorService.execute(() -> {
                while (employee.getMaximumHours() > 0 && !Thread.currentThread().isInterrupted()) {
                    Task task = generateTask();
                    int taskDuration = task.getHoursWorked();
                    if (taskDuration <= employee.getMaximumHours()) {
                        employee.setWorkedHours(employee.getWorkedHours() + taskDuration);
                        employee.setMaximumHours(employee.getMaximumHours() - taskDuration);
                        tasks.put(employeeName, task);
                    } else {
                        break;
                    }
                }
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private Task generateTask() {
        int taskDuration = (int) (Math.random() * 16) + 1; // случайная длительность задачи от 1 до 16 часов
        return new Task(taskDuration);
    }


    public Map<String, Task> getTasks() {
        return tasks;
    }

    public void printStatistics() {
        for (Map.Entry<String, Employee> entry : employees.entrySet()) {
            String employeeName = entry.getKey();
            Employee employee = entry.getValue();
            System.out.println("Worker: " + employeeName);
            System.out.println("Has worked : " + employee.getWorkedHours() + " hours");
            System.out.println("He/she still needs to work: " + employee.getMaximumHours() + " hours");
            System.out.println("  ");
        }
    }
}
