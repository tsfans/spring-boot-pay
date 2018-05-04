DATE=$(date +%Y%m%d)
export JAVA_HOME PATH CLASSPATH
JAVA_HOME=/usr/java/jdk1.8.0_152
PATH=$JAVA_HOME/bin:$JAVA_HOME/jre/bin:$PATH
CLASSPATH=.:$JAVA_HOME/lib:$JAVA_HOME/jre/lib:$CLASSPATH
DIR=/data/wwwroot/spring-boot-payment
JARFILE=spring-boot-payment-1.0.0.jar

if [ ! -d $DIR/backup ];then
   mkdir -p $DIR/backup
fi
cd $DIR

ps -ef | grep $JARFILE | grep -v grep | awk '{print $2}' | xargs kill -9
if [ -f $JARFILE ];then
   mv $JARFILE backup/$JARFILE$DATE
fi
mv -f /var/lib/jenkins/workspace/spring-boot-payment/target/$JARFILE ./

BUILD_ID=dontKillMe nohup java -jar $JARFILE --spring.profiles.active=unionpay-prod,prod > out.log &
cd backup/
ls -lt|awk 'NR>5{print $NF}'|xargs rm -rf