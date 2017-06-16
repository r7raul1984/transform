
import com.yhd.pojo.HBaseAuditLogObject;
import com.yhd.parser.HBaseAuditLogParser;
import com.yhd.LogParseUtil;
import org.junit.Assert;
import org.junit.Test;

public class TestHBaseAuditLogParser {
    HBaseAuditLogParser parser = new HBaseAuditLogParser();

    @Test
    public void test() throws Exception {
        String log = "2017-06-02T02:14:49.201Z yhd-jqhadoop159.int.yihaodian.com 2015-08-11 13:31:03,729 TRACE SecurityLogger.org.apache.hadoop.hbase.security.access.AccessController: Access allowed for user eagle; reason: Table permission granted; remote address: /127.0.0.1; request: get; context: (user=eagle,scope=default:user_intent_focus_dbg,family=info, action=READ)";
        HBaseAuditLogObject obj = parser.parse(log);
        Assert.assertEquals(obj.action, "READ");
        Assert.assertEquals(obj.scope, "user_intent_focus_dbg");
        Assert.assertEquals(obj.client, "127.0.0.1");
        Assert.assertEquals(obj.host, "yhd-jqhadoop159.int.yihaodian.com");
    }

    @Test
    public void test2() throws Exception {
        String log = "2017-06-02T02:14:49.201Z yhd-jqhadoop19.int.yihaodian.com 2015-08-04 12:29:03,073 TRACE SecurityLogger.org.apache.hadoop.hbase.security.access.AccessController: Access allowed for user eagle; reason: Global check allowed; remote address: ; request: preOpen; context: (user=eagle, scope=GLOBAL, family=, action=ADMIN)";
        HBaseAuditLogObject obj = parser.parse(log);
        Assert.assertEquals(obj.action, "ADMIN");
        Assert.assertEquals(obj.scope, "GLOBAL");
        Assert.assertEquals(obj.host, "yhd-jqhadoop19.int.yihaodian.com");
    }

    @Test
    public void test3() throws Exception {
        String log = "2017-06-02T02:14:49.201Z yhd-jqhadoop159.int.yihaodian.com 2017-06-02 10:14:49,008 TRACE SecurityLogger.org.apache.hadoop.hbase.security.access.AccessController: Access allowed for user deploy; reason: Table permission granted; remote address: /10.4.22.30; request: get; context: (user=deploy, scope=default:intents_rank, family=int:, params=[table=default:intents_rank,family=int:],action=READ)";
        HBaseAuditLogObject obj = parser.parse(log);
        Assert.assertEquals(obj.action, "READ");
        Assert.assertEquals(obj.host, "yhd-jqhadoop159.int.yihaodian.com");
        Assert.assertEquals(obj.scope, "intents_rank");
        Assert.assertEquals(obj.client, "10.4.22.30");
    }

    @Test
    public void test4() throws Exception {
        String log = "2017-06-02T02:14:49.201Z yhd-jqhadoop159.int.yihaodian.com 2017-06-02 10:14:49,008 TRACE SecurityLogger.org.apache.hadoop.hbase.security.access.AccessController: Access allowed for user deploy; reason: Table permission granted; remote address: /10.4.22.30; request: get; context: (user=deploy, scope=default:intents_rank, family=int:, params=[table=default:intents_rank,family=int:],action=READ)";
        HBaseAuditLogObject obj = new HBaseAuditLogObject();
        LogParseUtil.setHost(log,obj);
        Assert.assertEquals(obj.host, "yhd-jqhadoop159.int.yihaodian.com");

    }
}
