# İlk olarak kullanılacak JDK sürümünü belirtmek gerekli.
FROM amazoncorretto:19

# Jar dosyasını Docker'a kopyalama:
# 1. YOL direkt olarak dosya adını verdik:
#COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
# 2. YOL argümandan dosya adını alacağız:
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Docker'a kopyaladığımız Jar dosyasını çalıştırma
ENTRYPOINT ["java","-jar","/app.jar"]

# Argüman kullanılarak oluşturulan dockerfile dosyasının çalıştırılması için:
# 1. YOL: DockerHub hesabı olmadan  (bunsuz yapıp dockera imaj oluşturursan, bunu dockerhuba yükleyemezsin, ondan bunu değil 2.YOL'u tercih et!)
# docker build --build-arg JAR_FILE=jardosyasınınyolu -t dockerdagörmekistediğinisim:görmekistediğintag .
# docker build --build-arg JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar -t java12:001 .
# 2. YOL: DockerHub hesabıyla
# docker build --build-arg JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar -t quexin/java12:001 .