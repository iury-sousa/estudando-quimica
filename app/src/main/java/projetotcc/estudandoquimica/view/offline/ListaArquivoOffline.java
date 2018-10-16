package projetotcc.estudandoquimica.view.offline;

import java.util.ArrayList;
import java.util.List;

import projetotcc.estudandoquimica.model.ConteudoOffline;

public class ListaArquivoOffline {

    private static List<ConteudoOffline> conteudoOffline;
    private static List<ConteudoOffline> conteudoTabelaPeriodica;
    private static List<ConteudoOffline> conteudoOrganica;
    private static List<ConteudoOffline> conteudoLigacoes;

    public ListaArquivoOffline() {

        conteudoOffline = new ArrayList<>();
        conteudoTabelaPeriodica = new ArrayList<>();
        conteudoOrganica = new ArrayList<>();
        conteudoLigacoes = new ArrayList<>();

        //Conteudo Tabela Periodica
        conteudoTabelaPeriodica.add(new ConteudoOffline(
                "\t\t\t\t\t\tA tabela periódica é uma representação ordenada, que mostra características e informações sobre todos os elementos químicos conhecidos. Estes são organizados em ordem crescente a partir do seu respectivo número atômico, ou seja, classificado com base no número de prótons existentes no núcleo do átomo. \n\n" +
                        "\t\t\t\t\t\tObs.: A tabela periódica tem este nome pelo fato de alguns elementos possuírem propriedades parecidas causando assim uma repetição, ou seja, características que aparecem de forma periódica, além de estarem organizadas em forma de tabela.  \n\n" +
                        " [*table_periodic*] "));

        conteudoTabelaPeriodica.add(new ConteudoOffline(
                   "       Os elementos químicos também chamados de substâncias simples, são conjuntos de átomos que apresentam o mesmo número de prótons no interior de seus núcleos, ou seja, átomos com o mesmo número atômico (característica identificada pela letra Z).\n\n" +
                        "       Os elementos químicos são divididos em dois grupos: \n\n" +
                        "     • Grupo dos Elementos Naturais: Elementos químicos encontrados na natureza, são átomos estáveis.  \n\n" +
                        "     • Grupo dos Elementos Sintéticos (Artificias): São elementos químicos cujo átomos são desenvolvidos artificialmente em laboratórios, estes podem ser divididos em duas categorias:\n\n" +
                        "         • Cisurânicos:\n" +
                        "           Os cisurânicos são elementos sintéticos que possuem número atômico (A) inferior a 92.\n" +
                        " Ex.: Tc (Tecnécio), Fr (Frâncio), At (Astato).\n\n" +
                        "         • Transurânicos ou Superpesados: \n" +
                        "           Os transurânicos são elementos sintéticos que possuem número atômico (A) superior a 92, são radioativos e instáveis. \n" +
                        " Ex.: Np (Netúnio), Pu (Plutônio), Hs (Hássio) entre outros.\n\n" +
                        "       Atualmente a tabela periódica possui 118 elementos químicos, dentre estes, 92 elementos são encontrados na natureza, os 26 restantes são desenvolvidos artificialmente.\n"));

        conteudoTabelaPeriodica.add(new ConteudoOffline("\t\t\t\t\t\tTodos os elementos químicos representados na tabela periódica possuem suas características. Estes são representados por uma sigla, onde a primeira letra sempre é em maiúsculo. Caso tenha uma segunda ou terceira sigla, estes devem ser em letra minúscula.\n\n" +
                "Exemplos de Nomenclaturas dos Elementos:\n" +
                "   Nomes simples 1 letra \n" +
                "   H – Elemento Hidrogênio\n" +
                "   K – Elemento Potássio\n" +
                "   C – Elemento Carbono\n\n" +
                "   Nomes Compostos 2 letras\n" +
                "   Ca – Elemento Cálcio \n" +
                "   Cr –  Elemento Crômio \n" +
                "   Na – Elemento Sódio\n\n" +
                "   Nomes Compostos 3 letras\n" +
                "   Uut – Elemento Unúntrio\n" +
                "   Uup – Elemento Unúpentio\n" +
                "   UUo – Elemento Ununóctio \n\n" +
                "\t\t\t\t\t\tOutras características sobre os elementos que podemos encontrar na tabela periodica são:  Nome, Símbolo, Número Atômico, Massa Atômica e Distribuição Atômica.\n" +
                "Ex.: Elemento Ferro – Sigla Fe\n" +
                " [*elemento_tp*] "));

        conteudoTabelaPeriodica.add(new ConteudoOffline(
                "[*table_periodic_2*] \t\t\t\t\t\tOs Elementos são organizados de maneira crescente a partir do seu número atômico. Entretanto são classificadas também pelo período e pelas famílias.\n\n" +
                        "\t\t\t\t\t\tPeríodos: São as 7 linhas da tabela periódica, onde são agrupados os elementos que possuem o mesmo número de camadas eletrônicas, o número do período significa a quantidade de camadas eletrônicas que o elemento possui em sua distribuição, ou seja, o número de camadas é igual ao número do período.   \n" +
                        " \n" +
                        "1º Período: 2 elementos\n" +
                        "2º Período: 8 elementos\n" +
                        "3º Período: 8 elementos\n" +
                        "4º Período: 18 elementos\n" +
                        "5º Período: 18 elementos\n" +
                        "6º Período: 32 elementos\n" +
                        "7º Período: 32 elementos \n\n" +
                        "[*table_periodic*] \t\t\t\t\t\tFamílias ou Grupos:  São as 18 colunas da tabela periódica que representam os elementos que possuem características semelhantes, ficando assim num mesmo grupo ou família.\n\n" +
                        "\t\t\t\t\t\tAs famílias A e B possuem 8 grupos cada uma:\n\n" +
                        "Família IA: Metais Alcalinos (lítio, sódio, potássio, rubídio, césio e frâncio).\n\n" +
                        "Família IIA: Metais Alcalinos -Terrosos (berílio, magnésio, cálcio, estrôncio, bário e rádio).\n\n" +
                        "Família IIIA: Família do Boro (boro, alumínio, gálio, índio, tálio e ununtrio).\n\n" +
                        "Família IVA: Família do Carbono (carbono, silício, germânio, estanho, chumbo e fleróvio).\n\n" +
                        "Família VA: Família do Nitrogênio (nitrogênio, fósforo, arsênio, antimônio, bismuto e ununpêntio).\n\n" +
                        "Família VIA: Calcogênios (oxigênio, enxofre, selênio, telúrio, polônio, livermório).\n\n" +
                        "Família VIIA: Halogênios (flúor, cloro, bromo, iodo, astato e ununséptio).\n\n" +
                        "Família VIIIA: Gases Nobres (hélio, neônio, argônio, criptônio, xenônio, radônio e ununóctio)." +
                        "\n\n" +
                        "\t\t\t\t\t\tOs elementos de transição (metais de transição) representam as 8 famílias da série B:\n\n" +
                        "Família IB: cobre, prata, ouro e roentgênio.\n\n" +
                        "Família IIB: zinco, cádmio, mercúrio e copernício.\n\n" +
                        "Família IIIB: escândio, ítrio e sério de lantanídios (15 elementos) e actinídeos (15 elementos).\n\n" +
                        "Família IVB: titânio, zircônio, háfnio e rutherfórdio.\n\n" +
                        "Família VB: vanádio, nióbio, tântalo e dúbnio.\n\n" +
                        "Família VIB: cromo, molibdênio, tungstênio e seabórgio.\n\n" +
                        "Família VIIB: manganês, tecnécio, rênio e bóhrio.\n\n" +
                        "Família VIIIB: ferro, rutênio, ósmio, hássio, cobalto, ródio, irídio, meitnério, níquel, paládio, platina, darmstádio.\n"));

        conteudoTabelaPeriodica.add(new ConteudoOffline("\t\t\t\t\t\tOs elementos químicos possuem dois tipos de propriedades, são estes:\n\n" +
                "\t\t\t\t\t\tPropriedade Periódica: Estas propriedades variam de acordo com seu número atômico. Ex.:\n" +
                "   •\tRaio Atômico\n" +
                "   •\tVolume Atômico\n" +
                "   •\tDensidade Absoluta\n" +
                "   •\tPonto de Fusão e Ponto de Ebulição\n" +
                "   •\tAfinidade Eletrônica\n" +
                "   •\tEnergia de Ionização\n" +
                "   •\tEletronegatividade\n" +
                "   •\tEletropositividade \n" +
                "\n" +
                "\t\t\t\t\t\tPropriedade Aperiódica: Não variam periodicamente. Ex.: \n" +
                "   •\tMassa Atômica\n" +
                "   •\tCalor Específico \n" +
                "\n" +

                "Segundo a Lei de Moseley:\n" +
                "“Muitas propriedades físicas e químicas dos elementos variam periodicamente na sequência dos números atômicos dos elementos. ”\n"));

        conteudoTabelaPeriodica.add(new ConteudoOffline(
                "\t\t\t\t\t\tCaracterística relacionada ao tamanho do átomo, este valor é obtido a partir da distância do núcleo de dois átomos de mesmo elemento. \n" +
                "Expressão: \n" +
                "R = D / 2\n" +
                "R = Raio\n" +
                "D = Distância internuclear \n\n" +
                "\t\t\t\t\t\tO raio atômico é medido em picnômetro (pm). \n\n" +
                "1 picnômetro em relação à medida em metros pode ser denotado como:\n\n" +
                " [*pm*] " +
                "\n" +
                "\t\t\t\t\t\tEm um grupo (coluna da tabela) o raio atômico aumenta de cima para baixo, porque existe um aumento num número de camadas ocupadas por elétrons\n\n" +
                "\t\t\t\t\t\tNo período (linhas da tabela) o raio atômico aumenta da direita para a esquerda, porque, para um mesmo número de camadas ocupadas os elementos situados à esquerda possuem uma carga nuclear menor.\n\n" +
                "\t\t\t\t\t\tA medida que o número atômico aumenta (da esquerda para a direita) em um mesmo período, o raio atômico diminui. Isto acontece porque, à medida que aumenta o número de prótons (carga nuclear), aumenta também a atração sobre os elétrons. Assim, diminui-se o tamanho dos átomos.\n" +
                        "[*variacao_ra*] "));

        conteudoTabelaPeriodica.add(new ConteudoOffline(
                "\t\t\t\t\t\tÉ a energia mínima necessária para retirar um elétron de um átomo químico que se encontra neutro.\n\n" +
                "\t\t\t\t\t\tAssim, essa propriedade indica a energia necessária para transferir o elétron de um átomo em um estado fundamental.\n\n" +
                "\t\t\t\t\t\tEstado fundamental de um átomo significa que o número de prótons é igual ao número de elétrons. [*num_proton*].\n\n" +
                "\t\t\t\t\t\tPortanto ao retirar um elétron de um átomo este é ionizado, ou seja, ficara com mais prótons do que elétrons em sua estrutura, se transformando assim em um cátion.\n\n" +
                "\t\t\t\t\t\tO processo de Ionização é contrário ao do raio atômica. Assim, ela aumenta da esquerda para a direita e de baixo para cima.\n" +
                        "[*variacao_p*] "));

        //Conteudo Quimica Organica
        conteudoOrganica.add(0, new ConteudoOffline(1,
                "\t\t\t\t\t\tA química orgânica é uma subdivisão da química que estuda os compostos carbônicos e compostos orgânicos.\n\n" +
                        "\t\t\t\t\t\tNo início dos estudos da química orgânica acreditava-se que os compostos orgânicos não poderiam ser produzidos em laboratório, sendo somente produzidos por organismos vivos, entretanto em 1828, o químico alemão Friedrich Wohler (1800-1882) sintetizou a ureia em laboratório a partir de um composto inorgânicos, o cianeto de amônio desta forma ele demostrou que compostos orgânicos nem sempre são originários de organismos vivos.\n\n" +
                        "\t\t\t\t\t\tO elemento carbono é tetravalente, desta forma podendo realizar quatro ligações de diversas formas com diversos elementos.\n\n" +
                        "\t\t\t\t\t\tOs compostos podem ser agrupados em diferentes funções que apresentam propriedades químicas semelhantes, tais como hidrocarbonetos, álcoois, cetonas, aldeídos, ésteres, éteres, ácidos carboxílicos, aminas, amidas, entre outros.\n"));

        conteudoOrganica.add(1, new ConteudoOffline(1, "\t\t\t\t\t\tAs funções orgânicas são caracterizadas por um grupo funcional, que confere características e nomenclaturas especificas.\n\n" +
                "\t\t\t\t\t\tDevido os diversos compostos orgânicos existentes, foi necessário agrupa-los em funções, para isto as substancias foram classificadas de acordo com suas propriedades semelhantes e composições. Nos tópicos adiante será exemplificada cada uma das funções.\n"));

        conteudoOrganica.add(new ConteudoOffline(2, "\t\t\t\t\t\tOs hidrocarbonetos são funções mais simples, pois ela é composta somente por Carbono e Hidrogênio. Está função serve de base para as demais, pois são os hidrocarbonetos que formam o “esqueleto” principal das outras funções.\n\n" +
                "\t\t\t\t\t\tO petróleo e o gás natural são fontes de hidrocarbonetos, sendo o ponto de partida para a produção de combustível, plástico, corantes, etc.\n\n" +
                "Fórmula geral: CXHY\n" +
                "Exemplo:\n" +
                "Octano (C8H18): gasolina\n [*octato*] \n\n" +
                "\t\t\t\t\t\tPara fazer a nomenclatura é necessário seguir as regras estabelecidas pela IUPAC (União internacional de química aplicada)."));

        conteudoOrganica.add(new ConteudoOffline(3, "\t\t\t\t\t\tOs prefixos indicam a quantidade de carbonos existentes na cadeia carbônica, estes prefixos presentes na tabela abaixo também são utilizados nas demais funções.\n\n [*prefixo_org*] \n\n" +
                "\n\t\t\t\t\t\tA parte central representa o tipo de ligação que está presente na cadeia\n\n" +
                "• an = ligação simples (utilizada pelos alcanos)\n" +
                "• en = ligação dupla (utilizada pelos alcenos)\n" +
                "• in = ligação tripla (utilizada pelos alcinos)\n\n" +
                "\t\t\t\t\t\tE por fim o sufixo ou terminação que indica a função química. " +
                "Neste caso como é a função de hidrocarbonetos a terminação é O.\n\n" +
                "Exemplos:\n" +
                "CH4 – metano\n" +
                "C2H6 – etano \n\n" +
                "\t\t\t\t\t\tQuando as cadeias carbônicas possuem radicais utiliza-se as seguintes regras: \n\n" +
                "• Determina qual é a maior cadeia possível de carbonos\n" +
                "• Determinas seus radicais\n" +
                "• A numeração da cadeia principal se da para que a ramificação possua os menores números possíveis.\n" +
                "• Os radicais são postos em ordem alfabética \n" +
                "• Os prefixos di,tri, tetra entre outros não são considerados para efeito de ordem alfabética exceto ISO.\n\n" +
                "\t\t\t\t\t\tEle pode ser dividido de acordo com sua estrutura e tipos de ligação. São tipos de hidrocarbonetos:\n\n" +
                "• ALCANOS: são cadeias formadas somente por ligações simples.\n" +
                "Exemplo:\n" +
                "CH4: metano\n" +
                "H3C — CH3: etano\n\n" +
                "• ALCENOS: são cadeias formadas por uma dupla ligação na cadeia principal.\n" +
                "Exemplo:\n" +
                "H2C = CH2: eteno\n" +
                "H2C = CH — CH3: propeno\n\n" +
                "• ALCINOS: são cadeias formadas por uma ligação tripla na cadeia principal.\n" +
                "Exemplo: \n" +
                " [*alcino*] " +
                "• ALCADIENOS: são cadeias formadas por duas duplas ligações na cadeia principal.\n" +
                "Exemplos:\n" +
                " H2C = HC — CH = CH2: But-1,3-dieno\n" +
                " H3C — H2C — CH = C = CH2: Pent-1,2-dieno\n\n" +
                "• HIDROCARBONETOS CICLICOS: são cadeias fechadas formadas por carbono e hidrogênio, podem ser encontrados em cicloalcanos, clicoalcenos ecicloalcinos.\n" +
                "Exemplos: " +
                " [*hidrocarboneto*] \n\n" +
                "• AROMATICOS: cadeia formada por uma ou mais anéis benzênicos.\n" +
                "Exemplo:\n" +
                " [*aromaticos*] "));

        conteudoOrganica.add(new ConteudoOffline("\t\t\t\t\t\tOs álcoois são compostos orgânicos que possui como principal característica a presença da hidroxila (OH), ligada a um átomo de Carbono saturado.\n\n" +
                "\t\t\t\t\t\tUm dos representantes desta função e mais conhecidos é o etanol que está presente em: perfumes, bebidas, solventes, combustíveis, etc. sendo assim de suma importância na indústria química.\n\n" +
                "\t\t\t\t\t\tA nomenclatura dos álcoois segue a mesma regra da nomenclatura dos hidrocarbonetos com diferença somente na sua terminação que ao invés de ser O  é o OL, e a cadeia principal é aquela ligada a hidroxila (OH).\n" +
                "Exemplo: \n" +
                " [*alcoois*] " +
                "\t\t\t\t\t\tEsta função é classificada de acordo com a posição da hidroxila ou pela quantidade de hidroxilas que podem estar presentes.\n\n" +
                "• Por posição da hidroxila (OH): temos o álcool primário, onde a hidroxila está ligada ao carbono primário, o álcool secundário onde a hidroxila esta ligada ao carbono secundário e a terciaria que esta ligada ao carbono terciário e assim por diante.\n" +
                "Exemplo: \n [*hidroxi*] " +
                "\n" +
                "Por número de Hidroxila:\n" +
                "Monoálcool: possui uma hidroxila.\n" +
                "Diálcool ou diol: possui duas hidroxilas.\n" +
                "Triálcool ou triol: possui três hidroxilas.\n" +
                "Poliálcool: possui quatro ou mais hidroxilas.\n" +
                " [*exmplo_alcooi*] "));

        conteudoOrganica.add(new ConteudoOffline("\t\t\t\t\t\tO fenol é um composto orgânico que possuem uma ou mais hidroxilas OH que ficam ligadas diretamente a um anel aromático.\n" +
                "[*fenol*] " +
                "Alguns tipos de fenóis:\n" +
                "\t\t\t\t\t\tFenol: é um solido branco, foi o primeiro antisséptico usado em hospitais para evitar a proliferação de micro-organismos, mas sua utilização foi proibida pois o mesmo é corrosivo e causa queimaduras.\n" +
                " [*fenol_2*] " +
                "\t\t\t\t\t\tÁcido salicílico: é utilizado como esfoliante em alguns tratamentos como o tratamento de psoríase e dermatites esfoliantes.\n" +
                " [*fenol_3*] " +
                "\t\t\t\t\t\tA nomenclatura dos fenóis se dá pelo termo Hidróxi. A numeração inicia-se na hidroxila e prossegue no sentido que proporciona números menores.\n" +
                "Exemplo:\n" +
                "[*fenol_4*] " +
                "\n\t\t\t\t\t1-hidróxi-3-metil-benzeno\n" +
                "\t\t\t\t\t\tm-hidróxi-tolueno\n" +
                "\t\t\t\t\t\tm-cresol\n"));

        conteudoOrganica.add(new ConteudoOffline("\n\t\t\t\t\tOs aldeídos são compostos orgânicos que possuem em sua estrutura o grupo CHO e fazem parte das funções oxigenadas.\n" +
                "\n\t\t\t\t\tO grupo carbonila CHO fica presente em um carbono primário da cadeia, ou seja esse grupo esta sempre presente em uma das extremidade da cadeia\n" +
                "[*aldeidos*]" +
                "\n" +
                "\n\t\t\t\t\tA nomenclatura das aldeídos se dão da mesma forma que nos hidrocarbonetos com diferença na sua terminação que ao invés de o é al. A cadeia principal deve conter a o grupo (CHO) e sua numeração é feita a partir do grupo.  \n" +
                "Exemplos:\n " +
                "[*aldeidos_2*]" +
                "\n\t\t\t\t\tmetanal ou aldeído fórmico" +
                "\n\t\t\t\t\t[*aldeidos_3*]" +
                "\n\t\t\t\t\tBenzaldeido"));

        conteudoOrganica.add(new ConteudoOffline(
                "\t\t\t\t\t\tAs cetonas são compostas orgânicos que possui em sua estrutura o grupo funcional CO, que fica localizada entre dois átomos de carbono.\n\n" +
                        "\t\t\t\t\t\tElas podem ser classificadas de acordo com a quantidade de carbonilas, ou seja se ela possuir uma carbonila ela é uma monocetona, se possui dois grupos é uma dicetona, se possuir três grupos uma tricetona.\n\n" +
                        "\t\t\t\t\t\tA cetona mais conhecida é a propanona, mais conhecida como acetona sendo utilizada como solvente de esmaltes, graxas vernizes e resinas.\n\n" +
                        "\t\t\t\t\t\tEla pode ser encontrada na natureza em flores e frutos , em geral são liquidas e possuem um odor agradável.\n\n" +
                        "\t\t\t\t\t\tA nomenclatura se da mesma forma que a das outras funções mudando apenas sua terminação que no caso das cetonas é usada a terminação ona. A cadeia que possui a carbonila é a cadeia principal, ou cadeia mais longa, a numeração é feita a partir da extremidade mais próxima da carbonila.\n" +
                        "Exemplos de cetonas:\n" +
                        "[*cetona*]" +
                        "\t\t\t\t\t\tpentan-2-ona" +
                        "[*cetona_2*]" +
                        "\t\t\t\t\t\t4- metilpentan-2-ona"));

        conteudoOrganica.add(new ConteudoOffline(
                "\t\t\t\t\t\tO éter é todo composto orgânico onde a cadeia carbônica apresenta o Oxigênio entre dois carbonos.\n\n" +
                        "Grupo funcional dos éteres \n" +
                        "[*eter*]" +
                        "\t\t\t\t\t\tA nomenclatura desses compostos pode ser feitas de duas formas:\n\n" +
                        "1 – Grupo menor + oxi +nome do hidrocarboneto radical maior\n" +
                        "2 – radical + radical + éter (radicais em ordem alfabética)\n" +
                        "Exemplos:\n" +
                        "[*eter_2*]"));

        conteudoOrganica.add(new ConteudoOffline(
                "\t\t\t\t\t\tSão compostos orgânicos que possuem um radical carbônico no lugar do hidrogênio dos carboxílicos.\n\n" +
                        "\t\t\t\t\t\tO éster é obtido através da esterificação, ele não é solúvel em agua, mas sim em álcool, éter e clorofórmio.\n\n" +
                        "\t\t\t\t\t\tSua nomenclatura é feita a partir do hidrocarboneto correspondente terminado em ato.\n\n" +
                        "Hidrocarboneto + ato de (prefixo da ramificação)+ ila\n" +
                        "Exemplos:\n" +
                        "Etanoato de etila\n" +
                        "[*ester*] " +
                        " Metanoato de propila (ácido metanoico) - IUPAC" +
                        "[*ester_dois*]"));

        conteudoOrganica.add(new ConteudoOffline(
                "\t\t\t\t\t\tSão compostos orgânicos que apresentam uma ou mais carboxilas (COOH) ligadas a cadeia.\n\n" +
                        "\t\t\t\t\t\tA nomenclatura dos ácidos carboxílicos inicia com a palavra ácido seguida do hidrocarboneto correspondente com a terminação oico :\n\n" +
                        "\t\t\t\t\t\tA cadeia principal ou mais longa é a que possui a carbonila, sendo assim a numeração é feita a partir do primeiro carbono após a carbonila.\n\n" +
                        "Exemplos:\n" +
                        "Ácido 4-metil-pentanoico\n" +
                        "[*carbo*] " +
                        "Ácido metanoico – IUPAC \n" +
                        " [*carbo_2*] \n"));

        //Conteudo Ligações Químicas
        conteudoLigacoes.add(new ConteudoOffline(
                "\n\t\t\t\t\t\tOs átomos ligam entre si para formar moléculas, para que isso ocorra existem três tipos de ligação: a ligação iônica, a ligação covalente e a ligação metálica.\n\n" +
                      "\t\t\t\t\t\tPara ter auxílio nesses tipos de ligação existe a regra do octeto que estabelece que os átomos se unem uns aos outros na tentativa de completar a sua camada de valência, quando a última camada (camada de valência) apresentar 8 elétrons, indica que o átomo está estável. Para atingir esta estabilidade é necessário que os elementos ganhem ou percam elétrons nas ligações químicas.\n"));

        conteudoLigacoes.add(new ConteudoOffline("\t\t\t\t\t\tNas ligações iônicas os átomos devem apresentar como característica, a possibilidade de ganhar ou perder elétrons, desta forma, elas podem ocorrer entre: um metal e um ametal; um metal e o hidrogênio, possui também o padrão YX onde Y é o elemento metálico.\n\n" +
                "\t\t\t\t\t\tNa formula dos compostos iônicos a quantidade de elétrons cedidos é igual a quantidade de elétrons recebidos, os coeficientes da formula final deve ser o inverso dos índices de carga elétrica geralmente os elementos que corresponde a este tipo de ligação são os que pertence as famílias IA, IIA, IIIA com as famílias VA, VIA e VIIA da tabela.\n\n" +
                "\t\t\t\t\t\tPara construir a formula de uma substancia formada a partir de uma ligação iônica é necessário:\n\n" +
                "• Determinar a carga do cátion;\n" +
                "• Determinar a carga do ânion;\n" +
                "• Cruzar as cargas, de forma que a carga do cátion seja o índice atômico ânion ou vice-versa.\n\n" +
                "Exemplo: fórmula com os elementos sódio e cloro\n" +
                "• Sódio: é um metal, portanto possui a tendência de perder elétrons; possui um elétron na camada de valência, portanto sua carga é +1;\n\n" +
                "• Cloro: é um é um ametal, portanto possui a tendência de ganhar elétrons; possui sete elétrons na camada de valência, portanto sua carga é -1;\n" +
                "[*ionica*]"));

        conteudoLigacoes.add(new ConteudoOffline("\t\t\t\t\t\tNas ligações covalentes os átomos têm que possuir como característica a possibilidade de compartilhar elétrons e assim como a ligação iônica a ligação covalente também segue a regra do octeto.\n\n" +
                "\t\t\t\t\t\tGeralmente este tipo de ligação ocorre entre hidrogênio, ametais ou semimetais.\n" +
                "[*covalente*]" +
                "\t\t\t\t\t\tA tabela acima mostra a quantidade de ligações covalentes que os principais ametais e semimetais podem realizar.\n\n" +
                "\t\t\t\t\t\tOs elementos podem fazer até três ligações covalentes.\n" +
                "\n" +
                "Exemplo: hidrogênio e oxigênio\n" +
                "• Hidrogênio possui um elétron na camada valência, desta forma ele precisa de um elétron para ficar estável (o hidrogênio fica estável com dois elétrons na camada de valência)\n\n" +
                "• Oxigênio possui seis elétrons na camada valência, desta forma ele precisa de dois elétrons para ficar estável (o oxigênio fica estável com oito elétrons na camada de valência)\n\n" +
                " [*covalente_2*] " +
                "Exemplo de ligações covalentes com ligações simples, duplas e triplas: [*covalente_3*] "));

        conteudoLigacoes.add(new ConteudoOffline(
                "\n\t\t\t\t\t\tSão ligações feitas entre metais e metais, que formam as chamadas ligas metálicas.\n\n" +
                "\t\t\t\t\t\tPode-se dizer que o metal seria um aglomerado de átomos neutros e cátions, mergulhados numa nuvem ou \"mar\" de elétrons livres. Esta nuvem de elétrons funcionaria como a ligação metálica, que mantém os átomos unidos.\n\n" +
                "\t\t\t\t\t\tSão estas ligações e suas estruturas que os metais apresentam uma série de propriedades bem características, como por exemplo o brilho metálico, a condutividade elétrica, o alto ponto de fusão e ebulição, a maleabilidade, a ductilidade, a alta densidade e a resistência à tração.\n\n" +
                "Exemplos de ligas metálicas:\n" +
                "• Aço Comum: liga metálica muito resistente composta de ferro (Fe) e carbono (C), utilizada nas construções de pontes, geladeira, dentre outras.\n\n" +
                "• Aço Inoxidável: Composta de ferro (Fe), carbono (C), cromo (Cr) e níquel (Ni). É utilizada na construção de vagões de metrô, trens, fabricações de peças automotivas, utensílios cirúrgicos, dentre outras.\n\n" +
                "• Bronze: Liga metálica formada por cobre (Cu) e estanho (Sn), utilizada na construção de estátuas, fabricação de sinos, moedas, dentre outras.\n\n" +
                "• Latão: Constituída de cobre (Cu) e zinco (Zn), esse tipo de liga metálica é muito utilizada na fabricação de armas, torneiras, dentre outras.\n"));

    }


    public ConteudoOffline getConteudoOffiline(int pos, int opcao) {

        if (opcao == 1) {

            conteudoOffline = conteudoTabelaPeriodica;

        } else if(opcao == 2){

            conteudoOffline = conteudoLigacoes;
        }else if (opcao == 3) {

            conteudoOffline = conteudoOrganica;
        }

        return conteudoOffline.get(pos);
    }

}
