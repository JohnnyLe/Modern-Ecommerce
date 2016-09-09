-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 09, 2016 at 04:54 AM
-- Server version: 5.7.9
-- PHP Version: 5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `modern_ecommerce`
--
CREATE DATABASE IF NOT EXISTS `modern_ecommerce` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `modern_ecommerce`;

-- --------------------------------------------------------

--
-- Table structure for table `applications`
--

DROP TABLE IF EXISTS `applications`;
CREATE TABLE IF NOT EXISTS `applications` (
  `theme_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `app_domain` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`theme_id`,`company_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_id`, `company_id`, `description`, `name`, `parent_id`, `position`, `status`) VALUES
(1, 1, NULL, 'T-Shirt', NULL, NULL, 1),
(2, 1, NULL, 'Shoes', NULL, NULL, 1),
(3, 1, NULL, 'Electronics', NULL, NULL, 1),
(4, 1, NULL, 'Phones', 3, NULL, 1),
(5, 1, NULL, 'Laptop', 3, NULL, 1),
(6, 1, NULL, 'Tablet', 3, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
CREATE TABLE IF NOT EXISTS `companies` (
  `company_id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`company_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `companies`
--

INSERT INTO `companies` (`company_id`, `create_date`, `name`, `status`) VALUES
(1, '2016-09-09 00:00:00', 'NIT-Software', 1);

-- --------------------------------------------------------

--
-- Table structure for table `histories`
--

DROP TABLE IF EXISTS `histories`;
CREATE TABLE IF NOT EXISTS `histories` (
  `history_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `type` int(11) NOT NULL,
  `value` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`history_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `base_grand_total` decimal(19,2) DEFAULT NULL,
  `base_subtotal` decimal(19,2) DEFAULT NULL,
  `checkout_comment` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `company_id` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `customer_dob` datetime DEFAULT NULL,
  `customer_email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customer_firstname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customer_gender` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customer_is_guest` smallint(6) DEFAULT NULL,
  `customer_lastname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customer_middlename` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customer_prefix` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customer_suffix` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `grand_total` decimal(19,2) DEFAULT NULL,
  `is_active` smallint(6) DEFAULT NULL,
  `is_changed` int(11) DEFAULT NULL,
  `is_multi_shipping` smallint(6) DEFAULT NULL,
  `is_virtual` smallint(6) DEFAULT NULL,
  `items_count` int(11) DEFAULT NULL,
  `items_quantity` decimal(19,2) DEFAULT NULL,
  `remote_ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) NOT NULL,
  `subtotal` decimal(19,2) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `user_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_addresses`
--

DROP TABLE IF EXISTS `order_addresses`;
CREATE TABLE IF NOT EXISTS `order_addresses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `adress_id` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `order_id` int(11) NOT NULL,
  `postcode` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prefix` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `region` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
  `suffix` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
CREATE TABLE IF NOT EXISTS `order_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `base_price` decimal(19,2) DEFAULT NULL,
  `base_row_total` decimal(19,2) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `free_shipping` bit(1) DEFAULT NULL,
  `is_virtual` smallint(6) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `order_id` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `product_id` int(11) NOT NULL,
  `product_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `quantity` decimal(19,2) DEFAULT NULL,
  `row_total` decimal(19,2) DEFAULT NULL,
  `row_weight` decimal(19,2) DEFAULT NULL,
  `sku` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `weight` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_payments`
--

DROP TABLE IF EXISTS `order_payments`;
CREATE TABLE IF NOT EXISTS `order_payments` (
  `payment_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `transaction_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`payment_id`,`order_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_reports`
--

DROP TABLE IF EXISTS `order_reports`;
CREATE TABLE IF NOT EXISTS `order_reports` (
  `report_id` int(11) NOT NULL AUTO_INCREMENT,
  `note` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `order_id` int(11) NOT NULL,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `product_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `browsing_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `company_id` int(11) NOT NULL,
  `created_on` datetime NOT NULL,
  `default_image` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_stock_controlled` bit(1) DEFAULT NULL,
  `list_price` double NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `overview` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `quantity` int(11) NOT NULL,
  `rank` int(11) NOT NULL,
  `sale_price` double NOT NULL,
  `sku` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `status` int(11) NOT NULL,
  `updated_on` datetime DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `browsing_name`, `company_id`, `created_on`, `default_image`, `description`, `is_stock_controlled`, `list_price`, `name`, `overview`, `quantity`, `rank`, `sale_price`, `sku`, `status`, `updated_on`) VALUES
(1, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/42/73704/iphone-6s-plus-64gb-400-400x450.png', 'iPhone 6s Plus 64 GB được nâng cấp độ phân giải camera sau lên 12 MP (thay vì 8 MP như trên iPhone 6 Plus), camera cho tốc độ lấy nét và chụp nhanh, thao tác chạm để chụp nhẹ nhàng. Chất lượng ảnh trong các điều kiện chụp khác nhau tốt.', NULL, 30, 'iPhone 6s Plus', 'Điện thoại iPhone 6s Plus 64GB', 20, 5, 22.99, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(2, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/42/75180/samsung-galaxy-s7-edge-1-400x460.png', 'Khả năng chống nước, chống bụi an toàn cho máy của Galaxy S7 Edge\r\nNhờ đạt chuẩn IP68 nên hoàn toàn có thể bảo vệ được tốt cho Galaxy S7 Edge khi vô tình dính nước mưa hay làm đổ nước, cho bạn thêm an tâm trong quá trình sử dụng.', NULL, 30, 'Samsung Galaxy S7', 'Điện thoại Samsung Galaxy S7 Edge', 20, 3, 18.49, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(3, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/42/74927/htc-102-400x460.png', 'Đầu tiên là mặt trước HTC 10, nổi bật lên ống kính camera trước rất lớn tuy rằng độ phân giải 5 MP, phím cảm biến ở mặt dưới. Cạnh dưới của máy, hai phím điều hướng cảm ứng nằm hai bên, không đặt ở trong màn hình để tăng diện tích sử dụng.', NULL, 30, 'HTC 10\r\n', 'Điện thoại HTC 10', 20, 5, 16.99, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(4, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/42/73023/sony-xperia-z5-premium-detail-400x460.png', 'Màn hình độ phân giải 4K trên Xperia Z5 Premium Dual cùng nhiều công nghệ độc quyền của Sony như: Triluminos (nâng cấp chất lượng hình ảnh), X-Reality™ (tối ưu hóa màu sắc, độ nét)... sẽ giúp bạn trải nghiệm hình ảnh, video 1 cách tuyệt vời nhất.', NULL, 30, 'Sony Xperia Z5', 'Điện thoại Sony Xperia Z5 Premium Dual', 20, 3, 15.49, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(5, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/42/75199/samsung-galaxy-a9-pro-1-400x460.png', 'Thiết kế sang trọng\r\nGalaxy A9 Pro vẫn sở hữu phong cách thiết kế tương tự các anh em dòng Galaxy A 2016 của mình với khung viền kim loại chắc chắn cùng 2 mặt kính cường lực trước sau sang trọng.', NULL, 30, 'Samsung Galaxy A9 Pro', 'Galaxy A9 Pro là smartphone lớn nhất trong gia đình Galaxy A 2016. Máy sở hữu viên pin khủng dung lượng 5.000 mAh cùng 4 GB RAM mạnh mẽ.', 20, 3, 11.99, 'skutestproduct', 1, '2016-09-06 18:10:07'),
(6, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/42/74997/iphone-se-16gb-400x460.png', 'iPhone SE giữ nguyên thiết kế tinh tế, sang trọng\r\niPhone SE vẫn giữ nguyên thiết kế trên người tiền nhiệm iPhone 5S và không có nhiều sự thay đổi. Khi đặt 2 thiết bị cạnh nhau thì bạn sẽ không thể nào phân biệt được đâu là iPhone 5S và đâu là iPhone SE.', NULL, 30, 'iPhone SE 16GB', 'Chiếc iPhone 4 inch mới với nhiều cải tiến với tên gọi iPhone SE.', 20, 5, 11.49, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(7, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/42/75244/sony-xperia-x-1-400x460.png', 'Thiết kế đối xứng mạnh mẽ Sony Xperia X vẫn mang trong mình bóng dáng của các anh em dòng Z Series với phong cách thiết kế omibaland quen thuộc. Máy trông vẫn rất sang trọng đúng phong cách rất “Sony”.', NULL, 30, 'Sony Xperia X', 'Sony vừa giới thiệu dòng sản phẩm X Serie mới của hãng trong năm 2016 tại triển lãm MWC. Xperia X là chiếc smartphone tầm trung mới với nhiều điểm nhấn đáng chú ý.', 20, 3, 11.9, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(8, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/42/74700/huawei-p9-400x460.png', 'Thiết kế sang trọng, tinh xảo\r\nHuawei P9 mang trong mình thiết kế sang trọng và tinh tế với khung viền kim loại cùng 4 góc được bo cong mềm mại đem lại cảm giác cầm nắm khá thoải mái.', NULL, 30, 'Huawei P9\r\n', 'Mới đây Huawei đã chính thức giới thiệu smartphone đầu bảng là chiếc Huawei P9 với điểm nhấn là camera kép hợp tác sản xuất cùng Leica.', 20, 5, 10.99, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(9, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/42/73898/samsung-galaxy-a7-2016-1-400x460-400x460.png', 'Thiết kế mới trên Galaxy A7 phiên bản 2016\r\nSo với A7 đời đầu khá vuông vức các góc cạnh thì với Galaxy A7 2016 được thay đổi hoàn toàn, mềm mại hơn nhưng vẫn khác biệt.\r\nKhung kim loại viền quanh máy tạo nên vẻ chắc chắn.', NULL, 30, 'Samsung Galaxy A7', 'Samsung Galaxy A7 (2016) đem lại màn hình to hơn, cấu hình mạnh mẽ hơn cùng nhiều tiện ích tốt cho bạn.', 20, 3, 9.99, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(10, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/42/75091/oppo-f1-plus-3-400x460.png', 'Oppo F1 Plus được thiết kế lấy cảm hứng từ nhà táo cắn dở\r\nKhông thể phủ nhận Oppo F1 Plus lấy cảm hứng thiết kế rất nhiều từ dòng iPhone 6, mặt lưng hoàn toàn giống cho tới phần camera lồi.', NULL, 30, 'OPPO F1 Plus', 'Oppo F1 Plus có một thiết kế mặt trước rất đẹp, trang bị cảm biến vân tay.', 20, 3, 9.99, 'skutestproduct', 1, '2016-09-06 18:10:07'),
(11, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/44/75558/lenovo-ideapad-100s-2-400x400.png', 'Màu sắc bắt mắt\r\nLà chiếc laptop hướng tới việc giải trí là chính nên hãng rất chú trọng đến phần màu sắc của tổng thể máy với tông đỏ, xanh dương và bạc.\r\nMáy khá gọn nhẹ khi chỉ nặng vỏn vẹn 1 kg.', NULL, 30, 'Laptop Lenovo IdeaPad 100S 11IBY Z3735/2GB/32GB/Win10', 'Lenovo IdeaPad 100S 11IBY có mức giá rẻ bất ngờ, thiết kế màu sắc dễ thương phù hợp mang theo bên mình để giải trí.', 20, 5, 3.99, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(12, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/44/73844/lenovo-ideapad-100-14iby-n2840-2gb-500gb-win10-400-400x400.png', 'Cấu hình vừa đủ cho công việc và giải trí nhẹ nhàng\r\nSử dụng CPU Celeron, tốc độ 2.16 GHz, máy chỉ có 1 khe cắm RAM 2 GB và bạn có thể nâng cấp lên tối đa 8 GB.\r\nKhi sử dụng một thời gian, bạn nên thay thanh RAM với dung lượng lớn hơn (tối đa 8 GB)', NULL, 30, 'Laptop Lenovo IdeaPad 100 14IBY N2840/2GB/500GB/Win10', 'Lenovo IdeaPad 100 14IBY có mức giá thấp ai cũng có thể sở hữu, phù hợp với các nhu cầu sử dụng đơn giản.', 20, 3, 5.49, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(13, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/44/75890/hp-pavilion-11-s001tu-n3050-2gb-500gb-win10-400-400x400.png', 'Phần cứng ổn\r\nHP Pavilion 11 S001TU N3050 không phải là chiếc máy chuyên chơi game hay sử dụng các ứng dụng nặng, máy phù hợp hơn với học sinh hay nhân viên làm việc, giải trí đơn giản.', NULL, 30, 'Laptop HP Pavilion 11 S001TU N3050/2GB/500GB/Win10', 'HP Pavilion 11 S001TU N3050 là chiếc laptop có thiết kế nhỏ gọn, cấu hình khá ổn và trên hết là mức giá rất tốt.', 20, 5, 5.99, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(14, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/44/76031/acer-es1-431-n3060-4gb-500gb-win10-400x400.png', 'Cấu hình phù hợp với các chương trình nhẹ nhàng\r\nMáy sử dụng chip xử lý Celeron để đảm bảo giá thành tốt, RAM 4 GB là điểm sáng giúp máy vận hành không quá chậm, bạn có thể nâng cấp lên tối đa 8 GB.\r\nỔ cứng HDD 500 GB thoải mái lưu trữ dữ liệu.', NULL, 30, 'Laptop Acer ES1 431 N3060/4GB/500GB/Win10', 'Acer ES1 431 N306 mang lại hiệu năng sử dụng đơn giản để học tập hay giải trí cho bạn.', 20, 3, 5.99, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(15, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/44/75414/asus-e402sa-n3050-2gb-500gb-win10-400-400x400.png', 'Cấu hình được cải thiện\r\nKhông có quá nhiều sự khác biệt so với bản Asus E402MA, Asus E402SA được sử dụng chip Celeron phiên bản tốt hơn là N3050, giúp máy hoạt động có phần nhẹ nhàng hơn.\r\nMáy vẫn trang bị đủ ổ cứng HDD 500 GB, RAM sẵn 2 GB.', NULL, 30, 'Laptop Asus E402SA N3050/2GB/500GB/Win10', 'Asus E402SA N3050 là bản nâng cấp nhẹ với chip xử lý Celeron cho khả năng hoạt động tốt hơn.', 20, 3, 6.29, 'skutestproduct', 1, '2016-09-06 18:10:07'),
(16, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/44/74357/dell-inspiron-3552-n3050-2gb-500gb-win10-400-1-400x313.png', 'Màn hình lớn\r\nMáy được trang bị màn hình lớn 15.6 inch, cùng với đó là công nghệ hiển thị HD WLED TrueLife có độ tương phản cao.', NULL, 30, 'Laptop Dell Inspiron 3552 N3050/2GB/500GB/Win10', 'Dell Inspiron 3552 N3050 thuộc dòng máy giá rẻ nhưng được trang bị màn hình lớn, bàn phím đầy đủ cụm số tiện ích.', 20, 5, 6.49, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(17, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/44/76030/acer-es1-431-n3710-4gb-500gb-win10-400x400.png', 'Màn hình nổi bật, hiển thị đẹp\r\nVẫn là màn hình HD 14 inch, và có phần nhỉnh hơn thế hệ trước bởi khả năng hiển thị độ sáng tốt, màu sắc đẹp nhờ công nghệ Active Matrix TFT Colour LCD.', NULL, 30, 'Laptop Acer ES1 431 N3710/4GB/500GB/Win10', 'Laptop Acer ES1 431 N3710 có cấu hình ổn định, gọn nhẹ, thích hợp với mọi lứa tuổi người dùng.', 20, 3, 6.99, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(18, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/44/78147/hp-14-ac198tu-n3700-2gb-500gb-win10-400x400.png', 'Là một sản phẩm không nhiều thay đổi trong thiết kế nhưng được được trang bị cấu hình và giá thành phù hợp hơn, HP 14 ac198TU xứng đáng là một lựa chọn tốt cho công việc và giải trí của người dùng ở phân khúc giá rẻ.', NULL, 30, 'Laptop HP 14 ac198TU N3700/2GB/500GB/Win10\r\n', 'HP 14 ac198TU N3700 - Laptop giá rẻ nhưng không kém phần sang trọng', 20, 5, 6.99, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(19, 'browsing', 1, '2016-09-06 00:00:00', 'https://cdn2.tgdd.vn/Products/Images/44/78342/asus-x403s-n3700-2g-500g-win10-300x300.jpg', 'Cấu hình phù hợp với game hay ứng dụng nhẹ\r\nMáy sử dụng chip xử lý Pentium, đạt tốc độ tối đa 2.4 GHz mang lại khả năng hoạt động tốt hơn so với các dòng máy cùng phân khúc giá hiện nay.\r\nRAM 2 GB và có thể nâng cấp', NULL, 30, 'Laptop ASUS X403SA N3700/2GB/500GB/Win10\r\n', 'ASUS X403SA N3700 là chiếc laptop giá rẻ, cấu hình nhẹ nhàng cùng nhiều cổng kết nối tiện ích cho công việc lẫn học tập.', 20, 3, 6.99, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(20, 'browsing', 1, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/44/82541/acer-es1-531-n3710-4gb-500gb-win10-400-400x400.png', 'Cấu hình tốt\r\nLaptop Acer ES1 531 mang trên mình vi xử lý Pentium N3710 tốc độ 1.6 GHz của Intel. Thiết bị có RAM 4 GB cùng một khe trống để bạn có thể nâng lên đến 8 GB sử dụng cho nhu cầu cao hơn.', NULL, 30, 'Laptop Acer ES1 531 N3710/4GB/500GB/Win10/KhôngDVD', 'Nếu bạn tìm kiếm một sản phẩm có tính di động cao, mức giá hợp lý trong phân khúc phổ thông, thì laptop Acer ES1 531 sẽ là một sự lựa chọn bạn không nên bỏ qua.', 20, 3, 6.99, 'skutestproduct', 1, '2016-09-06 18:10:07'),
(21, 'browsing', 2, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/522/73989/ipad-pro-wifi-cellular-128gb-400x460.png', 'To hơn, không gian làm việc lớn hơn\r\nĐược thiết kế rất to với kích thước ngang lên tới hơn 30 cm, iPad Pro tỏ ra là một chiếc máy hữu hiệu cho công việc nhiều hơn là một chiếc máy giải trí để có thể dễ dàng xách đi mọi nơi.', NULL, 30, 'Máy tính bảng iPad Pro Wifi Cellular 128GB', 'Giải trí, công việc, thiết kế sang trọng, tinh tế, mạnh mẽ, uy tín là những gì mà chiếc iPad Pro mang đến cho bạn.', 20, 5, 26.99, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(22, 'browsing', 2, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/522/78767/ipad-pro-97-inch-wifi-cellular-256gb-400-400x460.png', 'Thiết kế sang trọng\r\nVới những sản phẩm đến từ Apple thì điều ưu tiên và tiên quyết của họ là thiết kế sang trọng và bắt mắt, iPad Pro 9.7 inch được làm từ chất liệu nhôm nguyên khối tạo cho máy một cảm giác cầm nắm đầm tay và cực kì sang trọng.', NULL, 30, 'Máy tính bảng iPad Pro 9.7 inch Wifi Cellular 256GB', 'Chiếc iPad Pro 9.7 inch có cấu hình cực kì mạnh mẽ, sang trọng và trên hết là kích thước được làm gọn hơn.', 20, 3, 26.59, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(23, 'browsing', 2, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/522/76978/ipad-pro-97-inch-wifi-cellular-128gb-400x460.png', 'Thiết kế sang trọng\r\nVới những sản phẩm đến từ Apple thì điều ưu tiên và tiên quyết của họ là thiết kế sang trọng và bắt mắt, iPad Pro 9.7 inch được làm từ chất liệu nhôm nguyên khối tạo cho máy một cảm giác cầm nắm đầm tay và cực kì sang trọng.', NULL, 30, 'Máy tính bảng iPad Pro 9.7 inch Wifi Cellular 128GB', 'Chiếc iPad Pro 9.7 inch có cấu hình cực kì mạnh mẽ, sang trọng và trên hết là kích thước được làm gọn hơn.', 20, 5, 22.49, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(24, 'browsing', 2, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/522/73088/ipad-pro-wifi-32gb-400x460.png', 'To hơn, không gian làm việc lớn hơn\r\nVới kích thước lớn nên chiếc iPad Pro tỏ ra là một chiếc máy hữu hiệu cho công việc nhiều hơn là một chiếc máy giải trí để có thể dễ dàng xách đi mọi nơi.', NULL, 30, 'Máy tính bảng iPad Pro Wifi 32GB', 'Giải trí, công việc, thiết kế sang trọng, tinh tế, mạnh mẽ, uy tín là những gì mà chiếc iPad Pro mang đến cho bạn.', 20, 3, 19.99, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(25, 'browsing', 2, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/522/76976/ipad-pro-97-inch-wifi-128gb-400x460.png', 'Thiết kế sang trọng\r\niPad Pro 9.7 inch có kích thước và thiết kế rất giống với iPad Air 2 nhưng vẫn có sự khác biệt là có thêm màu vàng hồng rất hiện đại và có đến 4 chiếc loa ngoài đem đến trải nghiệm tốt hơn.', NULL, 30, 'Máy tính bảng iPad Pro 9.7 inch Wifi 128GB', 'iPad Pro 9.7 inch được nâng cấp chất lượng hiển thị, chất lượng camera tốt hơn rất nhiều, cùng các phụ kiện như bàn phím rời và bút Pencil chuyên dụng.', 20, 3, 19.49, 'skutestproduct', 1, '2016-09-06 18:10:07'),
(26, 'browsing', 2, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/522/76977/ipad-pro-97-inch-wifi-cellular-32gb-1-400x460.png', 'Nhỏ hơn, mạnh hơn và tinh tế hơn là những cụm từ dùng để miêu tả iPad Pro 9.7 inch vừa mới ra mắt của Apple so với iPad Pro 12.9 inch trước kia.', NULL, 30, 'Máy tính bảng iPad Pro 9.7 inch Wifi Cellular 32GB', 'iPad Pro 9.7 inch Wifi Cellular 32GB – Đẳng cấp, mạnh mẽ và thời trang', 20, 5, 18.99, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(27, 'browsing', 2, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/522/70243/ipad-air-2-cellular-64g-1-300x420.png', 'Cảm biến vân tay\r\nĐây chính là điểm nâng cấp khác biệt so với iPad Air đời đầu, nâng cao khả năng bảo mật hơn nữa của máy.', NULL, 30, 'Máy tính bảng iPad Air 2 Cellular 64GB', 'iPad Air 2 Cellular 64GB có bộ nhớ trong nhiều hơn, dành cho bạn có nhu cầu lưu trữ dữ liệu, hình ảnh.', 20, 3, 15.99, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(28, 'browsing', 2, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/522/75490/ipad-pro-97-inch-1-400x460.png', 'Sau khi giới thiệu chiếc iPad Pro 12.9 inch thì Apple đã đưa đến người tiêu dùng thêm một phiên bản mini khác của chiếc iPad cỡ lớn này với màn hình 9.7 inch với cấu hình tương đương.', NULL, 30, 'Máy tính bảng iPad Pro 9.7 inch Wifi 32GB', 'iPad Pro 9.7 inch – Máy tính bảng cao cấp, hiệu năng mượt mà', 20, 5, 15.9, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(29, 'browsing', 2, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/522/73766/ipad-mini-4-wifi-cellular-64gb-400x460.png', 'Kết nối mạng qua nhiều cách\r\nMáy có kết nối wifi chuẩn Wi-Fi 802.11 a/b/g/n/ac cho phép bạn duyệt web nhanh hơn, ngoài ra khi không thể vào wifi, bạn có thể tùy chỉnh bật 3G/4G để tiếp tục lên mạng.', NULL, 30, 'Máy tính bảng iPad Mini 4 Wifi Cellular 64GB', 'iPad Mini 4 Wifi Cellular 64GB với dung lượng lớn hơn, cho bạn thêm sự lựa chọn với kết nối mạng 3G/4G cùng cấu hình mạnh mẽ.', 20, 3, 14.99, 'skutestproduct', 1, '2016-09-06 18:10:09'),
(30, 'browsing', 2, '2016-09-06 00:00:00', '//cdn.tgdd.vn/Products/Images/522/70368/ipad-mini-3-retina-cellular-64gb-400x533.png', 'Cảm biến vân tay\r\nLà cải tiến lớn nhất của dòng iPad mini khi iPad Mini 3 được trang bị cảm biến, tăng khả năng bảo mật máy lên cao hơn. Cách sử dụng đơn giản, nhận diện nhanh chóng mang đến nhiều tiện lợi.', NULL, 30, 'Máy tính bảng iPad Mini 3 Retina Cellular 64GB', 'iPad Mini 3 Retina Cellular 64GB mang lại cho người dùng hiệu năng mạnh, tiện ích 3G/4G cùng cảm biến vân tay cho dòng mini.', 20, 3, 13.99, 'skutestproduct', 1, '2016-09-06 18:10:07');

-- --------------------------------------------------------

--
-- Table structure for table `product_attributes`
--

DROP TABLE IF EXISTS `product_attributes`;
CREATE TABLE IF NOT EXISTS `product_attributes` (
  `attribute_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`attribute_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `product_attributes`
--

INSERT INTO `product_attributes` (`attribute_id`, `company_id`, `name`) VALUES
(1, 1, 'promotion'),
(2, 1, 'sale_off');

-- --------------------------------------------------------

--
-- Table structure for table `product_attribute_details`
--

DROP TABLE IF EXISTS `product_attribute_details`;
CREATE TABLE IF NOT EXISTS `product_attribute_details` (
  `attribute_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `value_numberic` double DEFAULT NULL,
  `value_string` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`attribute_id`,`product_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `product_attribute_details`
--

INSERT INTO `product_attribute_details` (`attribute_id`, `product_id`, `value_numberic`, `value_string`) VALUES
(2, 5, 89000, NULL),
(2, 2, 89000, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `product_categories`
--

DROP TABLE IF EXISTS `product_categories`;
CREATE TABLE IF NOT EXISTS `product_categories` (
  `product_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  PRIMARY KEY (`product_id`,`category_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `product_categories`
--

INSERT INTO `product_categories` (`product_id`, `category_id`) VALUES
(1, 4),
(2, 4),
(3, 4),
(4, 4),
(5, 4),
(6, 4),
(7, 4),
(8, 4),
(9, 4),
(10, 4),
(11, 5),
(12, 5),
(13, 5),
(14, 5),
(15, 5),
(16, 5),
(17, 5),
(18, 5),
(19, 5),
(20, 5),
(21, 6),
(22, 6),
(23, 6),
(24, 6),
(25, 6),
(26, 6),
(27, 6),
(28, 6),
(29, 6),
(30, 6);

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
CREATE TABLE IF NOT EXISTS `reviews` (
  `review_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `company_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `rank` int(11) NOT NULL,
  `user_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`review_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
CREATE TABLE IF NOT EXISTS `suppliers` (
  `supplier_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`supplier_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `supplier_products`
--

DROP TABLE IF EXISTS `supplier_products`;
CREATE TABLE IF NOT EXISTS `supplier_products` (
  `supplier_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  PRIMARY KEY (`supplier_id`,`product_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `themes`
--

DROP TABLE IF EXISTS `themes`;
CREATE TABLE IF NOT EXISTS `themes` (
  `theme_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `source_path` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `thumbnail` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `version` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`theme_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `company_id` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `first_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `middle_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password_hash` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `role_id` int(11) NOT NULL,
  `salt` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_addresses`
--

DROP TABLE IF EXISTS `user_addresses`;
CREATE TABLE IF NOT EXISTS `user_addresses` (
  `adress_id` int(11) NOT NULL AUTO_INCREMENT,
  `adress` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `city` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `country` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `fax` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`adress_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_tokens`
--

DROP TABLE IF EXISTS `user_tokens`;
CREATE TABLE IF NOT EXISTS `user_tokens` (
  `token` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `company_id` int(11) NOT NULL,
  `expiration_date` datetime NOT NULL,
  `login_date` datetime NOT NULL,
  `user_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`token`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
