create table cliente (
    id_cliente bigint not null auto_increment,
    nome varchar(50) not null,
    plano_exclusive boolean,
    saldo decimal,
    numero_conta varchar,
    data_nascimento date,
    primary key(id_cliente)
);

create table movimentacao (
    id_transacao bigint primary key not null auto_increment,
    id_cliente bigint,
    tipo_operacao varchar(50) not null,
    data_transacao date,
    cobrado_taxa boolean,
    valor_operacao decimal,
    saldo_inicial decimal,
    novo_saldo decimal,
    regra_aplicada varchar,

    foreign key(id_cliente) references cliente (id_cliente)
);



INSERT INTO cliente (id_cliente, nome, plano_exclusive, saldo, numero_conta, data_nascimento)
SELECT 1, 'Cliente 1', false, 100000, '0000001', '2023-05-22' UNION ALL
SELECT 2, 'Cliente 2', false, 100000, '0000002', '2023-05-22' UNION ALL
SELECT 3, 'Cliente 3', true, 100000, '0000003', '2023-05-22' UNION ALL
SELECT 4, 'Cliente 4', false, 100000, '0000004', '2023-05-22' UNION ALL
SELECT 5, 'Cliente 5', false, 100000, '0000005', '2023-05-22' UNION ALL
SELECT 6, 'Cliente 6', false, 100000, '0000006', '2023-05-22' UNION ALL
SELECT 7, 'Cliente 7', true, 100000, '0000007', '2023-05-22' UNION ALL
SELECT 8, 'Cliente 8', false, 100000, '0000008', '2023-05-22';
    
    
INSERT INTO movimentacao (id_transacao, id_cliente, tipo_operacao, data_transacao, cobrado_taxa, valor_operacao, regra_aplicada)
SELECT 1, 1, 'SAQUE', '2023-05-22', true, 785, 'NENHUM' UNION ALL
SELECT 2, 2, 'SAQUE', '2023-05-22', true, 14, 'EXCLUSIVE' UNION ALL
SELECT 3, 1, 'SAQUE', '2023-05-22', false, 25, 'EXCLUSIVE' UNION ALL
SELECT 4, 3, 'SAQUE', '2023-05-22', false, 27, 'EXCLUSIVE' UNION ALL
SELECT 5, 1, 'DEPOSITO', '2023-05-22', false, 13.50, 'EXCLUSIVE' UNION ALL
SELECT 6, 3, 'DEPOSITO', '2023-05-22', false, 14, 'EXCLUSIVE' UNION ALL
SELECT 7, 2, 'DEPOSITO', '2023-05-22', false, 24.11, 'EXCLUSIVE' UNION ALL
SELECT 8, 1, 'DEPOSITO', '2023-05-22', false, 11.274, 'EXCLUSIVE' UNION ALL
SELECT 9, 4, 'DEPOSITO', '2023-05-22', false, 87.45, 'EXCLUSIVE';

CREATE SEQUENCE MOVIMENTACAO_SEQUENCE START WITH 10 INCREMENT BY 1;
CREATE SEQUENCE CLIENTE_SEQUENCE START WITH 10 INCREMENT BY 1;