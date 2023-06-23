-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 23, 2023 at 07:28 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `chatverse`
--

-- --------------------------------------------------------

--
-- Table structure for table `chat`
--

CREATE TABLE `chat` (
  `id` int(11) NOT NULL,
  `user1` varchar(100) DEFAULT NULL,
  `user2` varchar(100) DEFAULT NULL,
  `text` varchar(1000) DEFAULT NULL,
  `first_msg` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chat`
--

INSERT INTO `chat` (`id`, `user1`, `user2`, `text`, `first_msg`) VALUES
(3, 'mahfuz', 'rahim', 'hi', 1),
(4, 'mahfuz', 'rahim', 'Hi', NULL),
(6, 'rahim', 'mahfuz', 'wow', NULL),
(7, 'mahfuz', 'rahim', 'its working', 0),
(8, 'rahim', 'mahfuz', 'yes its working', 0),
(10, 'mahfuz', 'sohag', 'hi', 1),
(11, 'mahfuz', 'mahfuz', 'Hi', 1),
(23, 'mahfuz', 'rahim', 'hi', 0),
(25, 'mahfuz', 'rahim', 'hi', 0),
(28, 'mahfuz', 'joy', 'Hi', 1),
(29, 'mahfuz', 'joy', 'hlw', 0),
(30, 'user1', 'mahfuz', 'Hi', 1),
(31, 'mahfuz', 'user1', 'Hlw', 0),
(32, 'user1', 'joy', 'Hi', 1),
(33, 'user2', 'user1', 'Hi', 1),
(34, 'user1', 'user2', 'hi', 0),
(35, 'user2', 'user1', 'test', 0),
(36, 'user3', 'rahim', 'Hi', 1),
(37, 'user3', 'sohag', 'Hi', 1),
(38, 'user1', 'user4', 'Hi', 1),
(39, 'user4', 'user1', 'Hi', 0);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`) VALUES
(4, 'mahfuz', '1234'),
(5, 'rahim', '1234'),
(6, 'sohag', '1234'),
(7, 'joy', '1234'),
(8, 'user1', '1234'),
(9, 'user2', '1234'),
(10, 'user3', '1234'),
(11, 'user4', '1234');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `usename` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chat`
--
ALTER TABLE `chat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
