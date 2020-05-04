package com.am.planner.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Marques
 */
public class Planner
    implements 
        Serializable
{
    private int             id;
    private String          name;
    private String          description;
    private String          state;
    private Date            dateRegister;
    private User            user;
    private Organization    organization;
    private int             progress;
    
    public static final String ACTIVE  = "A";
    public static final String INATIVE = "I";
    
    public static final String PROGRESS[] = 
    {
        "Default",
        "Andamento",
        "Concluído"
    };

    public Planner() 
    {
        user = new User();
        organization = new Organization();
    }
    
    public String getState( int param )
    {
        return PROGRESS[param];
    }

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

    public String getDescription() 
    {
        return description;
    }

    public void setDescription( String description ) 
    {
        this.description = description;
    }

    public Date getDateRegister() 
    {
        return dateRegister;
    }

    public void setDateRegister( Date dateRegister ) 
    {
        this.dateRegister = dateRegister;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser( User user )
    {
        this.user = user;
    }

    public Organization getOrganization() 
    {
        return organization;
    }

    public void setOrganization( Organization organization )
    {
        this.organization = organization;
    }

    public int getProgress()
    {
        return progress;
    }

    public void setProgress( int progress ) 
    {
        this.progress = progress;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        
        hash = 61 * hash + this.id;
        hash = 61 * hash + Objects.hashCode( this.name );
        hash = 61 * hash + Objects.hashCode( this.description );
        hash = 61 * hash + Objects.hashCode( this.state );
        hash = 61 * hash + Objects.hashCode( this.dateRegister );
        hash = 61 * hash + Objects.hashCode( this.user );
        hash = 61 * hash + Objects.hashCode( this.organization );
        hash = 61 * hash + Objects.hashCode( this.progress );
        
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
        
        final Planner other = (Planner) obj;
        
        if ( this.id != other.id ) 
        {
            return false;
        }
        
        if ( ! Objects.equals( this.name, other.name ) ) 
        {
            return false;
        }
        
        if ( ! Objects.equals( this.description, other.description ) )
        {
            return false;
        }
        
        if ( ! Objects.equals( this.state, other.state ) )
        {
            return false;
        }
        
        if ( ! Objects.equals( this.dateRegister, other.dateRegister ) )
        {
            return true;
        }
        
        if ( ! Objects.equals( this.user, other.user ) )
        {
            return false;
        }
        
        if ( ! Objects.equals( this.organization, other.organization ) ) 
        {
            return false;
        }
        
        if ( ! Objects.equals( this.progress, other.progress ) ) 
        {
            return false;
        }
        
        return true;
    }
}