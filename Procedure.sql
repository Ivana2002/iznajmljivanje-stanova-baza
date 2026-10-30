DELIMITER //

CREATE PROCEDURE KreirajRezervaciju(
    IN p_lokacija VARCHAR(45),
    IN p_tipZaIznajmljivanje VARCHAR(45),
    IN p_rezervacijaOD DATE,
    IN p_rezervacijaDO DATE,
    IN p_stanId INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Greška prilikom kreiranja rezervacije!';
    END;

    -- Pokreni transakciju
    START TRANSACTION;

    -- Proveri da li je stan već rezervisan (provera pretragaRezervacijaId)
    IF EXISTS (
        SELECT 1 
        FROM STAN 
        WHERE idStan = p_stanId 
        AND pretragaRezervacijaId IS NOT NULL
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Ovaj stan je već rezervisan!';
    END IF;

    -- Unesi novu rezervaciju
    INSERT INTO PRETRAGA_I_REZERVACIJA (
        lokacija, 
        tipZaIznajmljivanje, 
        dostupnost, 
        rezervacijaOD, 
        rezervacijaDO
    )
    VALUES (
        p_lokacija, 
        p_tipZaIznajmljivanje, 
        1, -- Inicijalno dostupnost = 1, triger azuriraj_dostupnost_rezervacije će je postaviti na 0
        p_rezervacijaOD, 
        p_rezervacijaDO
    );

    -- Dohvati ID nove rezervacije
    SET @noviRezervacijaId = LAST_INSERT_ID();

    -- Poveži stan sa rezervacijom
    UPDATE STAN
    SET pretragaRezervacijaId = @noviRezervacijaId
    WHERE idStan = p_stanId;

    -- Potvrdi transakciju
    COMMIT;
END//

DELIMITER ;

DELIMITER //

CREATE PROCEDURE DodajNoviStan(
    IN p_brojSoba INT,
    IN p_povrsina DOUBLE,
    IN p_sprat INT,
    IN p_cijena DOUBLE,
    IN p_zgradaId INT,
    IN p_vlasnikId INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Greška prilikom dodavanja novog stana!';
    END;

    -- Pokreni transakciju
    START TRANSACTION;

    -- Proveri da li zgrada postoji
    IF NOT EXISTS (
        SELECT 1 
        FROM ZGRADA 
        WHERE idZgrade = p_zgradaId
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Zgrada sa datim ID-em ne postoji!';
    END IF;

    -- Proveri da li vlasnik postoji
    IF NOT EXISTS (
        SELECT 1 
        FROM VLASNIK 
        WHERE idVlasnik = p_vlasnikId
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Vlasnik sa datim ID-em ne postoji!';
    END IF;

    -- Unesi novi stan (triger ogranicenje_broja_stanova_po_vlasniku će proveriti limit)
    INSERT INTO STAN (
        brojSoba, 
        Povrsina, 
        sprat, 
        Cijena, 
        ZGRADA_idZgrade, 
        VLASNIK_idVlasnik, 
        pretragaRezervacijaId
    )
    VALUES (
        p_brojSoba, 
        p_povrsina, 
        p_sprat, 
        p_cijena, 
        p_zgradaId, 
        p_vlasnikId, 
        NULL -- Stan je inicijalno slobodan
    );

    -- Potvrdi transakciju
    COMMIT;
END//

DELIMITER ;


DELIMITER //

CREATE PROCEDURE PronadjiDostupneStanove(
    IN p_rezervacijaOD DATE,
    IN p_rezervacijaDO DATE
)
BEGIN
    -- Selektuj stanove koji nemaju rezervaciju ili čije rezervacije ne preklapaju dati period
    SELECT 
        s.idStan,
        s.brojSoba,
        s.Povrsina,
        s.sprat,
        s.Cijena,
        z.naziv AS nazivZgrade,
        v.ime AS imeVlasnika,
        v.prezime AS prezimeVlasnika
    FROM STAN s
    JOIN ZGRADA z ON s.ZGRADA_idZgrade = z.idZgrade
    JOIN VLASNIK v ON s.VLASNIK_idVlasnik = v.idVlasnik
    LEFT JOIN PRETRAGA_I_REZERVACIJA pir ON s.pretragaRezervacijaId = pir.idPRETRAGA_I_REZERVACIJA
    WHERE 
        -- Stanovi koji nemaju rezervaciju
        s.pretragaRezervacijaId IS NULL
        OR 
        -- Ili čije rezervacije ne preklapaju dati period
        NOT (
            pir.rezervacijaOD <= p_rezervacijaDO 
            AND pir.rezervacijaDO >= p_rezervacijaOD
        );
END//

DELIMITER ;