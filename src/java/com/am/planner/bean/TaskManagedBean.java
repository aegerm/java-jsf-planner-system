package com.am.planner.bean;

import com.am.planner.dao.PlannerManagerTransaction;
import com.am.planner.dao.TaskManagerTransaction;
import com.am.planner.entity.Planner;
import com.am.planner.entity.Task;
import com.am.planner.util.ConnectionFactory;
import com.am.planner.util.FaceMassageUtil;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class TaskManagedBean 
    implements 
        Serializable
{
    private Task            task;
    private List<Task>      tasks;
    private List<Planner>   filters;
    private Date            dateStart;
    private Date            dateEnd;
    private String          name;
    private String          taskFilter;
    private String          superFilter;
    private int             state;

    public Task getTask()
    {
        if( task == null )
        {
            task = new Task();
        }
        
        return task;
    }

    public int getState() 
    {
        return state;
    }

    public void setState( int state ) 
    {
        this.state = state;
    }
    
    public void setTask( Task task )
    {
        this.task = task;
    }

    public List<Task> getTasks()
    {
        return tasks;
    }

    public void setTasks( List<Task> tasks )
    {
        this.tasks = tasks;
    }

    public Date getDateEnd() 
    {
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

    public String getTaskFilter()
    {
        return taskFilter;
    }

    public void setTaskFilter( String taskFilter )
    {
        this.taskFilter = taskFilter;
    }

    public String getSuperFilter() 
    {
        return superFilter;
    }

    public void setSuperFilter( String superFilter )
    {
        this.superFilter = superFilter;
    }
    
    public List<Planner> getFilters()
    {
        return filters;
    }

    public void setFilters( List<Planner> filters ) 
    {
        this.filters = filters;
    }
    
    public void openDialog()
    {
        Map<String, Object> options = new HashMap<>();
        
        options.put( "modal", true );
        options.put( "resizable", false );
        options.put( "width", 800 );
        options.put( "height", 600 );
        options.put( "contentWidth", "100%" );
        options.put( "contentHeight", "100%" );
        
        RequestContext.getCurrentInstance().openDialog( "SelectorPlanner", options, null );
    }
    
    public void selectedPlanner( Planner planner )
    {
        RequestContext.getCurrentInstance().closeDialog( planner );
    }
    
    public void searchPlanner() throws Exception
    {
        filters = com.am.planner.util.ModuleContext
                                     .getInstance()
                                     .getPlannerManager()
                                     .findPlannersName( name );
    }
    
    public void searchTask() throws Exception
    {
        tasks = com.am.planner.util.ModuleContext
                                   .getInstance()
                                   .getTaskManager()
                                   .getListObject( taskFilter );
    }
    
    public void searchTaskSuper() throws Exception
    {
        tasks = com.am.planner.util.ModuleContext
                                   .getInstance()
                                   .getTaskManager()
                                   .getListObject( superFilter );
    }
    
    public void plannerSelected( SelectEvent event )
    {
        Planner p = (Planner) event.getObject();
        
        task.setPlanner( p );
    }
    
    public String getNamePlanner()
    {
        return task.getPlanner() == null ? null : task.getPlanner().getName();
    }
    
    public void controllerDataTask() throws Exception
    {
        if( task.getId() == 0 )
        {
            saveTask();
        }
        
        else
        {
            updateTask();
        }
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
    
    public void saveTask() throws Exception
    {
        if( dateEnd().before( dateStart() ) )
        {
            FaceMassageUtil.messageSucess( "Data final deve ser maior que a data inicial!" );
            return;
        }
        
        task.setDateStart( dateStart() );
        
        if( dateEnd != null )
        {
            task.setDateFinished( dateEnd() );
        }
        
        task.setState( Task.ACTIVE );
        task.setPlanner( task.getPlanner() );
        
        com.am.planner.util.ModuleContext
                           .getInstance()
                           .getTaskManager()
                           .addObject( task );
        
        task = new Task();
        
        FaceMassageUtil.messageSucess( "Tarefa criada com sucesso!" );
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
        
        FacesContext.getCurrentInstance().getExternalContext().redirect( "TaskManager.xhtml" );
    }
    
    public void updateTask() throws Exception
    {
        if( dateEnd().before( dateStart() ) )
        {
            FaceMassageUtil.messageSucess( "Data final deve ser maior que a data inicial!" );
            return;
        }
        
        task.setDateStart( dateStart() );
        
        if( dateEnd != null )
        {
            task.setDateFinished( dateEnd() );
        }
        
        task.setState( Task.ACTIVE );
        task.setPlanner( task.getPlanner() );
        
        com.am.planner.util.ModuleContext
                           .getInstance()
                           .getTaskManager()
                           .updateObject( task );
        
        FaceMassageUtil.messageSucess( "Tarefa atualizada com sucesso!" );
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
        
        FacesContext.getCurrentInstance().getExternalContext().redirect( "TaskManager.xhtml" );
    }
    
    public void updateProgress() throws Exception
    {
        com.am.planner.util.ModuleContext
                           .getInstance()
                           .getTaskManager()
                           .updateProgress( task );
        
        FaceMassageUtil.messageSucess( "Tarefa alimentada com sucesso!" );
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
        
        FacesContext.getCurrentInstance().getExternalContext().redirect( "MonitorTask.xhtml" );
    }
    
    public void deleteTask() throws Exception
    {
        task.setState( Task.INATIVE );
        
        com.am.planner.util.ModuleContext
                           .getInstance()
                           .getTaskManager()
                           .deleteObject( task );
        
        FaceMassageUtil.messageSucess( "Tarefa excluída com sucesso!" );
        
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
        
        FacesContext.getCurrentInstance().getExternalContext().redirect( "TaskManager.xhtml" );
    }
    
    public void listTasks() throws Exception
    {
        tasks = com.am.planner.util.ModuleContext
                                   .getInstance()
                                   .getTaskManager()
                                   .getListObject( taskFilter );
    }
    
    public void listTasksSuper() throws Exception
    {
        tasks = com.am.planner.util.ModuleContext
                                   .getInstance()
                                   .getTaskManager()
                                   .getListObjectSuper( superFilter );
    }
    
    public void loadTasks() throws Exception
    {
        String value = FaceMassageUtil.getParameter( "codeT" );
        
        if( value != null )
        {
            Integer code = Integer.parseInt( value );
            
            TaskManagerTransaction transaction = new TaskManagerTransaction();
            
            task = transaction.findTaskCode( code );
            
            if( task != null )
            {
                setDateStart( task.getDateStart() );
                setDateEnd( task.getDateFinished() );
            }
            
            else
            {
                setDateStart( null );
                setDateEnd( null );
            }
        }
    }
    
    public void printReport() throws IOException
    {
        try
        {
            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.responseComplete();
            
            JasperReport report = JasperCompileManager.compileReport( getClass().getResourceAsStream( "/reports/taskReport.jrxml" ) );
            
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

                response.setHeader( "Content-disposition", "_blank; filename=\"RelatórioTarefas.pdf\"" );

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
            
            JasperReport report = JasperCompileManager.compileReport( getClass().getResourceAsStream( "/reports/taskReportParam.jrxml" ) );
            
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

                response.setHeader( "Content-disposition", "_blank; filename=\"RelatórioTarefaParametrizado.pdf\"" );

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
    
    public void printReportParameterDateProgress() throws Exception
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
            param.put( "prog", progress );
            
            JasperReport report = JasperCompileManager.compileReport( getClass().getResourceAsStream( "/reports/taskReportParamDateProg.jrxml" ) );
            
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

                response.setHeader( "Content-disposition", "_blank; filename=\"RelatórioTarefaParametrizado.pdf\"" );

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
    
    public void printReportParameterProgressPlan() throws Exception
    {
        try
        {
            int id = task.getPlanner().getId();
            int progress = state;
            
            FacesContext facesContext = FacesContext.getCurrentInstance();

            facesContext.responseComplete();
        
            Map<String, Object> param = new HashMap<>();
            
            param.put( "plan", id );
            param.put( "prog", progress );
            
            JasperReport report = JasperCompileManager.compileReport( getClass().getResourceAsStream( "/reports/taskReportParamProgPlan.jrxml" ) );
            
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

                response.setHeader( "Content-disposition", "_blank; filename=\"RelatórioTarefaParametrizado.pdf\"" );

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
    
    public void exportExcel() throws IOException
    {
        List<Task> lists = new ArrayList<>();
        
        try 
        {
            lists = com.am.planner.util.ModuleContext.getInstance().getTaskManager().getTasksForReport( state );
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
        rowhead.createCell( 3 ).setCellValue( "Data Inicío" );
        rowhead.createCell( 4 ).setCellValue( "Data Fim" );
        rowhead.createCell( 5 ).setCellValue( "Plano" );

        HSSFRow row = sheet.createRow( ( short ) 1 );
        
        for( Task tk : lists )
        {
            row.createCell( 0 ).setCellValue( tk.getId() );
            row.createCell( 1 ).setCellValue( tk.getName() );
            row.createCell( 2 ).setCellValue( tk.getDescription() );
            row.createCell( 3 ).setCellValue( tk.getDateStart() );
            row.createCell( 4 ).setCellValue( tk.getDateFinished() );
            row.createCell( 5 ).setCellValue( tk.getPlanner().getName() );
        }
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"Tarefa.xls\"");
        workbook.write( externalContext.getResponseOutputStream() );
        facesContext.responseComplete();
    }
    
    public void loadPlannersTasks() throws Exception
    {
        String value = FaceMassageUtil.getParameter( "codeP" );
        
        if( value != null )
        {
            Integer code = Integer.parseInt( value );
            
            PlannerManagerTransaction transaction = new PlannerManagerTransaction();
            
            tasks = transaction.findPlannerTask( code );
        }
    }
}