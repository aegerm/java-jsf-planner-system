package com.am.planner.implement;

import com.am.planner.entity.Planner;
import com.am.planner.entity.Task;
import com.am.planner.util.ConnectionFactory;
import java.util.List;

/**
 *
 * @author Marques
 */
public interface PlannerImplements 
{
    public void addPlanner( ConnectionFactory connectionFactory, Planner planner ) throws Exception;
    public void updatePlanner( ConnectionFactory connectionFactory, Planner planner ) throws Exception;
    public void updateProgressPlanner( ConnectionFactory cf, Planner planner ) throws Exception;
    public void deletePlanner( ConnectionFactory connectionFactory, Planner planner ) throws Exception;
    public List<Planner> getPlanners( String name ) throws Exception;
    public Planner findPlannerCode( int id ) throws Exception;
    public List<Planner> findPlannersName( String name ) throws Exception;
    public List<Planner> reportPlannerState( int state ) throws Exception;
    public List<Task> findPlannerTask( int id ) throws Exception;
}
