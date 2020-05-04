package com.am.planner.implement;

import com.am.planner.entity.User;
import com.am.planner.util.ConnectionFactory;
import java.util.List;

/**
 *
 * @author alexandre.marques
 */
public interface UserImplements
{
    //Crud Módulo User
    public void addUser( ConnectionFactory connectionFactory, User user ) throws Exception;
    public void updateUser( ConnectionFactory connectionFactory, User user ) throws Exception;
    public void deleteUser( ConnectionFactory connectionFactory, User user ) throws Exception;
    public List<User> getUsers( String name ) throws Exception;
    public List<User> findUsersName( String name ) throws Exception;
    
    //Sistema de Login
    public User getUserLogin( ConnectionFactory cf, String login, String password ) throws Exception;
    public User findUserCode( int id ) throws Exception;
    
    //Buscar sessão
    public User getUserSession() throws Exception;
    public void addSession( ConnectionFactory connectionFactory, User user ) throws Exception;
    public void deleteSession( ConnectionFactory connectionFactory, User user ) throws Exception;
}
