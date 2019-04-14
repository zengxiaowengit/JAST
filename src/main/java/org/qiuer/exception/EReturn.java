package org.qiuer.exception;

public class EReturn extends IException {

  private Object object;

  public EReturn(Object object){
    this.object = object;
  }

  public EReturn(int code, String message) {
    super(code, message);
  }

  public EReturn(int code, String message, Object object) {
    super(code, message);
    this.object = object;
  }

  public Object getObject() {
    return object;
  }

  public void setObject(Object object) {
    this.object = object;
  }
}
