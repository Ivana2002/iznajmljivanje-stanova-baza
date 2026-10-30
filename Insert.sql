/*INSERT INTO DRZAVA (idDrzava, Naziv)
VALUES (1, 'Srbija'),
       (2, 'Hrvatska'),
       (3, 'Bosna i Hercegovina'),
       (4, 'Crna Gora'); */
       
/*INSERT INTO GRAD (idGrad, Naziv, DRZAVA_idDrzava)
VALUES (1, 'Beograd', 1),
       (2, 'Novi Sad', 1),
       (3, 'Zagreb', 2),
       (4, 'Split', 2),
       (5, 'Sarajevo', 3),
       (6, 'Banjaluka', 3),
       (7, 'Podgorica', 4);*/

/*INSERT INTO ADRESA_ZGRADE (idAdresa, Ulica, Broj, GRAD_idGrad)
VALUES (1, 'Bulevar kralja Aleksandra', '78', 1),
       (2, 'Trg Slobode', '15', 2),
       (3, 'Ilica', '47', 3),
       (4, 'Marmontova', '10', 4),
       (5, 'Zmaja od Bosne', '23', 5),
       (6, 'Njegoševa', '8', 6),
       (7, 'Maksim Gorki', '56', 7);*/
       
/*INSERT INTO ZGRADA (idZgrade, brojSpratova, godinaIzgradnje, imaLift, ADRESA_ZGRADE_idAdresa)
VALUES (1, 5, 1998, '1', 1),
       (2, 10, 2005, '0', 2),
       (3, 3, 1980, '1', 3),
       (4, 8, 2012, '1', 4),
       (5, 6, 1990, '0', 5),
       (6, 7, 2000, '1', 6),
       (7, 4, 1975, '0', 7);*/
       
/*INSERT INTO TIP_STANA_IZNAJMLJIVANJE (idTip_Stana, Opis)
VALUES (1, 'Na dan'),
       (2, 'Na mesec'),
       (3, 'Na godinu'),
       (4, 'Po dogovoru');*/

/*INSERT INTO TIP_STANA_PRODAJA (idTipStana, brojSoba, Kvadratura, Balkon)
VALUES (1, 2, 55, 'Da'),
       (2, 3, 75, 'Ne'),
       (3, 1, 40, 'Da'),
       (4, 4, 90, 'Da'),
       (5, 2, 60, 'Ne');*/
       
/*INSERT INTO ZAPOSLENI_U_AGENCIJI (idZaposleni, Ime, Prezime, Telefon, Email)
VALUES (1, 'Marko', 'Marković', '0621234567', 'marko.markovic@gmail.com'),
       (2, 'Jelena', 'Jovanović', '0639876543', 'jelena.jovanovic@gmail.com'),
       (3, 'Ivan', 'Ivić', '0644567890', 'ivan.ivic@gmail.com'),
       (4, 'Ana', 'Anić', '0651239876', 'ana.anic@gmail.com'),
       (5, 'Petar', 'Petrović', '0612345678', 'petar.petrovic@gmail.com');*/
       
/*INTO VLASNIK (idVlasnik, Ime, Prezime, Email, Telefon)
VALUES (1, 'Stefan', 'Savić', 'stefan.savic@gmail.com', '0612345678'),
       (2, 'Maja', 'Nikolić', 'maja.nikolic@gmail.com', '0623456789'),
       (3, 'Nikola', 'Jovanović', 'nikola.jovanovic@gmail.com', '0634567890'),
       (4, 'Ivana', 'Petrović', 'ivana.petrovic@gmail.com', '0645678901'),
       (5, 'Luka', 'Marković', 'luka.markovic@gmail.com', '0656789012');*/
       
/*INSERT INTO PRETRAGA_I_REZERVACIJA (idPRETRAGA_I_REZERVACIJA, lokacija, tipZaIznajmljivanje, rezervacijaOD, rezervacijaDO, dostupnost)
VALUES 
(1, 'Sarajevo, Centar', 1, '2025-04-01', '2025-04-01', 1),  
(2, 'Zagreb, Donji Grad', 2, '2025-05-01', '2025-05-31', 1),  
(3, 'Beograd, Vračar', 3, '2025-06-01', '2025-06-30', 0),  
(4, 'Novi Sad, Centar', 1, '2025-07-01', '2025-07-01', 0),  
(5, 'Banja Luka, Centar', 4, NULL, NULL, 1);  */





/*INSERT INTO ZAKUPAC (idZakupac, Ime, Prezime, Telefon, Email)
VALUES
(1, 'Luka', 'Mitić', '0611122334', 'luka.mitic@gmail.com'),
(2, 'Marija', 'Marković', '0622233445', 'marija.markovic@gmail.com'),
(3, 'Nemanja', 'Vuković', '0633344556', 'nemanja.vukovic@gmail.com'),
(4, 'Sara', 'Tomić', '0644455667', 'sara.tomic@gmail.com'),
(5, 'Nikola', 'Kovačević', '0655566778', 'nikola.kovacevic@gmail.com');*/

/*INSERT INTO STATUS_ZAKUPA_I_PRODAJE (idStatus_Zakupa, Opis)
VALUES
(1, 'Slobodan'),  -- Stan je slobodan za iznajmljivanje ili prodaju
(2, 'Iznajmljen'),  -- Stan je iznajmljen
(3, 'Prodat'),  -- Stan je prodat
(4, 'Rezervisan'),  -- Stan je rezervisan
(5, 'Otkazan');  -- Iznajmljivanje ili prodaja je otkazana*/

/*INSERT INTO RECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE (idRECENZIJE_STANOVA_ZA_IZNAJMLJIVANJE, ocjena, brojStana, komentar, datumRecenzije, klijent, STAN_idStan)
VALUES
(1, 5, 12, 'Odličan stan, sve je bilo kako je bilo obećano. Preporučujem!', '2025-03-28', 'Marko Jovanović', 1),
(2, 4, 4, 'Stan je bio u dobrom stanju, ali je buka u okolini bila problem.', '2025-03-28', 'Ana Petrović', 2),
(3, 1, 6, 'Stan nije bio kao na slikama. Nije bio čist i imao je nekoliko problema sa vodovodom.', '2025-03-28', 'Nikola Ilić', 3),
(4, 5, 10, 'Prelep stan! Odlična lokacija i sve je bilo u savršenom stanju.', '2025-03-28', 'Ivana Marković', 4),
(5, 3, 3, 'Stan je bio ok, ali je imao nekoliko manjih oštećenja i potrebna je renovacija.', '2025-03-28', 'Petar Savić', 5); */

/*INSERT INTO UGOVOR_ZA_IZNAJMLJIVANJE (idUgovor, datumPocetka, datumKraja, cijenaIznajmljivanja, ZAKUPAC_idZakupac, STAN_idStan, STATUS_ZAKUPA_idStatus_Zakupa, VLASNIK_idVlasnik, ZAPOSLENI_idZaposelni)
VALUES 
(1, '2025-04-01', '2025-04-01', 50, 1, 1, 2, 1, 1),  -- Na dan, cena je dnevna
(2, '2025-04-01', '2025-05-01', 650, 2, 2, 2, 2, 2),  -- Na mesec, cena je mesečna
(3, '2025-05-01', '2025-06-01', 700, 3, 3, 4, 3, 3),  -- Na mesec, cena je mesečna
(4, '2025-06-01', '2025-09-01', 800, 4, 4, 1, 4, 4),  -- Na mesec, cena je mesečna
(5, '2025-07-01', '2025-07-01', 60, 5, 5, 2, 5, 5);  -- Na dan, cena je dnevna*/

/*INSERT INTO PLACANJE (idPlacanje, datumPlacanja, Iznos, UGOVOR_ZA_IZNAJMLJIVANJE_idUgovor)
VALUES
(1, '2025-04-01', 50, 1),  
(2, '2025-05-01', 650, 2),  
(3, '2025-06-01', 50, 3),  
(4, '2025-07-01', 700, 4),  
(5, '2025-08-01', 800, 5); */

/*INSERT INTO UGOVOR_ZA_PRODAJU (idUgovor, datumProdaje, imeKupca, brojStana, STAN_idStan, tipStana, Kvadratura, UGOVOR_ZA_PRODAJUcol, Balkon, ImeProdavca, cijenaStana, ZAPOSLENI_U_AGENCIJI_idZaposelni, ZAKUPAC_idZakupac, VLASNIK_idVlasnik)
VALUES
(1, '2025-04-15', 'Luka Mitić', 12, 1, 'Stan za prodaju', 45, 'Povoljno', 'Da', 'Marko Marković', 95000, 1, 1, 1),
(2, '2025-05-10', 'Marija Marković', 4, 2, 'Stan za prodaju', 55, 'Skupo', 'Ne', 'Jelena Jovanović', 110000, 2, 2, 2),
(3, '2025-06-20', 'Nemanja Vuković', 6, 3, 'Stan za prodaju', 70, 'Povoljno', 'Da', 'Ivan Ivić', 145000, 3, 3, 3),
(4, '2025-07-01', 'Sara Tomić', 10, 4, 'Stan za prodaju', 60, 'Skupo', 'Ne', 'Ana Anić', 120000, 4, 4, 4),
(5, '2025-08-25', 'Nikola Kovačević', 3, 5, 'Stan za prodaju', 80, 'Povoljno', 'Da', 'Petar Petrović', 160000, 5, 5, 5);*/
 


/*INSERT INTO stan (Povrsina, VLASNIK_idVlasnik, ZGRADA_idZgrade, sprat, brojStana, vrstaUsluge_iznajmljivanje, 
    PRETRAGA_I_REZERVACIJA_idPRETRAGA_I_REZERVACIJA, TIP_STANA_IZNAJMLJIVANJE_idTip_Stana, 
    cijenaDan, cijenaMesec, cijenaGodina, cijenaPoDogovoru)
VALUES
(45, 1, 1, 3, 12, 2, 1, 2, 50, 1200, 14000, FALSE),
(55, 2, 2, 2, 4, 3, 2, 3, 60, 1400, 16000, FALSE),
(70, 3, 3, 4, 6, 1, 3, 1, NULL, NULL, NULL, TRUE), -- Ovaj stan se izdaje po dogovoru
(60, 4, 4, 5, 10, 2, 4, 2, 55, 1300, 15000, FALSE),
(80, 5, 5, 2, 3, 4, 5, 4, 80, 2000, 22000, FALSE);*/

INSERT INTO stan (Povrsina, VLASNIK_idVlasnik, ZGRADA_idZgrade, sprat, brojStana, vrstaUsluge_iznajmljivanje, 
    PRETRAGA_I_REZERVACIJA_idPRETRAGA_I_REZERVACIJA, TIP_STANA_IZNAJMLJIVANJE_idTip_Stana, 
    cijenaDan, cijenaMesec, cijenaGodina, cijenaPoDogovoru)
VALUES
(45, 1, 1, 3, 12, 2, 1, 2, 50, 1200, 14000, FALSE),
(55, 2, 2, 2, 4, 3, 2, 3, 60, 1400, 16000, FALSE),
(70, 3, 3, 4, 6, 1, 3, 1, NULL, NULL, NULL, TRUE), -- Ovaj stan se izdaje po dogovoru
(60, 4, 4, 5, 10, 2, 4, 2, 55, 1300, 15000, FALSE),
(80, 5, 5, 2, 3, 4, 5, 4, 80, 2000, 22000, FALSE);





