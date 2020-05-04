package com.am.planner.implement;

import com.am.planner.entity.Organization;
import com.am.planner.util.ConnectionFactory;
import java.util.List;

/**
 *
 * @author Marques
 */
public interface OrganizationImplements
{
    public void addOrganization( ConnectionFactory cf, Organization organization ) throws Exception;
    public void updateOrganization( ConnectionFactory cf, Organization organization ) throws Exception;
    public void deleteOrganization( ConnectionFactory cf, Organization organization ) throws Exception;
    public List<Organization> getOrganizations( String name ) throws Exception;
    public Organization findOrganization( int id ) throws Exception;
    public List<Organization> findOrganizationsName( String name ) throws Exception;
}