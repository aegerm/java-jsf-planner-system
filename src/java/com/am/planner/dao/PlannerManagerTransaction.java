package com.am.planner.dao;

import com.am.planner.entity.Organization;
import com.am.planner.entity.Planner;
import com.am.planner.entity.Task;
import com.am.planner.entity.User;
import com.am.planner.implement.PlannerImplements;
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
public class PlannerManagerTransaction 
    implements 
        PlannerImplements
{
    @Override
    public void addPlanner( ConnectionFactory cf, Planner planner ) throws Exception
    {
        String sql = "insert into planners ( name, description, date_register, state, id_organization, id_user, progress )" + "values ( " +
                     cf.format( planner.getName() )                                                                         + ", "        +
                     cf.format( planner.getDescription() )                                                                  + ", "        +
                     cf.formatDate( planner.getDateRegister() )                                                             + ", "        +
                     cf.format( planner.getState() )                                                                        + ", "        +
                     planner.getOrganization().getId()                                                                      + ", "        +
                     planner.getUser().getId()                                                                              + ", "        +
                     planner.getProgress()                                                                                  + " )";
        
        cf.executeQuery( sql );
    }

    @Override
    public void updatePlanner( ConnectionFactory cf, Planner planner ) throws Exception
    {
        String sql = "update planners set " + " name            = " + cf.format( planner.getName() )                + ", "
                                            + " description     = " + cf.format( planner.getDescription() )         + ", "
                                            + " date_register   = " + cf.formatDate( planner.getDateRegister() )    + ", "
                                            + " id_user         = " + planner.getUser().getId()                     + ", "              
                                            + " id_organization = " + planner.getOrganization().getId()             +               
                
                     " where id_planner = " + planner.getId();
        
        cf.executeQuery( sql );
    }

    @Override
    public void deletePlanner( ConnectionFactory cf, Planner planner ) throws Exception
    {
        String sql = "update planners set state = " + cf.format( Planner.INATIVE ) + " where id_planner = " + planner.getId();
        
        cf.executeQuery( sql );
    }

    @Override
    public List<Planner> getPlanners( String name ) throws Exception 
    {
        User user = com.am.planner.service.UserServiceManager.getInstance().getUserSession();
        
        String sql = "select p.id_planner as id, p.name as name, p.description as description, p.date_register as date, o.name as org, u.name as user, o.id_organization as io, u.id_user as iu, p.progress as prog from planners p inner join organizations o on o.id_organization = p.id_organization inner join users u on u.id_user = p.id_user and p.state <> 'I' and p.id_user = " + user.getId() + " and p.name like '%" + name + "%'";
    
        PreparedStatement ps;
        ResultSet rs;
        
        List<Planner> planners = new ArrayList<>();
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                Planner p = new Planner();
                
                Organization o = new Organization();
                
                User u = new User();
                
                p.setId( rs.getInt( "id" ) );
                p.setName( rs.getString( "name" ) );
                p.setDescription( rs.getString( "description" ) );
                p.setDateRegister( rs.getDate( "date" ) );
                p.setProgress( rs.getInt( "prog" ) );
                o.setId( rs.getInt( "io" ) );
                o.setName( rs.getString( "org" ) );
                u.setId( rs.getInt( "iu" ) );
                u.setName( rs.getString( "user" ) );
                
                p.setOrganization( o );
                p.setUser( u );
                
                planners.add( p );
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return planners;
    }
    
    @Override
    public Planner findPlannerCode( int id ) throws Exception
    {
        String sql = "select p.id_planner as id, p.name as name, p.description as description, p.date_register as date, o.name as org, u.name as user, o.id_organization as io, u.id_user as iu, p.progress as prog from planners p inner join organizations o on o.id_organization = p.id_organization inner join users u on u.id_user = p.id_user and p.state <> 'I' and p.id_planner = " + id;
        
        PreparedStatement ps;
        ResultSet rs;
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                Planner p = new Planner();
                
                Organization o = new Organization();
                
                User u = new User();
                
                p.setId( rs.getInt( "id" ) );
                p.setName( rs.getString( "name" ) );
                p.setDescription( rs.getString( "description" ) );
                p.setProgress( rs.getInt( "prog" ) );
                o.setId( rs.getInt( "io" ) );
                o.setName( rs.getString( "org" ) );
                u.setId( rs.getInt( "iu" ) );
                u.setName( rs.getString( "user" ) );
                
                p.setOrganization( o );
                p.setUser( u );
                
                return p;
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return null;
    }

    @Override
    public List<Planner> findPlannersName( String name ) throws Exception 
    {
        String sql = "select p.id_planner as id, p.name as name from planners p where p.state <> 'I' and p.name like '%" + name + "%'";
        
        PreparedStatement ps;
        ResultSet rs;
        
        List<Planner> planners = new ArrayList<>();
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                Planner p = new Planner();
                
                p.setId( rs.getInt( "id" ) );
                p.setName( rs.getString( "name" ) );
                
                planners.add( p );
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return planners;
    }

    @Override
    public void updateProgressPlanner( ConnectionFactory cf, Planner planner ) throws Exception 
    {
        String sql = "update planners set " + " progress = " + planner.getProgress() + " where id_planner = " + planner.getId();
        
        cf.executeQuery( sql );
    }

    @Override
    public List<Planner> reportPlannerState( int state ) throws Exception 
    {
        String sql = "select p.id_planner as id, p.name as name, p.description as description,  o.name as org, u.name as user from planners p inner join organizations o on o.id_organization = p.id_organization inner join users u on u.id_user = p.id_user and p.state <> 'I' and p.progress = " + state;
        
        PreparedStatement ps;
        ResultSet rs;
        
        List<Planner> planners = new ArrayList<>();
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                Planner p = new Planner();
                
                Organization o = new Organization();
                
                User u = new User();
                
                p.setId( rs.getInt( "id" ) );
                p.setName( rs.getString( "name" ) );
                p.setDescription( rs.getString( "description" ) );
                o.setName( rs.getString( "org" ) );
                u.setName( rs.getString( "user" ) );
                
                p.setOrganization( o );
                p.setUser( u );
                
                planners.add( p );
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return planners;
    }

    @Override
    public List<Task> findPlannerTask( int id ) throws Exception 
    {
        User user = com.am.planner.service.UserServiceManager.getInstance().getUserSession();
        
        String sql = "select t.id_task as id, t.name as name, t.description as description, t.date_start as start, t.date_finished as end, p.name as planner, t.progress as progress, p.id_planner as idp from tasks t, planners p where t.id_planner = p.id_planner and t.state <> 'I' and p.id_user = " + user.getId() + " and p.id_planner = " + id;
        
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
}