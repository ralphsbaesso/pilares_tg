-- Geração de Modelo físico
-- Sql ANSI 2003 - brModelo.



CREATE TABLE especialidades (
data_cadastro DATETIME,
descricao varchar(50),
detalhes varchar(50),
codigo varchar(50),
especialidade_id INTEGER AUTO_INCREMENT PRIMARY KEY,
mantenedor_id integer
);

CREATE TABLE mantenedores_especialidades (
especialidade_id integer,
mantenedor_id integer,
FOREIGN KEY(especialidade_id) REFERENCES especialidades (especialidade_id)
);

CREATE TABLE mantenedores (
nome varchar(50),
data_cadastro DATETIME,
cpf varchar(50),
registro varchar(50),
email varchar(50),
sexo varchar(50),
data_nascimento varchar(50),
mantenedor_id INTEGER AUTO_INCREMENT PRIMARY KEY,
tipo_manutencao_id integer,
setor_id integer
);

CREATE TABLE Atividades (
data_cadastro DATETIME,
Descricao varchar(50),
atividade_id INTEGER AUTO_INCREMENT PRIMARY KEY,
especialidade_id integer,
FOREIGN KEY(especialidade_id) REFERENCES especialidades (especialidade_id)
);

CREATE TABLE Tarefas (
detalhamento varchar(50),
data_cadastro DATETIME,
qtde_homem varchar(50),
qtde_hora varchar(50),
codigo varchar(50),
tarefa_id INTEGER AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE OS_tem_atividades (
atividade_id integer,
os_id integer,
FOREIGN KEY(atividade_id) REFERENCES Atividades (atividade_id)
);

CREATE TABLE mantenedores_tem_permissoes (
permissao_id integer,
mantenedor_id integer,
FOREIGN KEY(mantenedor_id) REFERENCES mantenedores (mantenedor_id)
);

CREATE TABLE permissoes (
permissao_id INTEGER AUTO_INCREMENT PRIMARY KEY,
data_cadastro DATETIME,
tipo varchar(50),
descricao varchar(50)
);

CREATE TABLE mantenedores_executam (
acao_id integer,
mantenedor_id integer
);

CREATE TABLE acoes (
codigo varchar(50),
detalhemento varchar(50),
data_inicial DATETIME,
data_cadastro DATETIME,
flg_cancelado BIT,
tipo varchar(50),
acao_id INTEGER AUTO_INCREMENT PRIMARY KEY,
data_final DATETIME,
tarefa_id integer,
mantenedor_id integer,
FOREIGN KEY(tarefa_id) REFERENCES Tarefas (tarefa_id),
FOREIGN KEY(mantenedor_id) REFERENCES mantenedores (mantenedor_id)
);

CREATE TABLE Ordens_de_servico (
codigo varchar(50),
criticidade varchar(50),
status varchar(50),
data_cadastro DATETIME,
observacao varchar(50),
data_limite DATETIME,
os_id INTEGER AUTO_INCREMENT PRIMARY KEY,
titulo varchar(50),
equipamento_id integer,
tipo_manutencao_id integer,
mantenedor_id integer,
FOREIGN KEY(mantenedor_id) REFERENCES mantenedores (mantenedor_id)
);

CREATE TABLE Equipamentos (
descricao varchar(50),
criticidade varchar(50),
equipamento_id INTEGER AUTO_INCREMENT PRIMARY KEY,
data_cadastro DATETIME,
tipo_equipamento_id integer,
setor_id integer,
mantenedor_id integer,
FOREIGN KEY(mantenedor_id) REFERENCES mantenedores (mantenedor_id)
);

CREATE TABLE tipos_equipamento (
data_cadastro DATETIME,
descricao varchar(50),
tipo_equipamento_id INTEGER AUTO_INCREMENT PRIMARY KEY,
mantenedor_id integer,
FOREIGN KEY(mantenedor_id) REFERENCES mantenedores (mantenedor_id)
);

CREATE TABLE centros_de_custos (
descricao varchar(50),
data_cadastro DATETIME,
detalhamento varchar(50),
codigo varchar(50),
cc_id INTEGER AUTO_INCREMENT PRIMARY KEY,
mantenedor_id integer,
FOREIGN KEY(mantenedor_id) REFERENCES mantenedores (mantenedor_id)
);

CREATE TABLE setores (
setor_id INTEGER AUTO_INCREMENT PRIMARY KEY,
descricao varchar(50),
data_cadastro DATETIME,
codigo varchar(50),
detalhamento varchar(50),
mantenedor_id integer,
cc_id integer,
FOREIGN KEY(mantenedor_id) REFERENCES mantenedores (mantenedor_id),
FOREIGN KEY(cc_id) REFERENCES centros_de_custos (cc_id)
);

CREATE TABLE tipos_manutencao (
descricao varchar(50),
data_cadastro DATETIME,
detalhamento varchar(50),
tipo_manutencao_id INTEGER AUTO_INCREMENT PRIMARY KEY
);

ALTER TABLE especialidades ADD FOREIGN KEY(mantenedor_id) REFERENCES mantenedores (mantenedor_id);
ALTER TABLE mantenedores_especialidades ADD FOREIGN KEY(mantenedor_id) REFERENCES mantenedores (mantenedor_id);
ALTER TABLE mantenedores ADD FOREIGN KEY(tipo_manutencao_id) REFERENCES tipos_manutencao (tipo_manutencao_id);
ALTER TABLE mantenedores ADD FOREIGN KEY(setor_id) REFERENCES setores (setor_id);
ALTER TABLE OS_tem_atividades ADD FOREIGN KEY(os_id) REFERENCES Ordens_de_servico (os_id);
ALTER TABLE mantenedores_tem_permissoes ADD FOREIGN KEY(permissao_id) REFERENCES permissoes (permissao_id);
ALTER TABLE mantenedores_executam ADD FOREIGN KEY(acao_id) REFERENCES acoes (acao_id);
ALTER TABLE mantenedores_executam ADD FOREIGN KEY(mantenedor_id) REFERENCES acoes (mantenedor_id);
ALTER TABLE Ordens_de_servico ADD FOREIGN KEY(equipamento_id) REFERENCES Equipamentos (equipamento_id);
ALTER TABLE Ordens_de_servico ADD FOREIGN KEY(tipo_manutencao_id) REFERENCES tipos_manutencao (tipo_manutencao_id);
ALTER TABLE Equipamentos ADD FOREIGN KEY(tipo_equipamento_id) REFERENCES tipos_equipamento (tipo_equipamento_id);
ALTER TABLE Equipamentos ADD FOREIGN KEY(setor_id) REFERENCES setores (setor_id);
