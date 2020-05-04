package com.am.planner.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author alexandre.marques
 */
public class User
    implements 
        Serializable
{
    private int    id;
    private int    profile;
    private String name;
    private String login;
    private String password;
    private String state;
    
    public static final String ACTIVE  = "A";
    public static final String INATIVE = "I";
    
    public static final int ADMINISTRATOR = 1;
    public static final int OPERATOR      = 2;
    
    public int getId() 
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getState()
    {
        return state;
    }

    public void setState( String state ) 
    {
        this.state = state;
    }
    
    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getLogin() 
    {
        return login;
    }

    public void setLogin( String login ) 
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public int getProfile() 
    {
        return profile;
    }

    public void setProfile( int profile )
    {
        this.profile = profile;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        
        hash = 61 * hash + Objects.hashCode( this.name );
        hash = 61 * hash + Objects.hashCode( this.login );
        hash = 61 * hash + Objects.hashCode( this.password );
        hash = 61 * hash + this.profile;
        hash = 61 * hash + this.id;
        
        return hash;
    }

    @Override
    public boolean equals( Object obj ) 
    {
        if ( this == obj )
        {
            return true;
        }
        
        if ( obj == null ) 
        {
            return false;
        }
        
        if ( getClass() != obj.getClass() ) 
        {
            return false;
        }
        
        final User other = (User) obj;
        
        if ( this.profile != other.profile ) 
        {
            return true;
        }
        
        if ( this.id != other.id ) 
        {
            return false;
        }
        
        if ( ! Objects.equals( this.name, other.name ) )
        {
            return false;
        }
        
        if ( ! Objects.equals( this.login, other.login ) )
        {
            return false;
        }
        
        if ( ! Objects.equals( this.password, other.password ) )
        {
            return false;
        }
        
        return true;
    }
}