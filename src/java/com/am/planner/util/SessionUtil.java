package com.am.planner.util;

import com.am.planner.entity.User;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Marques
 */
public class SessionUtil 
{
    private static SessionUtil instance;
    
    public static SessionUtil getInstance()
    {
        if( instance == null )
        {
            instance = new SessionUtil();
        }
        
        return instance;
    }

    public SessionUtil() 
    {
    }
    
    private ExternalContext currentExternalContext()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        if( context == null )
        {
            throw new RuntimeException( "Chamada externa realizada!" );
        }
        
        else
        {
            return context.getExternalContext();
        }
    }
    
    public User getUserLogin()
    {
        return (User) getAttribute( "usuariologin" );
    }
    
    public void setUserLogin( User user )
    {
        setAttribute( "userlogin", user );
    }
    
    public void finishedSession()
    {
        currentExternalContext().invalidateSession();
    }
    
    public Object getAttribute( String name )
    {
        return currentExternalContext().getSessionMap().get( name );
    }
    
    public void setAttribute( String name, Object value )
    {
        currentExternalContext().getSessionMap().put( name, value );
    }
}