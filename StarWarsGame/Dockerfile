FROM ubuntu
RUN apt-get -y update
RUN apt-get -y install openjdk-8-jdk
EXPOSE 8080
ADD target/starwarsgame-1.0-SNAPSHOT.jar /
CMD ["java", "-jar", "/starwarsgame-1.0-SNAPSHOT.jar"]
