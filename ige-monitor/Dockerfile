FROM openjdk:11-jre
LABEL maintainer="研究院研发组 shenkunlin@itcast.cn"
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo "Asia/Shanghai" > /etc/timezone
ADD ./ige-monitor-igem-1.0.jar /app.jar
ENV dbuname="root"
ENV dbpwd="root"
ENV dbname="ige_config"
ENV dburl="localhost:3306"
ENV iport="8085"
WORKDIR /
ENTRYPOINT ["sh","-c","java -jar -Ddbuname=$dbuname -Ddbpwd=$dbpwd -Ddbname=$dbname -Ddburl=$dburl -Diport=$iport -Xms512m -Xmx512m -XX:PermSize=512M -XX:MaxPermSize=512m /app.jar"]