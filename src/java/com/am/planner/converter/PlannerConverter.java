package com.am.planner.converter;

import com.am.planner.dao.PlannerManagerTransaction;
import com.am.planner.entity.Planner;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Marques
 */

@FacesConverter( forClass = Planner.class )
public class PlannerConverter
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
                
                PlannerManagerTransaction p = new PlannerManagerTransaction();
                
                return p.findPlannerCode( code );
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
                Planner organization = (Planner)o;
                
                return String.valueOf( organization.getId() );
            }
        }
        
        catch( Exception e )
        {
            throw new RuntimeException( e );
        }
        
        return null;
    }
}