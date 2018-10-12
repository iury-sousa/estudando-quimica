package projetotcc.estudandoquimica.view.offline;

import java.util.ArrayList;
import java.util.List;

import projetotcc.estudandoquimica.model.ConteudoOffline;

public class ListaArquivoOffline {

    private static List<ConteudoOffline> conteudoOffline;

    public ListaArquivoOffline() {

        conteudoOffline = new ArrayList<>();

        conteudoOffline.add(0, new ConteudoOffline(1, "\t\t\t\t\t\tA química orgânica é uma subdivisão da química que estuda os compostos carbônicos e compostos orgânicos.\n\n" +
                "\t\t\t\t\t\tNo início [*link*] dos estudos da química orgânica acreditava-se que os compostos orgânicos não poderiam ser produzidos em laboratório, sendo somente produzidos por organismos vivos, entretanto em 1828, o químico alemão Friedrich Wohler (1800-1882) sintetizou a ureia em laboratório a partir de um composto inorgânicos, o cianeto de amônio desta forma ele demostrou que compostos orgânicos nem sempre são originários de organismos vivos.  \n" +
                "\t\t\t\t\t\tO elemento [*link*] carbono é tetravalente, desta forma podendo realizar quatro ligações de diversas formas com diversos elementos.\n\n" +
                "\t\t\t\t\t\tOs compostos podem ser agrupados em diferentes funções que apresentam propriedades químicas semelhantes, tais como hidrocarbonetos, álcoois, cetonas, aldeídos, ésteres, éteres, ácidos carboxílicos, aminas, amidas, entre outros.     \n"));
    }

    public ConteudoOffline getConteudoOffiline(int pos){

        return conteudoOffline.get(pos);
    }

}
