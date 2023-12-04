# Image oluşturmadan önce
###  Docker Fıle yazdık
#### projenini builini almamız lazım
#### clean, build, buildDependents

# Image Oluşturma
### projenin docker image oluşturduk
### docker build --build -arg JAR_FILE= JAR_FILE=build/libs/SpringBoot12MovieApp-0.0.1-SNAPSHOT.jar -t <image.name>
### docker build --build-arg JAR_FILE=build/libs/SpringBoot12MovieApp-0.0.1-SNAPSHOT.jar -t 22580000/movieapp:V005 .

# Docker Containerde ayağa kaldıma
### docker run -d(arka planda çalıştırır) -p 9090:9091(dış port- içport)
### 9090 üzerinden projeye ulaştık 9090 içerde 9091 le yayın yapıyor.
### docker run --name java12postgredb -e POSTGRES_PASSWORD=berkin -d -p 5252:5432 postgres


# İkside farklı containerda ayağa kalktığı için aralrında bir iletişim kuramadık
# bunun için iksinide aynı network üzerinden ayağa kaldırdık

# Docker Network oluşturma
### docker network create --driver bridge --subnet 192.168.3.0/24 --gateway 192.168.3.1 java12-network
###  docker run --name java12postgre -e POSTGRES_PASSWORD=berkin --net java12-network -d -p 5252:5432 postgres
### --net dediğimiz netowrk ismini belirtiyoruz
### projeyi ayağa kaldırıyoruz--> docker run --net java12-network -d -p 9595:8390 22580000/movieapp:V005   