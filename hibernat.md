https://hibernate.org/orm/documentation/6.3/

https://docs.hibernate.org/stable/annotations/reference/en/html/

https://docs.spring.io/spring-data/jpa/reference/#repositories

06 Para saber mais: anotações do hibernate
 Próxima Atividade

O Hibernate é uma das especificações mais utilizadas da JPA, e fornece diversas anotações para a utilização do Mapeamento Objeto-Relacional.

Vamos conhecer as principais delas?

@Entity
Essa anotação é usada para marcar uma classe como uma entidade que deve ser mapeada para uma tabela de banco de dados. Cada entidade corresponde a uma tabela no banco de dados.

@Table
Por padrão, o Hibernate usa o nome da classe como o nome da tabela no banco de dados, fazendo apenas a conversão de padrão de nomenclatura do PascalCase para o SnakeCase, que é o padrão utilizado no banco de dados, no entanto, caso seja necessário que o nome da classe seja diferente do nome da tabela no banco de dados, é possível utilizar esta anotação que permite personalizar o mapeamento entre a classe de entidade e a tabela de banco de dados. Com ela, você pode especificar o nome da tabela, o esquema e as restrições de chave primária.

@Entity
@Table(name = "minha_tabela")
public class MinhaEntidade { ... }
Copiar código
@Id
Marca um campo como a chave primária da entidade. O Hibernate usa essa anotação para identificar exclusivamente os registros no banco de dados.

@GeneratedValue
Usada em conjunto com @Id, essa anotação especifica como a chave primária é gerada automaticamente. Pode ser usada com estratégias como AUTO, IDENTITY, SEQUENCE ou TABLE, dependendo do banco de dados.

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
Copiar código
@Column
Similar ao que acontece na anotação @Table, o Hibernate utiliza o nome dos atributos e os converte como sendo idênticos aos nomes das colunas no banco de dados, e caso seja necessário utilizar nomes diferentes,você pode configurar o nome da coluna, bem como seu tipo, e se ela é obrigatória.

@Column(name = "nome_completo", nullable = false)
private String nome;
Copiar código
@OneToMany e @ManyToOne
Usadas para mapear relacionamentos de um-para-muitos e muitos-para-um entre entidades. Elas definem as associações entre as tabelas no banco de dados.

@Entity
public class Autor {
    @OneToMany(mappedBy = "autor")
    private List<Livro> livros;
}

@Entity
public class Livro {
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
}
Copiar código
@ManyToMany
A anotação @ManyToMany é usada para mapear relacionamentos muitos-para-muitos entre entidades.

@OneToOne
A anotação @OneToOne é usada para mapear relacionamentos um-para-um entre entidades.

@JoinColum
A anotação @JoinColumn é usada para especificar a coluna que será usada para representar um relacionamento entre entidades. É frequentemente usada em conjunto com @ManyToOne e @OneToOne.

@ManyToOne
@JoinColumn(name = "autor_id")
private Autor autor;
Copiar código
@JoinTable
A anotação @JoinTable é usada para mapear tabelas de junção em relacionamentos muitos-para-muitos. Ela especifica a tabela intermediária que liga duas entidades.

@Entity
public class Estudante {
    @ManyToMany
    @JoinTable(name = "inscricao",
               joinColumns = @JoinColumn(name = "estudante_id"),
               inverseJoinColumns = @JoinColumn(name = "curso_id"))
    private List<Curso> cursos;
}
Copiar código
@Transient
A anotação @Transient é usada para marcar uma propriedade como não persistente. Isso significa que a propriedade não será mapeada para uma coluna no banco de dados.

@Transient
private transientProperty;
Copiar código
@Enumerated
A anotação @Enumerated é usada para mapear campos enumerados (enum) para colunas do banco de dados.

@Enumerated(EnumType.STRING)
private Status status;
Copiar código
@NamedQuery
Essa anotação é usada para definir consultas JPQL nomeadas que podem ser reutilizadas em várias partes do código.

@Entity
@NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
public class Cliente { ... }
Copiar código
@Cascade
A anotação @Cascade é usada para especificar o comportamento de cascata das operações de persistência, como salvar e excluir, em relacionamentos. Por exemplo, você pode configurar para que as operações de salvar em cascata afetem entidades relacionadas.

@OneToMany(mappedBy = "departamento")
@Cascade(CascadeType.SAVE_UPDATE)
private List<Funcionario> funcionarios;
Copiar código
@Embeddable e @Embedded
Essas anotações são usadas para representar tipos incorporados (embeddable types) que podem ser usados como componentes em entidades.

@Embeddable
public class Endereco { ... }

@Entity
public class Cliente {
    @Embedded
    private Endereco endereco;
}
Copiar código
Além dessas anotações, há muitas outras que podem ser consultadas na documentação de anotações do Hibernate, e que facilitam muito o dia a dia de pessoas desenvolvedoras que usam o ORM.


 Para saber mais: relacionamentos Uni e Bidirecionais com JPA
 Próxima Atividade

Ao trabalharmos com banco de dados, existem relacionamentos com diferentes tipos de direção: os relacionamentos unidirecionais e os bidirecionais. Os unidirecionais deixam a relação visível apenas em um lado, enquanto relacionamentos bidirecionais permitem que os objetos de ambos os lados acessem e/ou alterem o objeto do outro lado. Isso é muito útil quando você quer ter um controle maior sobre seus objetos e as operações que você pode executar neles, como no caso visto em vídeo.

Vamos dar uma olhada em como podemos definir, configurar e controlar esses relacionamentos.

Definindo um Relacionamento Bidirecional
Para definir um relacionamento bidirecional, precisamos ter duas entidades que estão de alguma forma conectadas. Por exemplo, vamos considerar um simples sistema de blog onde temos posts e comentários. Cada post pode ter vários comentários.

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
}

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
Copiar código
No exemplo acima, definimos um relacionamento "OneToMany" de Post para Comentários e um relacionamento "ManyToOne" inverso de Comentários para Post. mappedBy = "post" no Post se refere ao campo post na classe Comentário.

Erros comuns ao configurarmos mapeamentos bidirecionais

Erro 1: Problemas de mapeamento bidirecional
Quando se tem uma relação bidirecional entre duas entidades, como, por exemplo, uma relação entre os objetos "Aluno" e "Disciplina", em que um aluno pode se matricular em diversas disciplinas e uma disciplina pode ter vários alunos, é necessário atentar para o mapeamento de ambos os lados da relação.

Exemplo prático:

@Entity
public class Aluno {
    @OneToMany(mappedBy = "aluno")
    private List<Disciplina> disciplinas;
}

@Entity
public class Disciplina {
    @ManyToOne
    private Aluno aluno;
}
Copiar código
Neste exemplo, a entidade "Disciplina" está mapeando para a entidade "Aluno". No entanto, a entidade "Aluno" também precisa mapear de volta para a "Disciplina". A falta desse mapeamento bidirecional é uma causa comum de erros.

Para resolver, inclua o mapeamento no lado "Aluno" da relação:

@Entity
public class Aluno {
    @OneToMany(mappedBy = "aluno")
    private List<Disciplina> disciplinas;
    
    // inclua este método
    public void addDisciplina(Disciplina disciplina) {
        this.disciplinas.add(disciplina);
        disciplina.setAluno(this);
    }
}
Copiar código
Erro 2: Falha ao escolher o lado de posse corretamente
Em uma associação bidirecional, um lado é o proprietário, e o outro é o lado invertido. Na JPA, o lado do proprietário é sempre usado quando se atualiza a relação no banco de dados.

Por exemplo:

@Entity
public class Carro {
    @ManyToOne
    private Dono dono;
}

@Entity
public class Dono {
    @OneToMany(mappedBy = "dono")
    private List<Carro> carros;
}
Copiar código
"Neste caso, Carro é a entidade proprietária. Se esquecermos de fato de atualizar o lado do proprietário, a JPA não poderá sincronizar corretamente a associação com o banco de dados.

Para corrigir, você deve atualizar sempre o lado proprietário do relacionamento:

Dono dono = new Dono();
Carro carro = new Carro();
carro.setDono(dono); // Carro é a entidade proprietária, então atualizamos este lado
dono.getCarros().add(carro);
entityManager.persist(dono);
Copiar código
Estes são apenas dois exemplos de erros comuns que podem ocorrer ao configurar os mapeamentos na JPA. Outros erros também podem ocorrer, mas a chave para resolvê-los é entender bem como a JPA funciona e sempre analisar e testar cuidadosamente o seu código.


Estudo aprofundado sobre Fetch Types: Lazy e Eager
Aprendemos que geralmente encontramos dois tipos de carregamento de dados: "lazy" e "eager". Esses dois conceitos são essencialmente sobre quando e como os dados são recuperados do banco de dados para serem usados em nossas aplicações.

O que é Fetch Type?
De modo bem simples, Fetch Type define qual a estratégia será utilizada para carregar os dados do banco para sua aplicação.

Para facilitar nosso entendimento, gosto de usar a analogia do café da manhã. Imagine que você tem uma mesa de café da manhã e pode haver vários itens nela, como pão, café, leite, frutas, etc.

A forma como você vai pegar esses itens, quando e quantos de uma vez, é basicamente o conceito por trás do fetch type, que veremos a seguir.

Lazy Fetch Type
Lazy, em inglês, significa preguiçoso. Em termos de programação, Lazy Fetch Type é quando você pega apenas o que precisa, no momento em que precisa.

Se voltarmos para a nossa analogia do café da manhã, seria como pegar somente o café primeiro. Quando se sentir pronto para comer algo, você então vai e pega uma fruta ou um pão. Ou seja, você só busca os outros itens quando realmente vai utilizá-los.

Vamos tomar como exemplo a relação entre um usuário e seus posts em uma aplicação de blog. Se optarmos pelo fetch type lazy, ao carregarmos o usuário, seus posts não serão carregados automaticamente. Eles serão postergados até que explicitamente solicitado, conforme a seguir:

@Entity
public class User {
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;
}
Copiar código
Eager Fetch Type
Eager, em inglês, pode ser traduzido como ansioso. Em programação, Eager Fetch Type é mais rápido, pois vai pegar todos os dados relacionados ao mesmo tempo.

Voltando à analogia do café da manhã, Eager Fetch Type seria como se você pegasse tudo que há na mesa de uma vez só. Você pega o café, pão, leite, frutas, tudo de uma única vez.

Na relação usuário/posts, se optarmos pelo fetch type eager, ao carregar o usuário, todos os seus posts serão carregados simultaneamente.

@Entity
public class User {
    @OneToMany(fetch = FetchType.EAGER)
    private List<Post> posts;
}
Copiar código
Impacto no desempenho da aplicação
A estratégia de carregamento afeta diretamente o desempenho da aplicação. Um carregamento Eager pode parecer eficiente, pois tudo já está carregado de uma vez. No entanto, se a relação envolver muitos dados, isso pode causar problemas de desempenho, além de consumir muito mais memória, uma vez que estamos carregando mais dados do que realmente precisamos.

Por outro lado, Lazy Fetch Type pode parecer uma estratégia mais eficaz, pois carrega os dados sob demanda. No entanto, se não administrado cuidadosamente, pode acabar resultando em múltiplas chamadas ao banco de dados, aumentando o tempo de resposta.

Escolher entre Lazy e Eager não é uma decisão trivial e deve ser baseada na necessidade da aplicação. Um bom ponto de partida é começar com Lazy Fetch Type e optar por Eager onde o carregamento completo é muitas vezes necessário.

O escopo da aplicação, a quantidade de dados, a frequência de acesso e muitos outros fatores serão decisivos para essa escolha. É importante sempre analisar o contexto e testar o desempenho para alcançar a melhor estratégia.

Entender sobre os conceitos de Fetch Types, lazy e eager, é um passo muito importante para melhorar o desempenho do seu aplicativo. Portanto, sempre busque melhorar e aperfeiçoar seu conhecimento nesta área.

Para saber mais: consultas derivadas ("derived queries")
A JPA tem diversos recursos, e um dos mais legais que podemos utilizar são as derived queries, em que trabalhamos com métodos específicos que consultam o banco de forma personalizada. Esses métodos são criados na interface que herda de JpaRepository. Neles, utilizaremos palavras-chave (em inglês) para indicar qual a busca que queremos fazer.

A estrutura básica de uma derived query na JPA consiste em:

verbo introdutório + palavra-chave “By” + critérios de busca

Como verbos introdutórios, temos find, read, query, count e get. Já os critérios são variados. Veremos alguns exemplos em vídeo, mas você pode explorar bastante a prática para entendê-los melhor.

Os critérios mais simples envolvem apenas os atributos da classe mapeada no Repository. No nosso caso, um exemplo desse critério seria o findByTitulo, em que fazemos uma busca por séries com um atributo específico da classe Serie. Mas podemos acrescentar condições a esses critérios. É aí que surge o findByTituloContainingIgnoreCase(). Para fazer os filtros, poderíamos utilizar várias outras palavras. Dentre elas, podemos citar:

Palavras relativas à igualdade:

Is, para ver igualdades
Equals, para ver igualdades (essa palavra-chave e a anterior têm os mesmos princípios, e são mais utilizadas para a legibilidade do método).
IsNot, para checar desigualdades
IsNull, para verificar se um parâmetro é nulo
Palavras relativas à similaridade:

Containing, para palavras que contenham um trecho
StartingWith, para palavras que comecem com um trecho
EndingWith, para palavras que terminem com um trecho
Essas palavras podem ser concatenadas com outras condições, como o ContainingIgnoreCase, para não termos problemas de Case Sensitive.
Palavras relacionadas à comparação:

LessThan, para buscar registros menores que um valor
LessThanEqual, para buscar registros menores ou iguais a um valor
GreaterThan, para identificar registros maiores que um valor
GreaterThanEqual, para identificar registros maiores ou iguais a um valor
Between, para saber quais registros estão entre dois valores
Essas são apenas algumas das palavras que podemos utilizar, e podemos combiná-las de muitas formas! Ao longo dos próximos vídeos, vamos exercitar nossos conhecimentos fazendo várias buscas com essas palavras-chave, mas também te convidamos a testar com vários exemplos para ver na prática como funciona!

