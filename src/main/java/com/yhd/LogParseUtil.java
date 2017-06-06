package com.yhd;

public class LogParseUtil {

  public static final String YHD = "int.yihaodian.com";

  public static String parseUserFromUGI(String newUgi) {
    if (newUgi == null)
      return null;
    int index = newUgi.indexOf("/");
    if (index != -1)
      return newUgi.substring(0, index);
    index = newUgi.indexOf("@");
    if (index != -1)
      return newUgi.substring(0, index);
    index = newUgi.indexOf("(");
    if (index != -1)
      return newUgi.substring(0, index).trim();
    return newUgi.trim();
  }

  public static String setHostAndReturnLogWithOutHost(String log, HBaseAuditLogObject object) {
    int start = log.indexOf(YHD);
    setHost(log, object);
    return log.substring(start + YHD.length()).trim();
  }

  public static void setHost(String log, HBaseAuditLogObject object) {

    int start = log.indexOf(YHD);
    String[] rs = log.substring(0, start + YHD.length()).split(" ");
    String host = rs[1];
    object.host = host;
  }
}
