package com.am.planner.dao;

import com.am.planner.entity.Organization;
import com.am.planner.implement.OrganizationImplements;
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
public class OrganizationManagerTransaction 
    implements 
        OrganizationImplements
{
    @Override
    public void addOrganization( ConnectionFactory cf, Organization organization ) throws Exception 
    {
        String sql = "insert into organizations ( name, description, date_register, address, city, state, uf )" + "values ( " +
                     cf.format( organization.getName() )                + ", " +
                     cf.format( organization.getDescription() )         + ", " +
                     cf.formatDate( organization.getDateRegister() )    + ", " +
                     cf.format( organization.getAddress() )             + ", " +
                     cf.format( organization.getCity() )                + ", " +
                     cf.format( organization.getState() )               + ", " +
                     organization.getUf()                               + " )";
        
        cf.executeQuery( sql );
    }

    @Override
    public void updateOrganization( ConnectionFactory cf, Organization organization ) throws Exception 
    {
        String sql = "update organizations set " + "name = "        + cf.format( organization.getName() )           + ", "
                                                 + "description = " + cf.format( organization.getDescription() )    + ", "
                                                 + "address = "     + cf.format( organization.getAddress() )        + ", "
                                                 + "city = "        + cf.format( organization.getCity() )           + ", "
                                                 + "uf = "          + organization.getUf()                          +
                
                     " where id_organization = " + organization.getId();
        
        cf.executeQuery( sql );
    }

    @Override
    public void deleteOrganization( ConnectionFactory cf, Organization organization ) throws Exception 
    {
        String sql = "update organizations set state = " + cf.format( Organization.INATIVE ) + " where id_organization = " + organization.getId();
        
        cf.executeQuery( sql );
    }

    @Override
    public List<Organization> getOrganizations( String name ) throws Exception 
    {
        String sql = "select o.id_organization as id, o.name as name, o.description as descr, o.address as address, o.city as city, o.uf as uf from organizations o where o.state <> 'I'" + " and o.name like '%" + name + "%'";;
        
        PreparedStatement ps;
        ResultSet rs;
        
        List<Organization> organizations = new ArrayList<>();
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                Organization o = new Organization();
                
                o.setId( rs.getInt( "id" ) );
                o.setName( rs.getString( "name" ) );
                o.setDescription( rs.getString( "descr" ) );
                o.setAddress( rs.getString( "address" ) );
                o.setCity( rs.getString( "city" ) );
                o.setUf( rs.getInt( "uf" ) );
                
                organizations.add( o );
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return organizations;
    }
    
    @Override
    public Organization findOrganization( int id ) throws Exception 
    {
        String sql = "select o.id_organization as id, o.name as name, o.description as descr, o.address as address, o.city as city, o.uf as uf from organizations o where o.state <> 'I' and o.id_organization = " + id;
        
        PreparedStatement ps;
        ResultSet rs;
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                Organization o = new Organization();
                
                o.setId( rs.getInt( "id" ) );
                o.setName( rs.getString( "name" ) );
                o.setDescription( rs.getString( "descr" ) );
                o.setAddress( rs.getString( "address" ) );
                o.setCity( rs.getString( "city" ) );
                o.setUf( rs.getInt( "uf" ) );
                
                return o;
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return null;
    }

    @Override
    public List<Organization> findOrganizationsName( String name ) throws Exception
    {
        String sql = "select o.id_organization as id, o.name as name from organizations o where o.state <> 'I' and o.name like '%" + name + "%'";
        
        PreparedStatement ps;
        ResultSet rs;
        
        List<Organization> organizations = new ArrayList<>();
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                Organization o = new Organization();
                
                o.setId( rs.getInt( "id" ) );
                o.setName( rs.getString( "name" ) );
                
                organizations.add( o );
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return organizations;
    }
}