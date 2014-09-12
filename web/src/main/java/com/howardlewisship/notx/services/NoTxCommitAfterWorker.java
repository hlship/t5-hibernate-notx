package com.howardlewisship.notx.services;

import com.howardlewisship.notx.NoTx;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

public class NoTxCommitAfterWorker implements ComponentClassTransformWorker2 {

  private final MethodAdvice advice;

  public NoTxCommitAfterWorker(@NoTx MethodAdvice advice) {
    this.advice = advice;
  }

  @Override
  public void transform(PlasticClass plasticClass, TransformationSupport support, MutableComponentModel model) {
    for (PlasticMethod method : plasticClass.getMethodsWithAnnotation(CommitAfter.class)) {
      method.addAdvice(advice);
    }
  }
}
