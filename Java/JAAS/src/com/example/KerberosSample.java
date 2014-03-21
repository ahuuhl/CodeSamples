package com.example;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.AccessControlException;
import java.security.PrivilegedAction;
import java.util.Properties;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

class ClientAction implements PrivilegedAction<Object> {

   @Override
   public Object run() {
      System.err.println("Executing privileged action ...");
      
      File f = new File("login.config");
      if (f.exists()) {
         System.err.println("File exists, reading file ...");
      }

      return null;
   }
   
}

class TextCallbackHandler implements CallbackHandler {

   private BufferedReader input = null;
   private Console cons = null;

   public TextCallbackHandler() {
      // set either cons or input
      cons = System.console();
      if (cons == null) {
         input = new BufferedReader(new InputStreamReader(System.in));
      }
   }

   private char[] readPassword() {
      if (cons != null) {
         return cons.readPassword();
      } else {
         try {
            return input.readLine().toCharArray();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      
      return new char[0];
   }

   private String readLine() {
      if (cons != null) {
         return cons.readLine();
      } else {
         try {
            return input.readLine();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      
      return "";
   }

   @Override
   public void handle(Callback[] arg0) throws IOException,
         UnsupportedCallbackException {
      
      for (Callback callback : arg0) {
         if (callback instanceof NameCallback) {
            NameCallback nc = (NameCallback) callback;
            System.out.print(nc.getPrompt());
            String name = readLine();
            if (name.length() > 0) {
               nc.setName(name);
            }
         } else if (callback instanceof PasswordCallback) {
            PasswordCallback pc = (PasswordCallback) callback;
            System.out.print(pc.getPrompt());
            char[] pwd = readPassword();
            pc.setPassword(pwd);
         } else {
            System.out.println(callback);
            throw new UnsupportedCallbackException(callback, "Handler");
         }
      }
   }
}

/*
 * http://docs.oracle.com/javase/7/docs/jre/api/security/jaas/spec/com/sun/security/auth/module/Krb5LoginModule.html
 * http://docs.oracle.com/javase/7/docs/technotes/guides/security/jgss/single-signon.html
 * http://docs.oracle.com/javase/7/docs/technotes/guides/security/jgss/tutorials/index.html
 * http://docs.oracle.com/javase/7/docs/technotes/guides/security/jgss/tutorials/KerberosReq.html
 */
public class KerberosSample {
   
   public static void main(String[] args) {
      // set the name of our local policy file - can also be passed  
      // when launching the JVM through -D
      System.setProperty("java.security.policy", "=local.policy");

      // install a security manager - uses the policy file set through java.security.policy
      System.setSecurityManager(new SecurityManager());
      
      // some (non-sensitive) properties are still readable
      String osName = System.getProperty("os.name");
      System.err.println("OS Name: " + osName);

      // Others can not be read anymore
      try {
         String loginConfig = System.getProperty("java.security.auth.login.config");
         System.err.println("Login config: " + loginConfig);
      }catch(AccessControlException e) {
         System.err.println("Error: " + e.getMessage());
      }

      // Load security related system properties from property file
      Properties p = System.getProperties();
      try {
         p.load(new FileInputStream("security.properties"));
      } catch (IOException e) {
         e.printStackTrace();
      }
      System.setProperties(p);

      LoginContext lc = null;
      try {
        lc = new LoginContext("KerberosLogin", new TextCallbackHandler());

        // attempt authentication
        System.err.println("Logging in ...");
        lc.login();
        System.err.println("Logged in.");
      } catch (LoginException le) {
         le.printStackTrace();
      }

       // Now try to execute ClientAction as the authenticated Subject

       Subject mySubject = lc.getSubject();
       System.out.println(mySubject);

       PrivilegedAction<?> action = new ClientAction();
       Subject.doAs(mySubject, action);
   }
}