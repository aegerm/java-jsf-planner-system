package com.am.planner.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Marques
 */
public class Task
    implements 
        Serializable
{
    private int     id;
    private String  name;
    private String  description;
    private String  state;
    private Date    dateStart;
    private Date    dateFinished;
    private int     progress;
    private Planner planner;
    
    public static final String ACTIVE  = "A";
    public static final String INATIVE = "I";
    
    public static final String STATES[] =
    {
        "",
        "Não iniciado",
        "Em andamento",
        "Concluída"
    };

    public Task() 
    {
        planner = new Planner();
    }
    
    public String getState( int param )
    {
        return STATES[param];
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

    public Date getDateStart()
    {
        return dateStart;
    }

    public void setDateStart( Date dateStart )
    {
        this.dateStart = dateStart;
    }

    public Date getDateFinished() 
    {
        return dateFinished;
    }

    public void setDateFinished( Date dateFinished ) 
    {
        this.dateFinished = dateFinished;
    }

    public int getProgress()
    {
        return progress;
    }

    public void setProgress( int progress ) 
    {
        this.progress = progress;
    }

    public Planner getPlanner() 
    {
        return planner;
    }

    public void setPlanner( Planner planner ) 
    {
        this.planner = planner;
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode( this.name );
        hash = 53 * hash + Objects.hashCode( this.description );
        hash = 53 * hash + Objects.hashCode( this.state );
        hash = 53 * hash + Objects.hashCode( this.dateStart );
        hash = 53 * hash + Objects.hashCode( this.dateFinished );
        hash = 53 * hash + this.progress;
        hash = 53 * hash + Objects.hashCode( this.planner );
        
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
        
        final Task other = (Task) obj;
        
        if ( this.id != other.id ) 
        {
            return false;
        }
        
        if ( this.progress != other.progress ) 
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
        
        if ( ! Objects.equals( this.dateStart, other.dateStart ) ) 
        {
            return false;
        }
        
        if ( ! Objects.equals( this.dateFinished, other.dateFinished ) )
        {
            return false;
        }
        
        if ( ! Objects.equals( this.planner, other.planner ) ) 
        {
            return false;
        }
        
        return true;
    }
}