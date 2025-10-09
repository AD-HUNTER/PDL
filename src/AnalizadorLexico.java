import java.util.Arrays;

public class AnalizadorLexico {

    private boolean esLetra (char caracter) {
        char letras[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z'};
        return (Arrays.binarySearch(letras, caracter) >= 0)? true : false;
    }
    private boolean esSigno (char caracter) {
        char signos[] = {'-', '=', '!', '>', ',', ';', '(', ')', '{', '}', '+'};
        return (Arrays.binarySearch(signos, caracter) >= 0)? true : false;
    }
    private boolean esNumero (char caracter) {
        return caracter >= '0' && caracter <= '9';
    }

    public AnalizadorLexico() {}

    public Token procesarFichero(int caracter) {

        return null;
    }

}