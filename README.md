# crud-java-databases

Este repositório contém um conjunto de implementações CRUD simples para diferentes bancos de dados, como parte do curso de Banco de Dados oferecido pela Geek University. Cada diretório corresponde a um banco de dados específico e contém uma implementação básica das operações de criação, leitura, atualização e exclusão (CRUD) usando a linguagem Java.

## Bancos de Dados Disponíveis
- CouchDB
- Firebase
- MongoDB
- MySQL
- PostgreSQL
- Redis
- SQLite

## Estrutura do Código
Cada diretório dedicado a um banco de dados segue a mesma estrutura básica e inclui o arquivo Utils.java com funções genéricas para simular a conexão e operações CRUD. O código fornecido oferece uma estrutura pronta para as operações, permitindo que você se concentre na implementação específica de cada banco de dados.

## Funcionalidades Implementadas
- conectar(): Realiza conexão com o banco de dados.
- desconectar(): Realiza a desconexão do banco de dados.
- listar(): Lista produtos do banco de dados.
- inserir(): Insere produtos no banco de dados.
- atualizar(): Atualiza produtos no banco de dados.
- deletar(): Exclui produtos do banco de dados.
- menu(): Apresenta um menu interativo para selecionar uma ação.
