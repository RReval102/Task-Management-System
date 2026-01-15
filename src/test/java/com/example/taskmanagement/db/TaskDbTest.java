package com.example.taskmanagement.db;

import com.example.taskmanagement.dao.TaskDAO;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.utils.HibernateUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link TaskDAO} using an in‑memory H2 database.
 * These tests verify basic CRUD operations directly against the
 * database layer.  The session factory is shut down after all tests
 * run to ensure resources are released.
 */
public class TaskDbTest {

    private TaskDAO dao;

    @BeforeEach
    void setUp() {
        dao = new TaskDAO();
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
    }

    @Test
    void shouldSaveAndRetrieveTask() {
        Task task = new Task();
        task.setTitle("DB Task");
        task.setDescription("Db description");
        task.setStatus("NEW");

        dao.save(task);

        assertNotNull(task.getId(), "Task ID should be generated upon saving");

        Task retrieved = dao.getById(task.getId());
        assertNotNull(retrieved, "Saved task should be retrievable");
        assertEquals("DB Task", retrieved.getTitle());

        List<Task> all = dao.getAll();
        assertTrue(all.size() >= 1, "At least one task should exist in the database");
    }
}