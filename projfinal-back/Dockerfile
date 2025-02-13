# 1. 使用 Maven 建構應用
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2. 使用 OpenJDK 來運行應用
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

# 第一階段（建構 Maven 專案）

# 使用 Maven 官方映像檔（maven:3.8.5-openjdk-17）
# 將 pom.xml 和 src/ 複製進容器
# 執行 mvn package 來打包 Spring Boot 應用程式
# 產生 JAR 檔案到 target/ 目錄
# 第二階段（運行 Spring Boot 應用）

# 使用輕量版 JDK 映像檔 openjdk:17
# 從第一階段拷貝 target/*.jar 到 /app/app.jar
# 開放 8080 端口
# 運行 Spring Boot 應用 java -jar app.jar
