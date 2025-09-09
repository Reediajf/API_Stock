# 📦 Gerenciador de Estoque

Um sistema em **Java** com console interativo para controlar produtos, gerenciar o estoque e gerar relatórios em **PDF**.  
Simples, rápido e ideal para mostrar na prática o uso de **Java + MySQL**! 🚀

---

## ✨ Funcionalidades

✅ Cadastrar produtos  
✅ Atualizar informações e estoque  
✅ Remover produtos  
✅ Listar todos os itens cadastrados  
✅ Exportar relatórios em **PDF**  

---

## 🛠️ Tecnologias Utilizadas

Este projeto combina Java com bibliotecas poderosas:

- ⚡ **Lombok** – elimina código repetitivo (getters, setters, etc.)  
- 🔑 **dotenv-java** – gerenciamento de variáveis de ambiente  
- 🐬 **MySQL Connector/J** – conexão com banco de dados  
- 📝 **SLF4J (API + Simple)** – logs mais organizados  
- 📚 **Apache Commons** – utilitários para manipulação de dados  
- 📑 **iText (io, kernel, layout)** – geração de relatórios em PDF  

---

## 🗄️ Configuração do Banco de Dados

Crie o banco de dados no MySQL com o nome **`gerenciadorestoque`**:

**`sql
CREATE DATABASE gerenciadorestoque;`**

E configure suas credenciais no arquivo .env:

**`DB_URL=jdbc:mysql://localhost:3306/gerenciadorestoque
DB_USER=seu_usuario
DB_PASSWORD=sua_senha`**

---

▶️ Como Rodar o Projeto

Clone o repositório:

**`git clone https://github.com/seu-usuario/Stock.git`**


Compile o projeto (assumindo que as libs estão em lib/):

**`javac -cp "lib/*" -d bin src/**/*.java`**


Execute a aplicação:

**`java -cp "bin;lib/*" Main`**

---

🎯 Demonstração

<img width="447" height="355" alt="image" src="https://github.com/user-attachments/assets/dce3682d-d7fb-41f8-b418-6fca20d8d336" />
<img width="447" height="355" alt="image" src="https://github.com/user-attachments/assets/07ad55dd-0551-47fe-b540-3fd955a4936a" />
<img width="447" height="355" alt="image" src="https://github.com/user-attachments/assets/788b5d43-f603-4591-80bb-ec4609f79179" />
<img width="447" height="355" alt="image" src="https://github.com/user-attachments/assets/4b0b6db0-7f5f-48ec-a392-2c24cdf5cad1" />
<img width="447" height="355" alt="image" src="https://github.com/user-attachments/assets/1f3533c6-4ba6-4cc5-b2b4-2638bd999024" />



---

Se curtiu, deixe uma ⭐ no repositório!

