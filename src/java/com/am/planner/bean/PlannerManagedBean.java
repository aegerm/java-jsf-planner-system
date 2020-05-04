package com.am.planner.bean;

import com.am.planner.dao.PlannerManagerTransaction;
import com.am.planner.entity.Organization;
import com.am.planner.entity.Planner;
import com.am.planner.entity.Task;
import com.am.planner.entity.User;
import com.am.planner.util.ConnectionFactory;
import com.am.planner.util.FaceMassageUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
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
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Marques
 */
@ManagedBean
@SessionScoped
public class PlannerManagedBean
    implements
        Serializable
{
    private Planner             planner;
    private List<Planner>       planners;
    private List<User>          filterUser;
    private List<Organization>  filterOrganization;
    private Date                dateStart;
    private Date                dateFinished;
    private String              name;
    private String              plannerFilter;
    private int                 state;

    public Planner getPlanner()
    {
        if( planner == null )
        {
            planner = new Planner();
        }
        
        return planner;
    }

    public int getState()
    {
        return state;
    }

    public void setState( int state )
    {
        this.state = state;
    }

    public void setPlanner( Planner planner )
    {
        this.planner = planner;
    }

    public List<Planner> getPlanners()
    {
        return planners;
    }

    public void setPlanners( List<Planner> planners ) 
    {
        this.planners = planners;
    }

    public Date getDateStart()
    {
        return dateStart;
    }

    public void setDateStart( Date dateStart )
    {
        this.dateStart = dateStart;
    }

    public List<User> getFilterUser() 
    {
        return filterUser;
    }

    public void setFilterUser( List<User> filterUser ) 
    {
        this.filterUser = filterUser;
    }

    public List<Organization> getFilterOrganization()
    {
        return filterOrganization;
    }

    public void setFilterOrganization( List<Organization> filterOrganization ) 
    {
        this.filterOrganization = filterOrganization;
    }

    public String getName() 
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getPlannerFilter() 
    {
        return plannerFilter;
    }

    public void setPlannerFilter( String plannerFilter )
    {
        this.plannerFilter = plannerFilter;
    }

    public Date getFinished()
    {
        return dateFinished;
    }

    public void setFinished( Date dateFinished )
    {
        this.dateFinished = dateFinished;
    }

    public void openDialogUser()
    {
        Map<String, Object> options = new HashMap<>();
        
        options.put( "modal", true );
        options.put( "resizable", false );
        options.put( "width", 800 );
        options.put( "height", 600 );
        options.put( "contentWidth", "100%" );
        options.put( "contentHeight", "100%" );
        
        RequestContext.getCurrentInstance().openDialog( "SelectorUser", options, null );
    }

    public void openDialogOrganization()
    {
        Map<String, Object> options = new HashMap<>();
        
        options.put( "modal", true );
        options.put( "resizable", false );
        options.put( "width", 800 );
        options.put( "height", 600 );
        options.put( "contentWidth", "100%" );
        options.put( "contentHeight", "100%" );
        
        RequestContext.getCurrentInstance().openDialog( "SelectorOrganization", options, null );
    }

    public void selectedUser( User user )
    {
        RequestContext.getCurrentInstance().closeDialog( user );
    }

    public void selectedOrganization( Organization organization )
    {
        RequestContext.getCurrentInstance().closeDialog( organization );
    }

    public void userSelected( SelectEvent event )
    {
        User u = (User) event.getObject();
        
        planner.setUser( u );
    }

    public void organizationSelected( SelectEvent event )
    {
        Organization o = (Organization) event.getObject();
        
        planner.setOrganization( o );
    }

    public void searchUsers() throws Exception
    {
        filterUser = com.am.planner.util.ModuleContext
                                        .getInstance()
                                        .getUserManager()
                                        .findUsersName( name );
                                        
    }
    
    public void searchOrganizations() throws Exception
    {
        filterOrganization = com.am.planner.util.ModuleContext
                                           .getInstance()
                                           .getOrganizationManager()
                                           .findOrganizationsName( name );
                                        
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
    
    public void controllerDataPlanner() throws Exception
    {
        if( planner.getId() == 0 )
        {
            savePlanner();
        }
        
        else
        {
            updatePlanner();
        }
    }
    
    public void savePlanner() throws Exception
    {
        planner.setProgress( 1 );
        planner.setDateRegister( dateFunction() );
        planner.setState( Planner.ACTIVE );
        planner.setUser( planner.getUser() );
        planner.setOrganization( planner.getOrganization() );
        
        com.am.planner.util.ModuleContext
                           .getInstance()
                           .getPlannerManager()
                           .addObject( planner );
        
        planner = new Planner();
        
        FaceMassageUtil.messageSucess( "Plano criado com sucesso!" );
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
        
        FacesContext.getCurrentInstance().getExternalContext().redirect( "HubPlanner.xhtml" );
    }
    
    public void updatePlanner() throws Exception
    {
        planner.setDateRegister( dateFunction() );
        planner.setState( Planner.ACTIVE );
        planner.setUser( planner.getUser() );
        planner.setOrganization( planner.getOrganization() );
        
        com.am.planner.util.ModuleContext
                           .getInstance()
                           .getPlannerManager()
                           .updateObject( planner );
        
        FaceMassageUtil.messageSucess( "Plano atualizado com sucesso!" );
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
        
        FacesContext.getCurrentInstance().getExternalContext().redirect( "HubPlanner.xhtml" );
    }
    
    public void deletePlanner() throws Exception
    {
        planner.setState( Planner.INATIVE );
        
        com.am.planner.util.ModuleContext
                           .getInstance()
                           .getPlannerManager()
                           .deleteObject( planner );
        
        FaceMassageUtil.messageSucess( "Plano excluído com sucesso!" );
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
        
        FacesContext.getCurrentInstance().getExternalContext().redirect( "HubPlanner.xhtml" );
    }
    
    public void listPlanners() throws Exception
    {
        planners = com.am.planner.util.ModuleContext
                                      .getInstance()
                                      .getPlannerManager()
                                      .getListObject( plannerFilter );
    }
    
    public void loadPlanners() throws Exception
    {
        String value = FaceMassageUtil.getParameter( "codeP" );
        
        if( value != null )
        {
            Integer code = Integer.parseInt( value );
            
            PlannerManagerTransaction transaction = new PlannerManagerTransaction();
            
            planner = transaction.findPlannerCode( code );
        }
    }
    
    private java.sql.Date dateEnd() throws Exception
    {
        DateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );

        DateFormat formats = DateFormat.getDateInstance( DateFormat.MEDIUM );

        String dt = formats.format( dateFinished );
        
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
    
    public void printReport() throws IOException
    {
        try
        {
            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.responseComplete();
            
            JasperReport report = JasperCompileManager.compileReport( getClass().getResourceAsStream( "/reports/plannerReport.jrxml" ) );
            
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

                response.setHeader( "Content-disposition", "_blank; filename=\"RelatórioPlanos.pdf\"" );

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
            
            JasperReport report = JasperCompileManager.compileReport( getClass().getResourceAsStream( "/reports/plannerReportParam.jrxml" ) );
            
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

                response.setHeader( "Content-disposition", "_blank; filename=\"RelatórioPlanoParametrizado.pdf\"" );

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
    
    public void printReportParameterState() throws Exception
    {
        try
        {
            Date start = dateStart();
            Date finished = dateEnd();
            
            int progress = state;
            
            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.responseComplete();
        
            Map<String, Object> param = new HashMap<>();
            
            param.put( "dts", start );
            param.put( "dtf", finished );
            param.put( "pro", progress );
            
            JasperReport report = JasperCompileManager.compileReport( getClass().getResourceAsStream( "/reports/plannerReportParamState.jrxml" ) );
            
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

                response.setHeader( "Content-disposition", "_blank; filename=\"RelatórioPlanoParametrizado.pdf\"" );

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
    
    public void defineStatePlanner() throws Exception
    {
        List<Task> tasks = com.am.planner.util.ModuleContext
                                              .getInstance()
                                              .getTaskManager()
                                              .getListTaskForPlanner( planner );
        
        List<Task> aux = new ArrayList<Task>();
        
        Iterator<Task> it = tasks.iterator();
        
        while( it.hasNext() )
        {
            Task t = it.next();
            
            if( t.getProgress() == 3 )
            {
                aux.add( t );
            }
        }
        
        for( Task tk : tasks )
        {
            if( tk.getProgress() == 3 )
            {
                aux.remove( tk );
            }
            
            else
            {
                aux.add( tk );
            }
        }
        
        if( aux.isEmpty() )
        {
            planner.setProgress( 2 );
            
            com.am.planner.util.ModuleContext.getInstance().getPlannerManager().updateProgressPlanner( planner );
            
            FaceMassageUtil.messageSucess( "Plano concluído com sucesso!" );
            
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
        
            FacesContext.getCurrentInstance().getExternalContext().redirect( "MonitorPlanner.xhtml" );
        }
        
        else
        {
            FaceMassageUtil.messageSucess( "Existem tarefas pendentes!" );
            
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
        
            FacesContext.getCurrentInstance().getExternalContext().redirect( "MonitorPlanner.xhtml" );
        }
    }
    
    public void exportExcel() throws IOException
    {
        List<Planner> lists = new ArrayList<>();
        
        try 
        {
            lists = com.am.planner.util.ModuleContext.getInstance().getPlannerManager().reportPlannerState( state );
        }
        
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet( "FirstSheet" );  

        HSSFRow rowhead = sheet.createRow( ( short ) 0 );
        rowhead.createCell( 0 ).setCellValue( "Código" );
        rowhead.createCell( 1 ).setCellValue( "Nome" );
        rowhead.createCell( 2 ).setCellValue( "Descrição" );
        rowhead.createCell( 3 ).setCellValue( "Organização" );
        rowhead.createCell( 4 ).setCellValue( "Responsável" );

        HSSFRow row = sheet.createRow( ( short ) 1 );
        
        for( Planner pl : lists )
        {
            row.createCell( 0 ).setCellValue( pl.getId() );
            row.createCell( 1 ).setCellValue( pl.getName() );
            row.createCell( 2 ).setCellValue( pl.getDescription() );
            row.createCell( 3 ).setCellValue( pl.getOrganization().getName() );
            row.createCell( 4 ).setCellValue( pl.getUser().getName() );
        }
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"Plano.xls\"");
        workbook.write( externalContext.getResponseOutputStream() );
        facesContext.responseComplete();
    }
}