package com.am.planner.service;

import com.am.planner.dao.OrganizationManagerTransaction;
import com.am.planner.entity.Organization;
import com.am.planner.util.ConnectionFactory;
import java.util.List;

/**
 *
 * @author Marques
 */
public class OrganizationServiceManager
{
    private static OrganizationServiceManager instance;
    private static OrganizationManagerTransaction transaction;
    
    public static OrganizationServiceManager getInstance()
    {
        if( instance == null )
        {
            instance = new OrganizationServiceManager();
        }
        
        return instance;
    }

    public OrganizationServiceManager()
    {
        transaction = new OrganizationManagerTransaction();
    }
    
    public void addObject( Organization object ) throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.addOrganization( factory, object );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }

    public void updateObject( Organization object ) throws Exception 
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.updateOrganization( factory, object );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }

    public void deleteObject( Organization object ) throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.deleteOrganization( factory, object );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }

    public List<Organization> getListObject( String name ) throws Exception 
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            return transaction.getOrganizations( name );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
    
    public List<Organization> findOrganizationsName( String name ) throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            return transaction.findOrganizationsName( name );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
}