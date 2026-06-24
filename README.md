# 💰 LeLu Seven Finance Dashboard — Spring Boot + Plaid + DDD

Sistema de dashboard financeiro integrado com instituições bancárias via **Plaid API**, construído com **Spring Boot**, autenticação **JWT**, **Clean Architecture** e **DDD**.

---

## 🏗️ Arquitetura

```
┌─────────────────────────────────────────────────────┐
│                    Frontend                         │
│              (React / Angular / Vue)                │
└─────────────────────┬───────────────────────────────┘
                      │ HTTP / REST
┌─────────────────────▼───────────────────────────────┐
│                 Spring Boot API                     │
│                                                     │
│  ┌──────────┐  ┌──────────┐  ┌──────────────────┐   │
│  │  Auth    │  │ Usuario  │  │     Plaid        │   │
│  │  JWT     │  │  DDD     │  │  Integration     │   │
│  └──────────┘  └──────────┘  └──────────────────┘   │
│                                                     │
│  ┌──────────┐  ┌──────────┐  ┌──────────────────┐   │
│  │Dashboard │  │Transacao │  │    Categoria     │   │
│  └──────────┘  └──────────┘  └──────────────────┘   │
└──────────┬──────────────────────────┬───────────────┘
           │                          │
┌──────────▼──────┐        ┌──────────▼──────────────┐
│   PostgreSQL    │        │       Plaid API         │
│                 │        │    (Sandbox/Production) │
└─────────────────┘        └─────────────────────────┘
```

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Uso |
|---|--------|---|
| Java | 17     | Linguagem principal |
| Spring Boot | 4.0.x  | Framework web |
| Spring Security | 6.x    | Autenticação e autorização |
| JWT (jjwt) | 0.12.6 | Tokens de acesso |
| PostgreSQL | 15     | Banco de dados |
| JPA / Hibernate | 6.x    | ORM |
| Plaid Java SDK | 28.x   | Integração bancária |
| Lombok | latest | Redução de boilerplate |
| Docker | latest | Containerização |
| Maven | 3.x    | Gerenciamento de dependências |

---

## 📐 Padrões e Princípios

- **DDD** — Domain-Driven Design com agregados, entidades e value objects
- **Clean Architecture** — separação em domain, application e infrastructure
- **SOLID** — princípios aplicados em toda a base de código
- **JWT** — access token (15min) + refresh token (7d)
- **Value Objects** — `Email`, `Senha`, `Valor`, `AccessToken` com validação no domínio

---

## 📁 Estrutura do Projeto

```
src/main/java/com/empresa/financeiro/
│
├── shared/                          # compartilhado entre domínios
│   ├── domain/
│   │   ├── ValueObject.java
│   │   └── DomainException.java
│   └── infrastructure/
│       └── security/
│           ├── JwtFilter.java
│           ├── TokenService.java
│           └── SecurityConfig.java
│
├── usuario/
│   ├── domain/
│   │   ├── Usuario.java             # agregado raiz
│   │   ├── IUsuarioRepository.java   # contrato
│   │   └── vo/
│   │       ├── CodUsuario.java
│   │       ├── Email.java
│   │       └── Senha.java
│   ├── application/
│   │   ├── dto/
│   │   │   ├── LoginDto.java
│   │   │   ├── RegistroDto.java
│   │   │   └── UsuarioDto.java
│   │   ├── LoginUseCase.java
│   │   ├── RegisterUseCase.java
│   │   └── RefreshTokenUseCase.java
│   └── infrastructure/
│       ├── AuthController.java
│       ├── UsuarioEntity.java
│       ├── UsuarioMapper.java
│       ├── IUsuarioJpaRepository.java
│       └── UsuarioJpaRepositoryImpl.java
│
├── perfil/
│   ├── domain/
│   │   ├── Perfil.java             # agregado raiz
│   │   ├── IPerfilRepository.java   # contrato
│   │   └── vo/
│   │       ├── CodPerfil.java
│   │       └── NomePerfil.java
│   ├── application/
│   │   ├── dto/
│   │   │   └── PerfilDto
│   │   ├── ListaPerfisUseCase.java
│   │   ├── CriarPerfilUseCase.java
│   │   └── AtribuirPerfilUseCase.java
│   └── infrastructure/
│       ├── PerfilController.java
│       ├── PerfilEntity.java
│       ├── PerfilMapper.java
│       ├── IPerfilJpaRepository.java
│       └── PerfilJpaRepositoryImpl.java
│
├── plaid/
│   ├── domain/
│   │   ├── ContaBancaria.java
│   │   ├── IPlaidRepository.java     # contrato
│   │   └── vo/
│   │       └── AccessToken.java
│   ├── application/
│   │   ├── CriarLinkTokenUseCase.java
│   │   ├── TrocarPublicTokenUseCase.java
│   │   ├── ListarContasUseCase.java
│   │   └── ListarTransacoesUseCase.java
│   └── infrastructure/
│       ├── PlaidController.java
│       ├── PlaidConfig.java
│       └── PlaidRepositoryImpl.java
│
├── transacao/
│   ├── domain/
│   │   ├── Transacao.java
│   │   ├── ITransacaoRepository.java
│   │   └── vo/
│   │       ├── TransacaoId.java
│   │       └── Valor.java
│   ├── application/
│   │   ├── SincronizarTransacoesUseCase.java
│   │   ├── ListarTransacoesUseCase.java
│   │   └── CategorizarTransacaoUseCase.java
│   └── infrastructure/
│       ├── TransacaoController.java
│       ├── TransacaoEntity.java
│       ├── TransacaoMapper.java
│       ├── ITransacaoJpaRepository.java
│       └── TransacaoJpaRepositoryImpl.java
│
└── dashboard/
    ├── domain/
    │   └── RelatorioFinanceiro.java
    ├── application/
    │   ├── GerarResumoUseCase.java
    │   ├── GerarGraficoGastosUseCase.java
    │   └── GerarProjecaoUseCase.java
    └── infrastructure/
        └── DashboardController.java
```

---

## ⚙️ Pré-requisitos

- Java 17+
- Maven 3.8+
- Docker e Docker Compose
- Conta no [Plaid](https://dashboard.plaid.com/signup) (sandbox é gratuito)

---

## 🚀 Instalação e Execução

### 1. Clone o repositório

```bash
git clone https://github.com/LuckSkyDevel/LeluSevenFinanceDashboard.git
cd LeluSevenFinanceDashboard
```

### 2. Configure as variáveis de ambiente

```bash
cp .env.example .env
```

Edite o `.env`:

```env
# Banco de dados
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/financeiro
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres

# JWT
JWT_SECRET=sua_chave_secreta_256bits
JWT_EXPIRATION=900000
JWT_REFRESH_SECRET=sua_chave_refresh_256bits
JWT_REFRESH_EXPIRATION=604800000

# Plaid
PLAID_CLIENT_ID=seu_client_id
PLAID_SECRET=seu_secret_sandbox
PLAID_ENV=sandbox
```

### 3. Suba com Docker Compose

```bash
docker compose up --build
```

### 4. Ou rode localmente

```bash
# Sobe apenas o banco
docker compose up db -d

# Roda a aplicação
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`

---

## 📋 Endpoints

### Autenticação

| Método | Rota                         | Auth | Descrição |
|---|------------------------------|---|---|
| POST | `/api/auth/registra-usuario` | — | Cadastro |
| POST | `/api/auth/login`            | — | Login |
| POST | `/api/auth/refresh`          | Refresh Token | Renovar tokens |
| POST | `/api/auth/logout`           | Bearer Token | Logout |

### Plaid

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| GET | `/api/plaid/link-token` | Bearer Token | Gera link token |
| POST | `/api/plaid/exchange-token` | Bearer Token | Troca public token |
| GET | `/api/plaid/contas` | Bearer Token | Lista contas bancárias |
| GET | `/api/plaid/transacoes` | Bearer Token | Lista transações |

### Dashboard

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| POST | `/api/dashboard/sincronizar` | Bearer Token | Sincroniza transações |
| GET | `/api/dashboard/resumo` | Bearer Token | Resumo financeiro |
| GET | `/api/dashboard/gastos-por-categoria` | Bearer Token | Gastos por categoria |

---

## 🔄 Fluxo de Integração com Plaid

```
1. Cadastro/Login           POST /api/auth/register ou /login
        ↓
2. Obter link token         GET  /api/plaid/link-token
        ↓
3. Abrir Plaid Link         (no frontend com o linkToken)
        ↓
4. Usuário conecta banco    (fluxo do Plaid Link)
        ↓
5. Trocar public token      POST /api/plaid/exchange-token
        ↓
6. Sincronizar transações   POST /api/dashboard/sincronizar
        ↓
7. Visualizar dashboard     GET  /api/dashboard/resumo
```

---

## 🧪 Exemplos de uso

### Cadastro

```bash
curl -X POST http://localhost:8080/api/auth/registra-usuario \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@email.com",
    "senha": "senha123"
  }'
```

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "nomeUsuario": "joaoX",
    "senha": "senha123"
  }'
```

### Resumo financeiro

```bash
curl http://localhost:8080/api/dashboard/resumo \
  -H "Authorization: Bearer SEU_ACCESS_TOKEN" \
  -G \
  --data-urlencode "inicio=2024-01-01" \
  --data-urlencode "fim=2024-01-31"
```

---

## 🐳 Docker

```yaml
# docker-compose.yml
services:
  db:
    image: postgres:15
    environment:
      POSTGRES_DB: financeiro
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - '5432:5432'

  api:
    build: .
    depends_on:
      db:
        condition: service_healthy
    ports:
      - '8080:8080'
    env_file:
      - .env
```

```bash
# Subir tudo
docker compose up --build

# Subir em background
docker compose up -d

# Ver logs
docker compose logs -f api

# Parar
docker compose down
```

---

## 🔐 Segurança

- Senhas com **BCrypt** (strength 12)
- **Access Token** de curta duração (15 min)
- **Refresh Token** de longa duração (7 dias) com hash no banco
- Refresh token **rotativo** — invalidado a cada uso
- **HTTPS** obrigatório em produção
- Credenciais Plaid nunca expostas ao frontend

---

## 📊 Funcionalidades do Dashboard

- ✅ Resumo financeiro (receitas, despesas, saldo)
- ✅ Gastos por categoria
- ✅ Histórico de transações
- ✅ Sincronização automática com o banco
- ✅ Suporte a múltiplas contas bancárias
- ✅ Swagger / OpenAPI
- 🔜 Projeção de gastos futuros
- 🔜 Alertas de gastos
- 🔜 Metas financeiras

---

## 🗺️ Próximos passos

- [ ] Implementar cache com Redis
- [ ] Adicionar testes unitários e de integração
- [ ] CI/CD com GitHub Actions
- [ ] Deploy no Render / Railway
- [ ] Webhook do Plaid para sincronização em tempo real
- [ ] Notificações por e-mail

---

## 📄 Licença

MIT License — sinta-se livre para usar e modificar.
