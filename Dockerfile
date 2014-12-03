FROM java:7

ENV IRCSLACKRELAY_VER 1.3.0

RUN mkdir /app
ADD target/scala-2.11/ircslackrelay-assembly-${IRCSLACKRELAY_VER}.jar /app/ircslackrelay.jar
ADD target/scala-2.11/ircslackrelay.conf /app/ircslackrelay.conf

CMD ["java", "-jar", "/app/ircslackrelay.jar"]


