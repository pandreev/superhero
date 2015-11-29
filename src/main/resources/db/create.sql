CREATE TABLE IF NOT EXISTS ACCOUNT
(
    username varchar2  PRIMARY KEY NOT NULL,
    password varchar2  NOT NULL,
);
CREATE TABLE IF NOT EXISTS SUPERHERO
(
    name varchar2  PRIMARY KEY NOT NULL,
    pseudonym varchar2  NOT NULL,
    publisher varchar2  NOT NULL,
    firstAppearance varchar2  NOT NULL,
);
CREATE TABLE IF NOT EXISTS HERO_SKILLS (
    hero_name varchar2 NOT NULL,
    skill varchar2 NOT NULL,
    PRIMARY KEY (hero_name, skill),
    CONSTRAINT fk_hero_skill FOREIGN KEY (hero_name) REFERENCES superhero (name),
);
CREATE TABLE IF NOT EXISTS HERO_ALLIES (
    hero_name varchar2 NOT NULL,
    ally_name varchar2 NOT NULL,
    PRIMARY KEY (hero_name, ally_name),
    CONSTRAINT fk_hero FOREIGN KEY (hero_name) REFERENCES superhero (name),
    CONSTRAINT fk_ally FOREIGN KEY (ally_name) REFERENCES superhero (name)
);
