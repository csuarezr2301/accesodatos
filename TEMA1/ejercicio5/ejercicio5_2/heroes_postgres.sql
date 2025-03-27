
DROP TABLE IF EXISTS movies;

CREATE TABLE movies (
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    duration INTEGER,
    year INTEGER,
    producer TEXT
);
DROP TABLE IF EXISTS characters;
CREATE TABLE characters (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    powers TEXT,
    company TEXT,
    origin TEXT,
    isHeroe INTEGER CHECK (isHeroe IN (0, 1))
);
DROP TABLE IF EXISTS acts;
CREATE TABLE acts (
     id SERIAL PRIMARY KEY not NULL,
     character_id INTEGER NOT NULL,
     movie_id INTEGER NOT NULL,
     minutes_in_movie INTEGER,
     main_character BOOLEAN NOT NULL,
     actor TEXT,
     FOREIGN KEY (character_id) REFERENCES characters(id),
     FOREIGN KEY (movie_id) REFERENCES movies(id) );
     
    
    INSERT INTO movies (title,duration,"year",producer) VALUES
	 ('The Avengers',143,2012,'Marvel Studios'),
	 ('Wonder Woman',141,2017,'Warner Bros'),
	 ('Deadpool',108,2016,'20th Century Fox'),
	 ('Aquaman',143,2018,'Warner Bros'),
	 ('Black Panther',0,2018,'Marvel Studios'),
	 ('Wonder Woman 1984',158,2020,'Warner Bros.'),
	 ('Guardians of the Galaxy, Vol. 3',150,0,NULL);
	
	INSERT INTO "characters" (name,powers,company,origin,isHeroe) VALUES
	 ('Spider-Man','Agilidad, trepar paredes, sentido arácnido','Marvel','Nueva York',1),
	 ('Batman','Alta inteligencia, habilidades marciales','DC','Gotham City',1),
	 ('Wonder Woman','Fuerza, agilidad, inmortalidad','DC','Themyscira',1),
	 ('Iron Man','Armadura avanzada, vuelo, armas láser','Marvel','Malibu',1),
	 ('Black Widow','Agilidad, combate cuerpo a cuerpo','Marvel','Rusia',1),
	 ('Superman','Fuerza, vuelo, visión de rayos láser','DC','Krypton',1),
	 ('Captain Marvel','Fuerza, vuelo, energía cósmica','Marvel','Planeta Hala',1),
	 ('Deadpool','Regeneración, inmortalidad','Marvel','Canadá',0),
	 ('Aquaman','Control del agua, comunicación con criaturas marinas','DC','Atlantis',1),
	 ('Harley Quinn','Experta en combate cuerpo a cuerpo, inteligencia','DC','Gotham City',0);
INSERT INTO "characters" (name,powers,company,origin,isHeroe) VALUES
	 ('Star-Lord','Habilidades para el combate,uso de tecnología avanzada','Marvel','Missouri(USA)',1);
    
    
    INSERT INTO acts (character_id,movie_id,minutes_in_movie,main_character,actor) VALUES
	 (1,1,90,true,'Tom Holland'),
	 (2,2,100,true,'Gal Gadot'),
	 (8,3,85,true,'Ryan Reynolds'),
	 (9,4,110,true,'Jason Momoa'),
	 (5,1,45,false,'Scarlett Johansson'),
	 (1,2,NULL,false,NULL),
	 (11,7,NULL,true,'Chris Pratt');
