FROM eclipse-temurin:23-jre

WORKDIR /app

COPY target/*.jar app.jar

# Criar diretório para imagens e copiar
COPY src/main/resources/static/images/ ./images/
# OU se tiver uma pasta images/ na raiz:
# COPY images/ ./images/

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]