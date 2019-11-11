-- Table: public.endereco

-- DROP TABLE public.endereco;

CREATE TABLE public.endereco
(
    idendereco integer NOT NULL,
    residencia character varying(150) COLLATE pg_catalog."default",
    estado character varying(15) COLLATE pg_catalog."default",
    cidade character varying(15) COLLATE pg_catalog."default",
    cep character varying COLLATE pg_catalog."default",
    CONSTRAINT endereco_pkey PRIMARY KEY (idendereco)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.endereco
    OWNER to topicos;