package com.yhd;

import com.yhd.parser.HBaseAuditLogParser;
import com.yhd.parser.HDFSAuditLogParser;
import com.yhd.parser.HDFSSecurityLogParser;
import com.yhd.parser.OozieAuditLogParser;
import com.yhd.pojo.HBaseAuditLogObject;
import com.yhd.pojo.HDFSAuditLogObject;
import com.yhd.pojo.HDFSSecurityLogObject;
import com.yhd.pojo.OozieAuditLogObject;

import java.util.Scanner;

public class LogMapper {

  private static HBaseAuditLogParser hBaseAuditLogParser = new HBaseAuditLogParser();
  private static HDFSAuditLogParser hdfsAuditLogParser = new HDFSAuditLogParser();
  private static HDFSSecurityLogParser hdfsSecurityLogParser = new HDFSSecurityLogParser();
  private static OozieAuditLogParser oozieAuditLogParser = new OozieAuditLogParser();

  public static void main(String[] args) throws Exception {
    Scanner stdIn = new Scanner(System.in);
    String flag;
    if (args.length == 0) {
      flag = "hbasea";
    } else {
      flag = args[0];//hbasea  hdfsa   hdfss   ooziea
    }
    String line;
    while (stdIn.hasNext()) {
      line = stdIn.nextLine();
      if (flag.equals("hbasea")) {
        HBaseAuditLogObject obj = hBaseAuditLogParser.parse(line);
        System.out.println(
            obj.host + "\t" + obj.action + "\t" + obj.scope + "\t" + obj.request + "\t" + obj.status
                + "\t" + obj.user + "\t" + obj.timestamp + "\t" + obj.client);
      } else if (flag.equals("hdfsa")) {
        HDFSAuditLogObject obj = hdfsAuditLogParser.parse(line);
        System.out.println(
            obj.timestamp + "\t" + obj.host + "\t" + obj.allowed + "\t" + obj.user + "\t" + obj.cmd
                + "\t" + obj.src + "\t" + obj.dst );
      } else if (flag.equals("hdfss")) {
        HDFSSecurityLogObject obj = hdfsSecurityLogParser.parse(line);
        System.out.println(
            obj.timestamp + "\t" + obj.allowed + "\t" + obj.user);
      } else if (flag.equals("ooziea")) {
        OozieAuditLogObject obj = oozieAuditLogParser.parse(line);
        System.out.println(
            obj.timestamp + "\t" + obj.level + "\t" + obj.ip + "\t" + obj.user + "\t" + obj.group
                + "\t" + obj.app + "\t" + obj.jobId + "\t" + obj.operation
                + "\t" + obj.parameter + "\t" + obj.status + "\t" + obj.httpcode
                + "\t" + obj.errorcode + "\t" + obj.errormessage);
      }

    }
  }
}