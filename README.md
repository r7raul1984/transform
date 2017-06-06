CREATE EXTERNAL TABLE tandem.hbaselogparsed(


  host string,
  
  
  action string,
  
  
  scope string,
  
  
  request string,
  
  
  status string,
  
  
  user string,
  
  
  timestamp string,
  
  
  client string
  
  
)


 STORED AS TEXTFILE
 
 
 LOCATION '/data/camus/topics/xxxxx';
 
 

 add jar /xxx/xx/xxx/transform.jar;
 
 
 insert overwrite  table tandem.hbaselogparsed SELECT TRANSFORM(log) USING "java -cp transform.jar com.yhd.LogMapper" AS host,action,scope,request,status,user,timestamp,client from tandem.hbase_log;
