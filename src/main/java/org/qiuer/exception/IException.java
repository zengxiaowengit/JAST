package org.qiuer.exception;

public abstract class IException extends Exception{
  protected int code;
  protected String msg;

  public IException(){}

  public IException(int code, String msg) {
    super(msg);
    this.code = code;
    this.msg = msg;
  }


  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }
}
