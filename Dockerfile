FROM java:8

MAINTAINER chenshu<chenshu@gmail.com>

COPY *.jar /app.jar

CMD ["-- start fly-manage-api -----port:8088"]

EXPOSE 8088

ENTRYPOINT ["java","-jar","-Duser.timezone=GMT+8","/app.jar"]
