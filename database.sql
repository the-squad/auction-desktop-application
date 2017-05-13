-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 14, 2017 at 01:02 AM
-- Server version: 10.1.10-MariaDB
-- PHP Version: 5.5.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project-firefly`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddNotifyOnStart` ()  NO SQL
BEGIN
DECLARE v_auction_ID INT;
DECLARE cursor1 CURSOR FOR (SELECT auctions.ID FROM `auctions` WHERE auctions.StartDate <= CURRENT_TIMESTAMP);
OPEN cursor1;
read_loop: LOOP
	FETCH cursor1 INTO v_auction_ID;
    DELETE FROM notifications WHERE notifications.status = '2' AND notifications.auctionId = v_auction_ID;
    INSERT INTO notifications (`auctionId`,`status`) VALUES (v_auction_ID , '2');
 END LOOP read_loop;
CLOSE cursor1;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdatingItemState` ()  BEGIN
DECLARE v_auction_ID INT;
DECLARE v_auction_ItemID INT;
DECLARE v_auction_ItemQuantity INT;
DECLARE v_auction_highestBidder INT;
DECLARE inverntory_ID INT;
DECLARE item_id int;
DECLARE imag_item mediumblob;
DECLARE cursor1 CURSOR FOR (SELECT auctions.ID,auctions.ItemID, auctions.ItemQuantity,(SELECT bids.UserID FROM bids WHERE bids.AuctionID=auctions.ID ORDER BY bids.Price DESC LIMIT 1) AS `highestBidder` FROM `auctions` WHERE TerminationDate < CURRENT_TIMESTAMP);
OPEN cursor1;
read_loop: LOOP
	FETCH cursor1 INTO v_auction_ID,v_auction_ItemID, v_auction_ItemQuantity, v_auction_highestBidder;
      
IF v_auction_highestBidder is null THEN
    UPDATE items set items.Quantity = items.Quantity + v_auction_ItemQuantity WHERE items.ID = v_auction_ItemID;
    else
    select getinv(v_auction_highestBidder) into inverntory_ID;
    INSERT INTO `items`(InventoryID, CategoryID, Name, Quantity, Description)
    SELECT  inverntory_ID, `CategoryID`, `Name`, v_auction_ItemQuantity , `Description` FROM items WHERE items.ID = v_auction_ItemID;
    SELECT MAX(id) INTO item_id FROM items WHERE items.InventoryID = inverntory_ID;
    INSERT INTO `notifications`(`itemId`, `status`) VALUES (item_id , '0');
    SELECT`images`.`Image` into imag_item FROM images WHERE images.ItemID = v_auction_ItemID LIMIT 1;
    INSERT INTO `images`(`ItemID`, `Image`) VALUES (item_id,imag_item);
    END IF;
    DELETE FROM auctions WHERE auctions.ID = v_auction_ID;
 END LOOP read_loop;
CLOSE cursor1;
END$$

--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `getinv` (`id` INT(200) UNSIGNED) RETURNS INT(200) UNSIGNED NO SQL
BEGIN
DECLARE invid INT;
SELECT inventories.ID INTO invid from inventories JOIN users ON inventories.SellerID = users.ID WHERE users.ID = id;
RETURN invid;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `auctions`
--

CREATE TABLE `auctions` (
  `ID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `ItemID` int(11) NOT NULL,
  `ItemQuantity` int(11) NOT NULL,
  `StartDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `TerminationDate` datetime NOT NULL,
  `InitialPrice` double NOT NULL,
  `BidRate` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `auction_reports`
--

CREATE TABLE `auction_reports` (
  `ID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `AuctionID` int(11) NOT NULL,
  `Message` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bids`
--

CREATE TABLE `bids` (
  `ID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `AuctionID` int(11) NOT NULL,
  `Price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Triggers `bids`
--
DELIMITER $$
CREATE TRIGGER `AddBidNotify` AFTER INSERT ON `bids` FOR EACH ROW BEGIN
	DELETE FROM notifications WHERE notifications.auctionId = NEW.AuctionID and status = '1';
    INSERT INTO notifications(`auctionId`,`status`) VALUES (NEW.AuctionID , '1');
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `ID` int(11) NOT NULL,
  `Name` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`ID`, `Name`) VALUES
(1, 'All'),
(2, 'Appliances'),
(3, 'Automotive Parts'),
(4, 'Books'),
(5, 'Computers'),
(6, 'Electronics'),
(7, 'Smart Phones'),
(8, 'Smart Watches');

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

CREATE TABLE `images` (
  `ID` int(11) NOT NULL,
  `ItemID` int(11) NOT NULL,
  `Image` mediumblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `inventories`
--

CREATE TABLE `inventories` (
  `ID` int(11) NOT NULL,
  `SellerID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `ID` int(11) NOT NULL,
  `InventoryID` int(11) NOT NULL,
  `CategoryID` int(11) NOT NULL,
  `Name` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Description` varchar(5000) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `ID` int(11) NOT NULL,
  `auctionId` int(11) DEFAULT NULL,
  `itemId` int(11) DEFAULT NULL,
  `status` enum('0','1','2') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `seller_reports`
--

CREATE TABLE `seller_reports` (
  `ID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `SellerID` int(11) NOT NULL,
  `Message` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `subscribe_auctions`
--

CREATE TABLE `subscribe_auctions` (
  `ID` int(11) NOT NULL,
  `AuctionID` int(11) NOT NULL,
  `SubscriberID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `subscribe_sellers`
--

CREATE TABLE `subscribe_sellers` (
  `ID` int(11) NOT NULL,
  `SubscriberID` int(11) NOT NULL,
  `SelleID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `UserTypeID` int(11) NOT NULL,
  `Name` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Phone` varchar(13) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Address` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Photo` mediumblob,
  `Password` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_types`
--

CREATE TABLE `user_types` (
  `ID` int(11) NOT NULL,
  `Type` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user_types`
--

INSERT INTO `user_types` (`ID`, `Type`) VALUES
(1, 'Admin'),
(2, 'Seller'),
(3, 'Buyer');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `auctions`
--
ALTER TABLE `auctions`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Fk_user_id` (`UserID`),
  ADD KEY `Fk_item_id` (`ItemID`);

--
-- Indexes for table `auction_reports`
--
ALTER TABLE `auction_reports`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_UserReport_id` (`UserID`),
  ADD KEY `FK_AuctionReport_id` (`AuctionID`);

--
-- Indexes for table `bids`
--
ALTER TABLE `bids`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_Bider` (`UserID`),
  ADD KEY `FK_Auction_id` (`AuctionID`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_Item_Image` (`ItemID`);

--
-- Indexes for table `inventories`
--
ALTER TABLE `inventories`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_Seller_Inventory` (`SellerID`);

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_InventoryId_item` (`InventoryID`),
  ADD KEY `FK_Catagory_item` (`CategoryID`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `auctionID_FK` (`auctionId`),
  ADD KEY `ItemId_FK` (`itemId`);

--
-- Indexes for table `seller_reports`
--
ALTER TABLE `seller_reports`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Fk_ReporterID_User` (`UserID`),
  ADD KEY `Fk_Seller_User` (`SellerID`);

--
-- Indexes for table `subscribe_auctions`
--
ALTER TABLE `subscribe_auctions`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_Auction_Subscriber` (`AuctionID`),
  ADD KEY `FK_user_Subscriber` (`SubscriberID`);

--
-- Indexes for table `subscribe_sellers`
--
ALTER TABLE `subscribe_sellers`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_SUB` (`SubscriberID`),
  ADD KEY `FK_Seller` (`SelleID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_UserType` (`UserTypeID`);

--
-- Indexes for table `user_types`
--
ALTER TABLE `user_types`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `auctions`
--
ALTER TABLE `auctions`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
--
-- AUTO_INCREMENT for table `auction_reports`
--
ALTER TABLE `auction_reports`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `bids`
--
ALTER TABLE `bids`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=448;
--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `images`
--
ALTER TABLE `images`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `inventories`
--
ALTER TABLE `inventories`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `items`
--
ALTER TABLE `items`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=98;
--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;
--
-- AUTO_INCREMENT for table `seller_reports`
--
ALTER TABLE `seller_reports`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `subscribe_auctions`
--
ALTER TABLE `subscribe_auctions`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `subscribe_sellers`
--
ALTER TABLE `subscribe_sellers`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `user_types`
--
ALTER TABLE `user_types`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `auctions`
--
ALTER TABLE `auctions`
  ADD CONSTRAINT `Fk_item_id` FOREIGN KEY (`ItemID`) REFERENCES `items` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Fk_user_id` FOREIGN KEY (`UserID`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `auction_reports`
--
ALTER TABLE `auction_reports`
  ADD CONSTRAINT `FK_AuctionReport_id` FOREIGN KEY (`AuctionID`) REFERENCES `auctions` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_UserReport_id` FOREIGN KEY (`UserID`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `bids`
--
ALTER TABLE `bids`
  ADD CONSTRAINT `FK_Auction_id` FOREIGN KEY (`AuctionID`) REFERENCES `auctions` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_Bider` FOREIGN KEY (`UserID`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `images`
--
ALTER TABLE `images`
  ADD CONSTRAINT `FK_Item_Image` FOREIGN KEY (`ItemID`) REFERENCES `items` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `inventories`
--
ALTER TABLE `inventories`
  ADD CONSTRAINT `FK_Seller_Inventory` FOREIGN KEY (`SellerID`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `items`
--
ALTER TABLE `items`
  ADD CONSTRAINT `FK_Catagory_item` FOREIGN KEY (`CategoryID`) REFERENCES `categories` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_InventoryId_item` FOREIGN KEY (`InventoryID`) REFERENCES `inventories` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `ItemId_FK` FOREIGN KEY (`itemId`) REFERENCES `items` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `auctionID_FK` FOREIGN KEY (`auctionId`) REFERENCES `auctions` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `seller_reports`
--
ALTER TABLE `seller_reports`
  ADD CONSTRAINT `Fk_ReporterID_User` FOREIGN KEY (`UserID`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Fk_Seller_User` FOREIGN KEY (`SellerID`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `subscribe_auctions`
--
ALTER TABLE `subscribe_auctions`
  ADD CONSTRAINT `FK_Auction_Subscriber` FOREIGN KEY (`AuctionID`) REFERENCES `auctions` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_user_Subscriber` FOREIGN KEY (`SubscriberID`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `subscribe_sellers`
--
ALTER TABLE `subscribe_sellers`
  ADD CONSTRAINT `FK_SUB` FOREIGN KEY (`SubscriberID`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_Seller` FOREIGN KEY (`SelleID`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FK_UserType` FOREIGN KEY (`UserTypeID`) REFERENCES `user_types` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

DELIMITER $$
--
-- Events
--
CREATE DEFINER=`root`@`localhost` EVENT `TerminateAuction` ON SCHEDULE EVERY 1 SECOND STARTS '2017-05-14 00:00:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
call UpdatingItemState();
call AddNotifyOnStart();
END$$

DELIMITER ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
