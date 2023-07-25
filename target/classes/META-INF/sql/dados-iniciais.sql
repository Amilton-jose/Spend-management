-- Inserir dados na tabela "Categoria"
INSERT INTO categoria (id, nome) VALUES (1, 'Alimentação');
INSERT INTO categoria (id, nome) VALUES (2, 'Transporte');
INSERT INTO categoria (id, nome) VALUES (3, 'Lazer');
INSERT INTO categoria (id, nome) VALUES (4, 'Higiene');

-- Inserir dados na tabela "Gasto"
INSERT INTO gasto (id, valor, data, descricao, categoria_id) VALUES (1, 50.0, '2023-07-01', 'Restaurante', 1);
INSERT INTO gasto (id, valor, data, descricao, categoria_id) VALUES (2, 25.0, '2023-07-05', 'Uber', 2);
INSERT INTO gasto (id, valor, data, descricao, categoria_id) VALUES (3, 100.0, '2023-07-10', 'Cinema', 3);
INSERT INTO gasto (id, valor, data, descricao, categoria_id) VALUES (4, 100.0, '2023-07-11', 'Pasta Dental', 4);
