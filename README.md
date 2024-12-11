# keycloak-email-event-listener
only test in keycloak 26.0.1 ,when user register ，will send email to which you want ,you can set email address in keycloak docker-compose.yml


Quick Start
```bash
# Clone the repository
git clone https://github.com/yuhongwei380/keycloak-email-event-listener.git
cd keycloak-email-event-listener

#OR get it
cp providers/keycloak-newuser-email-event.jar    ../keycloak/

# Build the extension
mvn clean package

# Copy JAR to Keycloak 
cp target/keycloak-newuser-email-event.jar  ../keycloak/

#vim docker-compose for keycloak like this ; you can fill it with email  which  you want to receive the email when keycloak-new user came in
cd ../ && mkdir keycloak
cd keycloak

#docker-compose.yml for example
keycloak:
    image: quay.io/keycloak/keycloak:26.0.1
    container_name: keycloak
    ports:
      - "8080:8080"
    deploy:
      resources:
        limits:
          cpus: "2"              # 限制使用的 CPU 数量
          memory: "4G"  
    environment:
      ···                   
      - RECIPIENT_EMAIL=youremailaddress@email.com  #the email you want to receice the message when keycloak-new user register
    volumes:
      - ./keycloak-newuser-email-event.jar:/opt/keycloak/providers/keycloak-newuser-email-event.jar
    command: 
      - start   #start-dev测试环境使用   start--optimized 第二次启动时可配置。
    networks:
      - keycloak-network
```
