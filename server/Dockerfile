FROM eclipse-temurin:23-jre

# 1. Atualiza o Linux e instala o Python3 e o PIP
RUN apt-get update && apt-get install -y python3 python3-pip


# 2. Instala as bibliotecas que o teu script usa (OSMnx, etc.)
RUN pip3 install osmnx scikit-learn networkx --break-system-packages

WORKDIR /app

COPY target/*.jar app.jar

# Criar diretório para imagens e copiar
COPY src/main/resources/static/images/ ./images/
# OU se tiver uma pasta images/ na raiz:
# COPY images/ ./images/
COPY python/ /app/python/

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]