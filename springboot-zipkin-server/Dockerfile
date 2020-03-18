FROM openjdk:13
VOLUME /tmp
EXPOSE 9411
ADD zipkin-server-2.20.2-exec.jar zipkin-server.jar
ENTRYPOINT ["java", "-jar", "/zipkin-server.jar"]
