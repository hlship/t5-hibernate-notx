package com.howardlewisship.notx.modules;

import com.howardlewisship.notx.NoTx;
import com.howardlewisship.notx.services.NoTxHibernateSessionManager;
import com.howardlewisship.notx.services.NoTxHibernateTransactionAdvisor;
import com.howardlewisship.notx.services.NoTxMethodAdvice;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.apache.tapestry5.hibernate.HibernateTransactionAdvisor;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.ioc.services.PerthreadManager;
import org.apache.tapestry5.ioc.services.ServiceOverride;
import org.apache.tapestry5.plastic.MethodAdvice;

/**
 * This module acts as a mix-in to Tapestry's default Hibernate support to remove automatic transactions.
 * Transactions will only be started by the (replaced) CommitAfter annotation worker.
 * The Hibernate session will neither automatically start, nor rollback a transaction
 * ... if will simply be discarded at the end of each request.
 */
public class NoTxCoreModule {

  public static void bind(ServiceBinder binder) {
    binder.bind(MethodAdvice.class, NoTxMethodAdvice.class);
    binder.bind(HibernateTransactionAdvisor.class, NoTxHibernateTransactionAdvisor.class);
  }

  @Scope(ScopeConstants.PERTHREAD)
  @Marker(NoTx.class)
  public static HibernateSessionManager buildNoTxHibernateSessionManager(HibernateSessionSource sessionSource,
                                                                         PerthreadManager perthreadManager) {
    NoTxHibernateSessionManager service = new NoTxHibernateSessionManager(sessionSource);

    perthreadManager.addThreadCleanupListener(service);

    return service;
  }

  @Contribute(ServiceOverride.class)
  public static void replaceStandardServices(MappedConfiguration<Class, Object> configuration,
                                             @NoTx HibernateSessionManager noTxManager,
                                             @NoTx HibernateTransactionAdvisor noTxAdvisor) {
    configuration.add(HibernateSessionManager.class, noTxManager);
    configuration.add(HibernateTransactionAdvisor.class, noTxAdvisor);
  }
}
