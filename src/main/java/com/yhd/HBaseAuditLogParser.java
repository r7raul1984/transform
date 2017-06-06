package com.yhd;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HBaseAuditLogParser implements Serializable {

  private final static int LOGDATE_INDEX = 1;
  private final static int LOGLEVEL_INDEX = 2;
  private final static int LOGATTRS_INDEX = 3;
  private final static String ALLOWED = "allowed";
  private final static String DENIED = "denied";
  private final static Pattern loggerPattern = Pattern
      .compile("^([\\d\\s\\-:,]+)\\s+(\\w+)\\s+(.*)");
  private final static Pattern loggerContextPattern = Pattern.compile("\\w+:\\s*\\((.*)\\s*\\)");
  private final static Pattern allowedPattern = Pattern.compile(ALLOWED);

  public HBaseAuditLogObject parse(String logLine) {
    if (logLine == null || logLine.length() == 0) {
      return null;
    }

    HBaseAuditLogObject ret = new HBaseAuditLogObject();
    String timestamp = "";
    String user = "";
    String scope = "";
    String action = "";
    String ip = "";
    String request = "";
    String family = "";
    String context = "";
    String newLogLine = LogParseUtil.setHostAndReturnLogWithOutHost(logLine, ret);
    Matcher loggerMatcher = loggerPattern.matcher(newLogLine);
    if (loggerMatcher.find()) {
      try {
        timestamp = loggerMatcher.group(LOGDATE_INDEX);
        String[] attrs = loggerMatcher.group(LOGATTRS_INDEX).split(";");
        ret.status = allowedPattern.matcher(attrs[0]).find() ? ALLOWED : DENIED;
        try {
          ip = attrs[2].split(":")[1].trim();
        } catch (Exception e) {
          ip = "";
        }
        try {
          request = attrs[3].split(":")[1].trim();
        } catch (Exception e) {
          request = "";
        }
        try {
          context = attrs[4].trim();
        } catch (Exception e) {
          context = "";
        }

        Matcher contextMatcher = loggerContextPattern.matcher(context.replaceAll("\\s+", ""));
        if (contextMatcher.find()) {
          boolean paramsOpen = false;

          List<String> kvs = new LinkedList<String>(
              Arrays.asList(contextMatcher.group(1).split(",")));

          while (!kvs.isEmpty()) {
            String kv = kvs.get(0);

            if (kv.split("=").length < 2) {
              kvs.remove(0);
              continue;
            }

            String k = kv.split("=")[0];
            String v = kv.split("=")[1];

            if (paramsOpen && kv.substring(kv.length() - 1).equals("]")) {
              paramsOpen = false;
              v = v.substring(0, v.length() - 1);
            }

            switch (k) {
              case "user":
                user = v;
                break;
              case "scope":
                scope = v;
                break;
              case "family":
                family = v;
                break;
              case "action":
                action = v;
                break;
              case "params":
                kvs.add(v.substring(1) + "=" + kv.split("=")[2]);
                paramsOpen = true;
                break;
              default:
                break;
            }

            kvs.remove(0);
          }
        }
        if (scope.contains(":")){
          scope = scope.split(":")[1];
        }
       /* if (family != null && family.length() != 0) {
          if (!scope.contains(":"))
            scope = "default:" + scope;
          scope = String.format("%s:%s", scope, family);
        }*/
        if (ip != null && ip.length() != 0) {
          ret.client = ip.substring(1);
        }
        ret.timestamp = DateTimeUtil.humanDateToMilliseconds(timestamp);
        ret.scope = scope;
        ret.action = action;
        ret.user = LogParseUtil.parseUserFromUGI(user);
        ret.request = request;
        return ret;
      } catch (Exception e) {
      }
    }
    return null;
  }
}

