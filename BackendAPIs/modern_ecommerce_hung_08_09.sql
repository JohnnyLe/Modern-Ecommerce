-- phpMyAdmin SQL Dump
-- version 4.5.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 08, 2016 at 05:02 AM
-- Server version: 5.7.11
-- PHP Version: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `modern_ecommerce`
--

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

CREATE TABLE `address` (
  `adress_id` int(10) UNSIGNED NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `adress` varchar(255) NOT NULL,
  `phone` text NOT NULL,
  `fax` text NOT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `app`
--

CREATE TABLE `app` (
  `theme_id` int(10) UNSIGNED NOT NULL,
  `company_id` int(10) UNSIGNED NOT NULL,
  `status` tinyint(1) NOT NULL,
  `app_domain` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `category_id` int(10) UNSIGNED NOT NULL,
  `company_id` int(10) UNSIGNED NOT NULL,
  `parent_id` int(10) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_id`, `company_id`, `parent_id`, `name`, `status`, `description`) VALUES
(1, 1, 0, 'IPHONE', 6, ''),
(2, 1, 0, 'LAPTOP', 5, ''),
(3, 1, 0, 'TABLET', 5, '');

-- --------------------------------------------------------

--
-- Table structure for table `companies`
--

CREATE TABLE `companies` (
  `company_id` int(10) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `create_date` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `companies`
--

INSERT INTO `companies` (`company_id`, `name`, `status`, `create_date`) VALUES
(1, 'NIT-Software', 1, '2016-09-06 00:00:00'),
(2, 'Test company', 1, '2016-09-06 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `history`
--

CREATE TABLE `history` (
  `history_id` int(10) UNSIGNED NOT NULL,
  `company_id` int(10) UNSIGNED NOT NULL,
  `type` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `value` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(10) UNSIGNED NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `company_id` int(10) UNSIGNED NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `is_active` smallint(5) UNSIGNED DEFAULT '1',
  `is_virtual` smallint(5) UNSIGNED DEFAULT '0',
  `is_multi_shipping` smallint(5) UNSIGNED DEFAULT '1',
  `status` int(11) NOT NULL,
  `items_count` int(10) UNSIGNED DEFAULT '0',
  `items_quantity` decimal(12,4) DEFAULT '0.0000',
  `grand_total` decimal(12,4) DEFAULT '0.0000',
  `base_grand_total` decimal(12,4) DEFAULT '0.0000',
  `checkout_comment` varchar(255) DEFAULT NULL,
  `customer_email` varchar(255) DEFAULT NULL,
  `customer_prefix` varchar(40) DEFAULT NULL,
  `customer_firstname` varchar(255) DEFAULT NULL,
  `customer_middlename` varchar(40) DEFAULT NULL,
  `customer_lastname` varchar(255) DEFAULT NULL,
  `customer_suffix` varchar(40) DEFAULT NULL,
  `customer_dob` datetime DEFAULT NULL,
  `customer_is_guest` smallint(5) UNSIGNED DEFAULT '0',
  `remote_ip` varchar(32) DEFAULT NULL,
  `customer_gender` varchar(255) DEFAULT NULL,
  `subtotal` decimal(12,4) DEFAULT NULL,
  `base_subtotal` decimal(12,4) DEFAULT NULL,
  `is_changed` int(10) UNSIGNED DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `order_address`
--

CREATE TABLE `order_address` (
  `id` int(10) UNSIGNED NOT NULL,
  `order_id` int(10) UNSIGNED NOT NULL,
  `adress_id` int(10) UNSIGNED NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `region_id` int(11) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `postcode` varchar(255) DEFAULT NULL,
  `prefix` varchar(255) DEFAULT NULL,
  `suffix` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `order_detail`
--

CREATE TABLE `order_detail` (
  `id` int(10) UNSIGNED NOT NULL,
  `parent_id` int(10) UNSIGNED DEFAULT NULL,
  `order_id` int(10) UNSIGNED NOT NULL,
  `product_id` int(10) UNSIGNED NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `is_virtual` smallint(5) UNSIGNED DEFAULT NULL,
  `sku` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` longtext,
  `free_shipping` smallint(5) UNSIGNED DEFAULT '0',
  `weight` decimal(12,4) DEFAULT '0.0000',
  `quantity` decimal(12,4) DEFAULT '0.0000',
  `price` decimal(12,4) DEFAULT '0.0000',
  `base_price` decimal(12,4) DEFAULT '0.0000',
  `row_total` decimal(12,4) DEFAULT '0.0000',
  `base_row_total` decimal(12,4) DEFAULT '0.0000',
  `row_weight` decimal(12,4) DEFAULT '0.0000',
  `product_type` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `order_payments`
--

CREATE TABLE `order_payments` (
  `order_id` int(10) UNSIGNED NOT NULL,
  `payment_id` int(10) UNSIGNED NOT NULL,
  `transaction_id` varchar(255) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `order_reports`
--

CREATE TABLE `order_reports` (
  `report_id` int(10) UNSIGNED NOT NULL,
  `order_id` int(10) UNSIGNED NOT NULL,
  `status` tinyint(1) NOT NULL,
  `note` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `payment_id` int(10) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `desc` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `product_id` int(10) UNSIGNED NOT NULL,
  `company_id` int(10) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `browsing_name` varchar(255) NOT NULL,
  `sale_price` double NOT NULL,
  `list_price` double NOT NULL,
  `default_image` varchar(255) NOT NULL,
  `overview` varchar(255) NOT NULL,
  `quantity` int(10) UNSIGNED NOT NULL,
  `is_stock_controlled` tinyint(1) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `rank` int(11) NOT NULL,
  `sku` varchar(100) NOT NULL,
  `created_on` datetime NOT NULL,
  `updated_on` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `company_id`, `name`, `browsing_name`, `sale_price`, `list_price`, `default_image`, `overview`, `quantity`, `is_stock_controlled`, `status`, `description`, `rank`, `sku`, `created_on`, `updated_on`) VALUES
(1, 1, 'iPhone 6s Plus', 'browsing', 22.99, 30, '//cdn.tgdd.vn/Products/Images/42/73704/iphone-6s-plus-64gb-400-400x450.png', 'Điện thoại iPhone 6s Plus 64GB', 20, NULL, 1, 'iPhone 6s Plus 64 GB được nâng cấp độ phân giải camera sau lên 12 MP (thay vì 8 MP như trên iPhone 6 Plus), camera cho tốc độ lấy nét và chụp nhanh, thao tác chạm để chụp nhẹ nhàng. Chất lượng ảnh trong các điều kiện chụp khác nhau tốt.', 5, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(2, 1, 'Samsung Galaxy S7', 'browsing', 18.49, 30, '//cdn.tgdd.vn/Products/Images/42/75180/samsung-galaxy-s7-edge-1-400x460.png', 'Điện thoại Samsung Galaxy S7 Edge', 20, NULL, 1, 'Khả năng chống nước, chống bụi an toàn cho máy của Galaxy S7 Edge\r\nNhờ đạt chuẩn IP68 nên hoàn toàn có thể bảo vệ được tốt cho Galaxy S7 Edge khi vô tình dính nước mưa hay làm đổ nước, cho bạn thêm an tâm trong quá trình sử dụng.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(3, 1, 'HTC 10\r\n', 'browsing', 16.99, 30, '//cdn.tgdd.vn/Products/Images/42/74927/htc-102-400x460.png', 'Điện thoại HTC 10', 20, NULL, 1, 'Đầu tiên là mặt trước HTC 10, nổi bật lên ống kính camera trước rất lớn tuy rằng độ phân giải 5 MP, phím cảm biến ở mặt dưới. Cạnh dưới của máy, hai phím điều hướng cảm ứng nằm hai bên, không đặt ở trong màn hình để tăng diện tích sử dụng.', 5, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(4, 1, 'Sony Xperia Z5', 'browsing', 15.49, 30, '//cdn.tgdd.vn/Products/Images/42/73023/sony-xperia-z5-premium-detail-400x460.png', 'Điện thoại Sony Xperia Z5 Premium Dual', 20, NULL, 1, 'Màn hình độ phân giải 4K trên Xperia Z5 Premium Dual cùng nhiều công nghệ độc quyền của Sony như: Triluminos (nâng cấp chất lượng hình ảnh), X-Reality™ (tối ưu hóa màu sắc, độ nét)... sẽ giúp bạn trải nghiệm hình ảnh, video 1 cách tuyệt vời nhất.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(5, 1, 'Samsung Galaxy A9 Pro', 'browsing', 11.99, 30, '//cdn.tgdd.vn/Products/Images/42/75199/samsung-galaxy-a9-pro-1-400x460.png', 'Galaxy A9 Pro là smartphone lớn nhất trong gia đình Galaxy A 2016. Máy sở hữu viên pin khủng dung lượng 5.000 mAh cùng 4 GB RAM mạnh mẽ.', 20, NULL, 1, 'Thiết kế sang trọng\r\nGalaxy A9 Pro vẫn sở hữu phong cách thiết kế tương tự các anh em dòng Galaxy A 2016 của mình với khung viền kim loại chắc chắn cùng 2 mặt kính cường lực trước sau sang trọng.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:07'),
(6, 1, 'iPhone SE 16GB', 'browsing', 11.49, 30, '//cdn.tgdd.vn/Products/Images/42/74997/iphone-se-16gb-400x460.png', 'Chiếc iPhone 4 inch mới với nhiều cải tiến với tên gọi iPhone SE.', 20, NULL, 1, 'iPhone SE giữ nguyên thiết kế tinh tế, sang trọng\r\niPhone SE vẫn giữ nguyên thiết kế trên người tiền nhiệm iPhone 5S và không có nhiều sự thay đổi. Khi đặt 2 thiết bị cạnh nhau thì bạn sẽ không thể nào phân biệt được đâu là iPhone 5S và đâu là iPhone SE.', 5, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(7, 1, 'Sony Xperia X', 'browsing', 11.9, 30, '//cdn.tgdd.vn/Products/Images/42/75244/sony-xperia-x-1-400x460.png', 'Sony vừa giới thiệu dòng sản phẩm X Serie mới của hãng trong năm 2016 tại triển lãm MWC. Xperia X là chiếc smartphone tầm trung mới với nhiều điểm nhấn đáng chú ý.', 20, NULL, 1, 'Thiết kế đối xứng mạnh mẽ Sony Xperia X vẫn mang trong mình bóng dáng của các anh em dòng Z Series với phong cách thiết kế omibaland quen thuộc. Máy trông vẫn rất sang trọng đúng phong cách rất “Sony”.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(8, 1, 'Huawei P9\r\n', 'browsing', 10.99, 30, '//cdn.tgdd.vn/Products/Images/42/74700/huawei-p9-400x460.png', 'Mới đây Huawei đã chính thức giới thiệu smartphone đầu bảng là chiếc Huawei P9 với điểm nhấn là camera kép hợp tác sản xuất cùng Leica.', 20, NULL, 1, 'Thiết kế sang trọng, tinh xảo\r\nHuawei P9 mang trong mình thiết kế sang trọng và tinh tế với khung viền kim loại cùng 4 góc được bo cong mềm mại đem lại cảm giác cầm nắm khá thoải mái.', 5, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(9, 1, 'Samsung Galaxy A7', 'browsing', 9.99, 30, '//cdn.tgdd.vn/Products/Images/42/73898/samsung-galaxy-a7-2016-1-400x460-400x460.png', 'Samsung Galaxy A7 (2016) đem lại màn hình to hơn, cấu hình mạnh mẽ hơn cùng nhiều tiện ích tốt cho bạn.', 20, NULL, 1, 'Thiết kế mới trên Galaxy A7 phiên bản 2016\r\nSo với A7 đời đầu khá vuông vức các góc cạnh thì với Galaxy A7 2016 được thay đổi hoàn toàn, mềm mại hơn nhưng vẫn khác biệt.\r\nKhung kim loại viền quanh máy tạo nên vẻ chắc chắn.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(10, 1, 'OPPO F1 Plus', 'browsing', 9.99, 30, '//cdn.tgdd.vn/Products/Images/42/75091/oppo-f1-plus-3-400x460.png', 'Oppo F1 Plus có một thiết kế mặt trước rất đẹp, trang bị cảm biến vân tay.', 20, NULL, 1, 'Oppo F1 Plus được thiết kế lấy cảm hứng từ nhà táo cắn dở\r\nKhông thể phủ nhận Oppo F1 Plus lấy cảm hứng thiết kế rất nhiều từ dòng iPhone 6, mặt lưng hoàn toàn giống cho tới phần camera lồi.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:07'),
(11, 1, 'Laptop Lenovo IdeaPad 100S 11IBY Z3735/2GB/32GB/Win10', 'browsing', 3.99, 30, '//cdn.tgdd.vn/Products/Images/44/75558/lenovo-ideapad-100s-2-400x400.png', 'Lenovo IdeaPad 100S 11IBY có mức giá rẻ bất ngờ, thiết kế màu sắc dễ thương phù hợp mang theo bên mình để giải trí.', 20, NULL, 1, 'Màu sắc bắt mắt\r\nLà chiếc laptop hướng tới việc giải trí là chính nên hãng rất chú trọng đến phần màu sắc của tổng thể máy với tông đỏ, xanh dương và bạc.\r\nMáy khá gọn nhẹ khi chỉ nặng vỏn vẹn 1 kg.', 5, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(12, 1, 'Laptop Lenovo IdeaPad 100 14IBY N2840/2GB/500GB/Win10', 'browsing', 5.49, 30, '//cdn.tgdd.vn/Products/Images/44/73844/lenovo-ideapad-100-14iby-n2840-2gb-500gb-win10-400-400x400.png', 'Lenovo IdeaPad 100 14IBY có mức giá thấp ai cũng có thể sở hữu, phù hợp với các nhu cầu sử dụng đơn giản.', 20, NULL, 1, 'Cấu hình vừa đủ cho công việc và giải trí nhẹ nhàng\r\nSử dụng CPU Celeron, tốc độ 2.16 GHz, máy chỉ có 1 khe cắm RAM 2 GB và bạn có thể nâng cấp lên tối đa 8 GB.\r\nKhi sử dụng một thời gian, bạn nên thay thanh RAM với dung lượng lớn hơn (tối đa 8 GB)', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(13, 1, 'Laptop HP Pavilion 11 S001TU N3050/2GB/500GB/Win10', 'browsing', 5.99, 30, '//cdn.tgdd.vn/Products/Images/44/75890/hp-pavilion-11-s001tu-n3050-2gb-500gb-win10-400-400x400.png', 'HP Pavilion 11 S001TU N3050 là chiếc laptop có thiết kế nhỏ gọn, cấu hình khá ổn và trên hết là mức giá rất tốt.', 20, NULL, 1, 'Phần cứng ổn\r\nHP Pavilion 11 S001TU N3050 không phải là chiếc máy chuyên chơi game hay sử dụng các ứng dụng nặng, máy phù hợp hơn với học sinh hay nhân viên làm việc, giải trí đơn giản.', 5, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(14, 1, 'Laptop Acer ES1 431 N3060/4GB/500GB/Win10', 'browsing', 5.99, 30, '//cdn.tgdd.vn/Products/Images/44/76031/acer-es1-431-n3060-4gb-500gb-win10-400x400.png', 'Acer ES1 431 N306 mang lại hiệu năng sử dụng đơn giản để học tập hay giải trí cho bạn.', 20, NULL, 1, 'Cấu hình phù hợp với các chương trình nhẹ nhàng\r\nMáy sử dụng chip xử lý Celeron để đảm bảo giá thành tốt, RAM 4 GB là điểm sáng giúp máy vận hành không quá chậm, bạn có thể nâng cấp lên tối đa 8 GB.\r\nỔ cứng HDD 500 GB thoải mái lưu trữ dữ liệu.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(15, 1, 'Laptop Asus E402SA N3050/2GB/500GB/Win10', 'browsing', 6.29, 30, '//cdn.tgdd.vn/Products/Images/44/75414/asus-e402sa-n3050-2gb-500gb-win10-400-400x400.png', 'Asus E402SA N3050 là bản nâng cấp nhẹ với chip xử lý Celeron cho khả năng hoạt động tốt hơn.', 20, NULL, 1, 'Cấu hình được cải thiện\r\nKhông có quá nhiều sự khác biệt so với bản Asus E402MA, Asus E402SA được sử dụng chip Celeron phiên bản tốt hơn là N3050, giúp máy hoạt động có phần nhẹ nhàng hơn.\r\nMáy vẫn trang bị đủ ổ cứng HDD 500 GB, RAM sẵn 2 GB.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:07'),
(16, 1, 'Laptop Dell Inspiron 3552 N3050/2GB/500GB/Win10', 'browsing', 6.49, 30, '//cdn.tgdd.vn/Products/Images/44/74357/dell-inspiron-3552-n3050-2gb-500gb-win10-400-1-400x313.png', 'Dell Inspiron 3552 N3050 thuộc dòng máy giá rẻ nhưng được trang bị màn hình lớn, bàn phím đầy đủ cụm số tiện ích.', 20, NULL, 1, 'Màn hình lớn\r\nMáy được trang bị màn hình lớn 15.6 inch, cùng với đó là công nghệ hiển thị HD WLED TrueLife có độ tương phản cao.', 5, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(17, 1, 'Laptop Acer ES1 431 N3710/4GB/500GB/Win10', 'browsing', 6.99, 30, '//cdn.tgdd.vn/Products/Images/44/76030/acer-es1-431-n3710-4gb-500gb-win10-400x400.png', 'Laptop Acer ES1 431 N3710 có cấu hình ổn định, gọn nhẹ, thích hợp với mọi lứa tuổi người dùng.', 20, NULL, 1, 'Màn hình nổi bật, hiển thị đẹp\r\nVẫn là màn hình HD 14 inch, và có phần nhỉnh hơn thế hệ trước bởi khả năng hiển thị độ sáng tốt, màu sắc đẹp nhờ công nghệ Active Matrix TFT Colour LCD.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(18, 1, 'Laptop HP 14 ac198TU N3700/2GB/500GB/Win10\r\n', 'browsing', 6.99, 30, '//cdn.tgdd.vn/Products/Images/44/78147/hp-14-ac198tu-n3700-2gb-500gb-win10-400x400.png', 'HP 14 ac198TU N3700 - Laptop giá rẻ nhưng không kém phần sang trọng', 20, NULL, 1, 'Là một sản phẩm không nhiều thay đổi trong thiết kế nhưng được được trang bị cấu hình và giá thành phù hợp hơn, HP 14 ac198TU xứng đáng là một lựa chọn tốt cho công việc và giải trí của người dùng ở phân khúc giá rẻ.', 5, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(19, 1, 'Laptop ASUS X403SA N3700/2GB/500GB/Win10\r\n', 'browsing', 6.99, 30, 'https://cdn2.tgdd.vn/Products/Images/44/78342/asus-x403s-n3700-2g-500g-win10-300x300.jpg', 'ASUS X403SA N3700 là chiếc laptop giá rẻ, cấu hình nhẹ nhàng cùng nhiều cổng kết nối tiện ích cho công việc lẫn học tập.', 20, NULL, 1, 'Cấu hình phù hợp với game hay ứng dụng nhẹ\r\nMáy sử dụng chip xử lý Pentium, đạt tốc độ tối đa 2.4 GHz mang lại khả năng hoạt động tốt hơn so với các dòng máy cùng phân khúc giá hiện nay.\r\nRAM 2 GB và có thể nâng cấp', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(20, 1, 'Laptop Acer ES1 531 N3710/4GB/500GB/Win10/KhôngDVD', 'browsing', 6.99, 30, '//cdn.tgdd.vn/Products/Images/44/82541/acer-es1-531-n3710-4gb-500gb-win10-400-400x400.png', 'Nếu bạn tìm kiếm một sản phẩm có tính di động cao, mức giá hợp lý trong phân khúc phổ thông, thì laptop Acer ES1 531 sẽ là một sự lựa chọn bạn không nên bỏ qua.', 20, NULL, 1, 'Cấu hình tốt\r\nLaptop Acer ES1 531 mang trên mình vi xử lý Pentium N3710 tốc độ 1.6 GHz của Intel. Thiết bị có RAM 4 GB cùng một khe trống để bạn có thể nâng lên đến 8 GB sử dụng cho nhu cầu cao hơn.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:07'),
(21, 2, 'Máy tính bảng iPad Pro Wifi Cellular 128GB', 'browsing', 26.99, 30, '//cdn.tgdd.vn/Products/Images/522/73989/ipad-pro-wifi-cellular-128gb-400x460.png', 'Giải trí, công việc, thiết kế sang trọng, tinh tế, mạnh mẽ, uy tín là những gì mà chiếc iPad Pro mang đến cho bạn.', 20, NULL, 1, 'To hơn, không gian làm việc lớn hơn\r\nĐược thiết kế rất to với kích thước ngang lên tới hơn 30 cm, iPad Pro tỏ ra là một chiếc máy hữu hiệu cho công việc nhiều hơn là một chiếc máy giải trí để có thể dễ dàng xách đi mọi nơi.', 5, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(22, 2, 'Máy tính bảng iPad Pro 9.7 inch Wifi Cellular 256GB', 'browsing', 26.59, 30, '//cdn.tgdd.vn/Products/Images/522/78767/ipad-pro-97-inch-wifi-cellular-256gb-400-400x460.png', 'Chiếc iPad Pro 9.7 inch có cấu hình cực kì mạnh mẽ, sang trọng và trên hết là kích thước được làm gọn hơn.', 20, NULL, 1, 'Thiết kế sang trọng\r\nVới những sản phẩm đến từ Apple thì điều ưu tiên và tiên quyết của họ là thiết kế sang trọng và bắt mắt, iPad Pro 9.7 inch được làm từ chất liệu nhôm nguyên khối tạo cho máy một cảm giác cầm nắm đầm tay và cực kì sang trọng.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(23, 2, 'Máy tính bảng iPad Pro 9.7 inch Wifi Cellular 128GB', 'browsing', 22.49, 30, '//cdn.tgdd.vn/Products/Images/522/76978/ipad-pro-97-inch-wifi-cellular-128gb-400x460.png', 'Chiếc iPad Pro 9.7 inch có cấu hình cực kì mạnh mẽ, sang trọng và trên hết là kích thước được làm gọn hơn.', 20, NULL, 1, 'Thiết kế sang trọng\r\nVới những sản phẩm đến từ Apple thì điều ưu tiên và tiên quyết của họ là thiết kế sang trọng và bắt mắt, iPad Pro 9.7 inch được làm từ chất liệu nhôm nguyên khối tạo cho máy một cảm giác cầm nắm đầm tay và cực kì sang trọng.', 5, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(24, 2, 'Máy tính bảng iPad Pro Wifi 32GB', 'browsing', 19.99, 30, '//cdn.tgdd.vn/Products/Images/522/73088/ipad-pro-wifi-32gb-400x460.png', 'Giải trí, công việc, thiết kế sang trọng, tinh tế, mạnh mẽ, uy tín là những gì mà chiếc iPad Pro mang đến cho bạn.', 20, NULL, 1, 'To hơn, không gian làm việc lớn hơn\r\nVới kích thước lớn nên chiếc iPad Pro tỏ ra là một chiếc máy hữu hiệu cho công việc nhiều hơn là một chiếc máy giải trí để có thể dễ dàng xách đi mọi nơi.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(25, 2, 'Máy tính bảng iPad Pro 9.7 inch Wifi 128GB', 'browsing', 19.49, 30, '//cdn.tgdd.vn/Products/Images/522/76976/ipad-pro-97-inch-wifi-128gb-400x460.png', 'iPad Pro 9.7 inch được nâng cấp chất lượng hiển thị, chất lượng camera tốt hơn rất nhiều, cùng các phụ kiện như bàn phím rời và bút Pencil chuyên dụng.', 20, NULL, 1, 'Thiết kế sang trọng\r\niPad Pro 9.7 inch có kích thước và thiết kế rất giống với iPad Air 2 nhưng vẫn có sự khác biệt là có thêm màu vàng hồng rất hiện đại và có đến 4 chiếc loa ngoài đem đến trải nghiệm tốt hơn.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:07'),
(26, 2, 'Máy tính bảng iPad Pro 9.7 inch Wifi Cellular 32GB', 'browsing', 18.99, 30, '//cdn.tgdd.vn/Products/Images/522/76977/ipad-pro-97-inch-wifi-cellular-32gb-1-400x460.png', 'iPad Pro 9.7 inch Wifi Cellular 32GB – Đẳng cấp, mạnh mẽ và thời trang', 20, NULL, 1, 'Nhỏ hơn, mạnh hơn và tinh tế hơn là những cụm từ dùng để miêu tả iPad Pro 9.7 inch vừa mới ra mắt của Apple so với iPad Pro 12.9 inch trước kia.', 5, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(27, 2, 'Máy tính bảng iPad Air 2 Cellular 64GB', 'browsing', 15.99, 30, '//cdn.tgdd.vn/Products/Images/522/70243/ipad-air-2-cellular-64g-1-300x420.png', 'iPad Air 2 Cellular 64GB có bộ nhớ trong nhiều hơn, dành cho bạn có nhu cầu lưu trữ dữ liệu, hình ảnh.', 20, NULL, 1, 'Cảm biến vân tay\r\nĐây chính là điểm nâng cấp khác biệt so với iPad Air đời đầu, nâng cao khả năng bảo mật hơn nữa của máy.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(28, 2, 'Máy tính bảng iPad Pro 9.7 inch Wifi 32GB', 'browsing', 15.9, 30, '//cdn.tgdd.vn/Products/Images/522/75490/ipad-pro-97-inch-1-400x460.png', 'iPad Pro 9.7 inch – Máy tính bảng cao cấp, hiệu năng mượt mà', 20, NULL, 1, 'Sau khi giới thiệu chiếc iPad Pro 12.9 inch thì Apple đã đưa đến người tiêu dùng thêm một phiên bản mini khác của chiếc iPad cỡ lớn này với màn hình 9.7 inch với cấu hình tương đương.', 5, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(29, 2, 'Máy tính bảng iPad Mini 4 Wifi Cellular 64GB', 'browsing', 14.99, 30, '//cdn.tgdd.vn/Products/Images/522/73766/ipad-mini-4-wifi-cellular-64gb-400x460.png', 'iPad Mini 4 Wifi Cellular 64GB với dung lượng lớn hơn, cho bạn thêm sự lựa chọn với kết nối mạng 3G/4G cùng cấu hình mạnh mẽ.', 20, NULL, 1, 'Kết nối mạng qua nhiều cách\r\nMáy có kết nối wifi chuẩn Wi-Fi 802.11 a/b/g/n/ac cho phép bạn duyệt web nhanh hơn, ngoài ra khi không thể vào wifi, bạn có thể tùy chỉnh bật 3G/4G để tiếp tục lên mạng.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:09'),
(30, 2, 'Máy tính bảng iPad Mini 3 Retina Cellular 64GB', 'browsing', 13.99, 30, '//cdn.tgdd.vn/Products/Images/522/70368/ipad-mini-3-retina-cellular-64gb-400x533.png', 'iPad Mini 3 Retina Cellular 64GB mang lại cho người dùng hiệu năng mạnh, tiện ích 3G/4G cùng cảm biến vân tay cho dòng mini.', 20, NULL, 1, 'Cảm biến vân tay\r\nLà cải tiến lớn nhất của dòng iPad mini khi iPad Mini 3 được trang bị cảm biến, tăng khả năng bảo mật máy lên cao hơn. Cách sử dụng đơn giản, nhận diện nhanh chóng mang đến nhiều tiện lợi.', 3, 'skutestproduct', '2016-09-06 00:00:00', '2016-09-06 18:10:07');

-- --------------------------------------------------------

--
-- Table structure for table `product_attributes`
--

CREATE TABLE `product_attributes` (
  `attribute_id` int(10) UNSIGNED NOT NULL,
  `company_id` int(10) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product_attributes`
--

INSERT INTO `product_attributes` (`attribute_id`, `company_id`, `name`) VALUES
(1, 1, 'Product Name'),
(2, 1, 'Product price'),
(3, 1, 'Product color\r\n'),
(4, 1, 'Product images\r\n'),
(5, 1, 'Product details\r\n'),
(6, 2, 'Product Name'),
(7, 2, 'Product price'),
(8, 2, 'Product color\r\n'),
(9, 2, 'Product images\r\n'),
(10, 2, 'Product details\r\n');

-- --------------------------------------------------------

--
-- Table structure for table `product_attribute_details`
--

CREATE TABLE `product_attribute_details` (
  `product_id` int(10) UNSIGNED NOT NULL,
  `attribute_id` int(10) UNSIGNED NOT NULL,
  `value_string` varchar(255) DEFAULT NULL,
  `value_numberic` double DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product_attribute_details`
--

INSERT INTO `product_attribute_details` (`product_id`, `attribute_id`, `value_string`, `value_numberic`) VALUES
(1, 1, 'Test product 1', NULL),
(1, 2, NULL, 990000),
(2, 1, 'Test car', NULL),
(2, 2, NULL, 999000000);

-- --------------------------------------------------------

--
-- Table structure for table `product_categories`
--

CREATE TABLE `product_categories` (
  `product_id` int(10) UNSIGNED NOT NULL,
  `category_id` int(10) UNSIGNED NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `review_id` int(10) UNSIGNED NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `product_id` int(10) UNSIGNED NOT NULL,
  `company_id` int(10) UNSIGNED NOT NULL,
  `rank` int(10) NOT NULL,
  `comment` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `role_id` int(10) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

CREATE TABLE `suppliers` (
  `supplier_id` int(10) UNSIGNED NOT NULL,
  `company_id` int(10) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `create_date` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `supplier_products`
--

CREATE TABLE `supplier_products` (
  `product_id` int(10) UNSIGNED NOT NULL,
  `supplier_id` int(10) UNSIGNED NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `theme`
--

CREATE TABLE `theme` (
  `theme_id` int(10) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `version` varchar(255) NOT NULL,
  `thumbnail` varchar(255) NOT NULL,
  `source_path` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` varchar(32) NOT NULL,
  `company_id` int(10) UNSIGNED NOT NULL,
  `group_id` int(10) UNSIGNED NOT NULL,
  `role_id` int(10) UNSIGNED NOT NULL,
  `email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `salt` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user_tokens`
--

CREATE TABLE `user_tokens` (
  `token` varchar(32) NOT NULL,
  `company_id` int(10) UNSIGNED NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `login_date` datetime NOT NULL,
  `expiration_date` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`adress_id`);

--
-- Indexes for table `app`
--
ALTER TABLE `app`
  ADD PRIMARY KEY (`theme_id`,`company_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `companies`
--
ALTER TABLE `companies`
  ADD PRIMARY KEY (`company_id`);

--
-- Indexes for table `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`history_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_address`
--
ALTER TABLE `order_address`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_detail`
--
ALTER TABLE `order_detail`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_payments`
--
ALTER TABLE `order_payments`
  ADD PRIMARY KEY (`order_id`,`payment_id`);

--
-- Indexes for table `order_reports`
--
ALTER TABLE `order_reports`
  ADD PRIMARY KEY (`report_id`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`payment_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `product_attributes`
--
ALTER TABLE `product_attributes`
  ADD PRIMARY KEY (`attribute_id`);

--
-- Indexes for table `product_attribute_details`
--
ALTER TABLE `product_attribute_details`
  ADD PRIMARY KEY (`product_id`,`attribute_id`);

--
-- Indexes for table `product_categories`
--
ALTER TABLE `product_categories`
  ADD PRIMARY KEY (`product_id`,`category_id`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`review_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`role_id`);

--
-- Indexes for table `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`supplier_id`);

--
-- Indexes for table `supplier_products`
--
ALTER TABLE `supplier_products`
  ADD PRIMARY KEY (`product_id`,`supplier_id`);

--
-- Indexes for table `theme`
--
ALTER TABLE `theme`
  ADD PRIMARY KEY (`theme_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `user_tokens`
--
ALTER TABLE `user_tokens`
  ADD PRIMARY KEY (`token`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `address`
--
ALTER TABLE `address`
  MODIFY `adress_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `companies`
--
ALTER TABLE `companies`
  MODIFY `company_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `history`
--
ALTER TABLE `history`
  MODIFY `history_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `order_address`
--
ALTER TABLE `order_address`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `order_detail`
--
ALTER TABLE `order_detail`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `payment_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `product_attributes`
--
ALTER TABLE `product_attributes`
  MODIFY `attribute_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `product_attribute_details`
--
ALTER TABLE `product_attribute_details`
  MODIFY `product_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `reviews`
--
ALTER TABLE `reviews`
  MODIFY `review_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `supplier_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `theme`
--
ALTER TABLE `theme`
  MODIFY `theme_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
