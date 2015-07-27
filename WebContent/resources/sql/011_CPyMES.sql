-- =============================================
-- Author:  	Julian Barragan Verano
-- Create date: 27-JUL-2015
-- Description: TABLE PARAMETROS
-- Name SQL: 010_CPyMES.sql
-- =============================================

CREATE TABLE `nit_onix` (
  `id` bigint(20) NOT NULL,
  `code_service` varchar(100) NOT NULL,
  `nit` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




CREATE TABLE `parametro` (
  `name` varchar(45) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `parametro` (`name`,`value`) VALUES ('alarmRestoreCPYMES','0');
INSERT INTO `parametro` (`name`,`value`) VALUES ('alarmRestoreIVR','0');
INSERT INTO `parametro` (`name`,`value`) VALUES ('fechaUltimoCargueNits','27/07/15');
INSERT INTO `parametro` (`name`,`value`) VALUES ('sendFromKou','0');
INSERT INTO `parametro` (`name`,`value`) VALUES ('sendToCPYMESActive','0');
INSERT INTO `parametro` (`name`,`value`) VALUES ('sendToCPYMESNoActive','0');
INSERT INTO `parametro` (`name`,`value`) VALUES ('sendToIVR','0');

CREATE TABLE `fecha_esperanza` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `divisional` varchar(45) NOT NULL,
  `causa` varchar(45) NOT NULL,
  `numero_horas` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

INSERT INTO `fecha_esperanza` (`id`,`divisional`,`causa`,`numero_horas`) VALUES (1,'Centro','Fibra',17);
INSERT INTO `fecha_esperanza` (`id`,`divisional`,`causa`,`numero_horas`) VALUES (2,'Centro','Nodo',7);
INSERT INTO `fecha_esperanza` (`id`,`divisional`,`causa`,`numero_horas`) VALUES (3,'Occidente','Fibra',19);
INSERT INTO `fecha_esperanza` (`id`,`divisional`,`causa`,`numero_horas`) VALUES (4,'Occidente','Nodo',8);
INSERT INTO `fecha_esperanza` (`id`,`divisional`,`causa`,`numero_horas`) VALUES (5,'Norte','Fibra',21);
INSERT INTO `fecha_esperanza` (`id`,`divisional`,`causa`,`numero_horas`) VALUES (6,'Norte','Nodo',9);

