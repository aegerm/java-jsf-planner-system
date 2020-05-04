package com.am.planner.util;

import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Marques
 */
public class FaceMassageUtil
{
    public static void messageSucess( String message )
    {
        FacesMessage faces = new FacesMessage( FacesMessage.SEVERITY_INFO, message, message );
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage( null, faces );
    }
    
    public static void messageError( String message )
    {
        FacesMessage faces = new FacesMessage( FacesMessage.SEVERITY_ERROR, message, message );
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage( null, faces );
    }
    
    public static String getParameter( String param )
    {
        FacesContext context = FacesContext.getCurrentInstance();
        
        ExternalContext externalContext = context.getExternalContext();
        
        Map<String, String> map = externalContext.getRequestParameterMap();
        
        String value = map.get( param );
        
        return value;
    }
}