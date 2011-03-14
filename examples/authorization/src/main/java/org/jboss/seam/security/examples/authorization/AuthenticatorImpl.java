package org.jboss.seam.security.examples.authorization;

import javax.inject.Inject;

import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.UserImpl;

/**
 * 
 * @author Shane Bryzak
 *
 */
public class AuthenticatorImpl extends BaseAuthenticator implements Authenticator 
{
   @Inject Identity identity;
   @Inject Credentials credentials;
   
   @Override
   public void authenticate()
   {
      if ("demo".equals(credentials.getUsername()))
      {
         identity.addRole("admin", "USERS", "USER");
      }      
      
      // Let any user log in
      setStatus(AuthenticationStatus.SUCCESS);
      setUser(new UserImpl(credentials.getUsername()));
   }
}