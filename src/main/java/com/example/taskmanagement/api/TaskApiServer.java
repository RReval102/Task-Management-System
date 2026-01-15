package com.example.taskmanagement.api;

import com.example.taskmanagement.dao.TaskDAO;
import com.example.taskmanagement.model.Task;
import com.google.gson.Gson;
import io.javalin.Javalin;

/**
 * Simple REST API server exposing CRUD operations for tasks.  This
 * server uses the Javalin framework and Gson for JSON
 * serialization/deserialization.  It is intended for demonstration
 * purposes and is started/stopped by the test suite.
 */
public class TaskApiServer {
    private final Javalin app;
    private final TaskDAO taskDao;
    private final Gson gson = new Gson();

    /**
     * Create and start the API server on the given port.  It
     * registers handlers for GET/POST operations on `/tasks` and
     * `/tasks/{id}`.
     *
     * @param port the port number on which to start the server
     */
    public TaskApiServer(int port) {
        this.taskDao = new TaskDAO();
        app = Javalin.create().start(port);
        registerRoutes();
    }

    private void registerRoutes() {
        app.get("/tasks", ctx -> ctx.json(taskDao.getAll()));

        app.post("/tasks", ctx -> {
            Task task = gson.fromJson(ctx.body(), Task.class);
            taskDao.save(task);
            ctx.json(task);
        });

        app.get("/tasks/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            Task task = taskDao.getById(id);
            if (task != null) {
                ctx.json(task);
            } else {
                ctx.status(404);
            }
        });
    }

    /**
     * Stop the API server.  This method is idempotent; calling it
     * multiple times has no additional effect.
     */
    public void stop() {
        if (app != null) {
            app.stop();
        }
    }
}