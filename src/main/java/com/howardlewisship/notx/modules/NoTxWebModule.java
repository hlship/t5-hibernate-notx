package com.howardlewisship.notx.modules;

import com.howardlewisship.notx.services.NoTxCommitAfterWorker;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

/**
 * An extension to {@link com.howardlewisship.notx.modules.NoTxCoreModule} that provides a
 * a replacement implementation of the "CommitAfter" component class transformation worker.
 */
public class NoTxWebModule {
  @Contribute(ComponentClassTransformWorker2.class)
  @Primary
  public static void provideCommitAfterAnnotationSupport(
      OrderedConfiguration<ComponentClassTransformWorker2> configuration) {
    // When replacing a contribution, you have to duplicate the ordering constraints as well.
    configuration.overrideInstance("CommitAfter", NoTxCommitAfterWorker.class, "after:Log");
  }
}
