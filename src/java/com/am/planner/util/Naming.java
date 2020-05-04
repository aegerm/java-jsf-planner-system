package com.am.planner.util;

/**
 *
 * @author Marques
 */
public class Naming
{
    private static final String SYSTEM = "Windows";
    private static final String DISK   = "C:\\configuration.properties";
    private static final String HOME   = "";

    public Naming() 
    {
    }
    
    public static String getSystemProperties()
    {
        if( System.getProperties().get( "os.name" ).toString().contains( SYSTEM ) )
        {
            return DISK;
        }
        
        else
        {
            return HOME;
        }
    }
}