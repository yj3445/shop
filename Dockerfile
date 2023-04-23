FROM cbd/java:8

VOLUME /tmp

COPY target/it-shop-web.jar /app/app.jar 

WORKDIR /app

RUN bash -c "touch app.jar"

EXPOSE 8008

# ENTRYPOINT ["java", "-server","${JAVA_OPTS}","-jar","app.jar","--server.port=8008","--spring.profiles.active=prod"]
ENTRYPOINT java $JAVA_OPTS -server -jar app.jar --server.port=8008 --spring.profiles.active=prod -Djava.awt.headless=true -Dfile.encoding=utf-8
