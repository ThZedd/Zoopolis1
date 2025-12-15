-- ==================================================================================
-- 1. RESET TOTAL
-- ==================================================================================
DROP SCHEMA IF EXISTS zoopolis CASCADE;
CREATE SCHEMA zoopolis AUTHORIZATION postgres;
SET search_path TO zoopolis;

-- ==================================================================================
-- 2. CRIAÇÃO DAS TABELAS
-- ==================================================================================

CREATE TABLE class (
                       cla_id SERIAL PRIMARY KEY,
                       cla_name VARCHAR(60) NOT NULL,
                       cla_order VARCHAR(60),
                       cla_fam VARCHAR(60)
);

CREATE TABLE area (
                      area_id SERIAL PRIMARY KEY,
                      area_name VARCHAR(60)
);

CREATE TABLE sub_area (
                          sa_id SERIAL PRIMARY KEY,
                          sa_area_id INT REFERENCES area(area_id),
                          sa_name VARCHAR(60)
);

CREATE TABLE enclosure (
                           enc_id SERIAL PRIMARY KEY,
                           enc_name VARCHAR(60) NOT NULL,
                           enc_sup_amount INT,
                           enc_aniclass VARCHAR(30),
                           enc_lat DOUBLE PRECISION NOT NULL,
                           enc_long DOUBLE PRECISION NOT NULL,
                           enc_sa_id INT REFERENCES sub_area(sa_id)
);

CREATE TABLE animal (
                        ani_id SERIAL PRIMARY KEY,
                        ani_name VARCHAR(60) NOT NULL,
                        ani_ci_name VARCHAR(60),
                        ani_description TEXT,
                        ani_weight FLOAT,
                        ani_height FLOAT,
                        ani_length FLOAT,
                        ani_cla_id INT REFERENCES class(cla_id),
                        imageurl TEXT -- MUDANÇA: Mudado para TEXT para aceitar URLs longos
);

CREATE TABLE ae (
                    ae_id SERIAL PRIMARY KEY,
                    ae_ani_id INT REFERENCES animal(ani_id),
                    ae_enc_id INT REFERENCES enclosure(enc_id),
                    ae_dt_in TIMESTAMP,
                    ae_dt_out TIMESTAMP,
                    ae_code VARCHAR(30)
);

CREATE TABLE person (
                        per_id SERIAL PRIMARY KEY,
                        per_name VARCHAR(60) NOT NULL,
                        per_email VARCHAR(60) UNIQUE,
                        per_password VARCHAR(255) NOT NULL,
                        per_gender VARCHAR(20),
                        per_points INT NOT NULL DEFAULT 0
);

CREATE TABLE activity (
                          ac_id SERIAL PRIMARY KEY,
                          ac_name VARCHAR(60),
                          ac_schedule TIMESTAMP,
                          ac_cap INT,
                          ac_area_id INT REFERENCES area(area_id)
);

CREATE TABLE kiosk (
                       kio_id SERIAL PRIMARY KEY,
                       kio_name VARCHAR(60),
                       kio_area_id INT REFERENCES area(area_id)
);

CREATE TABLE product (
                         pro_id SERIAL PRIMARY KEY,
                         pro_name VARCHAR(100),
                         pro_barcode BIGINT,
                         pro_price DOUBLE PRECISION
);

CREATE TABLE stock (
                       stock_id SERIAL PRIMARY KEY,
                       stock_amount INT,
                       stock_kio_id INT REFERENCES kiosk(kio_id),
                       stock_pro_id INT REFERENCES product(pro_id)
);

CREATE TABLE visited (
                         vi_id SERIAL PRIMARY KEY,
                         vi_per_id INT REFERENCES person(per_id),
                         vi_sa_id INT REFERENCES sub_area(sa_id),
                         vi_dtime TIMESTAMP NOT NULL
);

CREATE TABLE favorite (
                          fav_id SERIAL PRIMARY KEY,
                          fav_animal VARCHAR(10),
                          fav_ani_id INT REFERENCES animal(ani_id),
                          fav_per_id INT REFERENCES person(per_id)
);


-- ==================================================================================
-- 3. DADOS BÁSICOS
-- ==================================================================================

INSERT INTO class (cla_name, cla_order, cla_fam) VALUES
                                                     ('Mamíferos', 'Mammalia', 'Vários'),
                                                     ('Aves', 'Aves', 'Vários'),
                                                     ('Répteis', 'Reptilia', 'Vários'),
                                                     ('Anfíbios', 'Amphibia', 'Vários'),
                                                     ('Serviços', 'Urbano', 'Serviço'),
                                                     ('Transporte', 'Urbano', 'Transporte'),
                                                     ('Comida', 'Urbano', 'Restauração'),
                                                     ('Cidade', 'Urbano', 'Geral'),
                                                     ('Saúde', 'Urbano', 'Hospital');

INSERT INTO area (area_name) VALUES ('Zoo Lisboa'), ('Cidade Envolvente');
INSERT INTO sub_area (sa_area_id, sa_name) VALUES (1, 'Interior do Zoo'), (2, 'Exterior / Sete Rios');


-- ==================================================================================
-- 4. LOCAIS DO MAPA (ENCLOSURES)
-- ==================================================================================

INSERT INTO enclosure (enc_name, enc_lat, enc_long, enc_sa_id, enc_sup_amount, enc_aniclass) VALUES
-- TRANSPORTES & CIDADE
('Laranjeiras (Metro)', 38.7475369, -9.170902, 2, 0, 'TRANSPORTE'),
('Praça de Espanha', 38.737684, -9.1592473, 2, 0, 'CIDADE'),
('Alto dos Moinhos', 38.7498954, -9.1797946, 2, 0, 'TRANSPORTE'),
('Sete Rios (Estação)', 38.7419616, -9.167221, 2, 0, 'TRANSPORTE'),
('Sete Rios - Zoo', 38.7424765, -9.1674358, 2, 0, 'TRANSPORTE'),
('Estrada de Benfica (Furnas)', 38.742575, -9.1726282, 2, 0, 'CIDADE'),
('Estrada das Laranjeiras', 38.7453615, -9.1693658, 2, 0, 'CIDADE'),
('Loja do Cidadão', 38.7496467, -9.1724903, 2, 0, 'SERVICO'),
('Hospital dos Lusíadas', 38.749171, -9.178159, 2, 0, 'CIDADE'),
('Hospital da Cruz Vermelha', 38.745889, -9.175394, 2, 0, 'CIDADE'),

-- SERVIÇOS & COMIDA
('Jardim Zoológico (Entrada)', 38.7420882, -9.1689132, 1, 0, 'SERVICO'),
('Saída', 38.743849, -9.1696622, 1, 0, 'SERVICO'),
('Carrossel', 38.7434484, -9.1683219, 1, 0, 'SERVICO'),
('ZOOvenir', 38.743413, -9.169527, 1, 0, 'SERVICO'),
('McDonald''s', 38.7434275, -9.1699526, 1, 0, 'COMIDA'),
('Comboio do Zoo', 38.7432354, -9.1707117, 1, 0, 'TRANSPORTE'),
('Teleférico', 38.744130, -9.170596, 1, 0, 'TRANSPORTE'),
('Snack-Bar Africa', 38.7447463, -9.1704399, 1, 0, 'COMIDA'),
('Bar Zoo', 38.7429171, -9.1721373, 1, 0, 'COMIDA'),
('Cafetaria Orquídea', 38.7452398, -9.1690847, 2, 0, 'COMIDA'),
('Solar dos Canadianos', 38.7436821, -9.1743438, 2, 0, 'COMIDA'),
('Restaurante Coral', 38.7421129, -9.1722881, 2, 0, 'COMIDA'),
('Farmácia Sete Rios', 38.7453217, -9.1691272, 2, 0, 'SERVICO'),
('Farmácia Alegria', 38.743115, -9.1738965, 2, 0, 'SERVICO'),
('O Churrasco', 38.7461241, -9.1695217, 2, 0, 'COMIDA'),
('Talho Ferdinando', 38.743779, -9.1739238, 2, 0, 'COMIDA'),
('Big Kebab House', 38.7470128, -9.1679048, 2, 0, 'COMIDA'),

-- ANIMAIS (Destinos)
('Vale dos Tigres', 38.7432704, -9.1711608, 1, 5, 'ANIMAL'),
('Tigres Brancos', 38.744536, -9.1706392, 1, 3, 'ANIMAL'),
('Rinocerontes Brancos', 38.7443442, -9.1703226, 1, 4, 'ANIMAL'),
('Pequenos Primatas', 38.7441465, -9.1707578, 1, 10, 'ANIMAL'),
('Ilha dos Macacos', 38.7438071, -9.1704214, 1, 15, 'ANIMAL'),
('Áxis e Veado', 38.742928, -9.1715755, 1, 8, 'ANIMAL'),
('Bongos', 38.7428199, -9.1717875, 1, 4, 'ANIMAL'),
('Koalas e Cangurus', 38.7432588, -9.1718274, 1, 6, 'ANIMAL'),
('Bisonte-americano', 38.742841, -9.1724858, 1, 5, 'ANIMAL'),
('Búfalos', 38.7429355, -9.1726818, 1, 6, 'ANIMAL'),
('Tartaruga-gigante', 38.742979, -9.172862, 1, 3, 'ANIMAL'),
('Aligátor-americano', 38.7431357, -9.1724443, 1, 4, 'ANIMAL'),
('Rinocerontes-indianos', 38.7433744, -9.172159, 1, 2, 'ANIMAL'),
('Suricatas', 38.7435261, -9.1724172, 1, 12, 'ANIMAL'),
('Girafas', 38.744016, -9.1725065, 1, 5, 'ANIMAL'),
('Órix-austral', 38.7437922, -9.1720426, 1, 5, 'ANIMAL'),
('Elandes', 38.7438506, -9.1715994, 1, 6, 'ANIMAL'),
('Panda-vermelho', 38.7442037, -9.1720043, 1, 2, 'ANIMAL'),
('Hipopótamos', 38.7442932, -9.1723487, 1, 3, 'ANIMAL'),
('Lémures', 38.744425, -9.1725352, 1, 10, 'ANIMAL'),
('Ilha dos Lémures', 38.7444312, -9.172151, 1, 10, 'ANIMAL'),
('Elefantes-africanos', 38.7447806, -9.1729194, 1, 3, 'ANIMAL'),
('Leões', 38.7450277, -9.1724427, 1, 4, 'ANIMAL'),
('Flamingo-rubro', 38.7447194, -9.1724379, 1, 30, 'ANIMAL'),
('Chimpanzé', 38.7447904, -9.171235, 1, 6, 'ANIMAL'),
('Gorilas', 38.7447392, -9.1712232, 1, 4, 'ANIMAL'),
('Pumas', 38.744963, -9.1706668, 1, 2, 'ANIMAL'),
('Serval', 38.7450389, -9.1710972, 1, 2, 'ANIMAL'),
('Leopardo-da-pérsia', 38.7451831, -9.1709506, 1, 2, 'ANIMAL'),
('Leopardo-das-neves', 38.7452328, -9.1712997, 1, 2, 'ANIMAL'),
('Lince-euro-asiático', 38.7453273, -9.1711546, 1, 2, 'ANIMAL'),
('Babuíno-hamadrias', 38.7453845, -9.1705393, 1, 8, 'ANIMAL'),
('Adaxes', 38.7455325, -9.1707593, 1, 6, 'ANIMAL'),
('Chitas', 38.7454653, -9.1727152, 1, 2, 'ANIMAL'),
('Okapis', 38.7453186, -9.1734342, 1, 2, 'ANIMAL'),
('Reptilário', 38.743255, -9.172581, 1, 40, 'ANIMAL'),
('Templo dos Primatas', 38.744672, -9.171407, 1, 20, 'ANIMAL'),
('Baía dos Golfinhos', 38.742597, -9.170947, 1, 4, 'ANIMAL'),
('Ursos-pardos', 38.746050, -9.170526, 1, 2, 'ANIMAL'),
('Pelicanos', 38.744455, -9.170010, 1, 10, 'ANIMAL'),
('Encosta dos Felinos', 38.745222, -9.171105, 1, 8, 'ANIMAL'),
('Aldeia dos Macacos', 38.743963, -9.171033, 1, 15, 'ANIMAL'),
('Palácio das Araras', 38.743691, -9.171244, 1, 10, 'ANIMAL'),
('Leões-marinhos', 38.744100, -9.171427, 1, 5, 'ANIMAL'),
('Pinguins-do-cabo', 38.744220, -9.171265, 1, 20, 'ANIMAL'),
('Aves Exóticas', 38.743777, -9.171306, 1, 15, 'ANIMAL'),
('Aviário Asiático', 38.742973, -9.171377, 1, 10, 'ANIMAL'),
('Parque Arco-Íris', 38.742605, -9.172331, 1, 10, 'ANIMAL'),
('Lince-ibérico', 38.7460401, -9.1709958, 1, 2, 'ANIMAL'),
('Muntjac-chinês', 38.7440669, -9.1721574, 1, 4, 'ANIMAL'),
('Palanca-ruana', 38.7457426, -9.1719899, 1, 5, 'ANIMAL'),
('Palanca-negra', 38.7457028, -9.1725845, 1, 5, 'ANIMAL'),
('Colobo-guereza', 38.7447392, -9.1712232, 1, 6, 'ANIMAL');


-- ==================================================================================
-- 5. ANIMAIS (COM URLS DA INTERNET e DESCRIÇÕES TOP)
-- ==================================================================================



-- Inserir com os nomes EXATOS da tua pasta drawable (com o prefixo 'animal_')
INSERT INTO animal (ani_name, ani_ci_name, ani_description, ani_weight, ani_height, ani_length, ani_cla_id, imageurl) VALUES
                                                                                                                          ('Tigre da Sibéria', 'Panthera tigris altaica', 'O maior felino do mundo. Pelagem laranja com riscas pretas.', 300, 110, 330, 1, 'animal_tigre_da_siberia'),
                                                                                                                          ('Tigre Branco', 'Panthera tigris', 'Variação rara do tigre de Bengala. Olhos azuis e nariz rosado.', 220, 100, 300, 1, 'animal_tigre_branco'),
                                                                                                                          ('Leão Africano', 'Panthera leo', 'O rei da selva. Machos têm juba imponente.', 190, 120, 250, 1, 'animal_leao_africano'),
                                                                                                                          ('Golfinho Roaz', 'Tursiops truncatus', 'Mamífero marinho inteligente. Usa ecolocalização.', 300, 0, 400, 1, 'animal_golfinho_roaz'),
                                                                                                                          ('Elefante Africano', 'Loxodonta africana', 'Maior mamífero terrestre. Grandes orelhas.', 6000, 330, 700, 1, 'animal_elefante_africano'),
                                                                                                                          ('Girafa', 'Giraffa camelopardalis', 'Animal mais alto do mundo. Língua azul.', 1200, 550, 0, 1, 'animal_girafa'),
                                                                                                                          ('Chimpanzé', 'Pan troglodytes', 'Parente próximo do humano. Usa ferramentas.', 60, 120, 0, 1, 'animal_chimpanze'),
                                                                                                                          ('Gorila', 'Gorilla gorilla', 'Maior primata. Gigante gentil e vegetariano.', 180, 170, 0, 1, 'animal_gorila'),
                                                                                                                          ('Koala', 'Phascolarctos cinereus', 'Dorme a maior parte do dia. Come eucalipto.', 12, 70, 0, 1, 'animal_koala'),
                                                                                                                          ('Panda Vermelho', 'Ailurus fulgens', 'Pequeno mamífero dos Himalaias. Solitário.', 5, 25, 50, 1, 'animal_panda_vermelho'),
                                                                                                                          ('Lémure de Cauda Anelada', 'Lemur catta', 'Endémico de Madagáscar. Cauda com anéis.', 3, 40, 0, 1, 'animal_lemure_de_cauda_anelada'),
                                                                                                                          ('Pinguim do Cabo', 'Spheniscus demersus', 'Único pinguim que se reproduz em África.', 3, 60, 0, 2, 'animal_pinguim_do_cabo'),
                                                                                                                          ('Urso Pardo', 'Ursus arctos', 'Grande omnívoro. Hiberna no inverno.', 400, 150, 200, 1, 'animal_urso_pardo'),
                                                                                                                          ('Rinoceronte Branco', 'Ceratotherium simum', 'Segundo maior mamífero. Dois cornos.', 2300, 180, 400, 1, 'animal_rinoceronte_branco'),
                                                                                                                          ('Hipopótamo', 'Hippopotamus amphibius', 'Passa o dia na água. Muito territorial.', 1500, 150, 450, 1, 'animal_hipopotamo'),
                                                                                                                          ('Lince Ibérico', 'Lynx pardinus', 'Felino ameaçado. Come coelhos.', 13, 50, 90, 1, 'animal_lince_iberico'),
                                                                                                                          ('Suricata', 'Suricata suricatta', 'Fica de pé a vigiar. Vive em colónias.', 0.7, 25, 0, 1, 'animal_suricata'),
                                                                                                                          ('Aligátor Americano', 'Alligator mississippiensis', 'Réptil dos pântanos. Focinho em U.', 300, 0, 400, 3, 'animal_aligator_americano'),
                                                                                                                          ('Arara Azul e Amarela', 'Ara ararauna', 'Papagaio colorido e inteligente.', 1.1, 0, 85, 2, 'animal_arara_azul_e_amarela'),
                                                                                                                          ('Bongo', 'Tragelaphus eurycerus', 'Antílope noturno com riscas brancas.', 250, 120, 200, 1, 'animal_bongo'),
                                                                                                                          ('Okapi', 'Okapia johnstoni', 'Parente da girafa com pernas de zebra.', 250, 150, 250, 1, 'animal_okapi'),
                                                                                                                          ('Ádax', 'Addax nasomaculatus', 'Antílope do Saara. Muda de cor.', 100, 105, 160, 1, 'animal_adax'),
                                                                                                                          ('Bisonte Americano', 'Bison bison', 'Gigante da pradaria. Cabeça massiça.', 900, 180, 300, 1, 'animal_bisonte_americano'),
                                                                                                                          ('Búfalo Africano', 'Syncerus caffer', 'Cornos fundidos como armadura.', 700, 150, 300, 1, 'animal_bufalo_africano'),
                                                                                                                          ('Tartaruga Gigante', 'Aldabrachelys gigantea', 'Vive mais de 150 anos. Pesa muito.', 250, 60, 120, 3, 'animal_tartaruga_gigante'),
                                                                                                                          ('Canguru Vermelho', 'Osphranter rufus', 'Maior marsupial. Usa a cauda.', 85, 150, 0, 1, 'animal_canguru_vermelho'),
                                                                                                                          ('Muntjac Chinês', 'Muntiacus reevesi', 'Veado-ladrador. Machos têm presas.', 15, 50, 0, 1, 'animal_muntjac_chines'),
                                                                                                                          ('Palanca Negra', 'Hippotragus niger', 'Símbolo de Angola. Cornos curvos.', 230, 140, 0, 1, 'animal_palanca_negra'),
                                                                                                                          ('Colobo Guereza', 'Colobus guereza', 'Macaco preto e branco sem polegares.', 10, 60, 0, 1, 'animal_colobo_guereza'),
                                                                                                                          ('Chita', 'Acinonyx jubatus', 'Animal mais rápido. Mia e ronrona.', 50, 80, 130, 1, 'animal_chita'),
                                                                                                                          ('Babuíno Hamadrias', 'Papio hamadryas', 'Primata sagrado. Juba prateada.', 25, 70, 0, 1, 'animal_babuino_hamadrias'),
                                                                                                                          ('Pelicano', 'Pelecanus onocrotalus', 'Ave com bolsa enorme no bico.', 10, 0, 160, 2, 'animal_pelicano'),
                                                                                                                          ('Leopardo da Pérsia', 'Panthera pardus saxicolor', 'Mestre da camuflagem. Sobe árvores.', 70, 70, 150, 1, 'animal_leopardo_da_persia'),
                                                                                                                          ('Serval', 'Leptailurus serval', 'Orelhas grandes. Salta muito alto.', 15, 60, 90, 1, 'animal_serval'),
                                                                                                                          ('Órix', 'Oryx gazella', 'Unicórnio do deserto. Sobrevive sem água.', 200, 120, 0, 1, 'animal_orix'),
                                                                                                                          ('Rinoceronte Indiano', 'Rhinoceros unicornis', 'Pele parece armadura. Um só corno.', 2000, 170, 350, 1, 'animal_rinoceronte_indiano'),
                                                                                                                          ('Puma', 'Puma concolor', 'Leão-da-montanha. Grande distribuição.', 80, 70, 150, 1, 'animal_puma'),
                                                                                                                          ('Águia das Estepes', 'Aquila nipalensis', 'Ave de rapina. Boca parece sorriso.', 3.2, 76, 0, 2, 'animal_aguia_das_estepes'),
                                                                                                                          ('Anaconda Amarela', 'Eunectes notaeus', 'Serpente constritora. Nada bem.', 30, 0, 350, 3, 'animal_anaconda_amarela'),
                                                                                                                          ('Araçari Verde', 'Pteroglossus viridis', 'Pequeno tucano colorido.', 0.13, 0, 35, 2, 'animal_aracari_verde'),
                                                                                                                          ('Arara de Asa Verde', 'Ara chloropterus', 'Riscas verdes nas asas.', 1.5, 0, 92, 2, 'animal_arara_de_asa_verde'),
                                                                                                                          ('Arara de Fronte Vermelha', 'Ara rubrogenys', 'Espécie rara da Bolívia.', 0.55, 0, 60, 2, 'animal_arara_de_fronte_vermelha'),
                                                                                                                          ('Arara Escarlate', 'Ara macao', 'Ave de cores vibrantes.', 1.2, 0, 86, 2, 'animal_arara_escarlate'),
                                                                                                                          ('Arara Jacinta', 'Anodorhynchus hyacinthinus', 'Maior das araras. Azul-cobalto.', 1.5, 0, 100, 2, 'animal_arara_jacinta');
-- 6. LIGAÇÃO ANIMAL -> LOCAL (TABELA AE)
-- ==================================================================================

INSERT INTO ae (ae_ani_id, ae_enc_id, ae_dt_in, ae_dt_out, ae_code)
SELECT a.ani_id, e.enc_id, NOW(), NULL, 'ZOO-' || a.ani_id
FROM animal a, enclosure e
WHERE
    (a.ani_name LIKE '%Tigre da Sibéria%' AND e.enc_name LIKE '%Vale dos Tigres%') OR
    (a.ani_name LIKE '%Tigre Branco%' AND e.enc_name LIKE '%Tigres Brancos%') OR
    (a.ani_name LIKE '%Leão%' AND e.enc_name = 'Leões') OR
    (a.ani_name LIKE '%Golfinho%' AND e.enc_name LIKE '%Golfinhos%') OR
    (a.ani_name LIKE '%Elefante%' AND e.enc_name LIKE '%Elefantes%') OR
    (a.ani_name LIKE '%Girafa%' AND e.enc_name = 'Girafas') OR
    (a.ani_name LIKE '%Chimpanzé%' AND e.enc_name = 'Chimpanzé') OR
    (a.ani_name LIKE '%Gorila%' AND e.enc_name LIKE '%Gorilas%') OR
    (a.ani_name LIKE '%Koala%' AND e.enc_name LIKE '%Koalas%') OR
    (a.ani_name LIKE '%Panda%' AND e.enc_name LIKE '%Panda%') OR
    (a.ani_name LIKE '%Lémure%' AND e.enc_name LIKE '%Lémures%') OR
    (a.ani_name LIKE '%Pinguim%' AND e.enc_name LIKE '%Pinguins%') OR
    (a.ani_name LIKE '%Urso Pardo%' AND e.enc_name LIKE '%Ursos%') OR
    (a.ani_name LIKE '%Rinoceronte Branco%' AND e.enc_name LIKE '%Rinocerontes Brancos%') OR
    (a.ani_name LIKE '%Hipopótamo%' AND e.enc_name LIKE '%Hipopótamos%') OR
    (a.ani_name LIKE '%Lince%' AND e.enc_name LIKE '%Lince%') OR
    (a.ani_name LIKE '%Suricata%' AND e.enc_name LIKE '%Suricatas%') OR
    (a.ani_name LIKE '%Aligátor%' AND e.enc_name LIKE '%Aligátor%') OR
    (a.ani_name LIKE '%Bongo%' AND e.enc_name LIKE '%Bongos%') OR
    (a.ani_name LIKE '%Okapi%' AND e.enc_name LIKE '%Okapis%') OR
    (a.ani_name LIKE '%Ádax%' AND e.enc_name LIKE '%Adaxes%') OR
    (a.ani_name LIKE '%Bisonte%' AND e.enc_name LIKE '%Bisonte%') OR
    (a.ani_name LIKE '%Búfalo%' AND e.enc_name LIKE '%Búfalos%') OR
    (a.ani_name LIKE '%Tartaruga%' AND e.enc_name LIKE '%Tartaruga%') OR
    (a.ani_name LIKE '%Canguru%' AND e.enc_name LIKE '%Cangurus%') OR
    (a.ani_name LIKE '%Muntjac%' AND e.enc_name LIKE '%Muntjac%') OR
    (a.ani_name LIKE '%Palanca%' AND e.enc_name LIKE '%Palanca%') OR
    (a.ani_name LIKE '%Colobo%' AND e.enc_name LIKE '%Colobo%') OR
    (a.ani_name LIKE '%Chita%' AND e.enc_name LIKE '%Chitas%') OR
    (a.ani_name LIKE '%Babuíno%' AND e.enc_name LIKE '%Babuíno%') OR
    (a.ani_name LIKE '%Pelicano%' AND e.enc_name LIKE '%Pelicanos%') OR
    (a.ani_name LIKE '%Leopardo%' AND e.enc_name LIKE '%Leopardo%') OR
    (a.ani_name LIKE '%Serval%' AND e.enc_name LIKE '%Serval%') OR
    (a.ani_name LIKE '%Órix%' AND e.enc_name LIKE '%Órix%') OR
    (a.ani_name LIKE '%Rino%Indiano%' AND e.enc_name LIKE '%Rinocerontes-indianos%') OR
    (a.ani_name LIKE '%Puma%' AND e.enc_name LIKE '%Pumas%') OR
    (a.ani_name LIKE '%Águia%' AND e.enc_name LIKE '%Aves Exóticas%') OR
    (a.ani_name LIKE '%Anaconda%' AND e.enc_name LIKE '%Reptilário%') OR
    (a.ani_name LIKE '%Araçari%' AND e.enc_name LIKE '%Aves Exóticas%') OR
    (a.ani_name LIKE '%Arara%' AND e.enc_name LIKE '%Palácio das Araras%');


-- ==================================================================================
-- 7. DADOS EXTRA
-- ==================================================================================

INSERT INTO person (per_name, per_email, per_password, per_gender, per_points) VALUES
                                                                                   ('Visitante Demo', 'visitante@zoo.pt', '1234', 'F', 50),
                                                                                   ('Admin Zoo', 'admin@zoo.pt', 'admin', 'M', 999);

INSERT INTO activity (ac_name, ac_schedule, ac_cap, ac_area_id) VALUES
                                                                    ('Alimentação dos Pelicanos', NOW() + INTERVAL '1 day', 50, 1),
                                                                    ('Apresentação dos Golfinhos', NOW() + INTERVAL '2 hours', 200, 1);

INSERT INTO kiosk (kio_name, kio_area_id) VALUES ('Quiosque Principal', 1), ('Gelados Olá', 1);

INSERT INTO product (pro_name, pro_barcode, pro_price) VALUES
                                                           ('Garrafa de Água 50cl', 1001, 1.50), ('Gelado', 1002, 2.50);

INSERT INTO stock (stock_amount, stock_kio_id, stock_pro_id) VALUES (50, 1, 1), (50, 1, 2);

INSERT INTO favorite (fav_ani_id, fav_per_id, fav_animal) VALUES (1, 1, 'true');

SELECT 'Base de Dados ZOOPOLIS reconstruída com SUCESSO (Com URLs da Internet)!' as status;