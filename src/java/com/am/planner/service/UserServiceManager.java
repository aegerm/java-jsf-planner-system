package com.am.planner.service;

import com.am.planner.dao.UserManagerTransaction;
import com.am.planner.entity.User;
import com.am.planner.util.ConnectionFactory;
import java.util.List;

/**
 *
 * @author alexandre.marques
 */
public class UserServiceManager
{
    private static UserServiceManager instance;
    private static UserManagerTransaction transaction;
    
    public static UserServiceManager getInstance()
    {
        if( instance == null )
        {
            instance = new UserServiceManager();
        }
        
        return instance;
    }

    public UserServiceManager() 
    {
        transaction = new UserManagerTransaction();
    }
    
    public void addObject( User object ) throws Exception 
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.addUser( factory, object );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }

    public void updateObject( User object ) throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.updateUser( factory, object );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }

    public void deleteObject( User object ) throws Exception 
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.deleteUser( factory, object );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }

    public List<User> getListObject( String name ) throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            return transaction.getUsers( name );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
    
    public User getUserLogin( String login, String password ) throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            return transaction.getUserLogin( factory, login, password );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
    
    public void addSession( User user ) throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.addSession( factory, user );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
    
    public User getUserSession() throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            return transaction.getUserSession();
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
    
    public void deleteSession( User user ) throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.deleteSession( factory, user );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
    
    public List<User> findUsersName( String name ) throws Exception 
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            return transaction.findUsersName( name );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
}