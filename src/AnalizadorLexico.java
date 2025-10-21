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

    private boolean esDelimitador(char caracter) {
        return (caracter == ' ' || caracter == '\t' || caracter == '\n');
    }

    public AnalizadorLexico() {}

    public Token procesarFichero(int caracter) {
        char c = (char) caracter;
        if (esDelimitador(c)) {
            return null;
        }
        switch (c) {
            default:
                String error="Caracter no reconocido: " + (char)caracter;
                throw new IllegalArgumentException(error);
            case '>':
                return new Token("Mayor", "-");
            case '!':
                return new Token("Not", "-");
            case '}':
                return new Token("CierraCorch", "-");
            case '{':
                return new Token("AbreCorch", "-");
            case '(':
                return new Token("AbrePar", "-");
            case ')':
                return new Token("CierraPar", "-");
            case ',':
                return new Token("Coma", "-");
            case ';':
                return new Token("Pcoma", "-");
            case '+':
                return null;
            case '-':
        }
    }

}