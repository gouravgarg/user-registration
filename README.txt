##Execution Instrucitons-  Following are the two ways to run "cashier-tech" either by Spring Boot or by Maven 

#1. By Spring-Boot	
#Optional extra: with IIN
	mvn spring-boot:run -Dspring-boot.run.arguments=--iin.blockedSet=456789,987654

#Optional extra: without IIN	
	mvn spring-boot:run

#2. By Maven
# Build Jar 	
	mvn clean package

#Execute Jar-  Optional extra: with IIN
	mvn spring-boot:run -Dspring-boot.run.arguments=--iin.blockedSet=456789,987654

#Execute Jar- Optional extra: without IIN	
	java -jar target/cashier-tech-1.0.0.RELEASE.jar



