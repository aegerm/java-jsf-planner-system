package com.am.planner.dao;

import com.am.planner.entity.Planner;
import com.am.planner.entity.Task;
import com.am.planner.entity.User;
import com.am.planner.implement.TaskImplements;
import com.am.planner.util.ConnectionFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marques
 */
public class TaskManagerTransaction 
    implements 
        TaskImplements
{
    @Override
    public void addTask( ConnectionFactory cf, Task task ) throws Exception
    {
        String sql = "insert into tasks ( name, description, date_start, date_finished, state, progress, id_planner )" + " values ( " +
                     cf.format( task.getName() )                                                                       + ", " +
                     cf.format( task.getDescription() )                                                                + ", " +
                     cf.formatDate( task.getDateStart() )                                                              + ", " +
                     cf.formatDate( task.getDateFinished() )                                                           + ", " +
                     cf.format( task.getState() )                                                                      + ", " +
                     task.getProgress()                                                                                + ", " +
                     task.getPlanner().getId()                                                                         + " ) ";
                     
        cf.executeQuery( sql );
    }

    @Override
    public void updateTask( ConnectionFactory cf, Task task ) throws Exception 
    {
        String sql = "update tasks set " + " name = "               + cf.format( task.getName() )               + ", "
                                         + " description = "        + cf.format( task.getDescription() )        + ", "
                                         + " date_start = "         + cf.formatDate( task.getDateStart() )      + ", "
                                         + " date_finished  = "     + cf.formatDate( task.getDateFinished() )   + ", "
                                         + " progress  = "          + task.getProgress()                        + ", "
                                         + " id_planner  = "        + task.getPlanner().getId() +
                
                     " where id_task = " + task.getId();
        
        cf.executeQuery( sql );
    }
    
    public void updateProgress( ConnectionFactory cf, Task task ) throws Exception 
    {
        String sql = "update tasks set " + " progress  = " + task.getProgress() +
                
                     " where id_task = " + task.getId();
        
        cf.executeQuery( sql );
    }

    @Override
    public void deleteTask( ConnectionFactory cf, Task task ) throws Exception 
    {
        String sql = "update tasks set state = " + cf.format( Task.INATIVE ) + " where id_task = " + task.getId();
        
        cf.executeQuery( sql );
    }

    @Override
    public List<Task> getTasks( String name ) throws Exception
    {
        User user = com.am.planner.service.UserServiceManager.getInstance().getUserSession();
        
        String sql = "select t.id_task as id, t.name as name, t.description as description, t.date_start as start, t.date_finished as end, p.name as planner, t.progress as progress, p.id_planner as idp from tasks t, planners p where t.id_planner = p.id_planner and t.state <> 'I' and p.id_user = " + user.getId() + " and t.name like '%" + name + "%'";
        
        PreparedStatement ps;
        ResultSet rs;
        
        List<Task> tasks = new ArrayList<>();
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                Task t = new Task();
                
                Planner p = new Planner();
                
                t.setId( rs.getInt( "id" ) );
                t.setName( rs.getString( "name" ) );
                t.setDescription( rs.getString( "description" ) );
                t.setDateStart( rs.getDate( "start" ) );
                t.setDateFinished( rs.getDate( "end" ) );
                t.setProgress( rs.getInt( "progress" ) );

                p.setId( rs.getInt( "idp" ) );
                p.setName( rs.getString( "planner" ) );
                
                t.setPlanner( p );
                
                tasks.add( t );
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return tasks;
    }
    
    @Override
    public List<Task> getTasksSuper( String find ) throws Exception
    {
        String sql = "select t.id_task as id, t.name as name, t.description as description, t.date_start as start, t.date_finished as end, p.name as planner, t.progress as progress, p.id_planner as idp from tasks t, planners p, users u where t.id_planner = p.id_planner and u.id_user = p.id_user and t.state <> 'I' and u.profile = " + User.OPERATOR + " and t.name like '%" + find + "%'";
        
        PreparedStatement ps;
        ResultSet rs;
        
        List<Task> tasks = new ArrayList<>();
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                Task t = new Task();
                
                Planner p = new Planner();
                
                t.setId( rs.getInt( "id" ) );
                t.setName( rs.getString( "name" ) );
                t.setDescription( rs.getString( "description" ) );
                t.setDateStart( rs.getDate( "start" ) );
                t.setDateFinished( rs.getDate( "end" ) );
                t.setProgress( rs.getInt( "progress" ) );

                p.setId( rs.getInt( "idp" ) );
                p.setName( rs.getString( "planner" ) );
                
                t.setPlanner( p );
                
                tasks.add( t );
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return tasks;
    }

    @Override
    public Task findTaskCode( int id ) throws Exception
    {
        String sql = "select t.id_task as id, t.name as name, t.description as description, t.date_start as start, t.date_finished as end, p.name as planner, t.progress as progress, p.id_planner as idp from tasks t, planners p where t.id_planner = p.id_planner and t.state <> 'I' and t.id_task = " + id;
        
        PreparedStatement ps;
        ResultSet rs;
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                Task t = new Task();
                
                Planner p = new Planner();
                
                t.setId( rs.getInt( "id" ) );
                t.setName( rs.getString( "name" ) );
                t.setDescription( rs.getString( "description" ) );
                t.setDateStart( rs.getDate( "start" ) );
                t.setDateFinished( rs.getDate( "end" ) );
                t.setProgress( rs.getInt( "progress" ) );
                
                p.setId( rs.getInt( "idp" ) );
                p.setName( rs.getString( "planner" ) );
                
                t.setPlanner( p );
                
                return t;
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return null;
    }
    
    @Override
    public List<Task> getTasksForPlanner( Planner planner ) throws Exception
    {
        String sql = "select t.id_task as id, name as name, t.progress as progress  from tasks t where  t.state <> 'I' and t.id_planner = " + planner.getId();
        
        PreparedStatement ps;
        ResultSet rs;
        
        List<Task> tasks = new ArrayList<>();
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                Task t = new Task();
                
                t.setId( rs.getInt( "id" ) );
                t.setName( rs.getString( "name" ) );
                t.setProgress( rs.getInt( "progress" ) );
                
                tasks.add( t );
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return tasks;
    }

    @Override
    public List<Task> getTasksForReport( int state ) throws Exception
    {
        String sql = "select t.id_task as id, t.name as name, t.description as description, t.date_start as start, t.date_finished as end, p.name as planner from tasks t, planners p where t.id_planner = p.id_planner and t.state <> 'I' and t.progress = " + state;
        
        PreparedStatement ps;
        ResultSet rs;
        
        List<Task> tasks = new ArrayList<>();
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                Task t = new Task();
                
                Planner p = new Planner();
                
                t.setId( rs.getInt( "id" ) );
                t.setName( rs.getString( "name" ) );
                t.setDescription( rs.getString( "description" ) );
                t.setDateStart( rs.getDate( "start" ) );
                t.setDateFinished( rs.getDate( "end" ) );
                
                p.setName( rs.getString( "planner" ) );
                
                t.setPlanner( p );
                
                tasks.add( t );
                
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return tasks;
    }
}