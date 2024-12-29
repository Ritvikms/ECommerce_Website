
 -- -----------------------------------------------------
 -- Schema full-stack-sports-center
 -- -----------------------------------------------------
 CREATE DATABASE IF NOT EXISTS `sportscenter`;
 --
 USE `sportscenter` ;

 -- Drop existing tables if they exist
 DROP TABLE IF EXISTS Product;
 DROP TABLE IF EXISTS Brand;
 DROP TABLE IF EXISTS Type;

 -- Create the Brand table
 CREATE TABLE `Brand` (
                          `Id` INT AUTO_INCREMENT PRIMARY KEY,
                          `Name` VARCHAR(255) NOT NULL
 );
 -- Create the Type table
 CREATE TABLE `Type` (
                         `Id` INT AUTO_INCREMENT PRIMARY KEY,
                         `Name` VARCHAR(255) NOT NULL
 );

 -- Create the Product table
 CREATE TABLE `Product` (
                            `Id` INT AUTO_INCREMENT PRIMARY KEY,
                            `Name` VARCHAR(255) NOT NULL,
                            `Description` TEXT,
                            `Price` DECIMAL(10, 2) NOT NULL,
                            `PictureUrl` VARCHAR(255),
                            `ProductTypeId` INT NOT NULL,
                            `ProductBrandId` INT NOT NULL,
                            FOREIGN KEY (`ProductTypeId`) REFERENCES `Type`(`Id`),
                            FOREIGN KEY (`ProductBrandId`) REFERENCES `Brand`(`Id`)
 );
