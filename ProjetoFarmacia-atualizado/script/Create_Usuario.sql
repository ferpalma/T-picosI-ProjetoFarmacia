-- Table: public.usuario

-- DROP TABLE public.usuario;

CREATE TABLE public.usuario
(
    idusuario integer NOT NULL DEFAULT nextval('usuario_idusuario_seq'::regclass),
    nome character varying(150) COLLATE pg_catalog."default",
    email character varying(50) COLLATE pg_catalog."default",
    senha character varying(64) COLLATE pg_catalog."default",
    dataadmissao date,
    ativo boolean,
    telefone integer,
    endereco integer,
    perfil integer,
    CONSTRAINT usuario_pkey PRIMARY KEY (idusuario),
    CONSTRAINT endereco FOREIGN KEY (endereco)
        REFERENCES public.endereco (idendereco) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT perfil FOREIGN KEY (perfil)
        REFERENCES public.perfil (idperfil) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT telefone FOREIGN KEY (telefone)
        REFERENCES public.telefone (idtelefone) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.usuario
    OWNER to topicos;