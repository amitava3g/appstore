CREATE TABLE `app` (
  `id` varchar(255) NOT NULL,
  `date` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `master_data` (
  `id` varchar(255) NOT NULL,
  `app_name` varchar(255) DEFAULT NULL,
  `app_type` varchar(255) DEFAULT NULL,
  `app_type_display_name` varchar(255) DEFAULT NULL,
  `bundleid` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into master_data values(1, "My App - 1", "type-1", "My App - 1", "com.my.app1", "My Company 1");
insert into master_data values(2, "My App - 2", "type-2", "My App - 2", "com.my.app2", "My Company 2");
commit;