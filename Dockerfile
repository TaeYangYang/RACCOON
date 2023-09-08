#open jdk java11
FROM openjdk:11-jdk

#JAR_FILE 변수 정의 -> 기본적으로 jar 파일이 2개기때문에 이름을 특정해줌
ARG JAR_FILE=./build/libs/RACCOON-1.0-SNAPSHOT.jar

# JAR 파일 메인 디렉토리에 복사
COPY ${JAR_FILE} app.jar

#시스템 진입점
ENTRYPOINT ["java", "-jar", "/app.jar"]