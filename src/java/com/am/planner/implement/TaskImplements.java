package com.am.planner.implement;

import com.am.planner.entity.Planner;
import com.am.planner.entity.Task;
import com.am.planner.util.ConnectionFactory;
import java.util.List;

/**
 *
 * @author Marques
 */
public interface TaskImplements 
{
    public void addTask( ConnectionFactory connectionFactory, Task task ) throws Exception;
    public void updateTask( ConnectionFactory connectionFactory, Task task ) throws Exception;
    public void deleteTask( ConnectionFactory connectionFactory, Task task ) throws Exception;
    public List<Task> getTasks( String name ) throws Exception;
    public Task findTaskCode( int id ) throws Exception;
    public List<Task> getTasksSuper( String find ) throws Exception;
    public List<Task> getTasksForPlanner( Planner planner ) throws Exception;
    public List<Task> getTasksForReport( int state ) throws Exception;
}
