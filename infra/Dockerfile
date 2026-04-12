# ---- Stage 1: Compile Java sources ----
FROM tomcat:8.5-jdk8 AS builder

WORKDIR /build

# Copy source code and web content
COPY server/src ./src
COPY server/WebContent ./WebContent

# Replace DB host: localhost -> mysql (Docker network hostname)
RUN sed -i 's/localhost/mysql/g' src/com/dondonqiang2/server/util/DBHelper.java

# Compile all Java sources
# servlet-api.jar comes from the Tomcat image; other JARs are in WebContent/WEB-INF/lib/
RUN mkdir -p WebContent/WEB-INF/classes \
    && find src -name "*.java" | xargs javac \
       -cp "/usr/local/tomcat/lib/servlet-api.jar:WebContent/WEB-INF/lib/*" \
       -d WebContent/WEB-INF/classes

# ---- Stage 2: Deploy to Tomcat ----
FROM tomcat:8.5-jdk8

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Deploy compiled web application with context path /DonDonQiang20_Server
COPY --from=builder /build/WebContent /usr/local/tomcat/webapps/DonDonQiang20_Server

EXPOSE 8080
CMD ["catalina.sh", "run"]
