package com.howardlewisship.notx.services;

import com.howardlewisship.notx.NoTx;
import org.apache.tapestry5.hibernate.HibernateTransactionAdvisor;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.ServiceId;
import org.apache.tapestry5.plastic.MethodAdvice;

import java.lang.reflect.Method;

@Marker(NoTx.class)
@ServiceId("NoTxHibernateTransactionAdvisor")
public class NoTxHibernateTransactionAdvisor implements HibernateTransactionAdvisor {

  private final MethodAdvice advice;

  public NoTxHibernateTransactionAdvisor(@NoTx MethodAdvice advice) {
    this.advice = advice;
  }

  @Override
  public void addTransactionCommitAdvice(MethodAdviceReceiver receiver) {
    for (Method m : receiver.getInterface().getMethods()) {
      if (m.getAnnotation(CommitAfter.class) != null) {
        receiver.adviseMethod(m, advice);
      }
    }
  }
}
