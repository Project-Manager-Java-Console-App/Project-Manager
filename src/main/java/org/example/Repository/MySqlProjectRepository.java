package org.example.Repository;

import org.example.model.Project;

import java.sql.*;

public class MySqlProjectRepository
        implements ProjectRepository {
    private final Connection conn;

    public MySqlProjectRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Project save(Project project) {
        String sql = "INSERT INTO project(project_name,description,created_by,createdDate) VALUES(?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setInt(3, project.getCreatedByUserId());
            stmt.setDate(4, Date.valueOf(project.getCreatedDate()));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    project.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to save project: " + e.getMessage());
        }
        return project;
    }


    @Override
    public boolean delete(Project project) {
        String query = "DELETE FROM project WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, project.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Failed to delete project " + e.getMessage());
        }
        return false;
    }

    @Override
    public Project update(Project project, int id) {
        String sql = "UPDATE project SET project_name = ?, description = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setInt(3, id);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.err.println("Failed to update project");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Failed to update project " + e.getMessage());
        }
        return project;
    }

    @Override
    public Project findByName(String name) {
        String query = "SELECT * FROM project WHERE project_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;
                int id = rs.getInt("id");
                String description = rs.getString("description");
                int createdByUserId = rs.getInt("created_by");

                Project project = Project.create(name, description, createdByUserId);
                project.setId(id);
                return project;
            }
        } catch (SQLException e) {
            System.err.println("Failed to find project by name " + e.getMessage());
            return null;
        }
    }

    @Override
    public Project findById(Integer id) {
        String query = "SELECT * FROM project WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;
                String description = rs.getString("description");
                int createdByUserId = rs.getInt("created_by");
                String projectName = rs.getString("project_name");
                Project project = Project.create(projectName, description, createdByUserId);
                project.setId(id);
                return project;
            }
        } catch (SQLException e) {
            System.err.println("Failed to find project " + e.getMessage());
            return null;
        }
    }
}