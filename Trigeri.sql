DELIMITER //

DROP TRIGGER IF EXISTS sprijeci_duple_rezervacije//

CREATE TRIGGER sprijeci_duple_rezervacije
BEFORE UPDATE ON STAN
FOR EACH ROW
BEGIN
    DECLARE staraRezervacijaOD DATE;
    DECLARE staraRezervacijaDO DATE;
    DECLARE novaRezervacijaOD DATE;
    DECLARE novaRezervacijaDO DATE;

    IF OLD.pretragaRezervacijaId IS NOT NULL 
       AND NEW.pretragaRezervacijaId IS NOT NULL 
       AND NEW.pretragaRezervacijaId != OLD.pretragaRezervacijaId THEN
        
        SELECT rezervacijaOD, rezervacijaDO INTO staraRezervacijaOD, staraRezervacijaDO
        FROM PRETRAGA_I_REZERVACIJA
        WHERE idPRETRAGA_I_REZERVACIJA = OLD.pretragaRezervacijaId;

        SELECT rezervacijaOD, rezervacijaDO INTO novaRezervacijaOD, novaRezervacijaDO
        FROM PRETRAGA_I_REZERVACIJA
        WHERE idPRETRAGA_I_REZERVACIJA = NEW.pretragaRezervacijaId;

        IF novaRezervacijaOD <= staraRezervacijaDO AND novaRezervacijaDO >= staraRezervacijaOD THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Ovaj stan je već rezervisan u datom periodu!';
        END IF;
    END IF;
END//

DELIMITER ;

DELIMITER //

DROP TRIGGER IF EXISTS azuriraj_dostupnost_rezervacije//

CREATE TRIGGER azuriraj_dostupnost_rezervacije
AFTER INSERT ON PRETRAGA_I_REZERVACIJA
FOR EACH ROW
BEGIN
    IF NEW.rezervacijaOD IS NOT NULL AND NEW.rezervacijaDO IS NOT NULL THEN
        UPDATE PRETRAGA_I_REZERVACIJA
        SET dostupnost = 0
        WHERE idPRETRAGA_I_REZERVACIJA = NEW.idPRETRAGA_I_REZERVACIJA;
    END IF;
END//

DELIMITER ;

DELIMITER //

DROP TRIGGER IF EXISTS ogranicenje_broja_stanova_po_vlasniku//

CREATE TRIGGER ogranicenje_broja_stanova_po_vlasniku
BEFORE INSERT ON STAN
FOR EACH ROW
BEGIN
    DECLARE brojStanovaVlasnika INT;
    DECLARE maxStanovaPoVlasniku INT DEFAULT 5;

    SELECT COUNT(*) INTO brojStanovaVlasnika
    FROM STAN
    WHERE VLASNIK_idVlasnik = NEW.VLASNIK_idVlasnik;

    IF brojStanovaVlasnika >= maxStanovaPoVlasniku THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Vlasnik već ima maksimalan broj stanova (5)! Ne može se dodati novi stan.';
    END IF;
END//

DELIMITER ;

