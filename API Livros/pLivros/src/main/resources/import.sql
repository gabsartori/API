INSERT INTO Usuarios(nome_user, email, senha) VALUES ('Maria Clara', 'maria@gmail.com', '000');
INSERT INTO Usuarios(nome_user, email, senha) VALUES ('Fabrício Santos', 'fabricio@gmail.com', '111');
INSERT INTO Usuarios(nome_user, email, senha) VALUES ('Ana Maria', 'ana@gmail.com', '222');

INSERT INTO Tipo_Livro(tipo_livro) VALUES ('Fantasia');
INSERT INTO Tipo_Livro(tipo_livro) VALUES ('Drama');
INSERT INTO Tipo_Livro(tipo_livro) VALUES ('Ficção Científica');

INSERT INTO Livros(id_user, id_tipo, nome_livro, autor) VALUES (1, 3, 'Devoradores de estrelas', 'Andy Weir');
INSERT INTO Livros(id_user, id_tipo, nome_livro, autor) VALUES (2, 1, 'A menina que roubava livros', 'Markus Zusak');
INSERT INTO Livros(id_user, id_tipo, nome_livro, autor) VALUES (3, 2, 'Senhor dos anéis', 'J. R. R. Tolkien');

