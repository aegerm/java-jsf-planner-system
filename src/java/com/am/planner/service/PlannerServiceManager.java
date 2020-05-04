package com.am.planner.service;

import com.am.planner.dao.PlannerManagerTransaction;
import com.am.planner.entity.Planner;
import com.am.planner.util.ConnectionFactory;
import java.util.List;

/**
 *
 * @author Marques
 */
public class PlannerServiceManager 
{
    private static PlannerServiceManager instance;
    private static PlannerManagerTransaction transaction;
    
    public static PlannerServiceManager getInstance()
    {
        if( instance == null )
        {
            instance = new PlannerServiceManager();
        }
        
        return instance;
    }

    public PlannerServiceManager() 
    {
        transaction = new PlannerManagerTransaction();
    }
    
    public void addObject( Planner object ) throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.addPlanner( factory, object );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }

    public void updateObject( Planner object ) throws Exception 
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.updatePlanner( factory, object );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }

    public void deleteObject( Planner object ) throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.deletePlanner( factory, object );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
    
    public void updateProgressPlanner( Planner object ) throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            transaction.updateProgressPlanner( factory, object );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }

    public List<Planner> getListObject( String name ) throws Exception
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            return transaction.getPlanners( name );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
    
    public List<Planner> findPlannersName( String name ) throws Exception 
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            return transaction.findPlannersName( name );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
    
    public List<Planner> reportPlannerState( int state ) throws Exception 
    {
        ConnectionFactory factory = ConnectionFactory.getInstance();
        
        try
        {
            return transaction.reportPlannerState( state );
        }
        
        finally
        {
            factory.closeConnection();
        }
    }
}