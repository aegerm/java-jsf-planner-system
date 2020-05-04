package com.am.planner.bean;

import com.am.planner.dao.OrganizationManagerTransaction;
import com.am.planner.entity.Organization;
import com.am.planner.util.ConnectionFactory;
import com.am.planner.util.FaceMassageUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
public class OrganizationManagedBean
    implements
        Serializable
{
    private Organization        organization;
    private List<Organization>  organizations;
    private Date                dateStart;
    private Date                dateEnd;
    private String              name;

    public Organization getOrganization()
    {
        if( organization == null )
        {
            organization = new Organization();
        }
        
        return organization;
    }

    public void setOrganization( Organization organization )
    {
        this.organization = organization;
    }

    public List<Organization> getOrganizations() 
    {
        return organizations;
    }

    public void setOrganizations( List<Organization> organizations ) 
    {
        this.organizations = organizations;
    }

    public Date getDateStart()
    {
        return dateStart;
    }

    public void setDateStart( Date dateStart )
    {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() 
    {
        return dateEnd;
    }

    public void setDateEnd( Date dateEnd ) 
    {
        this.dateEnd = dateEnd;
    }

    public String getName() 
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    private java.sql.Date dateEnd() throws Exception
    {
        DateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );

        DateFormat formats = DateFormat.getDateInstance( DateFormat.MEDIUM );

        String dt = formats.format( dateEnd );
        
        java.util.Date dat = format.parse( dt );

        java.sql.Date sqlDate = new java.sql.Date( dat.getTime() );

        return sqlDate;
    }

    private java.sql.Date dateStart() throws Exception
    {
        DateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );

        DateFormat formats = DateFormat.getDateInstance( DateFormat.MEDIUM );

        String dt = formats.format( dateStart );

        java.util.Date dat = format.parse( dt );

        java.sql.Date sqlDate = new java.sql.Date( dat.getTime() );

        return sqlDate;
    }

    private java.sql.Date dateFunction() throws Exception
    {
        DateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );

        java.util.Date dates = new java.util.Date();

        DateFormat formats = DateFormat.getDateInstance( DateFormat.MEDIUM );

        String dt = formats.format( dates );

        java.util.Date dat = format.parse( dt );

        java.sql.Date sqlDate = new java.sql.Date( dat.getTime() );

        return sqlDate;
    }

    public void controllerDataOrganization() throws Exception
    {
        if( organization.getId() == 0 )
        {
            saveOrganization();
        }
        
        else
        {
            updateOrganization();
        }
    }

    public void saveOrganization() throws Exception
    {
        organization.setDateRegister( dateFunction() );
        organization.setState( Organization.ACTIVE );
        
        com.am.planner.util.ModuleContext
                           .getInstance()
                           .getOrganizationManager()
                           .addObject( organization );
        
        organization = new Organization();
        
        FaceMassageUtil.messageSucess( "Organização adicionada com sucesso!" );
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
        
        FacesContext.getCurrentInstance().getExternalContext().redirect( "OrganizationManager.xhtml" );
    }

    public void updateOrganization() throws Exception
    {
        organization.setDateRegister( dateFunction() );
        organization.setState( Organization.ACTIVE );
        
        com.am.planner.util.ModuleContext
                           .getInstance()
                           .getOrganizationManager()
                           .updateObject( organization );
        
        FaceMassageUtil.messageSucess( "Organização atualizada com sucesso!" );
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
        
        FacesContext.getCurrentInstance().getExternalContext().redirect( "OrganizationManager.xhtml" );
    }

    public void deleteOrganization() throws Exception
    {
        organization.setState( Organization.INATIVE );
        
        com.am.planner.util.ModuleContext
                           .getInstance()
                           .getOrganizationManager()
                           .deleteObject( organization );
        
        FaceMassageUtil.messageSucess( "Organização excluída com sucesso!" );
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
        
        FacesContext.getCurrentInstance().getExternalContext().redirect( "OrganizationManager.xhtml" );
    }

    public void listOrganizations() throws Exception
    {
        organizations = com.am.planner.util.ModuleContext
                                      .getInstance()
                                      .getOrganizationManager()
                                      .getListObject( name );
    }

    public void loadOrganization() throws Exception
    {
        String value = FaceMassageUtil.getParameter( "codeO" );
        
        if( value != null )
        {
            Integer code = Integer.parseInt( value );
            
            OrganizationManagerTransaction transaction = new OrganizationManagerTransaction();
            
            organization = transaction.findOrganization( code );
        }
    }

    public void printReport() throws IOException
    {
        try
        {
            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.responseComplete();
            
            JasperReport report = JasperCompileManager.compileReport( getClass().getResourceAsStream( "/reports/organizationReport.jrxml" ) );
            
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

                response.setHeader( "Content-disposition", "_blank; filename=\"RelatórioOrganização.pdf\"" );

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

    public void printReportParameter() throws Exception
    {
        try
        {
            Date start = dateStart();
            Date finished = dateEnd();
            
            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.responseComplete();
        
            Map<String, Object> param = new HashMap<>();
            
            param.put( "dts", start );
            param.put( "dtf", finished );
            
            JasperReport report = JasperCompileManager.compileReport( getClass().getResourceAsStream( "/reports/organizationReportParam.jrxml" ) );
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            JRPdfExporter exporter = new JRPdfExporter();
            
            JasperPrint print = JasperFillManager.fillReport( report, param, ConnectionFactory.connection() );

            exporter.setParameter( JRExporterParameter.JASPER_PRINT, print );
            exporter.setParameter( JRExporterParameter.OUTPUT_STREAM, baos );
            exporter.exportReport();

            byte[] bytes = baos.toByteArray();

            if ( bytes != null && bytes.length > 0 )
            {
                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

                response.setContentType( "application/pdf" );

                response.setHeader( "Content-disposition", "_blank; filename=\"RelatórioOrganizaçãoParametrizado.pdf\"" );

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