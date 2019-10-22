# Practice file encoding in Hive

The sample files are taken from [Stanford Online Educatio](https://lagunita.stanford.edu/courses/DB/2014/SelfPaced/about).

## HDFS home directory

Make sure a home directory exists under your name.

```bash
hadoop fs -ls /user
hadoop fs -mkdir /user/your_name
hadoop fs -mkdir /user/your_name/course4_2
```

**Question**: how could you do the commands 2 and 3 in one line?

This directory would be a staging area.

## Prepare staging area for each data domain

Create a directory for each data domain under your staging area. There are 3 main data domain here **movie**, **rating** and **reviewer**.
 
```
hadoop fs -mkdir /user/your_name/course4_2/movie
hadoop fs -mkdir /user/your_name/course4_2/rating
hadoop fs -mkdir /user/your_name/course4_2/reviewer
```

## Load data to the staging area

At this point, we could just use Hadoop API to accomplish this task and put extracted files to HDFS.

```
hadoop fs -put movie.csv /user/course4_2/stm_gtfs/movie
hadoop fs -put rating.csv /user/course4_2/stm_gtfs/rating
hadoop fs -put reviewer.csv /user/course4_2/stm_gtfs/reviewer
```

**Note**: in this example, the file names and the data domain directory's name are the same but this is not always the case.

## Connect to Hive

Run **beeline** to connet to the Hive server:

```bash
beeline -u jdbc:hive2://node1:10000 --showDbInPrompt
```

Create a database with your name:

```sql
jdbc:hive2://node1:10000 (default)> CREATE DATABASE your_name;
jdbc:hive2://node1:10000 (default)> USE your_name;
jdbc:hive2://node1:10000 (your_name)>
```

## CSV file encoding

The file we put on HDFS is in CSV format. Create an external table pointing to the directory on HDFS to run SQL queries.

```sql
CREATE EXTERNAL TABLE your_name.ext_movie (
    mID INT, 
    title STRING, 
    year INT, 
    director STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION '/user/your_name/course4_2/movie';
```

```sql
CREATE EXTERNAL TABLE your_name.ext_rating (
    rID INT, 
    mID INT, 
    stars INT, 
    ratingDate STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION '/user/your_name/course4_2/rating';
```

```sql
CREATE EXTERNAL TABLE your_name.ext_reviewer (
    rID INT, 
    name STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION '/user/your_name/course4_2/reviewer';
```

Create another table (managed) by importing data from external file

```sql
CREATE TABLE your_name.enriched_movie 
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS TEXTFILE
AS /* Query to enriched movie with reviewer and rating information */;
```

Check the encoding:

```sql
DESCRIBE your_name.enriched_movie;
```

Check the files:

```bash
hadoop fs -ls -h /user/hive/warehouse/your_name.db/enriched_movie
-rwxrwxr-x   1 root supergroup  248.1 M 2018-09-01 23:53 /user/hive/warehouse/your_name.db/stop_times_txt/000000_0
```

*Question*: Why only one file?


```bash
hadoop fs -tail /user/hive/warehouse/your_name.db/enriched_movie/000000_0;
```

## Output compression


Enable GZip encoding in Hive:

```bash
jdbc:hive2://node1:10000 (default)> set mapred.map.output.compression.codec=org.apache.hadoop.io.compress.GZipCodec;
jdbc:hive2://node1:10000 (default)> set hive.exec.compress.output=true;
```

```sql
CREATE TABLE enriched_movie_gz
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' 
AS SELECT * FROM enriched_movie;
```

```bash
hadoop dfs -ls -h /user/hive/warehouse/your_name.db/enriched_movie_gz;
-rwxrwxr-x   1 root supergroup     41.3 M 2018-09-02 00:43 /user/hive/warehouse/your_name.db/enriched_movie_gz/000000_0.deflate
```

## Sequence file

```sql
CREATE TABLE enriched_movie_seq
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' STORED AS SEQUENCEFILE
AS SELECT * FROM enriched_movie;
```

```
hadoop fs -ls -h /user/hive/warehouse/your_name.db/enriched_movie_seq;
-rwxrwxr-x   1 root supergroup    311.2 M 2018-09-02 00:02 /user/hive/warehouse/your_name.db/enriched_movie_seq/000000_0

hadoop fs -tail /user/hive/warehouse/your_name.db/enriched_movie_seq/000000_0;
```

## Sequence file compressed

```sql
CREATE TABLE enriched_movie_seq_gz
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' 
AS SELECT * FROM enriched_movie;
```
```bash
hive (stm_gtfs)> dfs -ls -h /user/hive/warehouse/your_name.db/enriched_movie_seq_gz;
-rwxrwxr-x   1 root supergroup     41.3 M 2018-09-02 01:36 /user/hive/warehouse/your_name.db/enriched_movie_seq_gz/000000_0.deflate
```
