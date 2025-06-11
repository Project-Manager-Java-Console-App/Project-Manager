package org.example.ProjectDAO;

import org.example.dataBase.AbstractFuncProjectTask;
import org.example.model.Status;
import org.example.model.Task;

import java.sql.*;

public class TaskDAO extends AbstractFuncProjectTask<Task,Integer> {

    @Override
    protected String tableName() {
        return "Task";
    }

    @Override
    protected Task fromResultSet(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setProject_id(rs.getInt("project_id"));
        task.setName(rs.getString("task_name"));
        task.setDescription(rs.getString("description"));
        task.setStatus(Status.valueOf(rs.getString("status")));
        Date startDate = rs.getDate("createdDate");
        if(startDate != null) {
            task.setStartDate(startDate.toLocalDate());
        }
        task.setCreatedBy(rs.getInt("created_by"));
        Date endDate = rs.getDate("endDate");
        if(endDate != null) {
            task.setEndDate(endDate.toLocalDate());
        }

        return task;
    }

    //task_name, description, status, createdDate, endDate, project_id, created_by
    @Override
    protected void fillPreparedStatementForInsert(PreparedStatement ps, Task dto) throws SQLException {
        ps.setString(1, dto.getName());
        ps.setString(2, dto.getDescription());
        ps.setString(3, dto.getStatus().toString());
        ps.setObject(4,dto.getStartDate());
        ps.setObject(5,dto.getEndDate());
        ps.setInt(6, dto.getProject_id());
        ps.setInt(7, dto.getCreatedBy());
    }

    @Override
    protected void fillPreparedStatementForUpdate(PreparedStatement ps, Task dto, Integer updatedObjectId) throws SQLException {
        ps.setString(1, dto.getName());
        ps.setString(2, dto.getDescription());
        ps.setString(3, dto.getStatus().toString());
        ps.setInt(4, updatedObjectId);
    }

    @Override
    protected Integer extractGeneratedKey(ResultSet rs) throws SQLException {
       return rs.getInt(1);
    }

    @Override
    protected String connectingTableName() {
        return "User_Task_Assigment";
    }

    @Override
    protected String getAllByAddedUser() {
        return "Select id from User_Task_Assigment where user_id = ?";
    }

    @Override
    protected Integer extractAddedUsersIdFromRow(ResultSet rs) throws SQLException {
        return rs.getInt(1);
    }

    @Override
    protected String addUserQuery(int task_id, int user_id) {
        return "Insert into User_Task_Assigment(id,user_id,assigment_date) values(?,?,?)";
    }

    @Override
    protected String getAddedUsers() {
        return "Select user_id from User_Task_Assigment where id = ?";
    }

    @Override
    protected String insertSQL() {
        return "Insert into Task(task_name, description, status, createdDate, endDate, project_id, created_by) values(?,?,?,?,?,?,?)";
    }

    @Override
    protected String updateSQL() {
        return "Update Task set task_name=? ,description=? ,status =? where id=?";
    }

    @Override
    protected String findByNameSQL() {
        return "Select * from task where task_name = ?";
    }

    public boolean changeStatus(Integer task_id, Status newStatus) throws SQLException {
        String sql = "Update task set status=? where id=?";
        try(Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, newStatus.toString());
            ps.setInt(2, task_id);
            return ps.executeUpdate() > 0;
        }

    }
}
