-- Table: public.telefone

-- DROP TABLE public.telefone;

CREATE TABLE public.telefone
(
    idtelefone integer NOT NULL,
    codigoarea character varying(3) COLLATE pg_catalog."default",
    numero character varying(10) COLLATE pg_catalog."default",
    CONSTRAINT telefone_pkey PRIMARY KEY (idtelefone)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.telefone
    OWNER to topicos;