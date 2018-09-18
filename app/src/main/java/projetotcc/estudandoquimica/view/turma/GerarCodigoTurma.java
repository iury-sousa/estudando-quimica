package projetotcc.estudandoquimica.view.turma;

public abstract class GerarCodigoTurma {


    private static final String[] carct ={"0","1","2","3","4","5","6","7","8","9",
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u",
            "v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P",
            "Q","R","S","T","U","V","W","X","Y","Z"};


    public static String gerar(int tamanho){

        StringBuilder codigo = new StringBuilder();
        for (int x = 0; x < tamanho; x++) {
            int caracter = (int) (Math.random() * carct.length);

            codigo.append(carct[caracter]);
        }

        return "@" + codigo.toString();
    }

}
