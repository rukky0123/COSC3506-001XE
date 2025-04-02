-- MySQL dump 10.13  Distrib 9.2.0, for macos14.7 (arm64)
--
-- Host: 34.130.26.167    Database: carrental
-- ------------------------------------------------------
-- Server version	8.0.41-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `CR_Booking`
--

DROP TABLE IF EXISTS `CR_Booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CR_Booking` (
  `booking_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `car_id` int DEFAULT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `status` enum('Pending','Confirmed','Cancelled','Completed') DEFAULT 'Pending',
  PRIMARY KEY (`booking_id`),
  KEY `user_id` (`user_id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `cr_booking_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `CR_User` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `cr_booking_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `CR_Inventory` (`car_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CR_Booking`
--

LOCK TABLES `CR_Booking` WRITE;
/*!40000 ALTER TABLE `CR_Booking` DISABLE KEYS */;
INSERT INTO `CR_Booking` VALUES (2,3,2,'2024-03-20','2024-03-22','Confirmed'),(4,3,2,'2024-03-20','2024-03-25','Cancelled'),(6,3,2,'2025-03-24','2025-03-25','Pending'),(16,3,2,'2025-03-10','2025-03-12','Completed'),(17,3,3,'2025-03-11','2025-03-14','Completed'),(18,6,4,'2025-03-12','2025-03-15','Cancelled'),(19,10,5,'2025-03-13','2025-03-17','Completed'),(20,11,6,'2025-03-14','2025-03-18','Completed'),(21,12,43,'2025-03-15','2025-03-19','Pending'),(22,13,44,'2025-03-16','2025-03-20','Completed'),(23,15,45,'2025-03-17','2025-03-21','Cancelled'),(24,44,46,'2025-03-18','2025-03-22','Completed'),(25,46,47,'2025-03-19','2025-03-23','Completed'),(26,47,48,'2025-03-20','2025-03-24','Pending'),(27,49,2,'2025-03-10','2025-03-11','Completed'),(28,3,3,'2025-03-11','2025-03-13','Completed'),(29,6,4,'2025-03-12','2025-03-14','Cancelled'),(30,10,5,'2025-03-13','2025-03-16','Completed'),(31,11,6,'2025-03-14','2025-03-17','Completed'),(32,12,43,'2025-03-15','2025-03-18','Pending'),(33,13,44,'2025-03-16','2025-03-19','Completed'),(34,15,45,'2025-03-17','2025-03-20','Cancelled'),(35,44,46,'2025-03-18','2025-03-21','Completed'),(36,46,47,'2025-03-19','2025-03-22','Completed'),(37,47,48,'2025-03-20','2025-03-23','Pending'),(38,49,2,'2025-03-10','2025-03-12','Completed'),(39,3,3,'2025-03-11','2025-03-13','Completed'),(40,6,4,'2025-03-12','2025-03-15','Cancelled'),(41,10,5,'2025-03-13','2025-03-16','Completed'),(42,11,6,'2025-03-14','2025-03-18','Completed'),(43,12,43,'2025-03-15','2025-03-19','Pending'),(44,13,44,'2025-03-16','2025-03-20','Completed'),(45,15,45,'2025-03-17','2025-03-21','Cancelled'),(46,44,46,'2025-03-18','2025-03-22','Completed'),(47,46,47,'2025-03-19','2025-03-23','Completed'),(48,47,48,'2025-03-20','2025-03-24','Pending'),(49,49,2,'2025-03-10','2025-03-12','Completed'),(50,3,3,'2025-03-11','2025-03-13','Completed');
/*!40000 ALTER TABLE `CR_Booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CR_Inventory`
--

DROP TABLE IF EXISTS `CR_Inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CR_Inventory` (
  `car_id` int NOT NULL AUTO_INCREMENT,
  `make` varchar(50) NOT NULL,
  `model` varchar(50) NOT NULL,
  `year` int NOT NULL,
  `price_per_day` decimal(10,2) NOT NULL,
  `availability` tinyint(1) DEFAULT '1',
  `image_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`car_id`)
) ENGINE=InnoDB AUTO_INCREMENT=257 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CR_Inventory`
--

LOCK TABLES `CR_Inventory` WRITE;
/*!40000 ALTER TABLE `CR_Inventory` DISABLE KEYS */;
INSERT INTO `CR_Inventory` VALUES (2,'Honda','Civic',2019,45.00,1,'1742751500387_civic-removebg-preview.png'),(3,'Ford','Focus',2021,55.00,1,'1742751490393_focus-removebg-preview.png'),(4,'Lamborghini','Gallardo',2023,150.00,1,'1742751480868_gallardo-removebg-preview.png'),(5,'Range Rover','Velar',2024,140.00,0,'1742751075715_image.png'),(6,'Ford','Mustang',2025,80.00,1,'1742751631393_mustang-removebg-preview.png'),(43,'Ford','Fusion',2020,48.00,1,'1742877008578_FordFusion.jpeg'),(44,'Chevrolet','Suburban',2022,68.00,1,'1742877646872_2022-Chevrolet-Suburban-front_50166_032_1836x867_G6M_cropped.jpeg'),(45,'Hyundai','Sonata',2022,60.00,0,'1742877865170_2022-hyundai-sonata-sel-plus-sedan-angular-front.jpeg'),(46,'Chevrolet','Malibu',2022,52.00,1,'1742878030005_2025-malibu-1zs69-1sp-gnt-trimselector.jpeg'),(47,'Toyota','Rav4',2025,54.00,1,'1742878067819_2025-toyota-rav4-hybrid-gray.jpeg'),(48,'Nissan','Altima',2024,52.00,1,'1742878135254_2024-Nissan-Altima-SR-Blue.jpeg');
/*!40000 ALTER TABLE `CR_Inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CR_Maintenance`
--

DROP TABLE IF EXISTS `CR_Maintenance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CR_Maintenance` (
  `maintenance_id` int NOT NULL AUTO_INCREMENT,
  `car_id` int DEFAULT NULL,
  `maintenance_date` date NOT NULL,
  `details` text NOT NULL,
  PRIMARY KEY (`maintenance_id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `cr_maintenance_ibfk_1` FOREIGN KEY (`car_id`) REFERENCES `CR_Inventory` (`car_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CR_Maintenance`
--

LOCK TABLES `CR_Maintenance` WRITE;
/*!40000 ALTER TABLE `CR_Maintenance` DISABLE KEYS */;
INSERT INTO `CR_Maintenance` VALUES (2,2,'2024-03-22','Brake Inspection and Engine Tune-up');
/*!40000 ALTER TABLE `CR_Maintenance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CR_Payment`
--

DROP TABLE IF EXISTS `CR_Payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CR_Payment` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `booking_id` int DEFAULT NULL,
  `amount` decimal(10,2) NOT NULL,
  `payment_status` enum('Paid','Pending','Failed') DEFAULT 'Pending',
  `payment_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`payment_id`),
  KEY `booking_id` (`booking_id`),
  CONSTRAINT `cr_payment_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `CR_Booking` (`booking_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CR_Payment`
--

LOCK TABLES `CR_Payment` WRITE;
/*!40000 ALTER TABLE `CR_Payment` DISABLE KEYS */;
INSERT INTO `CR_Payment` VALUES (2,2,225.00,'Pending','2025-03-22 20:25:09'),(3,16,90.00,'Paid','2025-03-12 00:00:00'),(4,17,165.00,'Paid','2025-03-14 00:00:00'),(5,19,560.00,'Paid','2025-03-17 00:00:00'),(6,20,320.00,'Paid','2025-03-18 00:00:00'),(7,22,272.00,'Paid','2025-03-20 00:00:00'),(8,24,216.00,'Paid','2025-03-22 00:00:00'),(9,25,208.00,'Paid','2025-03-23 00:00:00'),(10,27,45.00,'Paid','2025-03-11 00:00:00'),(11,28,110.00,'Paid','2025-03-13 00:00:00'),(12,30,420.00,'Paid','2025-03-16 00:00:00'),(13,31,240.00,'Paid','2025-03-17 00:00:00'),(14,33,204.00,'Paid','2025-03-19 00:00:00'),(15,35,162.00,'Paid','2025-03-21 00:00:00'),(16,36,156.00,'Paid','2025-03-22 00:00:00'),(17,38,90.00,'Paid','2025-03-12 00:00:00'),(18,39,110.00,'Paid','2025-03-13 00:00:00'),(19,41,420.00,'Paid','2025-03-16 00:00:00'),(20,42,320.00,'Paid','2025-03-18 00:00:00'),(21,44,272.00,'Paid','2025-03-20 00:00:00'),(22,46,216.00,'Paid','2025-03-22 00:00:00'),(23,47,208.00,'Paid','2025-03-23 00:00:00'),(24,49,90.00,'Paid','2025-03-12 00:00:00'),(25,50,110.00,'Paid','2025-03-13 00:00:00');
/*!40000 ALTER TABLE `CR_Payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CR_User`
--

DROP TABLE IF EXISTS `CR_User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CR_User` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('Admin','Staff','Customer') NOT NULL,
  `email` varchar(100) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `address1` varchar(100) DEFAULT NULL,
  `address2` varchar(100) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `province` varchar(50) DEFAULT NULL,
  `postal_code` varchar(20) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `card_name` varchar(100) DEFAULT NULL,
  `card_number` varchar(20) DEFAULT NULL,
  `exp_month` varchar(5) DEFAULT NULL,
  `exp_year` varchar(5) DEFAULT NULL,
  `security_code` varchar(10) DEFAULT NULL,
  `card_postal` varchar(20) DEFAULT NULL,
  `card_country` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=267 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CR_User`
--

LOCK TABLES `CR_User` WRITE;
/*!40000 ALTER TABLE `CR_User` DISABLE KEYS */;
INSERT INTO `CR_User` VALUES (1,'admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','Admin','admin@carrental.com','2025-03-17 21:04:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'staff','1562206543da764123c21bd524674f0a8aaf49c8a89744c97352fe677f7e4006','Staff','staff1@carrental.com','2025-03-17 21:04:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,'customer','b6c45863875e34487ca3c155ed145efe12a74581e27befec5aa661b8ee8ca6dd','Customer','customer1@carrental.com','2025-03-17 21:04:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,'pb@aa.com','849f1575ccfbf3a4d6cf00e6c5641b7fd4da2ed3e212c2d79ba9161a5a432ff0','Customer','pb@aa.com','2025-03-18 20:28:01',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,'pb@yoop.com','9be6fe965f5fb561fc312df93637ad68b384ce3b203b76ba04d0719a4412c6ad','Customer','pb@yoop.com','2025-03-18 20:34:26',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,'test','9be6fe965f5fb561fc312df93637ad68b384ce3b203b76ba04d0719a4412c6ad','Customer','testuser@aa.com','2025-03-21 22:18:24',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10,'thirduser','b816140000cb14150c1fdb98c83edadc3fc8fe4dc43647312559be8b202fb1be','Staff','thirduser@aca.com','2025-03-22 18:45:14',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,'test@last.com','c782df88cf0a0346bea06a43c2774dcce7f6f1fd3ba64a3940bb543196b8db46','Customer','test@last.com','2025-03-23 12:30:04',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(12,'test@last1.com','fb00ee8b8548a4f94e6e379c3979db3947cd79d41a54b2d7d53b032a0697812f','Customer','test@last1.com','2025-03-23 12:53:10','Test','Last1','11 Bay ST','','Toronto','ON','L6V3C8','CANADA','LAST1','1234567890123456','02','2027','123','L6V3C8','CANADA'),(13,'deepa@test.com','c782df88cf0a0346bea06a43c2774dcce7f6f1fd3ba64a3940bb543196b8db46','Admin','deepa@test.com','2025-03-23 14:23:39','Deepa','Test','10 Bay Street','unit 1','Toronto','Ontario','M5J0B8','CANADA','DEEPA TEST','1234567890123456','02','2027','123','M5J0B8','CANADA'),(15,'Deepasree','fb00ee8b8548a4f94e6e379c3979db3947cd79d41a54b2d7d53b032a0697812f','Admin','deepa2@test.com','2025-03-23 15:53:50','DEEPASREE','MP','10 Queen Street','Unit 3','BRAMPTON','ONTARIO','L6V5C9','CANADA','DEEPA','1234567890123456','09','2029','123','L6V5C9','CANADA'),(44,'user4','ea172dcf943ad14b3a583ba2883c9a2a5fa2c3f12a4ca183ca1de11615b20e42','Staff','user4@ca.com','2025-03-23 18:43:44',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(45,'john','ff7bd97b1a7789ddd2775122fd6817f3173672da9f802ceec57f284325bf589f','Customer','john.doe@email.com','2025-03-25 04:52:43','John','Doe','123 Main St',NULL,'Toronto','ON','M4B1B3','Canada','John Doe','4111111111111111','12','2026','123','M4B1B3','Canada'),(46,'jane','ff7bd97b1a7789ddd2775122fd6817f3173672da9f802ceec57f284325bf589f','Customer','jane.smith@email.com','2025-03-25 04:52:43','Jane','Smith','456 Queen St','Apt 12','Vancouver','BC','V5K1A1','Canada','Jane Smith','5500000000000004','06','2025','456','V5K1A1','Canada'),(47,'michael','ff7bd97b1a7789ddd2775122fd6817f3173672da9f802ceec57f284325bf589f','Customer','michael.jones@email.com','2025-03-25 04:52:43','Michael','Jones','789 King St',NULL,'Montreal','QC','H3Z2Y7','Canada','Michael Jones','340000000000009','09','2027','789','H3Z2Y7','Canada'),(48,'susan','ff7bd97b1a7789ddd2775122fd6817f3173672da9f802ceec57f284325bf589f','Staff','susan.wilson@carrental.com','2025-03-25 04:52:43','Susan','Wilson','101 Bay St',NULL,'Calgary','AB','T2P1J3','Canada','Susan Wilson','340000000000009','09','2027','789','H3Z2Y7','Canada'),(49,'david','ff7bd97b1a7789ddd2775122fd6817f3173672da9f802ceec57f284325bf589f','Staff','david.miller@carrental.com','2025-03-25 04:52:43','David','Miller','200 Front St',NULL,'Ottawa','ON','K1P5N7','Canada','David Miller','340000000000009','09','2027','789','H3Z2Y7','Canada'),(198,'testuser','ff7bd97b1a7789ddd2775122fd6817f3173672da9f802ceec57f284325bf589f','Customer','test@example.com','2025-03-27 08:08:22','Test','User','123 Street','','Toronto','ON','M1M1M1','Canada','Test User','4111111111111111','12','2030','123','M1M1M1','Canada');
/*!40000 ALTER TABLE `CR_User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'carrental'
--
