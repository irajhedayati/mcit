# Practice sharding in Hive

Sharding is implemented in Hive using **partitioning** concept.

The sample files are taken from [Stanford Online Educatio](https://lagunita.stanford.edu/courses/DB/2014/SelfPaced/about).

## Prerequisite

In this section we assume you have practiced [Hive Encoding](encoding.md).
We will re-use 
- The database we created called `your_name`
- The table we created called `enriched_movie` (doesn't matter what encodings you have used)
- The schema-on-read concept

## Create a partitioned table

```sql
CREATE TABLE your_name.enriched_movie_p (
  mid int, 
  title STRING, 
  director STRING, 
  rid int, 
  stars int, 
  ratingdate STRING
) 
PARTITIONED BY (year int)
stored as PARQUET
tblproperties("parquet.compression"="GZIP");
```

As you can see, it has exactly the same structure of "parquet" encoding table with "GZip" compression
except the `year` column that now is in the new clause of `PARTITIONED BY`.

Let's check the structure:

```sql
DESCRIBE FORMATTED TABLE your_name.enriched_movie_p
```

## Populate the partitioned table

```sql
INSERT INTO your_name.enriched_movie_p PARTITION(year)
SELECT * FROM enriched_movie
```

The query won't work if Hive is configured in the strict mode. It makes sens as we do partitioning (sharding)
when the table is big. Hence, you wouldn't normally run a query that works possibly on all the partitions.
However, if you really know what you're doing, in this case we know the data is not "big", you could turn it off.

In a Hive connection (CLI or JDBC) just run the following commands:

```sql
set hive.exec.dynamic.partition.mode=nonstrict;
set hive.mapred.mode=nonstrict;
```

If you are working with Hue, you could set the settings in your current session by clicking on
the gear icon on top-right of the page right above the query text box.

Now run the query again. If successful, then run the following command to list the partitions.

```sql
SHOW PARTITIONS your_name.enriched_movie_p
```

As you observe, the list of partitions is not quite right. Try to identify the problem!

The problem is due the mismatch in the list of columns. As discussed in the encoding section,
Hive follows the concept of schema on read. Hence, it won't apply the schema during the write operation.
The query, will use the following mapping of the columns:

| source     | destincation |
|------------|--------------|
| mid        | mid          |
| title      | title        |
| director   | director     |
| year       | rid          |
| rid        | stars        |
| stars      | ratingdate   |
| ratingdate | year         |

That makes Hive to partition data based on `ratingdate` when it writes the data not the `year`.
You will also see `null` values in incompatible fields. Find them.

In Hive, the proper way is to mention all the fields explicitly to avoid such bugs.

Drop the table and create it again.
This time, we will create table as text in order to be able to see the contents.

```sql
DROP TABLE your_name.enriched_movie_p;

CREATE TABLE your_name.enriched_movie_p (
  mid int, 
  title STRING, 
  director STRING, 
  rid int, 
  stars int, 
  ratingdate STRING
) 
PARTITIONED BY (year int)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;
```

Now we can populate the table. Note that `WHERE year<2000` filters data. We will practice another
concept in the next section with the rest of the data.

```sql
INSERT OVERWRITE TABLE your_name.enriched_movie_p PARTITION(year)
SELECT 
    mid,
    title,
    director,
    rid,
    stars,
    ratingdate,
    year
FROM enriched_movie
WHERE year<2000
```

Run `SHOW PARTITIONS your_name.enriched_movie_p` to see if the procedure was successful.

## File structure

Run `hdfs dfs -ls /user/hive/warehouse/your_name.db/enriched_movie_p/` to see the list of partitions.
Go through the sub-directories to get to the files. You could validate the content of the files
using `hdfs dfs -cat ...` command.

## Load data to the table directly

In the previous section, we loaded the data using a SQL query ran directly through Hive server.
You were able to see the files and metadata (using `SHOW PARTITIONS`) right after.

Another way is to load the data directly through the HDFS. As discussed, the path to the 
partition is `/path/to/hive/warehouse/db_name.db/table_name/partition_column_name=partition_column_value`.
For example, `/user/warehouse/your_name.db/enriched_movie_p/year=1976`.
The files under this firectory on HDFS hold the data of the partition.

In order to load the data using HDFS, let's create a directory for a partition that doesn't exist
and upload the data. Note the data that have the same schema without year.

```bash
hadoop fs -mkdir -p /user/hive/warehouse/iraj.db/enriched_movie_p/year=2009
cat <<EOF >~/enriched_movie_2009
107,Avatar,James Cameron,206,3,2011-01-15
107,Avatar,James Cameron,207,5,2011-01-20
EOF
hadoop fs -copyFromLocal ~/enriched_movie_2009 /user/hive/warehouse/iraj.db/enriched_movie_p/year=2009/
```

Try to see if the partition is detected by the Hive:

```sql
SHOW PARTITIONS your_name.enriched_movie_p;
SELECT * FROM your_name.enriched_movie_p;
```

You won't be able to see the new set of data. The reason is that you have updated the underlying data
storeage but not the Hive metastore and partitions are part of the metadata. In order to get the partitions:

```sql
MSCK REPAIR TABLE your_name.enriched_movie_p;
SHOW PARTITIONS your_name.enriched_movie_p;
SELECT * FROM your_name.enriched_movie_p;
```
