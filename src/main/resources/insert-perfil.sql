INSERT INTO seguranca.tb_perfil VALUES ('ADMIN', 'Administrador') ON CONFLICT (cod_perfil) DO NOTHING;
INSERT INTO seguranca.tb_perfil VALUES ('USER', 'Usuario') ON CONFLICT (cod_perfil) DO NOTHING;
INSERT INTO seguranca.tb_perfil VALUES ('MANAG', 'Gerente') ON CONFLICT (cod_perfil) DO NOTHING;