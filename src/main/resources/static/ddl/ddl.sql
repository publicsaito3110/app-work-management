/* MySQL(ver8.0.27) */

/* users */
CREATE TABLE `users` (
  `id` varchar(40) NOT NULL,
  `email` varchar(254) NOT NULL,
  `office` varchar(30) NOT NULL,
  `name` varchar(20) NOT NULL,
  `name_kana` varchar(40) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `password` text NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `tel` varchar(15) DEFAULT NULL,
  `note` varchar(400) DEFAULT NULL,
  `icon_kbn` varchar(1) DEFAULT NULL,
  `admin_flg` varchar(1) DEFAULT NULL,
  `del_flg` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`, `email`)
);