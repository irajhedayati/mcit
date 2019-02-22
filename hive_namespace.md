# Practice file encoding in Hive

## HDFS home directory

Make sure a home directory exists under your name and you put samle files from STM on HDFS under your directory.

```bash
hadoop fs -ls /user
hadoop fs -mkdir /user/your_name
hadoop fs -mkdir /user/your_name/stm_gtfs
hadoop fs -mkdri /user/your_name/stm_gtfs/stop_times
hadoop fs -put stop_times.txt /user/your_name/stm_gtfs/stop_times
```

Question: how could we do the commands from 2 to 4 in one line?


## Start client

Run **beeline** to connet to the Hive server:

```bash
beeline -u jdbc:hive2://node1:10000 --showDbInPrompt
```

Create a database with your name:

```sql
jdbc:hive2://node1:10000 (default)> CREATE DATABASE your_name;
jdbc:hive2://node1:10000 (default)> USER your_name;
jdbc:hive2://node1:10000 (your_name)>
```


## CSV file encoding

The file we put on HDFS is in CSV format. Create an external table pointing to the directory on HDFS to run SQL queries.

```sql
CREATE EXTERNAL TABLE your_name.ext_stop_times (
 trip_id STRING,
 arrival_time STRING,
 departure_time STRING,
 stop_id BIGINT,
 stop_sequence SMALLINT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION '/user/your_name/stm_gtfs/stop_times';

SELECT * FROM ext_stop_times LIMIT 10
```

Create another table (managed) by importing data from external file

```sql
CREATE TABLE your_name.stop_times_txt 
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE
AS SELECT * FROM your_name.ext_stop_times;
```

Check the files:

```bash
hadoop fs -ls -h /user/hive/warehouse/your_name.db/stop_times_txt
-rwxrwxr-x   1 root supergroup  248.1 M 2018-09-01 23:53 /user/hive/warehouse/your_name.db/stop_times_txt/000000_0
```

*Question*: Why only one file?


```bash
hadoop fs -tail /user/hive/warehouse/stm_gtfs.db/stop_times_txt/000000_0;
5:43:57,05:43:57,46,10
18S_18S_F2_2_0,05:45:13,05:45:13,36,11
18S_18S_F2_2_0,05:46:44,05:46:44,17,12
...
```

## Output compression


Enable GZip encoding in Hive:

```bash
jdbc:hive2://node1:10000 (default)> set mapred.map.output.compression.codec=org.apache.hadoop.io.compress.GZipCodec;
jdbc:hive2://node1:10000 (default)> set hive.exec.compress.output=true;
```

```sql
CREATE TABLE stop_times_txt_gz
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' 
AS SELECT * FROM stop_times_txt;
```

```bash
hadoop dfs -ls -h /user/hive/warehouse/your_name.db/stop_times_txt_gz;
-rwxrwxr-x   1 root supergroup     41.3 M 2018-09-02 00:43 /user/hive/warehouse/your_name.db/stop_times_txt_gz/000000_0.deflate
```

## Sequence file

```sql
CREATE TABLE stop_times_seq
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS SEQUENCEFILE
AS SELECT * FROM stop_times_txt;
```

```
hadoop fs -ls -h /user/hive/warehouse/stm_gtfs.db/stop_times_seq;
-rwxrwxr-x   1 root supergroup    311.2 M 2018-09-02 00:02 /user/hive/warehouse/stm_gtfs.db/stop_times_seq/000000_0
```

hive (stm_gtfs)> dfs -tail /user/hive/warehouse/stm_gtfs.db/stop_times_seq/000000_0;
:10,13,16+&18S_18S_F2_2_0,05:52:13,05:52:13,12,17+&18S_18S_F2_2_0,05:53:44,05:53:44,11,18+&18S_18S_F2_2_0,...

## Sequence file compressed
CREATE TABLE stop_times_seq_gz
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' 
AS SELECT * FROM stop_times_txt;

hive (stm_gtfs)> dfs -ls -h /user/hive/warehouse/stm_gtfs.db/stop_times_seq_gz;
-rwxrwxr-x   1 root supergroup     41.3 M 2018-09-02 01:36 /user/hive/warehouse/stm_gtfs.db/stop_times_seq_gz/000000_0.deflate

