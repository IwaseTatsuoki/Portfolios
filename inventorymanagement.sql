-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- ホスト: 127.0.0.1
-- 生成日時: 2022-04-23 09:15:20
-- サーバのバージョン： 10.4.21-MariaDB
-- PHP のバージョン: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- データベース: `inventorymanagement`
--

-- --------------------------------------------------------

--
-- テーブルの構造 `category`
--

CREATE TABLE `category` (
  `category_code` varchar(5) NOT NULL,
  `category_type` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- テーブルのデータのダンプ `category`
--

INSERT INTO `category` (`category_code`, `category_type`) VALUES
('cg1', '帽子'),
('cg2', 'トップス'),
('cg3', 'パンツ'),
('cg4', 'アウター'),
('cg5', 'シューズ'),
('cg6', 'バッグ');

-- --------------------------------------------------------

--
-- テーブルの構造 `color`
--

CREATE TABLE `color` (
  `color_code` varchar(10) NOT NULL,
  `color_type` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- テーブルのデータのダンプ `color`
--

INSERT INTO `color` (`color_code`, `color_type`) VALUES
('c1', 'white'),
('c2', 'black'),
('c3', 'red'),
('c4', 'green'),
('c5', 'blue'),
('c6', 'yellow'),
('c7', 'orange'),
('c8', 'brown');

-- --------------------------------------------------------

--
-- テーブルの構造 `inventory`
--

CREATE TABLE `inventory` (
  `store_code` varchar(100) NOT NULL,
  `item_code` varchar(30) NOT NULL,
  `inventory_count` int(10) NOT NULL,
  `shipment_pending` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- テーブルのデータのダンプ `inventory`
--

INSERT INTO `inventory` (`store_code`, `item_code`, `inventory_count`, `shipment_pending`) VALUES
('sw1', 'i1', 3, 29),
('sw1', 'i2', 30, 25),
('sw1', 'i3', 1004, 14),
('sw1', 'i4', 144, 17),
('sw1', 'i5', 321, 14),
('sw1', 'i7', 47, 12),
('sw1', 'i8', 3462, 22),
('sw3', 'i1', 48, 4),
('sw3', 'i2', 70, 2),
('sw3', 'i3', 21, 91),
('sw3', 'i4', 68, 0),
('sw3', 'i5', 47, 12),
('sw3', 'i6', 33, 16),
('sw3', 'i7', 88, -7),
('sw3', 'i8', 34, -8),
('sw4', 'i1', 88, 100),
('sw4', 'i2', 10, 0),
('sw4', 'i3', 30, 4),
('sw4', 'i4', 21, 2),
('sw4', 'i5', 94, 10),
('sw4', 'i6', 53, 0),
('sw4', 'i7', 94, 0),
('sw4', 'i8', 67, 0);

-- --------------------------------------------------------

--
-- テーブルの構造 `item`
--

CREATE TABLE `item` (
  `item_code` varchar(30) NOT NULL,
  `item_name` varchar(100) NOT NULL,
  `price` int(15) NOT NULL,
  `best_before` date NOT NULL,
  `size` varchar(10) NOT NULL,
  `color` varchar(10) NOT NULL,
  `category_code` varchar(5) NOT NULL,
  `sex_code` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- テーブルのデータのダンプ `item`
--

INSERT INTO `item` (`item_code`, `item_name`, `price`, `best_before`, `size`, `color`, `category_code`, `sex_code`) VALUES
('i1', 'メッシュキャップ', 3500, '2022-09-15', 's1', 'c1', 'cg1', 'se1'),
('i2', 'ハット', 50000, '2029-03-08', 's4', 'c4', 'cg1', 'se2'),
('i3', 'ニット帽', 9800, '2025-06-13', 's5', 'c5', 'cg1', 'se2'),
('i4', 'Ｔシャツ', 3500, '2026-03-11', 's1', 'c2', 'cg2', 'se1'),
('i5', 'Ｔシャツ', 3500, '2023-03-16', 's2', 'c7', 'cg2', 'se3'),
('i6', 'Ｔシャツ\r\n', 3500, '2022-12-09', 's3', 'c8', 'cg2', 'se2'),
('i7', 'ボトムス', 7800, '2026-03-06', 's4', 'c3', 'cg3', 'se1'),
('i8', 'ボトムス', 7800, '2026-03-05', 's5', 'c6', 'cg3', 'se2');

-- --------------------------------------------------------

--
-- テーブルの構造 `sex`
--

CREATE TABLE `sex` (
  `sex_code` varchar(5) NOT NULL,
  `sex_type` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- テーブルのデータのダンプ `sex`
--

INSERT INTO `sex` (`sex_code`, `sex_type`) VALUES
('se1', 'man'),
('se2', 'female'),
('se3', 'unisex');

-- --------------------------------------------------------

--
-- テーブルの構造 `shipping_slip`
--

CREATE TABLE `shipping_slip` (
  `slip_code` varchar(100) NOT NULL,
  `slip_date` date NOT NULL,
  `sender` varchar(30) NOT NULL,
  `sending_address` varchar(30) NOT NULL,
  `decision_flag` int(1) NOT NULL DEFAULT 0,
  `cancel` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- テーブルのデータのダンプ `shipping_slip`
--

INSERT INTO `shipping_slip` (`slip_code`, `slip_date`, `sender`, `sending_address`, `decision_flag`, `cancel`) VALUES
('08fcdd14-0e66-4c77-b500-3bc29a33fcdc', '2022-04-01', 'sw3', 'sw1', 0, 1),
('1e9aca5a-4c09-4c89-93cd-ff8a8812ec1f', '2022-04-01', 'sw3', 'sw1', 0, 1),
('2fc786a9-89ee-4cd3-9f08-d0d733ed3d7c', '2022-04-01', 'sw1', 'sw3', 0, 0),
('50343044-7135-41e4-b792-850feb2e3bc5', '2022-04-01', 'sw3', 'sw1', 1, 0),
('50371f83-41a9-4f80-9764-afdd50b8dce1', '2022-04-09', 'sw1', 'sw3', 1, 0),
('54d15486-b7b3-4818-8f45-96a186820589', '2022-04-01', 'sw3', 'sw1', 1, 0),
('64551484-28dc-4b41-9a78-065cb4dd89a3', '2022-04-01', 'sw3', 'sw1', 1, 0),
('647a4911-8be5-4432-ad32-4423366b88f3', '2022-04-01', 'sw1', 'sw3', 0, 1),
('7ab64f47-e6b3-42d6-a5e0-29b0cfb7b541', '2022-04-01', 'sw3', 'sw1', 1, 0),
('84d0eb49-5dca-4eba-91c7-7f13f5abf5ad', '2022-04-01', 'sw3', 'sw1', 1, 0),
('92405ff7-87db-4500-a9ab-6cd31b2ca121', '2022-04-01', 'sw3', 'sw1', 0, 1),
('92661ccf-3438-445d-becb-4bb84a95d2e5', '2022-04-23', 'sw1', 'sw4', 1, 0),
('927e04ea-02b7-4ff0-bcaf-42927be1aa17', '2022-04-01', 'sw3', 'sw1', 0, 1),
('a1d952ad-0ff1-4158-bb1a-eae8f2a28771', '2022-04-01', 'sw3', 'sw1', 0, 0),
('c30a8f2b-d649-4db8-8874-1e121803714c', '2022-04-09', 'sw3', 'sw1', 0, 0),
('c88665c7-f9de-4e34-a433-cc6396586bd8', '2022-04-01', 'sw3', 'sw1', 0, 0),
('ca2d2c29-c84f-4d54-b1d6-559d3e579a9b', '2022-04-01', 'sw3', 'sw1', 0, 0),
('d6b1f1c4-e540-4923-a23f-9003ce4e4b09', '2022-04-04', 'sw1', 'sw4', 0, 0),
('d83bd270-e0ca-4e48-89b0-bc73fba9323d', '2022-04-01', 'sw3', 'sw1', 0, 1),
('dfe2c634-ae18-43b2-83f3-e3af7c91e707', '2022-04-01', 'sw3', 'sw1', 0, 0),
('e7ae0521-2ca8-48de-8216-af7aab7e64d8', '2022-04-01', 'sw3', 'sw1', 0, 1),
('fbde4e1c-070f-4551-84a1-4f3efa461363', '2022-04-01', 'sw3', 'sw1', 0, 0);

-- --------------------------------------------------------

--
-- テーブルの構造 `size`
--

CREATE TABLE `size` (
  `size_code` varchar(10) NOT NULL,
  `size_type` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- テーブルのデータのダンプ `size`
--

INSERT INTO `size` (`size_code`, `size_type`) VALUES
('s1', 'small'),
('s2', 'medium'),
('s3', 'large'),
('s4', 'xlarge'),
('s5', 'xxlarge');

-- --------------------------------------------------------

--
-- テーブルの構造 `slip_item_list`
--

CREATE TABLE `slip_item_list` (
  `slip_code` varchar(100) NOT NULL,
  `item_code` varchar(30) NOT NULL,
  `shipping_count` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- テーブルのデータのダンプ `slip_item_list`
--

INSERT INTO `slip_item_list` (`slip_code`, `item_code`, `shipping_count`) VALUES
('08fcdd14-0e66-4c77-b500-3bc29a33fcdc', 'i1', 1),
('1e9aca5a-4c09-4c89-93cd-ff8a8812ec1f', 'i1', 1),
('2fc786a9-89ee-4cd3-9f08-d0d733ed3d7c', 'i1', 20),
('50343044-7135-41e4-b792-850feb2e3bc5', 'i7', 7),
('50343044-7135-41e4-b792-850feb2e3bc5', 'i8', 8),
('50371f83-41a9-4f80-9764-afdd50b8dce1', 'i1', 1),
('50371f83-41a9-4f80-9764-afdd50b8dce1', 'i5', 13),
('54d15486-b7b3-4818-8f45-96a186820589', 'i1', 1),
('64551484-28dc-4b41-9a78-065cb4dd89a3', 'i1', 1),
('64551484-28dc-4b41-9a78-065cb4dd89a3', 'i2', 2),
('64551484-28dc-4b41-9a78-065cb4dd89a3', 'i4', 4),
('647a4911-8be5-4432-ad32-4423366b88f3', 'i8', 1),
('7ab64f47-e6b3-42d6-a5e0-29b0cfb7b541', 'i1', 1),
('84d0eb49-5dca-4eba-91c7-7f13f5abf5ad', 'i1', 1),
('92405ff7-87db-4500-a9ab-6cd31b2ca121', 'i1', 1),
('92405ff7-87db-4500-a9ab-6cd31b2ca121', 'i3', 1),
('92405ff7-87db-4500-a9ab-6cd31b2ca121', 'i4', 1),
('92661ccf-3438-445d-becb-4bb84a95d2e5', 'i1', 1),
('92661ccf-3438-445d-becb-4bb84a95d2e5', 'i7', 1),
('927e04ea-02b7-4ff0-bcaf-42927be1aa17', 'i1', 1),
('a1d952ad-0ff1-4158-bb1a-eae8f2a28771', 'i1', 1),
('c30a8f2b-d649-4db8-8874-1e121803714c', 'i1', 1),
('c30a8f2b-d649-4db8-8874-1e121803714c', 'i2', 2),
('c88665c7-f9de-4e34-a433-cc6396586bd8', 'i1', 1),
('ca2d2c29-c84f-4d54-b1d6-559d3e579a9b', 'i1', 1),
('d6b1f1c4-e540-4923-a23f-9003ce4e4b09', 'i2', 5),
('d6b1f1c4-e540-4923-a23f-9003ce4e4b09', 'i4', 4),
('d83bd270-e0ca-4e48-89b0-bc73fba9323d', 'i1', 1),
('dfe2c634-ae18-43b2-83f3-e3af7c91e707', 'i1', 1),
('e7ae0521-2ca8-48de-8216-af7aab7e64d8', 'i1', 1),
('e7ae0521-2ca8-48de-8216-af7aab7e64d8', 'i2', 1),
('fbde4e1c-070f-4551-84a1-4f3efa461363', 'i1', 1);

-- --------------------------------------------------------

--
-- テーブルの構造 `store_warehouse`
--

CREATE TABLE `store_warehouse` (
  `store_code` varchar(30) NOT NULL,
  `store_name` varchar(100) NOT NULL,
  `post` int(7) NOT NULL,
  `addres` varchar(100) NOT NULL,
  `tel` varchar(12) NOT NULL,
  `abolition_flag` int(1) NOT NULL DEFAULT 0,
  `warehouse_flag` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- テーブルのデータのダンプ `store_warehouse`
--

INSERT INTO `store_warehouse` (`store_code`, `store_name`, `post`, `addres`, `tel`, `abolition_flag`, `warehouse_flag`) VALUES
('sw1', 'testStore名古屋店', 4550001, '愛知県名古屋市大須', '09011112222', 0, 0),
('sw2', 'testStore大阪', 5880001, '大阪府難波', '09011113333', 1, 0),
('sw3', 'testStore東京', 1112222, '東京都渋谷区', '08022229999', 0, 0),
('sw4', '名古屋倉庫', 4553333, '愛知県名古屋市中区', '0568222333', 0, 1),
('sw5', '大阪倉庫', 5440022, '大阪府堺市', '05682223333', 1, 1);

--
-- ダンプしたテーブルのインデックス
--

--
-- テーブルのインデックス `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_code`);

--
-- テーブルのインデックス `color`
--
ALTER TABLE `color`
  ADD PRIMARY KEY (`color_code`);

--
-- テーブルのインデックス `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`store_code`,`item_code`),
  ADD KEY `item_code` (`item_code`);

--
-- テーブルのインデックス `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`item_code`),
  ADD KEY `size` (`size`),
  ADD KEY `color` (`color`),
  ADD KEY `category_code` (`category_code`,`sex_code`),
  ADD KEY `sex_code` (`sex_code`);

--
-- テーブルのインデックス `sex`
--
ALTER TABLE `sex`
  ADD PRIMARY KEY (`sex_code`);

--
-- テーブルのインデックス `shipping_slip`
--
ALTER TABLE `shipping_slip`
  ADD PRIMARY KEY (`slip_code`),
  ADD KEY `sender` (`sender`),
  ADD KEY `sending_address` (`sending_address`);

--
-- テーブルのインデックス `size`
--
ALTER TABLE `size`
  ADD PRIMARY KEY (`size_code`);

--
-- テーブルのインデックス `slip_item_list`
--
ALTER TABLE `slip_item_list`
  ADD PRIMARY KEY (`slip_code`,`item_code`),
  ADD KEY `item_code` (`item_code`);

--
-- テーブルのインデックス `store_warehouse`
--
ALTER TABLE `store_warehouse`
  ADD PRIMARY KEY (`store_code`);

--
-- ダンプしたテーブルの制約
--

--
-- テーブルの制約 `inventory`
--
ALTER TABLE `inventory`
  ADD CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`store_code`) REFERENCES `store_warehouse` (`store_code`),
  ADD CONSTRAINT `inventory_ibfk_2` FOREIGN KEY (`item_code`) REFERENCES `item` (`item_code`);

--
-- テーブルの制約 `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `item_ibfk_1` FOREIGN KEY (`size`) REFERENCES `size` (`size_code`),
  ADD CONSTRAINT `item_ibfk_2` FOREIGN KEY (`color`) REFERENCES `color` (`color_code`),
  ADD CONSTRAINT `item_ibfk_3` FOREIGN KEY (`category_code`) REFERENCES `category` (`category_code`),
  ADD CONSTRAINT `item_ibfk_4` FOREIGN KEY (`sex_code`) REFERENCES `sex` (`sex_code`);

--
-- テーブルの制約 `shipping_slip`
--
ALTER TABLE `shipping_slip`
  ADD CONSTRAINT `shipping_slip_ibfk_1` FOREIGN KEY (`sender`) REFERENCES `store_warehouse` (`store_code`),
  ADD CONSTRAINT `shipping_slip_ibfk_2` FOREIGN KEY (`sending_address`) REFERENCES `store_warehouse` (`store_code`);

--
-- テーブルの制約 `slip_item_list`
--
ALTER TABLE `slip_item_list`
  ADD CONSTRAINT `slip_item_list_ibfk_1` FOREIGN KEY (`item_code`) REFERENCES `item` (`item_code`),
  ADD CONSTRAINT `slip_item_list_ibfk_2` FOREIGN KEY (`slip_code`) REFERENCES `shipping_slip` (`slip_code`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
