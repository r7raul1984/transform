package com.yhd;
import java.io.IOException;
import java.util.Scanner;

public class LogMapper {

  private static HBaseAuditLogParser parser = new HBaseAuditLogParser();

  public static void main(String[] args) throws IOException {
    Scanner stdIn = new Scanner(System.in);
    String line = null;

    while (stdIn.hasNext()) {
      line = stdIn.nextLine();
      HBaseAuditLogObject obj = parser.parse(line);
      System.out.println(
          obj.host + "\t" + obj.action + "\t" + obj.scope + "\t" + obj.request + "\t" + obj.status
              + "\t" + obj.user + "\t" + obj.timestamp+ "\t" + obj.client);
    }
  }
}