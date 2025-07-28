CREATE DATABASE  IF NOT EXISTS `mydb` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mydb`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `items` (
  `itemID` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(45) NOT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`itemID`),
  UNIQUE KEY `itemID_UNIQUE` (`itemID`)
) ENGINE=InnoDB AUTO_INCREMENT=341053320 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (341053316,'Gatorade',50.00,98),(341053317,'Pocari Sweat',60.00,100),(341053318,'Quest Protein Bar',235.00,100),(341053319,'Pure Protein Bar',150.00,100);
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `memberID` int unsigned NOT NULL AUTO_INCREMENT,
  `last_name` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `date_of_birth` date NOT NULL,
  `contact_information` varchar(11) NOT NULL,
  `registration_date` date DEFAULT NULL,
  `signed_waver` enum('TRUE','FALSE') DEFAULT NULL,
  `membership_status` enum('ACTIVE','INACTIVE','EXPIRED') DEFAULT NULL,
  PRIMARY KEY (`memberID`),
  UNIQUE KEY `customerID_UNIQUE` (`memberID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `membership_types`
--

DROP TABLE IF EXISTS `membership_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `membership_types` (
  `membership_type` varchar(45) NOT NULL,
  `price` double unsigned NOT NULL,
  PRIMARY KEY (`membership_type`),
  UNIQUE KEY `card_types_UNIQUE` (`membership_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `membership_types`
--

LOCK TABLES `membership_types` WRITE;
/*!40000 ALTER TABLE `membership_types` DISABLE KEYS */;
INSERT INTO `membership_types` VALUES ('NORMAL',0),('PWD',0.2),('SENIOR',0.2);
/*!40000 ALTER TABLE `membership_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `memberships`
--

DROP TABLE IF EXISTS `memberships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `memberships` (
  `membershipID` int unsigned NOT NULL AUTO_INCREMENT,
  `memberID` int unsigned NOT NULL,
  `membership_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`membershipID`),
  UNIQUE KEY `cardID_UNIQUE` (`membershipID`),
  KEY `FKmt01_idx` (`membership_type`),
  KEY `FKmt02_idx` (`memberID`),
  CONSTRAINT `FKmt01` FOREIGN KEY (`membership_type`) REFERENCES `membership_types` (`membership_type`),
  CONSTRAINT `FKmt02` FOREIGN KEY (`memberID`) REFERENCES `members` (`memberID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memberships`
--

LOCK TABLES `memberships` WRITE;
/*!40000 ALTER TABLE `memberships` DISABLE KEYS */;
/*!40000 ALTER TABLE `memberships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `salesID` int NOT NULL AUTO_INCREMENT,
  `transactionID` int DEFAULT NULL,
  `itemID` int NOT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`salesID`),
  UNIQUE KEY `salesID_UNIQUE` (`salesID`),
  KEY `FKst02_idx` (`itemID`),
  KEY `FKst01_idx` (`transactionID`),
  CONSTRAINT `FKst01` FOREIGN KEY (`transactionID`) REFERENCES `transactions` (`transactionID`),
  CONSTRAINT `FKst02` FOREIGN KEY (`itemID`) REFERENCES `items` (`itemID`)
) ENGINE=InnoDB AUTO_INCREMENT=98368 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES (6490,13368,341053316,2),(41545,33682,341053316,2),(50367,54682,341053316,1),(51745,54682,341053316,1),(68709,54682,341053316,1),(79912,19180,341053316,2),(82821,33682,341053316,2),(89456,54682,341053316,1),(90443,19180,341053316,95),(92834,377,341053317,100),(98364,93156,341053318,100);
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `transactionID` int NOT NULL AUTO_INCREMENT,
  `memberID` int DEFAULT NULL,
  `transaction_date` datetime DEFAULT NULL,
  `amount` decimal(10,0) NOT NULL,
  `transaction_type` enum('membership_purchase','membership_renewal','consumables','merchandise') DEFAULT NULL,
  PRIMARY KEY (`transactionID`),
  UNIQUE KEY `saleID_UNIQUE` (`transactionID`),
  KEY `FKts01_idx` (`memberID`)
) ENGINE=InnoDB AUTO_INCREMENT=99500 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (377,NULL,'2025-07-28 20:48:30',6000,'consumables'),(13368,NULL,'2025-07-28 21:08:33',100,'consumables'),(19180,NULL,'2025-07-28 20:42:00',4850,'consumables'),(31931,NULL,'2025-07-28 20:38:24',0,'consumables'),(33682,NULL,'2025-07-28 20:38:56',200,'consumables'),(54682,NULL,'2025-07-28 20:42:39',200,'consumables'),(93156,NULL,'2025-07-28 20:49:00',23500,'consumables');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-28 21:19:08
