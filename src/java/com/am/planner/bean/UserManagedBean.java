package com.am.planner.bean;

import com.am.planner.dao.UserManagerTransaction;
import com.am.planner.entity.User;
import com.am.planner.util.ConnectionFactory;
import com.am.planner.util.FaceMassageUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 *
 * @author Marques
 */
@ManagedBean
@SessionScoped
public class UserManagedBean 
    implements 
        Serializable
{
    private User        user;
    private List<User>  users;
    private Date        dateStart;
    private Date        dateEnd;
    private String      name;

    public User getUser()
    {
       if( user == null )
       {
           user = new User();
       }
       
       return user;
    }

    public void setUser( User user )
    {
        this.user = user;
    }

    public List<User> getUsers()
    {
        return users;
    }

    public void setUsers( List<User> users )
    {
        this.users = users;
    }
    
    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd( Date dateSelect )
    {
        this.dateEnd = dateSelect;
    }

    public Date getDateStart()
    {
        return dateStart;
    }

    public void setDateStart( Date dateStart )
    {
        this.dateStart = dateStart;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name ) 
    {
        this.name = name;
    }
    
    public void controllerData() throws Exception
    {
        if( user.getId() == 0 )
        {
            saveUser();
        }
        
        else
        {
            updateUser();
        }
    }
    
    public void saveUser() throws Exception
    {
        user.setState( User.ACTIVE );
            
        com.am.planner.util.ModuleContext
                           .getInstance()
                           .getUserManager()
                           .addObject( user );
        
        user = new User();
        
        FaceMassageUtil.messageSucess( "Usuário salvo com sucesso!" );
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
        
        FacesContext.getCurrentInstance().getExternalContext().redirect( "UserManager.xhtml" );
    }
    
    public void saveUserLogin() throws Exception
    {
        user.setState( User.ACTIVE );
            
        com.am.planner.util.ModuleContext
                           .getInstance()
                           .getUserManager()
                           .addObject( user );
        
        user = new User();
        
        FaceMassageUtil.messageSucess( "Usuário salvo com sucesso!" );
    }
    
    public void updateUser() throws Exception
    {
        user.setState( User.ACTIVE );
            
        com.am.planner.util.ModuleContext
                           .getInstance()
                           .getUserManager()
                           .updateObject( user );
            
        FaceMassageUtil.messageSucess( "Usuário atualizado com sucesso!" );
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
        
        FacesContext.getCurrentInstance().getExternalContext().redirect( "UserManager.xhtml" );
    }
    
    public void deleteUser() throws Exception
    {
        user.setState( User.INATIVE );
            
        com.am.planner.util.ModuleContext
                           .getInstance()
                           .getUserManager()
                           .deleteObject( user );
        
        FaceMassageUtil.messageSucess( "Usuário excluído com sucesso!" );
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
        
        FacesContext.getCurrentInstance().getExternalContext().redirect( "UserManager.xhtml" );
    }
    
    public void listUsers() throws Exception
    {
        users = com.am.planner.util.ModuleContext
                              .getInstance()
                              .getUserManager()
                              .getListObject( name );
    }
    
    public void loadUsers() throws Exception
    {
        String value = FaceMassageUtil.getParameter( "codeU" );
        
        if( value != null )
        {
            Integer code = Integer.parseInt( value );
            
            UserManagerTransaction transaction = new UserManagerTransaction();
            
            user = transaction.findUserCode( code );
        }
    }
    
    public void printReport() throws IOException
    {
        try
        {
            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.responseComplete();
            
            JasperReport report = JasperCompileManager.compileReport( getClass().getResourceAsStream( "/reports/userReport.jrxml" ) );
            
            Map<String, Object> param = new HashMap<>();
            
            JasperPrint print = JasperFillManager.fillReport( report, param, ConnectionFactory.connection() );
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setParameter( JRExporterParameter.JASPER_PRINT, print );
            exporter.setParameter( JRExporterParameter.OUTPUT_STREAM, baos );
            exporter.exportReport();

            byte[] bytes = baos.toByteArray();

            if ( bytes != null && bytes.length > 0 )
            {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

                response.setContentType( "application/pdf" );

                response.setHeader( "Content-disposition", "_blank; filename=\"RelatórioUsuário.pdf\"" );

                response.setContentLength( bytes.length );

                ServletOutputStream outputStream = response.getOutputStream();

                outputStream.write( bytes, 0, bytes.length );

                outputStream.flush();

                outputStream.close();
            }
        }
        
        catch( JRException e )
        {
            throw new RuntimeException( e );
        }
    }
}