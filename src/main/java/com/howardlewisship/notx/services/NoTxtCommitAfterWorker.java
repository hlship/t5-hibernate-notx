package com.howardlewisship.notx.services;

import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class NoTxtCommitAfterWorker implements ComponentClassTransformWorker2 {

  private final HibernateSessionManager manager;

  private final MethodAdvice advice = new MethodAdvice() {
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
  };

  public NoTxtCommitAfterWorker(HibernateSessionManager manager) {
    this.manager = manager;
  }

  @Override
  public void transform(PlasticClass plasticClass, TransformationSupport support, MutableComponentModel model) {
    for (PlasticMethod method : plasticClass.getMethodsWithAnnotation(CommitAfter.class))
    {
      method.addAdvice(advice);
    }
  }
}
