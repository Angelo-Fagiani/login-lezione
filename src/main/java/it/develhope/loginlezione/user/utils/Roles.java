package it.develhope.loginlezione.user.utils;

import it.develhope.loginlezione.user.entities.User;

public class Roles {

    public final static String REGISTERED="REGISTERED";
    public final static String RESTAURANT="RESTAURANT";
    public final static String RIDER="RIDER";

    public static boolean hasRole(User user,String roleInput){
        return user.getRoles().stream().filter(role -> role.getName().equals(roleInput)).count() != 0;
    }


    /*public final static String EDITOR="EDITOR";
    public final static String ADMIN="ADMIN";
    public final static String VIEWER="VIEWER";
    public static final String OWNER = "OWNER";
    public static final String SUPER_ADMIN = "SUPER_ADMIN";*/
}
