FROM cbd/java:8

COPY target/it-shop-web.jar /app/app.jar 

WORKDIR /app

RUN bash -c "touch app.jar"

EXPOSE 8008

ENV SPRING_ENV=prod

ENTRYPOINT java $JAVA_OPTS -server -jar app.jar --server.port=8008 --spring.profiles.active=$SPRING_ENV -Djava.awt.headless=true -Dfile.encoding=utf-8
