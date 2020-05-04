package com.am.planner.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Marques
 */
public class Organization
    implements 
        Serializable
{
    private int    id;
    private String state;
    private String name;
    private String description;
    private Date   dateRegister;
    private String city;
    private String address;
    private int    uf;
    
    public static final String ACTIVE  = "A";
    public static final String INATIVE = "I";
    
    public static final String UF[] =
    {
        "",
        "AC",
        "AL",
        "AP",
        "AM",
        "BA",
        "CE",
        "DF",
        "ES",
        "GO",
        "MA",
        "MT",
        "MS",
        "MG",
        "PA",
        "PB",
        "PR",
        "PE",
        "PI",
        "RJ",
        "RN",
        "RS",
        "RO",
        "RR",
        "SC",
        "SP",
        "SE",
        "TO"
    };
    
    public String getUf( int param )
    {
        return UF[param];
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

    public String getCity()
    {
        return city;
    }

    public void setCity( String city )
    {
        this.city = city;
    }

    public String getAddress() 
    {
        return address;
    }

    public void setAddress( String address ) 
    {
        this.address = address;
    }

    public int getUf() 
    {
        return uf;
    }

    public void setUf( int uf ) 
    {
        this.uf = uf;
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode( this.state );
        hash = 47 * hash + Objects.hashCode( this.name );
        hash = 47 * hash + Objects.hashCode( this.description );
        hash = 47 * hash + Objects.hashCode( this.dateRegister );
        hash = 47 * hash + Objects.hashCode( this.city );
        hash = 47 * hash + Objects.hashCode( this.address );
        hash = 47 * hash + this.uf;
        
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
        
        final Organization other = (Organization) obj;
        
        if ( this.id != other.id ) 
        {
            return false;
        }
        
        if ( this.uf != other.uf )
        {
            return true;
        }
        
        if ( ! Objects.equals( this.state, other.state ) ) 
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
        
        if ( ! Objects.equals( this.city, other.city ) )
        {
            return false;
        }
        
        if ( ! Objects.equals( this.address, other.address ) )
        {
            return false;
        }
        
        if ( ! Objects.equals( this.dateRegister, other.dateRegister ) )
        {
            return false;
        }
        
        return true;
    }
}