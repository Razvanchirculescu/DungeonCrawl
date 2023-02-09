DROP TABLE IF EXISTS public.player;


DROP TABLE IF EXISTS public.actor;
CREATE TABLE public.actor (
  id serial NOT NULL PRIMARY KEY,
  actor_name text NOT NULL,
  hp integer NOT NULL,
  x integer NOT NULL,
  y integer NOT NULL,
  dm integer,
  game_state_id integer NOT NULL
);


DROP TABLE IF EXISTS public.item;
CREATE TABLE public.item (
   id serial NOT NULL PRIMARY KEY,
   item_name text NOT NULL,
   x integer NOT NULL,
   y integer NOT NULL,
   game_state_id integer NOT NULL
);


DROP TABLE IF EXISTS public.game_state;
CREATE TABLE public.game_state (
    id serial NOT NULL PRIMARY KEY,
    current_map text NOT NULL,
    saved_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    save_name text NOT NULL
);

ALTER TABLE ONLY public.actor
    ADD CONSTRAINT fk_game_state_id FOREIGN KEY (game_state_id) REFERENCES public.game_state(id);

ALTER TABLE ONLY public.item
    ADD CONSTRAINT fk_game_state_id FOREIGN KEY (game_state_id) REFERENCES public.game_state(id);