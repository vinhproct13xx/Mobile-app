-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 27, 2019 lúc 03:37 PM
-- Phiên bản máy phục vụ: 10.1.38-MariaDB
-- Phiên bản PHP: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `appbanhang`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE `category` (
  `Id` int(11) NOT NULL,
  `Name` varchar(200) NOT NULL,
  `Image` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`Id`, `Name`, `Image`) VALUES
(1, 'Áo sơ mi nam', 'https://img.ltwebstatic.com/images2_pi/2019/06/10/1560159638758743579_thumbnail_600x799.webp'),
(2, 'Quần nam', 'https://img.ltwebstatic.com/images3_pi/2019/10/23/1571809282550b995bfd28814c371ce3c8e77044e5_thumbnail_405x552.webp'),
(3, 'Giày nam', 'https://img.ltwebstatic.com/images3_pi/2019/10/23/1571820151170e0a10415c189647cfe343b795cb36_thumbnail_600x.webp');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `comment`
--

CREATE TABLE `comment` (
  `makh` int(11) NOT NULL,
  `masp` int(11) NOT NULL,
  `content` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `comment`
--

INSERT INTO `comment` (`makh`, `masp`, `content`) VALUES
(1, 1, 'qua dep'),
(2, 1, 'sieu ben'),
(2, 1, 'xau vai'),
(6, 1, 'aaaaaa'),
(6, 7, 'ad');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cthd`
--

CREATE TABLE `cthd` (
  `mahd` int(11) NOT NULL,
  `masp` int(11) NOT NULL,
  `soluong` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `cthd`
--

INSERT INTO `cthd` (`mahd`, `masp`, `soluong`) VALUES
(1, 1, 1),
(2, 7, 1),
(3, 7, 1),
(4, 1, 1),
(5, 1, 7);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadon`
--

CREATE TABLE `hoadon` (
  `mahd` int(11) NOT NULL,
  `makh` int(11) DEFAULT NULL,
  `tongtien` int(11) DEFAULT NULL,
  `nguoinhan` varchar(100) DEFAULT NULL,
  `sodienthoai` varchar(12) DEFAULT NULL,
  `diachigiao` varchar(200) DEFAULT NULL,
  `ngaythanhtoan` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `hoadon`
--

INSERT INTO `hoadon` (`mahd`, `makh`, `tongtien`, `nguoinhan`, `sodienthoai`, `diachigiao`, `ngaythanhtoan`) VALUES
(1, 1, 200000, 'Nguyen Van A', '02356458265', '23 Hai Ba Trung, Q1, TpHCM', '2019-12-27'),
(2, 6, 400000, 'doan', '123', 'a', '2019-12-27'),
(3, 6, 400000, 'doan', '123', 'a', '2019-12-27'),
(4, 6, 200000, 'doan', '123', 'a', '2019-12-27'),
(5, 6, 1400000, 'doan', '123', 'a', '2019-12-27');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `makh` int(11) NOT NULL,
  `tenkh` varchar(100) DEFAULT NULL,
  `dienthoai` varchar(12) DEFAULT NULL,
  `diachi` varchar(200) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`makh`, `tenkh`, `dienthoai`, `diachi`, `email`, `password`) VALUES
(1, 'Nguyen Van A', '02356458265', 'Quan cam', 'vana@gmail', '123'),
(2, 'nguyen van b', NULL, NULL, 'vanb@gmail', '123'),
(3, 'c', '123', 'q', 'c@gmail', '123'),
(4, 'd', '123', 'q', 'd@gmail', '123'),
(5, 'e', '12', 'q', 'e@gmail', '1'),
(6, 'doan', '123', 'a', 'doan@gmail', '2');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `Id` int(11) NOT NULL,
  `Name` varchar(200) DEFAULT NULL,
  `Price` int(15) DEFAULT NULL,
  `Discount` int(15) DEFAULT '0',
  `Image` varchar(200) DEFAULT NULL,
  `Desciption` varchar(1000) DEFAULT NULL,
  `Id_Category` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`Id`, `Name`, `Price`, `Discount`, `Image`, `Desciption`, `Id_Category`) VALUES
(1, 'Men Shirts tay dài', 400000, 50, 'https://img.ltwebstatic.com/images3_pi/2019/09/24/1569306363dbff5236ffe72e7a50def8678c9c836a_thumbnail_600x.webp', 'Phong cách:	Giải trí\r\nMàu sắc :	Màu xanh lam\r\nKiểu mẫu:	Hình học\r\nVòng cổ:	Vòng cổ\r\nChiều dài:	Thường xuyên\r\nLoại áo sơ mi:	Áo sơ mi\r\nChiều dài tay:	Tay áo dài\r\nLoại tay áo:	Tay thường\r\nMùa:	Mùa Xuân/ Mùa Thu', 1),
(2, 'Men Shirts Nút Sọc', 30000, 0, 'https://img.ltwebstatic.com/images3_pi/2019/11/26/15747700401991fa1acf96890716162d8a63fc7df0_thumbnail_600x.webp', 'Màu sắc :	Xám\r\nKiểu mẫu:	Sọc\r\nVòng cổ:	Đứng cổ áo\r\nChiều dài:	Thường xuyên\r\nLoại áo sơ mi:	Áo sơ mi', 1),
(3, 'Men Shirts Sọc ', 500000, 10, 'https://img.ltwebstatic.com/images2_pi/2019/08/19/1566209106206349541_thumbnail_600x799.webp', 'Phong cách:	Sẵn sàng\r\nMàu sắc :	Màu vàng, Bright\r\nKiểu mẫu:	kẻ sọc\r\nVòng cổ:	Vòng cổ\r\nChiều dài:	Thường xuyên\r\nLoại áo sơ mi:	Áo sơ mi\r\n', 1),
(4, 'Men Shirts Nút ', 300000, 0, 'https://img.ltwebstatic.com/images3_pi/2019/10/17/15713043973c743fbd7fa792bf8773c1517253ffc7_thumbnail_600x.webp', 'Phong cách:	Giải trí\r\nMàu sắc :	Burgundy\r\nKiểu mẫu:	màu trơn\r\nVòng cổ:	Vòng cổ\r\nChiều dài:	Thường xuyên', 1),
(5, 'Áo sơ mi đen', 200000, 10, 'https://4menshop.com/images/thumbs/2019/05/ao-so-mi-den-asm1260-10804-slide-products-5ce7c634689a2.png', 'Áo Sơ Mi Đen ASM1260 được may từ chất vải cao cấp, mềm mại, ít nhăn, thấm hút tốt, thoáng mát và giặt mau khô. Thiết kế độc quyền bởi 4MEN, kiểu dáng hiện đại, sang trọng, form áo chuẩn tôn dáng là lựa chọn lý tưởng dành cho phái mạnh.\r\n', 1),
(6, 'Giày tây cao đen', 200000, 50, 'https://4menshop.com/images/thumbs/2018/11/giay-tang-chieu-cao-den-g206-10257-slide-products-5bfcf87273b40.jpg', 'Giày Tăng Chiều Cao Đen G206 chất liệu da sang trọng, bền, nam tính. Kiểu dáng đơn giản, tinh tế, thoải mái khi sử dụng. Đế tăng chiều cao bí mật 5-6cm. Dễ dàng kết hợp trang phục cho giới văn phòng, sinh viên...', 3),
(7, 'Quần jean xanh', 500000, 20, 'https://4menshop.com/images/thumbs/2019/01/quan-jeans-rach-xanh-bien-qj1635-10582-slide-products-5c36ca59a8685.jpg', 'Quần siêu đẹp siêu bền siêu rẻ', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `makh` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`makh`, `username`, `password`) VALUES
(1, 'doan', '123'),
(2, 'peter', '123');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`Id`);

--
-- Chỉ mục cho bảng `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`makh`,`masp`,`content`),
  ADD KEY `masp` (`masp`);

--
-- Chỉ mục cho bảng `cthd`
--
ALTER TABLE `cthd`
  ADD PRIMARY KEY (`mahd`,`masp`),
  ADD KEY `masp` (`masp`);

--
-- Chỉ mục cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`mahd`),
  ADD KEY `makh` (`makh`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`makh`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Id_Category` (`Id_Category`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`makh`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `category`
--
ALTER TABLE `category`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `mahd` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `makh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `product`
--
ALTER TABLE `product`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `makh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`makh`) REFERENCES `khachhang` (`makh`),
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`masp`) REFERENCES `product` (`Id`);

--
-- Các ràng buộc cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`makh`) REFERENCES `khachhang` (`makh`);

--
-- Các ràng buộc cho bảng `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`Id_Category`) REFERENCES `category` (`Id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
