package com.am.planner.service;

import com.am.planner.dao.TaskManagerTransaction;
import com.am.planner.entity.Planner;
import com.am.planner.entity.Task;
import com.am.planner.util.ConnectionFactory;
import java.util.List;

/**
 *
 * @author Marques
 */
public class TaskServiceManager
{
    private static TaskServiceManager instance;
    private static TaskManagerTransaction transaction;
    
    public static TaskServiceManager getInstance()
    {
        if( instance == null )
        {
            instance = new TaskServiceManager();
        }
        
        return instance;
    }

    public TaskServiceManager() 
    {
        transaction = new TaskManagerTransaction();
    }
    
    public void addObject( Task object ) throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.addTask( factory, object );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }

    public void updateObject( Task object ) throws Exception 
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.updateTask( factory, object );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
    
    public void updateProgress( Task object ) throws Exception 
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.updateProgress( factory, object );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }

    public void deleteObject( Task object ) throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.deleteTask( factory, object );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }

    public List<Task> getListObject( String name ) throws Exception 
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            return transaction.getTasks( name );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
    
    public List<Task> getListObjectSuper( String find ) throws Exception 
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            return transaction.getTasksSuper( find );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
    
    public List<Task> getListTaskForPlanner( Planner planner ) throws Exception 
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            return transaction.getTasksForPlanner( planner );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
    
    public List<Task> getTasksForReport( int state ) throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            return transaction.getTasksForReport( state );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
}