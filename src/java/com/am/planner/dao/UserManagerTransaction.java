package com.am.planner.dao;

import com.am.planner.entity.EncryptSystem;
import com.am.planner.entity.User;
import com.am.planner.implement.UserImplements;
import com.am.planner.util.ConnectionFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexandre.marques
 */
public class UserManagerTransaction 
    implements 
        UserImplements
{
    @Override
    public void addUser( ConnectionFactory cf, User user ) throws Exception 
    {
        String sql = "insert into users ( name, login, password, state, profile )" + "values ( " +
                     cf.format( user.getName() )                                   + ", " +
                     cf.format( user.getLogin() )                                  + ", " +
                     cf.format( EncryptSystem.crypt( user.getPassword() ) )        + ", " +
                     cf.format( user.getState() )                                  + ", " +
                     user.getProfile()                                             + " )";
        
        cf.executeQuery( sql );
    }

    @Override
    public void updateUser( ConnectionFactory cf, User user ) throws Exception
    {
        String sql = "update users set " + " name     = " + cf.format( user.getName() )     + ", "
                                         + " login    = " + cf.format( user.getLogin() )    + ", "
                                         + " password = " + cf.format( EncryptSystem.crypt( user.getPassword() ) ) + ", "
                                         + " profile  = " + user.getProfile() +
                
                     " where id_user = " + user.getId();
        
        cf.executeQuery( sql );
    }

    @Override
    public void deleteUser( ConnectionFactory cf, User user ) throws Exception
    {
        String sql = "update users set state = " + cf.format( User.INATIVE ) + " where id_user = " + user.getId();
        
        cf.executeQuery( sql );
    }

    @Override
    public List<User> getUsers( String name ) throws Exception 
    {
        String sql = "select u.id_user as id, u.name as name, u.login as login from users u where u.state <> 'I'" + " and u.name like '%" + name + "%'";
        
        PreparedStatement ps;
        ResultSet rs;
        
        List<User> users = new ArrayList<>();
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                User u = new User();
                
                u.setId( rs.getInt( "id" ) );
                u.setName( rs.getString( "name" ) );
                u.setLogin( rs.getString( "login" ) );
                
                users.add( u );
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return users;
    }
    
    @Override
    public User getUserLogin( ConnectionFactory cf, String login, String password ) throws Exception
    {
        String sql = "select u.id_user as id, u.login, u.password from users u where u.login = " + cf.format( login ) + " and u.password = " + cf.format( password ) + " and u.state = 'A' ";
    
        PreparedStatement ps;
        ResultSet rs;
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                User u = new User();
                
                u.setId( rs.getInt( "id" ) );
                u.setLogin( rs.getString( "login" ) );
                u.setPassword( rs.getString( "password" ) );
                
                return u;
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return null;
    }
    
    @Override
    public User findUserCode( int id ) throws Exception
    {
        String sql = "select u.id_user as id, u.name as name, u.login as login, u.profile as profile from users u where u.id_user = " + id;
        
        PreparedStatement ps;
        ResultSet rs;
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                User u = new User();
                
                u.setId( rs.getInt( "id" ) );
                u.setName( rs.getString( "name" ) );
                u.setLogin( rs.getString( "login" ) );
                u.setProfile( rs.getInt( "profile" ) );
                
                return u;
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return null;
    }

    @Override
    public User getUserSession() throws Exception
    {
        String sql = "select s.id_user as id, u.name as name, u.profile as profile from users u, sessions s where u.id_user = s.id_user";
        
        PreparedStatement ps;
        ResultSet rs;
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                User u = new User();
                
                u.setId( rs.getInt( "id" ) );
                u.setName( rs.getString( "name" ) );
                u.setProfile( rs.getInt( "profile" ) );
                
                return u;
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return null;
    }

    @Override
    public void addSession( ConnectionFactory cf, User user ) throws Exception
    {
        String sql = "insert into sessions ( id_user )" + "values ( " + user.getId() + " )";
        
        cf.executeQuery( sql );
    }

    @Override
    public void deleteSession( ConnectionFactory cf, User user ) throws Exception 
    {
        String sql = "delete from sessions where id_user = " + user.getId();
        
        cf.executeQuery( sql );
    }

    @Override
    public List<User> findUsersName( String name ) throws Exception 
    {
        String sql = "select u.id_user as id, u.name as name from users u where u.state <> 'I' and u.name like '%" + name + "%'";
        
        PreparedStatement ps;
        ResultSet rs;
        
        List<User> users = new ArrayList<>();
        
        try
        {
            ps = ConnectionFactory.connection().prepareStatement( sql );
            rs = ps.executeQuery();
            
            while( rs.next() )
            {
                User u = new User();
                
                u.setId( rs.getInt( "id" ) );
                u.setName( rs.getString( "name" ) );
                
                users.add( u );
            }
        }
        
        catch( SQLException e )
        {
            throw new RuntimeException( e );
        }
        
        return users;
    }
}