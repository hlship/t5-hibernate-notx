package com.howardlewisship.notx.services;

import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.apache.tapestry5.ioc.services.ThreadCleanupListener;
import org.hibernate.Session;

public class NoTxHibernateSessionManager implements HibernateSessionManager, ThreadCleanupListener {

  private final Session session;

  public NoTxHibernateSessionManager(HibernateSessionSource source) {
    session = source.create();
  }

  private static void invalid(String name) {
    throw new IllegalStateException(String.format("Method %s() should not be invoked when using the NoTxtHibernateSessionManager.", name));
  }

  public void abort() {
    invalid("abort");
  }

  public void commit() {
    invalid("commit");
  }

  public Session getSession() {
    return session;
  }

  /**
   * Closes the session at the end of the request.
   */
  public void threadDidCleanup() {
    session.close();
  }
}
