CREATE TABLE `campeonato` (
  `qtd_turnos` int(11) NOT NULL,
  `qtd_rodadas` int(11) NOT NULL,
  `qtd_partidas` int(11) NOT NULL,
  `qtd_clubes` int(11) NOT NULL,
  PRIMARY KEY (`qtd_turnos`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='campeonato';

CREATE TABLE `rodada` (
  `numero_rodada` int(11) NOT NULL,
  `numero_turno` int(11) NOT NULL,
  PRIMARY KEY (`numero_rodada`,`numero_turno`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='rodada';

CREATE TABLE `turno` (
  `numero_turno` int(11) NOT NULL,
  PRIMARY KEY (`numero_turno`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='turno';

CREATE TABLE `partida` (
  `numero_partida` int(11) NOT NULL AUTO_INCREMENT,
  `numero_rodada` int(11) NOT NULL,
  `time_a` int(11) NOT NULL,
  `time_b` int(11) NOT NULL,
  `gols_a` int(11),
  `gols_b` int(11),
  `ca_a` int(11),
  `ca_b` int(11),
  `cv_a` int(11),
  `cv_b` int(11),
  PRIMARY KEY (`numero_partida`),
  KEY `fk2` (`numero_rodada`),
  CONSTRAINT `FK_rodada` FOREIGN KEY (`numero_rodada`) REFERENCES `partida` (`numero_rodada`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  KEY `fk3` (`time_a`),
  CONSTRAINT `FK_rodada` FOREIGN KEY (`time_a`) REFERENCES `time` (`numero_time`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  KEY `fk4` (`time_b`),
  CONSTRAINT `FK_rodada` FOREIGN KEY (`time_b`) REFERENCES `time` (`numero_time`) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='partida';

CREATE TABLE `time` ( 
  `numero_time` int(11) NOT NULL AUTO_INCREMENT,
  `nome_time` varchar(45) NOT NULL,
  `pontos` int(11),
  `vitorias` int(11),
  `derrotas` int(11),
  `empates` int(11),
  `gols_pro` int(11),
  `gols_contra` int(11),
  `saldo_gols` int(11),
  `ca` int(11),
  `cv` int(11),
  `aproveitamento` double,
  PRIMARY KEY(`numero_time`),
  UNIQUE KEY `UK_nomeTime`(`nome_time`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='time';

