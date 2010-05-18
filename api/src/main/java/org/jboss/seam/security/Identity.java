package org.jboss.seam.security;

import java.security.Principal;
import java.util.Collection;

import javax.security.auth.Subject;

/**
 * API for authorization and authentication via Seam security.
 * 
 * @author Shane Bryzak
 */
public interface Identity
{   
   /**
    * Simple check that returns true if the user is logged in, without attempting to authenticate
    * 
    * @return true if the user is logged in
    */
   boolean isLoggedIn();
   
   /**
    * Will attempt to authenticate quietly if the user's credentials are set and they haven't
    * authenticated already.  A quiet authentication doesn't throw any exceptions if authentication
    * fails.
    * 
    * @return true if the user is logged in, false otherwise
    */
   boolean tryLogin();

   /**
    * Return the currently authenticated Principal
    * 
    * @return
    */
   Principal getPrincipal();
   
   /**
    * Return the Subject for the current session.
    * 
    * @return
    */
   Subject getSubject();
      
   /**
    * Performs an authorization check, based on the specified security expression string.
    * 
    * @param expr The security expression string to evaluate
    * @throws NotLoggedInException Thrown if the authorization check fails and
    * the user is not authenticated
    * @throws AuthorizationException Thrown if the authorization check fails and
    * the user is authenticated
    */
   void checkRestriction(String expr);

   /**
    * Attempts to authenticate the user.  This method is distinct to the
    * authenticate() method in that it raises events in response to whether
    * authentication is successful or not.  The following events may be raised
    * by calling login():
    * 
    * org.jboss.seam.security.events.LoggedInEvent - raised when authentication is successful
    * org.jboss.seam.security.events.LoginFailedEvent - raised when authentication fails
    * org.jboss.seam.security.events.AlreadyLoggedInEvent - raised if the user is already authenticated
    * 
    * @return String returns "loggedIn" if user is authenticated, or null if not.
    */
   String login();
   
   /**
    * Attempts a quiet login, suppressing any login exceptions and not creating
    * any faces messages. This method is intended to be used primarily as an
    * internal API call, however has been made public for convenience.
    */
   void quietLogin();

   /**
    * Logs out the currently authenticated user
    */
   void logout();

   /**
    * Checks if the authenticated user is a member of the specified role.
    * 
    * @param role String The name of the role to check
    * @return boolean True if the user is a member of the specified role
    */
   boolean hasRole(String role, String group, String groupType);
   
   /**
    * Adds a role to the authenticated user.  If the user is not logged in,
    * the role will be added to a list of roles that will be granted to the
    * user upon successful authentication, but only during the authentication
    * process.
    * 
    * @param role The name of the role to add
    */
   boolean addRole(String role, String group, String groupType);
   
   /**   
    * Removes a role from the authenticated user
    * 
    * @param role The name of the role to remove
    */
   void removeRole(String role, String group, String groupType);
   
   /**
    * Checks that the current authenticated user is a member of
    * the specified role.
    * 
    * @param role String The name of the role to check
    * @throws AuthorizationException if the authenticated user is not a member of the role
    */
   void checkRole(String role, String group, String groupType);
   
   /**
    * Checks if the currently authenticated user can perform the specified action
    * on the specified target object.
    * 
    * @param target The target object for which the user wishes to perform a restricted action
    * @param action The action that the user wishes to perform
    * @throws NotLoggedInException if the current user is not authenticated
    * @throws AuthorizationException if the current user does not have the necessary
    * privileges to perform the specified action on the specified target object.   
    */
   void checkPermission(Object target, String action);
   
   /**
    * Filters a collection of objects by a specified action, by removing the 
    * objects from the collection for which the user doesn't have the necessary
    * privileges to perform the specified action against that object.
    * 
    * @param collection The Collection to filter
    * @param action The name of the action to filter by
    */
   void filterByPermission(Collection<?> collection, String action);
   
   /**
    * Checks if the currently authenticated user has the necessary privileges to perform the
    * specified action on the specified target object.
    * 
    * @param target  
    * @param action
    * @return true if the user has the required privileges, otherwise false
    */
   boolean hasPermission(Object target, String action);      
}