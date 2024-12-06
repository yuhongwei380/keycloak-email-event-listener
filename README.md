# keycloak-email-event-listener
only test in keycloak 26.0.1 ,when user register ，will send email to which you want ,you can set email address in keycloak docker-compose.yml


Quick Start
```bash
# Clone the repository
git clone https://github.com/yuhongwei380/keycloak-email-event-listener.git
cd keycloak-email-event-listener

# Build the extension
mvn clean package

# Copy JAR to Keycloak providers
cp target/custom-event-listener.jar providers/

#vim docker-compose for keycloak like this ; you can fill it with your smtp server and which email address you want to receive the email when new user came in 
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
      - SMTP_EMAIL=   
      - SMTP_PASSWORD=       
      - SMTP_HOST=            
      - SMTP_PORT=                         
      - RECIPIENT_EMAIL=
    volumes:
      - ./keycloak-event-listener-1.0.0.jar:/opt/keycloak/providers/keycloak-event-listener-1.0.0.jar
    command: 
      - start   #start-dev测试环境使用   start--optimized 第二次启动时可配置。
    networks:
      - keycloak-network
```
