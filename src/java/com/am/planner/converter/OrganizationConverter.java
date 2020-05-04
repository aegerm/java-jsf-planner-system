package com.am.planner.converter;

import com.am.planner.dao.OrganizationManagerTransaction;
import com.am.planner.entity.Organization;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Marques
 */

@FacesConverter( forClass = Organization.class )
public class OrganizationConverter
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
                
                OrganizationManagerTransaction ot = new OrganizationManagerTransaction();
                
                return ot.findOrganization( code );
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
                Organization organization = (Organization)o;
                
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