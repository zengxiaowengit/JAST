package org.qiuer.exception;

public class EReturnObject extends IException {

  private Object object;

  public EReturnObject(Object object) {
    this.object = object;
  }

  public Object getObject() {
    return object;
  }
}
