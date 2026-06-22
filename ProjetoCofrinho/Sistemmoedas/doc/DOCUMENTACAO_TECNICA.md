# Documentação Técnica — Cofrinho

Aplicação de **console em Java puro** que simula um cofrinho virtual de moedas
(Real, Dólar e Euro). O usuário guarda, lista, remove moedas e calcula o total
convertido para Real. O foco do projeto é demonstrar **Orientação a Objetos**.

---

## 1. Stack e restrições

| Item | Definição |
|---|---|
| Linguagem | Java (projeto configurado para **JavaSE-21**, LTS) |
| Build | Nenhum — projeto Java puro do Eclipse (sem Maven/Gradle) |
| Persistência | Nenhuma — dados ficam **em memória** durante a execução |
| Dependências externas | Nenhuma — apenas a biblioteca padrão (`java.util`) |
| Entrada | `java.util.Scanner` (console) |
| Saída | `System.out` (console) |

Recursos de Java usados: classes `abstract`, herança (`extends`), `@Override`,
`switch` com setas (`->`, estável desde o Java 14), `ArrayList`,
`Collections.unmodifiableList` e `String.format` com `Locale`.

---

## 2. Estrutura de pastas

```
Sistemmoedas/
├── .classpath          # config Eclipse: source=src, output=bin, JRE=JavaSE-21
├── .project            # metadados do projeto Eclipse
├── bin/                # .class gerados (compilação)
├── doc/                # esta documentação
└── src/
    ├── Main.java        # ponto de entrada
    ├── MenuConsole.java # camada de interface (I/O + validação)
    ├── Cofrinho.java    # modelo: coleção de moedas + cálculos
    ├── Moeda.java       # classe abstrata base
    ├── Real.java        # subclasse concreta
    ├── Dolar.java       # subclasse concreta
    └── Euro.java        # subclasse concreta
```

---

## 3. Arquitetura em camadas

O projeto separa responsabilidades em três camadas bem definidas:

```
┌─────────────────────────────────────────────┐
│  Main                                         │  Inicialização
│  cria Cofrinho + MenuConsole e inicia o loop  │
└───────────────────┬───────────────────────────┘
                    │
┌───────────────────▼───────────────────────────┐
│  MenuConsole  (camada de apresentação)         │  I/O + validação
│  menus, leitura de teclado, validação,         │
│  criação dos objetos concretos                 │
└───────────────────┬───────────────────────────┘
                    │ usa (depende de) ↓
┌───────────────────▼───────────────────────────┐
│  Cofrinho  (camada de modelo/negócio)          │  Estado + regras
│  List<Moeda>, adicionar/remover, total em R$   │
└───────────────────┬───────────────────────────┘
                    │ contém ↓
┌───────────────────▼───────────────────────────┐
│  Moeda (abstrata) ◄── Real / Dolar / Euro      │  Domínio
│  valor, quantidade, conversão para Real        │
└────────────────────────────────────────────────┘
```

Regra de dependência: as setas apontam só para baixo. O `Cofrinho` **não conhece**
o `MenuConsole`, e a `Moeda` **não conhece** o `Cofrinho`. Isso mantém o modelo
independente da interface.

---

## 4. Detalhamento das classes

### 4.1 `Moeda` (abstrata)

Base de todas as moedas. Define o contrato e a lógica comum.

| Membro | Tipo | Descrição |
|---|---|---|
| `valor` | `double` (final) | denominação da moeda (ex.: `0.25`) |
| `quantidade` | `int` | quantas moedas dessa denominação |
| `getNome()` | abstrato | nome exibido (ex.: "Dólar") |
| `getSimbolo()` | abstrato | símbolo (ex.: "$") |
| `getCotacaoParaReal()` | abstrato | fator de conversão para Real |
| `calcularTotal()` | concreto | `valor * quantidade` |
| `converterParaReal()` | concreto | `calcularTotal() * getCotacaoParaReal()` |
| `toString()` | concreto | linha formatada para listagem |

Os três métodos abstratos forçam cada subclasse a definir seus próprios dados;
os métodos concretos reaproveitam essa definição via chamadas polimórficas.

### 4.2 `Real`, `Dolar`, `Euro` (concretas)

Cada uma `extends Moeda` e implementa apenas os três métodos abstratos:

| Classe | `getNome()` | `getSimbolo()` | `getCotacaoParaReal()` |
|---|---|---|---|
| `Real` | "Real" | "R$" | `1.0` |
| `Dolar` | "Dólar" | "$" | `5.0` |
| `Euro` | "Euro" | "€" | `5.5` |

As cotações são fixas (valores de exemplo), conforme o enunciado.

### 4.3 `Cofrinho` (modelo)

Guarda as moedas e expõe operações sobre a coleção. Trabalha **somente com o
tipo abstrato `Moeda`** — nunca com `Real`/`Dolar`/`Euro` diretamente.

| Método | Retorno | Descrição |
|---|---|---|
| `adicionar(Moeda)` | `void` | adiciona à lista |
| `remover(int indice)` | `boolean` | remove por índice (base 0); `false` se inválido |
| `estaVazio()` | `boolean` | lista vazia? |
| `getMoedas()` | `List<Moeda>` | **lista somente leitura** (`unmodifiableList`) |
| `calcularTotalConvertidoParaReal()` | `double` | soma de `converterParaReal()` de todas |

### 4.4 `MenuConsole` (apresentação)

Único ponto que conversa com o usuário. Contém os menus, o loop principal e
toda a validação de entrada. Cria os objetos concretos conforme a escolha e
delega o resto ao `Cofrinho`.

Métodos de leitura robustos (não quebram com entrada inválida):

| Método | Comportamento |
|---|---|
| `lerInteiro(msg)` | repete até receber um inteiro válido |
| `lerInteiroPositivo(msg)` | exige `> 0` |
| `lerValorPositivo(msg)` | aceita vírgula **ou** ponto; exige `> 0` |

### 4.5 `Main`

Apenas o *wiring*: cria `Cofrinho`, injeta no `MenuConsole` e chama `iniciar()`.

---

## 5. Fluxos principais

**Guardar moeda** (opção 2):
1. menu de moedas → usuário escolhe tipo (1–3) ou volta (0);
2. `lerValorPositivo` → valor da denominação;
3. `lerInteiroPositivo` → quantidade;
4. `switch` cria `new Real/Dolar/Euro(valor, quantidade)`;
5. `cofrinho.adicionar(moeda)`.

**Remover moeda** (opção 3):
1. lista as moedas com índice (a partir de 1);
2. lê o índice; `0` cancela;
3. converte para base 0 e chama `cofrinho.remover(indice - 1)`;
4. mensagem de sucesso ou de índice inválido.

**Calcular total** (opção 4): chama `calcularTotalConvertidoParaReal()` —
internamente percorre a lista chamando `converterParaReal()` em cada item
(polimorfismo puro).

---

## 6. Validação e tratamento de erros

- Leitura via `Scanner.nextLine()` + `parse`, dentro de laços que **repetem**
  até a entrada ser válida (`NumberFormatException` é capturada).
- Valores monetários e quantidades exigem `> 0`.
- Remoção valida limites do índice antes de mexer na lista.
- Opções de menu inválidas exibem aviso e voltam ao menu.

---

## 7. Decisões de projeto

1. **`double` em vez de `BigDecimal`** — fiel ao exemplo do enunciado e ao foco
   em POO. Exibição sempre via `String.format(Locale.US, "%.2f", ...)` para
   garantir 2 casas e ponto decimal. *Em produção financeira, o correto seria
   `BigDecimal`* (evita imprecisão de ponto flutuante binário).
2. **Listagem fica no `MenuConsole`, não no `Cofrinho`** — imprimir no modelo
   quebraria a separação de responsabilidades. O `Cofrinho` expõe dados; o menu
   formata e imprime.
3. **`getMoedas()` retorna lista imutável** — protege o estado interno do
   `Cofrinho` contra alterações externas (encapsulamento).
4. **Java 21** — versão já configurada no projeto Eclipse; LTS, sem ajustes.

---

## 8. Como compilar e executar

**Pelo Eclipse:** importe como *Existing Project*, clique direito em `Main.java`
→ Run As → Java Application; interaja pelo Console.

**Por linha de comando** (a partir de `Sistemmoedas/`):

```bash
javac -encoding UTF-8 -d bin src/*.java
java -cp bin Main
```
