package com.am.planner.util;

import com.am.planner.service.OrganizationServiceManager;
import com.am.planner.service.PlannerServiceManager;
import com.am.planner.service.TaskServiceManager;
import com.am.planner.service.UserServiceManager;


/**
 *
 * @author Marques
 */
public class ModuleContext
{
    private static ModuleContext instance;
    
    public static ModuleContext getInstance()
    {
        if( instance == null )
        {
            instance = new ModuleContext();
        }
        
        return instance;
    }
    
    public UserServiceManager getUserManager()
    {
        return UserServiceManager.getInstance();
    }
    
    public PlannerServiceManager getPlannerManager()
    {
        return PlannerServiceManager.getInstance();
    }
    
    public OrganizationServiceManager getOrganizationManager()
    {
        return OrganizationServiceManager.getInstance();
    }
    
    public TaskServiceManager getTaskManager()
    {
        return TaskServiceManager.getInstance();
    }
}