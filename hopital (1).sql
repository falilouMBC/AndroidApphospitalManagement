-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mer. 25 mai 2022 à 13:33
-- Version du serveur : 10.4.22-MariaDB
-- Version de PHP : 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `hopital`
--

-- --------------------------------------------------------

--
-- Structure de la table `consultation`
--

CREATE TABLE `consultation` (
  `id` int(10) NOT NULL,
  `login` varchar(50) NOT NULL,
  `dateC` varchar(50) NOT NULL,
  `symptome` varchar(50) NOT NULL,
  `medoc` varchar(50) NOT NULL,
  `medecin` varchar(50) NOT NULL,
  `services` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `consultation`
--

INSERT INTO `consultation` (`id`, `login`, `dateC`, `symptome`, `medoc`, `medecin`, `services`) VALUES
(1, 'falilou', '11/05/2022', 'Grippe', 'CaC1000(M), grippex(M,M), efferalgan(M,M,S)', 'Diop', 'consultation generale'),
(2, 'falilou', '13/05/2022', 'Appolo', 'Genta collyre(M,M,S)', 'CISSE', 'Medecine generale'),
(3, 'mbagnick', '16/05/2022', 'Constipation', 'Dufalac(Apres chaque repas)', 'GUEYE', 'Medecine generale'),
(5, 'mbagnick', '01/05/2022', 'Diarrhee', 'Eucarbone(apres chaque repas), Paregoric(apres cha', 'Seck', 'Medecine generale'),
(9, 'gallobamba', '249', 'sghs', 'vsjs', 'sbd', 'vsjsj'),
(10, 'gallobamba', '12/05/2022', 'gdkkd', 'jdlddl', 'jdkfk', 'jkfk'),
(11, 'falilou', '22/05/2022', 'Courbature', 'felden(matin et soir)', 'CISSE', 'medecine generale'),
(12, 'falilou', '18/05/2022', 'Courbature', 'Felden(matin et soir)', 'Gueye', 'medecine generale'),
(13, 'falilou', '21/05/2022', 'Diabete', 'Diamicron(matin et soir)', 'Dr Ka', 'medecine generale'),
(14, 'patient-SK', '18/05/2022', 'Diarrhee', 'Eucarbon(Matin et soir)', 'Diop', 'Medecine generale');

-- --------------------------------------------------------

--
-- Structure de la table `dossier`
--

CREATE TABLE `dossier` (
  `id` int(10) NOT NULL,
  `login` varchar(50) NOT NULL,
  `diagnostic` varchar(50) NOT NULL,
  `medocs` varchar(50) NOT NULL,
  `service` varchar(50) NOT NULL,
  `jour` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `dossier`
--

INSERT INTO `dossier` (`id`, `login`, `diagnostic`, `medocs`, `service`, `jour`) VALUES
(1, 'falilou', 'Diabete', 'diamicron', 'medecine generale', '21/05/2022'),
(3, 'mbagnick', 'Myopie', 'correctol', 'ophtalmologie', '17/05/2022'),
(9, 'gallobamba', 'Courbature', 'felden', 'medecine generale', '18/05/2022'),
(10, 'patient-SK', 'Diarrhee', 'Eucarbon', 'Medecine generale', '18/05/2022');

-- --------------------------------------------------------

--
-- Structure de la table `rendezvous`
--

CREATE TABLE `rendezvous` (
  `id` int(10) NOT NULL,
  `login` varchar(50) NOT NULL,
  `dateR` varchar(50) NOT NULL,
  `service` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `rendezvous`
--

INSERT INTO `rendezvous` (`id`, `login`, `dateR`, `service`) VALUES
(1, 'falilou', '19/05/2022', 'pneumologie'),
(3, 'falilou', '22/06/2022', 'Cardiologie'),
(4, 'mbagnick', '28/05/2022', 'ophtalmologie'),
(6, 'falilou', '22/05/2022', 'ophtalmologie'),
(7, 'patient-SK', '18/06/2022', 'Medecine generale');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `sexe` varchar(50) NOT NULL,
  `age` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `login`, `password`, `firstname`, `lastname`, `sexe`, `age`) VALUES
(3, 'falilou', 'soukeye', 'Serigne falilou mbacke', 'CISSE', 'homme', '24'),
(11, 'fox', 'laouratou', 'Fode', 'CISSE', 'Homme ', '33'),
(12, 'ndiambe@', 'kikia2', 'Ndiambe', 'GUEYE', 'Homme ', '22'),
(13, 'ventoline', 'falilou', 'Diarra', 'CISSE', 'Femme ', '25'),
(14, 'mbagnick', 'moussa', 'Fatou', 'Sarr', 'Femme ', '22'),
(15, 'diats', 'mansour2', 'Diatou', 'BEYE', 'Femme ', '22'),
(27, 'gallobamba', 'soukeye10', 'gallo', 'cisse', 'Homme ', '60'),
(28, 'patient-SK', 'julemo', 'Souleymane', 'KAMARA', 'Homme ', '22');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `consultation`
--
ALTER TABLE `consultation`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `dossier`
--
ALTER TABLE `dossier`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `rendezvous`
--
ALTER TABLE `rendezvous`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `consultation`
--
ALTER TABLE `consultation`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT pour la table `dossier`
--
ALTER TABLE `dossier`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `rendezvous`
--
ALTER TABLE `rendezvous`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
