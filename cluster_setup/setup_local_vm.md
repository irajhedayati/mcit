How to setup your VM (your laptop) to connect to the cluster

# Setup naming resolution

In a distributed environment, communication is a key. A good practice is to use
host names instead of IP address. The single node cluster is called
**quickstart.cloudera** and you should be able to reach it using the name.
Try

```
ping quickstart.cloudera
```

If you get **ping: unknown host quickstart.cloudera** message, then do the
following, otherwise skip to the next step.

- Open `/etc/hosts` file (it is used for name to IP conversion like a local
DNS). Note that it is a system file and you should use "sudo"

   ```
   sudo nano /etc/hosts
   ```

- Add the following entry to the end of the file, (you could use either
   SPACE or TAB character)

   ```
   172.16.129.58	quickstart.cloudera
   ```

- Make sure that it works by pinging the hostname again.

# Setup JAVA_HOME

The `JAVA_HOME` environment variable points to a distribution of Java that is
used by Hadoop to launch a process. To see the value of it use

```
echo $JAVA_HOME
```

If the output is empty, then it is not set and follow steps here. Otherwise, 
skip to the next step.

- Open `~/.bashrc` file
   
   ```
   nano ~/.bashrc
   ``` 

- Add the following line

   For Linux users:
 
   ```
   export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")
   ```
  
   For Mac users:

   ```
   export JAVA_HOME=$(/usr/libexec/java_home -v1.8)
   ```

- Source the script to take effect in the current session. As you set it in
  `~/.bashrc` file, it will be set everytime you open a new session.

  ```
  source ~/.bashrc
  ```

# Setup Hadoop

You need to have the slides from the course 3 handy as most of the steps
rely on your knowledge of Hadoop setup and confiugration.
The Hadoop connection is all about the configuraiton files and environment
variables.

## Download Hadoop

**Linux users**

Run the followin commands one by one:

```
mkdir ~/opt
cd ~/opt
wget https://archive.apache.org/dist/hadoop/core/hadoop-2.7.3/hadoop-2.7.3.tar.gz
tar -xf  hadoop-2.7.3.tar.gz
```

**Mac users**

Run the followin commands one by one:

```
mkdir ~/opt
cd ~/opt
curl -o hadoop-2.7.3.tar.gz https://archive.apache.org/dist/hadoop/core/hadoop-2.7.3/hadoop-2.7.3.tar.gz
tar -xf  hadoop-2.7.3.tar.gz
```

Note that you have installed Hadoop version 2.7.3 in `~/opt/hadoop-2.7.3` that we will be refered later
as "Hadoop home" directory.

## Setup environment variables

We need to set three environment variables
- `PATH` to be able to execute Hadoop commands that are under `~/opt/hadoop-2.7.3/bin` directory
  without providing the absolute path
- `HADOOP_HOME` to let Hadoop commands know where it is installed
- `HADOOP_CONF_DIR` to let Hadoop commands know where to find the confogirations

To achieve this task,

- Open `~/.nashrc` file

   ```
   nano ~/.bashrc
   ```
- Add the following lines on top

   ```
   export HADOOP_HOME=~/opt/hadoop-2.7.3
   export HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop
   export PATH=$PATH:$HADOOP_HOME/bin
   ```

## Connect to the cluster

Connection to a cluster is done using configuratoin files. These files should be taken
from the cluster and fedd to Hadoop.

- Create a directory for the configuration files of the Hadoop cluster.

   ```
   mkdir -p $HADOOP_HOME/etc/cloudera/
   ```

- Set Hadoop configuration directory environment variable. To do so, open the `~/.bashrc` file

   ```
   nano ~/.bashrc
   ```

- Add/edit `HADOOP_CONF_DIR` variable

   ```
   export HADOOP_CONF_DIR=$HADOOP_HOME/etc/cloudera
   ```

- Source the script to take effect.

   ```
   source ~/.bashrc
   ```

   and test it

   ```
   echo $HADOOP_CONF_DIR
   ```

- Download the required configuration files from cluster to the directory (they will be provided by your instructor).

- Test your connection

   ```
   hadoop fs -ls /
   ```

   you should see folders like 'solr' and 'benchmark'.

