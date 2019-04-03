package org.qiuer.exception;

import org.qiuer.util.JsonUtil;

public class EReturnJson extends EReturn {

  private String json;

  public EReturnJson(int code, String msg, Object object) {
    super(code, msg);
    this.json = JsonUtil.toJson(object);
  }
}
