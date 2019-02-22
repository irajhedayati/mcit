# .bashrc
# Java
export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")

# Hadoop
export HADOOP_HOME=/root/opt/hadoop
export HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop
export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin

export ZOOKEEPER_HOME=~/opt/zookeeper
export PATH=$PATH:$ZOOKEEPER_HOME/bin
export HIVE_HOME=~/opt/hive
export PATH=$PATH:$HIVE_HOME/bin
export KAFKA_HOME=/root/opt/kafka_2.11-2.0.0
export PATH=$PATH:$KAFKA_HOME/bin
export CLASSPATH=$CLASSPATH:$HADOOP_HOME/lib/*:.
export CLASSPATH=$CLASSPATH:$HIVE_HOME/lib/*:. 

export DERBY_HOME=~/opt/derby
export PATH=$PATH:$DERBY_HOME/bin
export CLASSPATH=$CLASSPATH:$DERBY_HOME/lib/derby.jar:$DERBY_HOME/lib/derbytools.jar

export HBASE_HOME=~/opt/hbase
export PATH=$PATH:$HBASE_HOME/bin

export SPARK_HOME=~/opt/spark
export PATH=$PATH:$SPARK_HOME/bin

#User specific aliases and functions

alias rm='rm -i'
alias cp='cp -i'
alias mv='mv -i'

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi
