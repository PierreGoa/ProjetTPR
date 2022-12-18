--Script de création des tables de la base Traçage Produits Restaurant

BEGIN TRANSACTION;

-- Categorie
CREATE TABLE CATEGORIE
(
	ID_CATEGORIE SERIAL PRIMARY KEY,
	NOM VARCHAR(80),
	DESIGNATION VARCHAR(255)
);

-- Frigos
CREATE TABLE FRIGO 
(
	ID_FRIGO SERIAL PRIMARY KEY,
	NOM VARCHAR(80)
);


-- Produit Reference
CREATE TABLE PRODUITREF
(
	ID_PRODUITREF SERIAL PRIMARY KEY,
	NOM VARCHAR(80),
	DESIGNATION VARCHAR(255),
	ID_CATEGORIE INT,
	FOREIGN KEY (ID_CATEGORIE) REFERENCES CATEGORIE(ID_CATEGORIE) ON DELETE SET NULL
);

-- Produit Stock
CREATE TYPE T_STATUT_PRODUIT AS ENUM ('MANAGER', 'UTILISATEUR');

CREATE TABLE PRODUITSTOCK
(
	ID_PRODUITSTOCK SERIAL PRIMARY KEY,
	DATE_OUVERTURE TIMESTAMP,
	STATUT T_STATUT_PRODUIT
);

-- Utilisateur
CREATE TYPE T_ROLE_USER AS ENUM ('MANAGER', 'UTILISATEUR');

CREATE TABLE UTILISATEUR
(
	ID_USER SERIAL PRIMARY KEY,
	NOM VARCHAR(80),
	PRENOM VARCHAR(80),
	LOGIN VARCHAR(80),
	PASSWORD VARCHAR (80),
	ROLE T_ROLE_USER
);

-- Associations

CREATE TABLE ASSO_PRISE_TEMP
(
	ID_FRIGO INTEGER,
	ID_USER INTEGER,
	DATEHEURE_PRISE TIMESTAMP,
	TEMP_MATIN DECIMAL,
	TEMP_MIDI DECIMAL,
	PRIMARY KEY (ID_USER,ID_FRIGO)
);

CREATE TABLE ASSO_MODIF_STOCK
(
	ID_PRODUITSTOCK INTEGER,
	ID_USER INTEGER,
	DATEHEURE_MODIF TIMESTAMP,
	PRIMARY KEY (ID_USER,ID_PRODUITSTOCK)
);

CREATE TABLE ASSO_APPARTIENT_STOCK
(
	ID_PRODUITREF INTEGER,
	ID_PRODUITSTOCK INTEGER,
	PRIMARY KEY (ID_PRODUITSTOCK,ID_PRODUITREF)
);

CREATE TABLE ASSO_APPARTIENT_CATEGORIE
(
	ID_PRODUITREF INTEGER,
	ID_CATEGORIE INTEGER,
	PRIMARY KEY (ID_CATEGORIE,ID_PRODUITREF)
);

-- Création enregistrements
INSERT INTO UTILISATEUR values (DEFAULT,'ADMIN','Manager','manager@resto.fr','resto1234','MANAGER');

COMMIT;