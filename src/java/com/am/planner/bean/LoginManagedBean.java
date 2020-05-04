package com.am.planner.bean;

import com.am.planner.entity.EncryptSystem;
import com.am.planner.entity.User;
import com.am.planner.util.FaceMassageUtil;
import com.am.planner.util.SessionUtil;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Marques
 */
@ManagedBean
@SessionScoped
public class LoginManagedBean
    implements
        Serializable
{
    private User user;

    public User getUser()
    {
       if( user == null )
       {
           user = new User();
       }
       
       return user;
    }

    public User access()
    {
        String login    = user.getLogin();
        String password = user.getPassword();
        
        try
        {
            user = com.am.planner.util.ModuleContext
                                 .getInstance()
                                 .getUserManager()
                                 .getUserLogin( login, EncryptSystem.crypt( password ) );
            
            if( user == null )
            {
                FaceMassageUtil.messageSucess( "Usuário ou senha inválidos!" );
                
                FacesContext.getCurrentInstance().validationFailed();
            }
            
            else
            {
                SessionUtil.getInstance().setAttribute( "userlogin", user );
                
                com.am.planner.service.UserServiceManager
                                      .getInstance()
                                      .addSession( user );
                
                FacesContext.getCurrentInstance().getExternalContext().redirect( "/PlannerSystem/faces/apps/Launcher.xhtml" );
            }
        }
        
        catch( Exception e )
        {
            throw new RuntimeException( e );
        }
        
        return user;
    }

    public void logout() throws Exception
    {
        SessionUtil.getInstance().finishedSession();
        
        try
        {
            com.am.planner.service.UserServiceManager
                                      .getInstance()
                                      .deleteSession( user );
            
            FacesContext.getCurrentInstance().getExternalContext().redirect( "/PlannerSystem/faces/Login.xhtml?faces-redirect=true" );
        }
        
        catch( IOException e )
        {
            throw new RuntimeException( e );
        }
    }

    public User getUserLogin()
    {
        SessionUtil util = SessionUtil.getInstance();
        
        return util.getUserLogin();
    }

    public boolean isAdministrator() throws Exception
    {
        User u = com.am.planner.service.UserServiceManager.getInstance().getUserSession();
        
        return u.getProfile() != User.OPERATOR;
    }
}