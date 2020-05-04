package com.am.planner.converter;

import com.am.planner.dao.UserManagerTransaction;
import com.am.planner.entity.User;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Marques
 */

@FacesConverter( forClass = User.class )
public class UserConverter 
    implements 
        Converter
{
    @Override
    public Object getAsObject( FacesContext fc, UIComponent uic, String id )
    {
        try
        {
            if( id != null && id.trim().length() > 0 )
            {
                Integer code = Integer.valueOf( id );
                
                UserManagerTransaction ut = new UserManagerTransaction();
                
                return ut.findUserCode( code );
            }
        }
        
        catch( Exception e )
        {
            throw new RuntimeException( e );
        }
        
        return null;
    }

    @Override
    public String getAsString( FacesContext fc, UIComponent uic, Object o ) 
    {
        try
        {
            if( o != null )
            {
                User user = (User)o;
                
                return String.valueOf( user.getId() );
            }
        }
        
        catch( Exception e )
        {
            throw new RuntimeException( e );
        }
        
        return null;
    }
}