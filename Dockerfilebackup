FROM openjdk:17-jdk
WORKDIR /app

# 複製 .war 檔案
COPY projfinal-back/target/projfinal-back-0.0.1-SNAPSHOT.war app.war

# 設定運行指令
CMD ["java", "-jar", "app.war"]

# 開放 8080 端口
EXPOSE 8080
