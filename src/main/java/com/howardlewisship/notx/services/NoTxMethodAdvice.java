package com.howardlewisship.notx.services;

import com.howardlewisship.notx.NoTx;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.ServiceId;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Shareable advice that can be used by {@link com.howardlewisship.notx.services.NoTxHibernateTransactionAdvisor}
 * and by {@link NoTxCommitAfterWorker}.
 */
@Marker(NoTx.class)
@ServiceId("NoTxMethodAdvice")
public class NoTxMethodAdvice implements MethodAdvice {

  private final HibernateSessionManager manager;

  public NoTxMethodAdvice(HibernateSessionManager manager) {
    this.manager = manager;
  }

  @Override
  public void advise(MethodInvocation invocation) {
    Session session = manager.getSession();

    Transaction transaction = session.beginTransaction();

    try {
      invocation.proceed();

      // Note: as per EJB specs, a checked exception is still considered
      // success, and the transaction still commits.

      transaction.commit();
    }
    catch (RuntimeException e) {
      transaction.rollback();

      throw e;
    }
  }
}
