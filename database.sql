-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: hospital
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `employee_id` int NOT NULL AUTO_INCREMENT,
  `employee_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `employee_surname` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `employee_age` tinyint NOT NULL,
  `employee_gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `position` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `hire_date` bigint NOT NULL,
  `dismiss_date` bigint NOT NULL DEFAULT '0',
  `employee_status` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `employee_id_UNIQUE` (`employee_id`),
  KEY `employee_status_id_idx` (`employee_status`),
  CONSTRAINT `employee_status_fk` FOREIGN KEY (`employee_status`) REFERENCES `statuses` (`status_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (7,'Daniel','Riccardo',21,'M','ASSISTANT',1608508800000,0,0),(9,'Doc','Tor',41,'F','DOCTOR',1599609600000,0,0),(10,'Vasya','Pupkin',26,'M','DOCTOR',1603411200000,0,0),(11,'James','Wattson',23,'M','ASSISTANT',1603411200000,0,2),(12,'Vladislav','Drobot',19,'M','DOCTOR',1603411200000,0,0),(13,'Alex','Newman',31,'M','DOCTOR',1603324800000,0,0),(14,'Matthew','Conway',42,'M','DOCTOR',1599609600000,0,0),(15,'Ivan','Petrov',22,'M','ASSISTANT',1595289600000,0,2),(16,'Mira','Pavlovski',21,'F','ASSISTANT',1602288000000,1605126359730,3),(17,'Carlos','Sainz',19,'M','ASSISTANT',1602374400000,1605127329630,3),(18,'Lando','Norris',20,'M','ASSISTANT',1603929600000,0,0),(19,'Lewis','Hamilton',26,'M','ASSISTANT',1604620800000,0,0),(20,'Max','Verstappen',21,'M','DOCTOR',1604966400000,0,0);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_records`
--

DROP TABLE IF EXISTS `patient_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_records` (
  `record_id` int NOT NULL AUTO_INCREMENT,
  `patient_id_fk` int NOT NULL,
  `attending_doctor_id` int NOT NULL,
  `curing_id` int NOT NULL,
  `executor_id` int DEFAULT NULL,
  `diagnosis` varchar(45) NOT NULL,
  PRIMARY KEY (`record_id`),
  UNIQUE KEY `record_id_UNIQUE` (`record_id`),
  KEY `curing_id_fk_idx` (`curing_id`),
  KEY `attending_doctor_id_idx` (`attending_doctor_id`),
  KEY `patient_id_fk_idx` (`patient_id_fk`),
  KEY `executor_id_fk_idx` (`executor_id`),
  CONSTRAINT `attending_doctor_id_fk` FOREIGN KEY (`attending_doctor_id`) REFERENCES `employees` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `curing_id_fk` FOREIGN KEY (`curing_id`) REFERENCES `treatments` (`treatment_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `executor_id_fk` FOREIGN KEY (`executor_id`) REFERENCES `employees` (`employee_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `patient_id_fk` FOREIGN KEY (`patient_id_fk`) REFERENCES `patients` (`patient_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_records`
--

LOCK TABLES `patient_records` WRITE;
/*!40000 ALTER TABLE `patient_records` DISABLE KEYS */;
INSERT INTO `patient_records` VALUES (12,5,12,2,12,'Fractures'),(13,5,12,2,12,'TBI'),(14,3,12,1,12,'Cold'),(15,5,12,2,12,'Burn'),(16,1,12,1,12,'Covid'),(17,2,12,2,NULL,'Bullet injure'),(18,6,12,1,9,'Stress'),(19,6,9,1,7,'Panic attack'),(20,8,12,1,NULL,'Cough');
/*!40000 ALTER TABLE `patient_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `patient_id` int NOT NULL AUTO_INCREMENT,
  `patient_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `patient_surname` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `patient_age` tinyint NOT NULL,
  `patient_gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `patient_status` tinyint NOT NULL DEFAULT '3',
  PRIMARY KEY (`patient_id`),
  UNIQUE KEY `record_id_UNIQUE` (`patient_id`),
  KEY `patient_status_fk_idx` (`patient_status`),
  CONSTRAINT `patient_status_fk` FOREIGN KEY (`patient_status`) REFERENCES `statuses` (`status_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (1,'Donald','Trump',74,'M',3),(2,'John','Connor',32,'M',4),(3,'Daniil','Kvyat',31,'M',3),(4,'Mark','Webber',40,'M',3),(5,'Ken','Miles',20,'M',5),(6,'Richard','Hendricks',21,'M',3),(7,'Esteban','Ocon',19,'M',3),(8,'Romain','Grosjean',40,'M',4);
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statuses`
--

DROP TABLE IF EXISTS `statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `statuses` (
  `status_id` tinyint NOT NULL,
  `status_name` varchar(20) NOT NULL,
  PRIMARY KEY (`status_id`),
  UNIQUE KEY `status_id_UNIQUE` (`status_id`),
  UNIQUE KEY `status_name_UNIQUE` (`status_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statuses`
--

LOCK TABLES `statuses` WRITE;
/*!40000 ALTER TABLE `statuses` DISABLE KEYS */;
INSERT INTO `statuses` VALUES (0,'ACTIVE'),(3,'ARCHIVE'),(1,'BLOCKED'),(127,'UNREMOVABLE'),(2,'VACATION'),(4,'WAITING_FOR_CURING'),(5,'WAITING_FOR_DECISION');
/*!40000 ALTER TABLE `statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treatments`
--

DROP TABLE IF EXISTS `treatments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treatments` (
  `treatment_id` int NOT NULL AUTO_INCREMENT,
  `treatment_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `allowed_for_assistants` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`treatment_id`),
  UNIQUE KEY `treatment_name_UNIQUE` (`treatment_name`),
  UNIQUE KEY `treatment_id_UNIQUE` (`treatment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatments`
--

LOCK TABLES `treatments` WRITE;
/*!40000 ALTER TABLE `treatments` DISABLE KEYS */;
INSERT INTO `treatments` VALUES (1,'PROCEDURE',_binary ''),(2,'SURGERY',_binary '\0');
/*!40000 ALTER TABLE `treatments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_employee`
--

DROP TABLE IF EXISTS `user_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_employee` (
  `inter_user_id` int NOT NULL,
  `inter_employee_id` int NOT NULL,
  UNIQUE KEY `inter_user_id_UNIQUE` (`inter_user_id`),
  UNIQUE KEY `inter_employee_id_UNIQUE` (`inter_employee_id`),
  CONSTRAINT `employee_id_fk` FOREIGN KEY (`inter_employee_id`) REFERENCES `employees` (`employee_id`) ON UPDATE CASCADE,
  CONSTRAINT `user_id_fk` FOREIGN KEY (`inter_user_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_employee`
--

LOCK TABLES `user_employee` WRITE;
/*!40000 ALTER TABLE `user_employee` DISABLE KEYS */;
INSERT INTO `user_employee` VALUES (6,7),(8,9),(9,10),(10,11),(1,12),(11,13),(12,14),(13,15),(14,16),(15,17),(16,18),(17,19),(18,20);
/*!40000 ALTER TABLE `user_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_status` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_table_pk` (`user_id`),
  UNIQUE KEY `user_table_login_uindex` (`login`),
  UNIQUE KEY `user_table_email_uindex` (`email`),
  KEY `user_status_fk_idx` (`user_status`),
  CONSTRAINT `user_status_fk` FOREIGN KEY (`user_status`) REFERENCES `statuses` (`status_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','vladislav.drobot@mail.ru','$2a$10$t9rvEg1ckVeGK2MiGmHIEuN6wMnIK5DCRf4RD3BNI19hFlo8sd9ea','ADMIN',127),(6,'assistant','assistant@mail.com','$2a$10$b8OJtHGPNohZIa/KjRcTPOh3cIp1KNnN4PvfRCNv7/LTy82lYJHjq','ASSISTANT',0),(8,'doctor','doctor@mail.ru','$2a$10$cylTOvHQ2nQT4KbzeQuQAOVJzWK6EjMKiF3HDCHe.Av23LD.iHrTm','DOCTOR',0),(9,'login','login@mail.ru','$2a$10$H.vxFizToBtudTvQmvHyz.tJaa/5NL32O0KqO6m9rAvqa76EkqwVW','DOCTOR',0),(10,'james240597','james240597@mail.com','$2a$10$u8eYMX0NsO7RTln8KKVV1eji7b5CnfASTUTHS4gImqcri/JbC8UJu','ASSISTANT',1),(11,'alex777','alex72@email.com','$2a$10$//MqEBrLI/0z8KFLQJKzaeiAqL/IrVd/8/tAPDwWwNNxl87IyyJkK','DOCTOR',0),(12,'mr-Conway','apple81@email.com','$2a$10$OpV59utrCW/hUxg/ihRq2OImYmKfTOy1TRmpQwAqfbIYZi6FhyJG2','DOCTOR',0),(13,'temp_USER','temporary@mail.ru','$2a$10$qmrXHq42kV4vpBM5wryOF.g.oQH74y/gw7w/DKF9OHJjT46.4Hfb.','ASSISTANT',0),(14,'hero01','some_mail@email.com','$2a$10$0zJn9O9vfJVXSTk2pj3yYust8N3cHjyXMi7Gfc3orwN7ji7Xv9Tum','ASSISTANT',1),(15,'carlos','sainz_for_offers@email.com','$2a$10$WJF4mkcEO59VGXz1MeVnzOfJsB9SOAAiIoLC9GPB8zHtHOJ0YJmle','ASSISTANT',1),(16,'user2901289','user2901289@email.com','$2a$10$YBH0OL3Rhb737r1iuXBeFuMaiW/63d1MGSB9B183MCo8Xo33dHgWq','ASSISTANT',0),(17,'mercedes','lewis@gmail.com','$2a$10$fT2om9kgCoZuRz.CHl9wMuYHjbE0qOsYtOOfWKEMmBUjGa6Z3aOnu','ASSISTANT',0),(18,'max1234','max@email.com','$2a$10$hmc6XR9HmPtqX0/c0lB4CuhxYDhoY2NtK64vFt3RbMDNreJNdO1wq','DOCTOR',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-23 11:52:00
