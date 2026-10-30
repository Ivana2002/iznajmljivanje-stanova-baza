-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`AGENCIJA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`AGENCIJA` (
  `idAgencija` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NULL,
  `Adresa` VARCHAR(45) NULL,
  `Telefon` VARCHAR(20) NULL,
  `Email` VARCHAR(45) NULL,
  PRIMARY KEY (`idAgencija`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`VLASNIK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`VLASNIK` (
  `idVlasnik` INT NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(45) NULL,
  `Prezime` VARCHAR(45) NULL,
  `Email` VARCHAR(45) NULL,
  `Telefon` VARCHAR(45) NULL,
  PRIMARY KEY (`idVlasnik`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`DRZAVA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`DRZAVA` (
  `idDrzava` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NULL,
  PRIMARY KEY (`idDrzava`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`GRAD`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`GRAD` (
  `idGrad` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NULL,
  `DRZAVA_idDrzava` INT NOT NULL,
  PRIMARY KEY (`idGrad`),
  INDEX `fk_GRAD_DRZAVA1_idx` (`DRZAVA_idDrzava` ASC) VISIBLE,
  CONSTRAINT `fk_GRAD_DRZAVA1`
    FOREIGN KEY (`DRZAVA_idDrzava`)
    REFERENCES `mydb`.`DRZAVA` (`idDrzava`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ADRESA_ZGRADE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ADRESA_ZGRADE` (
  `idAdresa` INT NOT NULL AUTO_INCREMENT,
  `Ulica` VARCHAR(45) NOT NULL,
  `Broj` VARCHAR(45) NOT NULL,
  `GRAD_idGrad` INT NOT NULL,
  PRIMARY KEY (`idAdresa`),
  INDEX `fk_ADRESA_ZGRADE_GRAD1_idx` (`GRAD_idGrad` ASC) VISIBLE,
  CONSTRAINT `fk_ADRESA_ZGRADE_GRAD1`
    FOREIGN KEY (`GRAD_idGrad`)
    REFERENCES `mydb`.`GRAD` (`idGrad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ZGRADA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ZGRADA` (
  `idZgrade` INT NOT NULL AUTO_INCREMENT,
  `brojSpratova` INT NOT NULL,
  `godinaIzgradnje` INT NOT NULL,
  `imaLift` TINYINT NOT NULL,
  `ADRESA_ZGRADE_idAdresa` INT NOT NULL,
  PRIMARY KEY (`idZgrade`),
  INDEX `fk_ZGRADA_ADRESA_ZGRADE1_idx` (`ADRESA_ZGRADE_idAdresa` ASC) VISIBLE,
  CONSTRAINT `fk_ZGRADA_ADRESA_ZGRADE1`
    FOREIGN KEY (`ADRESA_ZGRADE_idAdresa`)
    REFERENCES `mydb`.`ADRESA_ZGRADE` (`idAdresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TIP_STANA_IZNAJMLJIVANJE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TIP_STANA_IZNAJMLJIVANJE` (
  `idTip_Stana` INT NOT NULL AUTO_INCREMENT,
  `Opis` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idTip_Stana`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ZAKUPAC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ZAKUPAC` (
  `idZakupac` INT NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(45) NULL,
  `Prezime` VARCHAR(45) NULL,
  `Telefon` VARCHAR(20) NULL,
  `Email` VARCHAR(45) NULL,
  PRIMARY KEY (`idZakupac`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`STATUS_ZAKUPA_I_PRODAJE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`STATUS_ZAKUPA_I_PRODAJE` (
  `idStatus_Zakupa` INT NOT NULL AUTO_INCREMENT,
  `Opis` VARCHAR(45) NULL,
  PRIMARY KEY (`idStatus_Zakupa`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PRETRAGA_I_REZERVACIJA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`PRETRAGA_I_REZERVACIJA` (
  `idPRETRAGA_I_REZERVACIJA` INT NOT NULL AUTO_INCREMENT,
  `lokacija` VARCHAR(45) NOT NULL,
  `tipZaIznajmljivanje` VARCHAR(45) NOT NULL,
  `dostupnost` TINYINT NOT NULL,
  `rezervacijaOD` DATE NULL,
  `rezervacijaDO` DATE NULL,
  PRIMARY KEY (`idPRETRAGA_I_REZERVACIJA`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TIP_STANA_PRODAJA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TIP_STANA_PRODAJA` (
  `idTipStana` INT NOT NULL AUTO_INCREMENT,
  `brojSoba` INT NOT NULL,
  `Kvadratura` INT NOT NULL,
  `Balkon` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idTipStana`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`STAN`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`STAN` (
  `idStan` INT NOT NULL AUTO_INCREMENT,
  `Povrsina` INT NULL,
  `Cijena` DOUBLE NULL,
  `VLASNIK_idVlasnik` INT NOT NULL,
  `ZGRADA_idZgrade` INT NOT NULL,
  `sprat` VARCHAR(45) NOT NULL,
  `brojStana` INT NOT NULL,
  `vrstaUsluge_prodaja` VARCHAR(45) NOT NULL,
  `vrstaUsluge_iznajmljivanje` VARCHAR(45) NOT NULL,
  `cijenaProdaje` DOUBLE NOT NULL,
  `PRETRAGA_I_REZERVACIJA_idPRETRAGA_I_REZERVACIJA` INT NOT NULL,
  `TIP_STANA_PRODAJA_idTipStana` INT NOT NULL,
  `TIP_STANA_IZNAJMLJIVANJE_idTip_Stana` INT NOT NULL,
  PRIMARY KEY (`idStan`),
  INDEX `fk_STAN_VLASNIK_idx` (`VLASNIK_idVlasnik` ASC) VISIBLE,
  INDEX `fk_STAN_ZGRADA1_idx` (`ZGRADA_idZgrade` ASC) VISIBLE,
  INDEX `fk_STAN_PRETRAGA_I_REZERVACIJA1_idx` (`PRETRAGA_I_REZERVACIJA_idPRETRAGA_I_REZERVACIJA` ASC) VISIBLE,
  INDEX `fk_STAN_TIP_STANA_PRODAJA1_idx` (`TIP_STANA_PRODAJA_idTipStana` ASC) VISIBLE,
  INDEX `fk_STAN_TIP_STANA_IZNAJMLJIVANJE1_idx` (`TIP_STANA_IZNAJMLJIVANJE_idTip_Stana` ASC) VISIBLE,
  CONSTRAINT `fk_STAN_VLASNIK`
    FOREIGN KEY (`VLASNIK_idVlasnik`)
    REFERENCES `mydb`.`VLASNIK` (`idVlasnik`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_STAN_ZGRADA1`
    FOREIGN KEY (`ZGRADA_idZgrade`)
    REFERENCES `mydb`.`ZGRADA` (`idZgrade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_STAN_PRETRAGA_I_REZERVACIJA1`
    FOREIGN KEY (`PRETRAGA_I_REZERVACIJA_idPRETRAGA_I_REZERVACIJA`)
    REFERENCES `mydb`.`PRETRAGA_I_REZERVACIJA` (`idPRETRAGA_I_REZERVACIJA`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_STAN_TIP_STANA_PRODAJA1`
    FOREIGN KEY (`TIP_STANA_PRODAJA_idTipStana`)
    REFERENCES `mydb`.`TIP_STANA_PRODAJA` (`idTipStana`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_STAN_TIP_STANA_IZNAJMLJIVANJE1`
    FOREIGN KEY (`TIP_STANA_IZNAJMLJIVANJE_idTip_Stana`)
    REFERENCES `mydb`.`TIP_STANA_IZNAJMLJIVANJE` (`idTip_Stana`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ZAPOSLENI_U_AGENCIJI`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ZAPOSLENI_U_AGENCIJI` (
  `idZaposleni` INT NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(45) NULL,
  `Prezime` VARCHAR(45) NULL,
  `Telefon` VARCHAR(20) NULL,
  `Email` VARCHAR(45) NULL,
  PRIMARY KEY (`idZaposleni`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`UGOVOR_ZA_IZNAJMLJIVANJE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`UGOVOR_ZA_IZNAJMLJIVANJE` (
  `idUgovor` INT NOT NULL AUTO_INCREMENT,
  `datumPocetka` DATE NULL,
  `datumKraja` DATE NULL,
  `cijenaIznajmljivanja` DOUBLE NOT NULL,
  `ZAKUPAC_idZakupac` INT NOT NULL,
  `STAN_idStan` INT NOT NULL,
  `STATUS_ZAKUPA_idStatus_Zakupa` INT NOT NULL,
  `VLASNIK_idVlasnik` INT NOT NULL,
  `ZAPOSLENI_idZaposelni` INT NOT NULL,
  PRIMARY KEY (`idUgovor`),
  INDEX `fk_UGOVOR_ZAKUPAC1_idx` (`ZAKUPAC_idZakupac` ASC) VISIBLE,
  INDEX `fk_UGOVOR_STAN1_idx` (`STAN_idStan` ASC) VISIBLE,
  INDEX `fk_UGOVOR_STATUS_ZAKUPA1_idx` (`STATUS_ZAKUPA_idStatus_Zakupa` ASC) VISIBLE,
  INDEX `fk_UGOVOR_VLASNIK1_idx` (`VLASNIK_idVlasnik` ASC) VISIBLE,
  INDEX `fk_UGOVOR_ZAPOSLENI1_idx` (`ZAPOSLENI_idZaposelni` ASC) VISIBLE,
  CONSTRAINT `fk_UGOVOR_ZAKUPAC1`
    FOREIGN KEY (`ZAKUPAC_idZakupac`)
    REFERENCES `mydb`.`ZAKUPAC` (`idZakupac`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UGOVOR_STAN1`
    FOREIGN KEY (`STAN_idStan`)
    REFERENCES `mydb`.`STAN` (`idStan`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UGOVOR_STATUS_ZAKUPA1`
    FOREIGN KEY (`STATUS_ZAKUPA_idStatus_Zakupa`)
    REFERENCES `mydb`.`STATUS_ZAKUPA_I_PRODAJE` (`idStatus_Zakupa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UGOVOR_VLASNIK1`
    FOREIGN KEY (`VLASNIK_idVlasnik`)
    REFERENCES `mydb`.`VLASNIK` (`idVlasnik`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UGOVOR_ZAPOSLENI1`
    FOREIGN KEY (`ZAPOSLENI_idZaposelni`)
    REFERENCES `mydb`.`ZAPOSLENI_U_AGENCIJI` (`idZaposleni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PLACANJE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`PLACANJE` (
  `idPlacanje` INT NOT NULL AUTO_INCREMENT,
  `datumPlacanja` DATE NULL,
  `Iznos` DOUBLE NULL,
  `UGOVOR_ZA_IZNAJMLJIVANJE_idUgovor` INT NOT NULL,
  PRIMARY KEY (`idPlacanje`),
  INDEX `fk_PLACANJE_UGOVOR_ZA_IZNAJMLJIVANJE1_idx` (`UGOVOR_ZA_IZNAJMLJIVANJE_idUgovor` ASC) VISIBLE,
  CONSTRAINT `fk_PLACANJE_UGOVOR_ZA_IZNAJMLJIVANJE1`
    FOREIGN KEY (`UGOVOR_ZA_IZNAJMLJIVANJE_idUgovor`)
    REFERENCES `mydb`.`UGOVOR_ZA_IZNAJMLJIVANJE` (`idUgovor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`UGOVOR_ZA_PRODAJU`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`UGOVOR_ZA_PRODAJU` (
  `idUgovor` INT NOT NULL AUTO_INCREMENT,
  `datumProdaje` DATE NOT NULL,
  `imeKupca` VARCHAR(45) NOT NULL,
  `brojStana` INT NOT NULL,
  `STAN_idStan` INT NOT NULL,
  `tipStana` VARCHAR(45) NOT NULL,
  `Kvadratura` INT NOT NULL,
  `UGOVOR_ZA_PRODAJUcol` VARCHAR(45) NOT NULL,
  `Balkon` VARCHAR(45) NOT NULL,
  `ImeProdavca` VARCHAR(45) NOT NULL,
  `cijenaStana` DOUBLE NOT NULL,
  `ZAPOSLENI_U_AGENCIJI_idZaposelni` INT NOT NULL,
  `ZAKUPAC_idZakupac` INT NOT NULL,
  `VLASNIK_idVlasnik` INT NOT NULL,
  PRIMARY KEY (`idUgovor`),
  INDEX `fk_UGOVOR_ZA_PRODAJU_STAN1_idx` (`STAN_idStan` ASC) VISIBLE,
  INDEX `fk_UGOVOR_ZA_PRODAJU_ZAPOSLENI_U_AGENCIJI1_idx` (`ZAPOSLENI_U_AGENCIJI_idZaposelni` ASC) VISIBLE,
  INDEX `fk_UGOVOR_ZA_PRODAJU_ZAKUPAC1_idx` (`ZAKUPAC_idZakupac` ASC) VISIBLE,
  INDEX `fk_UGOVOR_ZA_PRODAJU_VLASNIK1_idx` (`VLASNIK_idVlasnik` ASC) VISIBLE,
  CONSTRAINT `fk_UGOVOR_ZA_PRODAJU_STAN1`
    FOREIGN KEY (`STAN_idStan`)
    REFERENCES `mydb`.`STAN` (`idStan`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UGOVOR_ZA_PRODAJU_ZAPOSLENI_U_AGENCIJI1`
    FOREIGN KEY (`ZAPOSLENI_U_AGENCIJI_idZaposelni`)
    REFERENCES `mydb`.`ZAPOSLENI_U_AGENCIJI` (`idZaposleni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UGOVOR_ZA_PRODAJU_ZAKUPAC1`
    FOREIGN KEY (`ZAKUPAC_idZakupac`)
    REFERENCES `mydb`.`ZAKUPAC` (`idZakupac`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UGOVOR_ZA_PRODAJU_VLASNIK1`
    FOREIGN KEY (`VLASNIK_idVlasnik`)
    REFERENCES `mydb`.`VLASNIK` (`idVlasnik`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`RECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`RECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE` (
  `idRECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE` INT NOT NULL AUTO_INCREMENT,
  `ocjena` DOUBLE NOT NULL,
  `brojStana` INT NOT NULL,
  `komentar` VARCHAR(200) NOT NULL,
  `datumRecenzije` DATE NOT NULL,
  `klijent` VARCHAR(45) NOT NULL,
  `STAN_idStan` INT NOT NULL,
  PRIMARY KEY (`idRECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE`),
  INDEX `fk_RECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE_STAN1_idx` (`STAN_idStan` ASC) VISIBLE,
  CONSTRAINT `fk_RECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE_STAN1`
    FOREIGN KEY (`STAN_idStan`)
    REFERENCES `mydb`.`STAN` (`idStan`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
