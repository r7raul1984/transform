/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import com.yhd.parser.HDFSSecurityLogParser;
import com.yhd.pojo.HDFSSecurityLogObject;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class TestHDFSSecuritylogParser {

  /**
   * Test success log message with simple auth
   * @throws ParseException
   */
    @Test
    public void testSimpleAuth() throws ParseException {
      String msg = "2015-11-18 08:41:10,200 INFO SecurityLogger.org.apache.hadoop.security.authorize.ServiceAuthorizationManager: Authorization successful for hbase (auth:SIMPLE) for protocol=interface org.apache.hadoop.hdfs.protocol.ClientProtocol";
      verifyParserAttributes(msg);
    }

    /**
     * Test success log message with kerberos auth for service principal
     * @throws ParseException
     */
    @Test
    public void testServicePrincipalAuth() throws ParseException {
      String msg = "2015-12-22 17:07:03,359 INFO SecurityLogger.org.apache.hadoop.security.authorize.ServiceAuthorizationManager: Authorization successful for hbase/node1.foo.com@EXAMPLE.COM (auth:KERBEROS) for protocol=interface org.apache.hadoop.hdfs.protocol.ClientProtocol";
      verifyParserAttributes(msg);
    }

    /**
     * Test success log message with kerberos auth for user principal
     * @throws ParseException
     */
    @Test
    public void testUserPrincipalAuth() throws ParseException {
      String msg = "2015-12-22 17:07:03,359 INFO SecurityLogger.org.apache.hadoop.security.authorize.ServiceAuthorizationManager: Authorization successful for hbase@EXAMPLE.COM (auth:KERBEROS) for protocol=interface org.apache.hadoop.hdfs.protocol.ClientProtocol";
      verifyParserAttributes(msg);
    }

    private void verifyParserAttributes(String logMessage) throws ParseException {
      HDFSSecurityLogParser parser = new HDFSSecurityLogParser();
      HDFSSecurityLogObject obj = parser.parse(logMessage);

      Assert.assertEquals("hbase", obj.user);
      Assert.assertEquals(true, obj.allowed);

    }
}
