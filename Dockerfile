#基础镜像，使用alpine操作系统，openjdk使用8u201
FROM openjdk:8u201-jdk-alpine3.9
#作者
MAINTAINER Specter <1292675809@qq.com>
#系统编码
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
#声明一个挂载点
VOLUME /tmp
#
ADD target/*.jar app.jar
#启动容器时的进程
ENTRYPOINT ["java", "-jar", "/app.jar"]
#暴露端口
EXPOSE 8080