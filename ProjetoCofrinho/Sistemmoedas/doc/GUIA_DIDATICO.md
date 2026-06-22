# Guia Didático — Entendendo o Cofrinho do zero

Este guia explica o projeto **para quem nunca viu o código**. A ideia é mostrar
o que cada arquivo faz, com palavras simples, focando nos **4 pilares da
Orientação a Objetos (POO)**. Você não precisa abrir a pasta `src/` para
entender — está tudo explicado aqui.

---

## O que o programa faz?

Imagine um cofrinho de verdade onde você joga moedas. Aqui é a mesma coisa, só
que no computador. Você pode:

- **guardar** moedas (Real, Dólar ou Euro);
- **listar** o que já guardou;
- **remover** uma moeda;
- **somar tudo** convertido para Real;
- **sair**.

Tudo acontece no **console** (aquela tela preta de texto), digitando números de
um menu. Quando você fecha o programa, as moedas somem — elas só existem na
memória enquanto ele está aberto.

---

## A grande ideia: tudo é "Moeda"

Antes de ver os arquivos, entenda a ideia central:

> No mundo real, "moeda" é um conceito genérico. Você nunca tem uma "moeda" na
> mão — você tem **um Real**, **um Dólar** ou **um Euro**. Cada um tem nome,
> símbolo e quanto vale.

O programa foi construído exatamente assim: existe uma ideia genérica chamada
**Moeda**, e dela nascem três tipos concretos: **Real**, **Dólar** e **Euro**.

---

## Os arquivos, um por um

Pense no projeto como uma pequena empresa, onde cada arquivo é um funcionário
com uma função específica.

### 1. `Moeda.java` — "o molde de toda moeda"

É um **molde** (em POO chamamos de *classe abstrata*). Ele diz:
"toda moeda tem um **valor** e uma **quantidade**, e toda moeda sabe calcular
seu total e se converter para Real". Mas ele **não** diz o nome nem a cotação —
porque isso depende de qual moeda é.

> Analogia: é como uma "ficha de cadastro de moeda" em branco. Sozinha ela não
> serve; alguém precisa preencher os campos "nome", "símbolo" e "cotação".

Por isso você **não pode** criar uma `Moeda` pura. Você cria um Real, um Dólar
ou um Euro — que são moedas "de verdade", com os campos preenchidos.

### 2. `Real.java`, `Dolar.java`, `Euro.java` — "as moedas de verdade"

Cada um pega o molde `Moeda` e **preenche o que faltava**:

| Arquivo | Nome | Símbolo | Quanto vale 1 unidade em Real |
|---|---|---|---|
| `Real.java` | Real | R$ | 1,00 (já é Real) |
| `Dolar.java` | Dólar | $ | 5,00 |
| `Euro.java` | Euro | € | 5,50 |

Eles **aproveitam** tudo que o molde já sabe fazer (calcular total, converter
para Real) e só acrescentam suas particularidades. Não precisam reescrever as
contas — elas vêm prontas do molde.

### 3. `Cofrinho.java` — "a caixa onde as moedas ficam"

É o cofrinho em si. Ele guarda uma **lista de moedas** e sabe:

- **adicionar** uma moeda na caixa;
- **remover** uma moeda pelo número (posição);
- dizer se está **vazio**;
- **somar** todas as moedas convertidas para Real.

O detalhe importante: o cofrinho **não se importa** se a moeda é Real, Dólar ou
Euro. Para ele, é tudo "Moeda". Quando precisa somar, ele pede para cada moeda
"se converter para Real" e confia que cada uma sabe fazer a sua parte.

> Analogia: o cofrinho é como um caixa de banco que aceita qualquer moeda sem
> precisar conhecer o câmbio de cada país — ele pergunta à própria moeda
> "quanto você vale em Real?" e anota.

### 4. `MenuConsole.java` — "o atendente que fala com você"

É quem **conversa com o usuário**. Mostra os menus, lê o que você digita,
confere se a entrada faz sentido e chama o cofrinho para executar a ação.

Ele também é cuidadoso: se você digitar uma letra onde devia ser número, ou um
valor negativo, ele **avisa e pergunta de novo**, em vez de quebrar.

> Analogia: é o atendente do banco. Ele não guarda dinheiro nem faz câmbio — ele
> entende o seu pedido e repassa para o cofrinho.

### 5. `Main.java` — "o botão de ligar"

É o arquivo mais simples. Ele só **monta as peças** (cria o cofrinho e o
atendente) e **liga o programa**. Nada de regra de negócio aqui.

---

## Como uma ação acontece (exemplo real)

Você quer guardar **4 moedas de 25 centavos de dólar**:

1. No menu, você digita **2** (Guardar moeda). → *MenuConsole* recebe.
2. Aparece a lista de moedas; você digita **2** (Dólar).
3. O atendente pergunta o valor: você digita **0.25**.
4. Pergunta a quantidade: você digita **4**.
5. O atendente cria **um objeto Dólar** com esses dados.
6. Entrega esse Dólar ao **Cofrinho**, que o guarda na lista.

Depois, ao pedir o total (opção **4**), o cofrinho percorre a lista e pergunta a
cada moeda "quanto você vale em Real?". O Dólar responde
`0.25 × 4 × 5.0 = R$ 5,00`. O cofrinho soma tudo e mostra o resultado.

---

## Os 4 pilares da POO — onde aparecem aqui

POO se apoia em quatro ideias. Veja cada uma com a analogia do projeto:

### 🎭 Abstração — "focar no essencial"
A classe `Moeda` define **o que** toda moeda faz (ter valor, converter para
Real) sem se prender a **qual** moeda é. É o conceito genérico, o "molde".
→ Está em **`Moeda.java`**.

### 🧬 Herança — "aproveitar o que já existe"
`Real`, `Dolar` e `Euro` **herdam** de `Moeda`. Eles ganham de graça tudo que o
molde já sabe (cálculos, atributos) e só acrescentam o que é diferente. Ninguém
reescreve a conta de conversão três vezes.
→ Está em **`Real.java`, `Dolar.java`, `Euro.java`**.

### 🔀 Polimorfismo — "tratar diferentes como iguais"
O cofrinho guarda tudo como "Moeda" e chama `converterParaReal()` sem saber o
tipo. Cada moeda responde do seu jeito (Dólar usa 5,0; Euro usa 5,5). A mesma
ordem, comportamentos diferentes.
→ Acontece em **`Cofrinho.java`** ao somar o total.

### 🔒 Encapsulamento — "proteger os dados"
Os dados de cada moeda (valor, quantidade) ficam **escondidos** e só são
acessados por métodos controlados. E o cofrinho entrega sua lista em modo
"somente leitura", para ninguém bagunçar por fora.
→ Está em **`Moeda.java`** (atributos privados) e **`Cofrinho.java`** (lista
protegida).

---

## Por que dividir em tantos arquivos?

Porque **cada um tem uma única responsabilidade**. Isso deixa o código:

- **fácil de entender** — você sabe onde procurar cada coisa;
- **fácil de mudar** — alterar o menu não mexe nas contas das moedas;
- **fácil de crescer** — quer adicionar a Libra? Basta criar `Libra.java`
  herdando de `Moeda`. Nada mais precisa mudar.

Esse último ponto é a grande vantagem da POO bem feita: **o programa cresce sem
quebrar o que já existe.**
