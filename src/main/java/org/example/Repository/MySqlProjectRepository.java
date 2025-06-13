package org.example.Repository;

import org.example.Exceptions.ProjectIdNotFound;
import org.example.Exceptions.UserIdNotFound;
import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.model.Project;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlProjectRepository
        implements ProjectRepository {
    private final Connection conn;

    public MySqlProjectRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Project save(Project project) {
        String sql = project.getId() == 0
                ? "INSERT INTO projects(name) VALUES(?)"
                : "UPDATE projects SET project_name = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, project.getName());
            if (project.getId() != 0) stmt.setInt(2, project.getId());
            stmt.executeUpdate();
            if (project.getId() == 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) project.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new ProjectIdNotFound();
        }
        return null;
    }


    @Override
    public boolean delete(Project project) throws SQLException {
        String query = "DELETE FROM project WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, project.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new ProjectIdNotFound();
        }
    }

    @Override
    public Project findByName(String name) throws UsernameAlreadyExistsException {
        String query = "SELECT * FROM project WHERE project_name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next())return null;
                int id = rs.getInt("id");
                String description = rs.getString("description");
                int createdByUserId = rs.getInt("created_by");

                Project project = Project.create(name, description, createdByUserId);
                project.setId(id);
                return project;
            }
        }
        catch (SQLException e) {
            throw new UsernameAlreadyExistsException(name);
        }
    }

    @Override
    public List<Project> getAllProjectsCreatedByUser(Integer userId) throws UserIdNotFound  {
        String query = "SELECT * FROM project WHERE created_by = ?";
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Project> list = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("project_name");
                    String description = rs.getString("description");
                    int createdByUserId = rs.getInt("created_by");
                    Project project = Project.create(name, description, createdByUserId);
                    project.setId(id);
                    list.add(project);
                }
                return list;
            }
        }
        catch (SQLException e){
           throw new UserIdNotFound();
        }
    }

    @Override
    public Project findById(Integer id) {
        String query = "SELECT * FROM project WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
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
        }catch (SQLException e){
            throw new ProjectIdNotFound();
        }
    }
}
