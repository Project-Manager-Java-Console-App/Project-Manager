package org.example.Repository;

import org.example.model.Status;
import org.example.model.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySqlTaskRepository implements TaskRepository {
    private final Connection conn;

    public MySqlTaskRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Task save(Task task) {
        String query = "Insert into Task(task_name, description, status, createdDate, project_id, created_by) values(?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getStatus().name());
            stmt.setObject(4, task.getStartDate());
            stmt.setInt(5, task.getProject_id());
            stmt.setInt(6, task.getCreatedBy());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    task.setId(rs.getInt(1));
                }
            }
            return task;
        } catch (SQLException e) {
            System.err.println("Failed to save task: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(Task task) {
        String query = "Delete from Task where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, task.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Task update(Task task, int id) {
        String sql = "Update task set task_name=?, description = ? where id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setInt(3, id);
            int rows = statement.executeUpdate();
            if (rows == 0) {
                System.err.println("No rows updated");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return task;
    }


    @Override
    public Task findByName(String name) {
        String query = "Select * from Task where task_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;
                int id = rs.getInt("id");
                String description = rs.getString("description");
                String status = rs.getString("status");
                Date createdDate = rs.getDate("createdDate");
                int project_id = rs.getInt("project_id");
                int createdBy = rs.getInt("created_by");

                Task task = Task.create(name, project_id, description, Status.valueOf(status), createdDate.toLocalDate(), createdBy);
                task.setId(id);
                return task;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Task> getAllTasksCreatedByUser(Integer userId) {
        String query = "Select * from Task where created_by = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Task> tasks = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("task_name");
                    String description = rs.getString("description");
                    String status = rs.getString("status");
                    Date createdDate = rs.getDate("createdDate");
                    int project_id = rs.getInt("project_id");
                    Task task = Task.create(name, project_id, description, Status.valueOf(status), createdDate.toLocalDate(), userId);
                    task.setId(id);
                    tasks.add(task);
                }
                return tasks;
            }
        } catch (SQLException e) {
            System.err.println("Failed to get all tasks created by user " + e.getMessage());
            return null;
        }
    }

    @Override
    public Task findById(Integer id) {
        String query = "Select * from Task where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;
                String name = rs.getString("task_name");
                String description = rs.getString("description");
                String status = rs.getString("status");
                int createdBy = rs.getInt("created_by");
                Date createdDate = rs.getDate("createdDate");
                int project_id = rs.getInt("project_id");
                Task task = Task.create(name, project_id, description, Status.valueOf(status), createdDate.toLocalDate(), createdBy);
                task.setId(id);
                return task;
            }
        } catch (SQLException e) {
            System.err.println("Failed to find user by id " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean changeStatus(Integer task_id, Status status) {
        String query = "Update Task set status = ?,endDate=? where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status.name());
            if (status == Status.COMPLETED || status == Status.FAILED || status == Status.ABORTED) {
                stmt.setDate(2, Date.valueOf(LocalDate.now()));
            } else {
                stmt.setDate(2, null);
            }
            stmt.setInt(3, task_id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("SQL error while changing task status: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Task> findTasksByProjectId(Integer projectId) {
        String query = "Select * from Task where project_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Task> tasks = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("task_name");
                    String description = rs.getString("description");
                    String status = rs.getString("status");
                    Date createdDate = rs.getDate("createdDate");
                    int createdBy = rs.getInt("created_by");
                    Task task = Task.create(name, projectId, description, Status.valueOf(status), createdDate.toLocalDate(), createdBy);
                    task.setId(id);
                    tasks.add(task);
                }
                return tasks;
            }

        } catch (SQLException e) {
            System.err.println("Failed to find tasks by project id " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean assignTaskToProject(Integer projectId, Integer taskId) {

        String sql = "Update Task set project_id = ? where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            stmt.setInt(2, taskId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Failed to change project contains task: " + e.getMessage());
            return false;
        }
    }
}
