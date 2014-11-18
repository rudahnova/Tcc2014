
CREATE SCHEMA common;

CREATE SCHEMA game;
 
CREATE SCHEMA analyst;


-- SCHEMA common--

CREATE TABLE common.tipo_usuario
(
  nome character varying(45) NOT NULL,
  id_tipo_usuario serial NOT NULL,
  CONSTRAINT pk_tipo_usuario PRIMARY KEY (id_tipo_usuario)
);



CREATE TABLE common.pessoa
(
  id_pessoa serial NOT NULL,
  nome character varying(255) NOT NULL,
  data_nascimento timestamp with time zone NOT NULL,
  cpf character varying(20),
  endereco character varying(255),
  telefone character varying(20) NOT NULL,
  data_cadastro timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT pk_pessoa PRIMARY KEY (id_pessoa)
);



CREATE TABLE common.usuario
(
  id_usuario bigserial NOT NULL,
  login character varying(45) NOT NULL,
  senha character varying(45) NOT NULL,
  id_tipo_pessoa integer NOT NULL,
  id_pessoa bigint NOT NULL,
  CONSTRAINT pk_usuario PRIMARY KEY (id_usuario),
  CONSTRAINT fk_usuario_pessoa FOREIGN KEY (id_pessoa)
      REFERENCES common.pessoa (id_pessoa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_usuario_tipo_usuario FOREIGN KEY (id_usuario)
      REFERENCES common.tipo_usuario (id_tipo_usuario) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE NO ACTION
);

-- SCHEMA game--

CREATE TABLE game.jogo
(
  id_jogo integer NOT NULL DEFAULT nextval('game.jogo_id_jongo_seq'::regclass),
  nome character varying(255) NOT NULL,
  descricao character varying(255) NOT NULL,
  capa character varying(255) NOT NULL,
  url character varying(255) NOT NULL,
  data_cadastro date NOT NULL DEFAULT now(),
  CONSTRAINT pk_jogo PRIMARY KEY (id_jogo)
);



CREATE TABLE game.configuracao
(
  id_configuracao serial NOT NULL,
  descricao character varying(255) NOT NULL,
  valor character varying(2500) NOT NULL,
  id_jogo integer NOT NULL,
  id_jogador bigint NOT NULL,
  CONSTRAINT pk_configuracao PRIMARY KEY (id_configuracao),
  CONSTRAINT fk_configuracao_jogador FOREIGN KEY (id_jogador)
      REFERENCES game.jogador (id_jogador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_configuracao_jogo FOREIGN KEY (id_jogo)
      REFERENCES game.jogo (id_jogo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE game.tipo_conquista
(
  id_tipo_conquista serial NOT NULL,
  nome character varying(255) NOT NULL,
  descricao text,
  data_cadastro date NOT NULL DEFAULT now(),
  CONSTRAINT pk_tipo_conquista PRIMARY KEY (id_tipo_conquista)
);


CREATE TABLE game.conquista
(
  id_conquista bigint NOT NULL,
  data_hora timestamp with time zone NOT NULL,
  id_tipo_conquista integer NOT NULL,
  id_jogador bigint NOT NULL,
  id_sessao integer NOT NULL,
  CONSTRAINT pk_conquista PRIMARY KEY (id_conquista),
  CONSTRAINT fk_conquista_jogador FOREIGN KEY (id_jogador)
      REFERENCES game.jogador (id_jogador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_conquista_sessao FOREIGN KEY (id_sessao)
      REFERENCES game.sessao (id_sessao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_conquista_tipo_conquista FOREIGN KEY (id_tipo_conquista)
      REFERENCES game.tipo_conquista (id_tipo_conquista) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE game.sessao
(
  id_sessao serial NOT NULL,
  num_pontos integer NOT NULL,
  num_falhas integer NOT NULL,
  num_itens integer NOT NULL,
  num_itens_especiais integer NOT NULL,
  duracao integer NOT NULL,
  data_hora date NOT NULL,
  id_jogador bigint NOT NULL,
  id_jogo integer NOT NULL,
  CONSTRAINT pk_sessao PRIMARY KEY (id_sessao),
  CONSTRAINT fk_sessao_jogador FOREIGN KEY (id_jogador)
      REFERENCES game.jogador (id_jogador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_sessao_jogo FOREIGN KEY (id_jogo)
      REFERENCES game.jogo (id_jogo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- SCHEMA analyst--

CREATE TABLE analyst.tipo_estatistica
(
  id_tipo_estatistica serial NOT NULL,
  descricao character varying(255) NOT NULL,
  CONSTRAINT pk_tipo_estatistica PRIMARY KEY (id_tipo_estatistica)
);


CREATE TABLE analyst.estatisticas
(
  id_estatisticas serial NOT NULL,
  arquivo bytea NOT NULL,
  id_sessao integer NOT NULL,
  id_tipo_estatistica integer NOT NULL,
  CONSTRAINT pk_estaisticas PRIMARY KEY (id_estatisticas),
  CONSTRAINT fk_estatisticas_sessao FOREIGN KEY (id_sessao)
      REFERENCES game.sessao (id_sessao) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_estatisticas_tipo_estatistica FOREIGN KEY (id_tipo_estatistica)
      REFERENCES analyst.tipo_estatistica (id_tipo_estatistica) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);



CREATE TABLE log_modificacoes
(
  id_log_modificacoes integer NOT NULL DEFAULT nextval(log_modificações_id_log_modificacoes_seq::regclass),
  mensagem text,
  tabela_modificada character varying(255) NOT NULL,
  id_usuario bigint NOT NULL,
  CONSTRAINT pk_log_modificacoes PRIMARY KEY (id_log_modificacoes),
  CONSTRAINT fk_log_modificacoes_usuario FOREIGN KEY (id_usuario)
      REFERENCES common.usuario (id_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE analyst.analise_jogo_jogador
(
  id_jogo integer NOT NULL,
  id_jogador integer NOT NULL,
  id_analise_jogo_jogador serial NOT NULL,
  CONSTRAINT pk_analise_jogo_jogador PRIMARY KEY (id_analise_jogo_jogador),
  CONSTRAINT fk_analise_jogador_jogo FOREIGN KEY (id_jogador)
      REFERENCES game.jogador (id_jogador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_analise_jogador_jogo_jogo FOREIGN KEY (id_jogo)
      REFERENCES game.jogo (id_jogo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE analyst.analise_fase_jogador
(
  melhor_score text,
  dificuldade integer NOT NULL DEFAULT 0,
  duracao_fase integer NOT NULL,
  duracao_vencer integer,
  id_analise_fase_jogador serial NOT NULL,
  id_analise_jogo_jogador integer NOT NULL,
  CONSTRAINT pk_analise_fase_jogador PRIMARY KEY (id_analise_fase_jogador),
  CONSTRAINT fk_analise_fase_jogador FOREIGN KEY (id_analise_jogo_jogador)
      REFERENCES analyst.analise_jogo_jogador (id_analise_jogo_jogador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE analyst.analise_parte_fase_jogador
(
  id_parte_fase_jogador serial NOT NULL,
  id_analise_fase_jogador integer NOT NULL,
  total_derrotas integer NOT NULL DEFAULT 0,
  CONSTRAINT pk_analise_parte_fase_jogador PRIMARY KEY (id_parte_fase_jogador),
  CONSTRAINT fk_analise_parte_fase_jogador_analise_fase_jogador FOREIGN KEY (id_analise_fase_jogador)
      REFERENCES analyst.analise_fase_jogador (id_analise_fase_jogador) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

