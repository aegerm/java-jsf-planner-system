package com.am.planner.entity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Marques
 */
public class EncryptSystem 
{
    private static MessageDigest messageDigest = null;

    public static String crypt( String value )
    {
        try
        {
            if ( value == null )
            {
                return null;
            }

            messageDigest = MessageDigest.getInstance( "MD5" );
            messageDigest.update( value.getBytes(), 0, value.length() );

            return new BigInteger( 1, messageDigest.digest() ).toString( 16 );
        } 
        
        catch ( NoSuchAlgorithmException ex )
        {
            throw new RuntimeException( ex );
        }
    }
}