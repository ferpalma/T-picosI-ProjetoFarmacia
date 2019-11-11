-- Table: public.produto

-- DROP TABLE public.produto;

CREATE TABLE public.produto
(
    idproduto integer NOT NULL DEFAULT nextval('produto_idproduto_seq'::regclass),
    nome character varying(150) COLLATE pg_catalog."default" NOT NULL,
    estoque integer NOT NULL,
    valor numeric(4,2) NOT NULL,
    CONSTRAINT produto_pkey PRIMARY KEY (idproduto)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.produto
    OWNER to topicos;