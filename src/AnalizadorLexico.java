import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnalizadorLexico {

    //A=(Q,Σ,δ,q0,F)
    private Set<String> estados;
    private Set<Character> alfabeto;
    private Set<Character> letras;
    private Set<Character> signos;
    private Set<Character> digitos;
    private Map<String, Map<Character, String>> transiciones;
    private String estadoInicial;
    private Set<String> estadosFinales;

    /*
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
        */
    public AnalizadorLexico() {

        this.estados = Set.of("q0", "q1", "q2", "q3", "q4", "q5", "q6", "q7", "q8", "q9", "q10",
                "q40", "q41", "q42", "q43", "q44", "q45", "q46", "q47", "q48", "q49", "q50", "q51", "q52", "q53");
        this.alfabeto = Set.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '-', '=', '!', '>', '.', ',', ';', '(',
                ')', '{', '}', '+', ' ', '/', '*', '\t', '\n', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        this.letras = Set.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z');
        this.signos = Set.of('-', '=', '!', '>', ',', ';', '(', ')', '{', '}', '+');
        this.digitos = Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        this.estadoInicial = "q0";
        this.estadosFinales = Set.of("q40", "q41", "q42", "q43", "q44", "q45", "q46", "q47", "q48", "q49", "q50", "q51", "q52", "q53");

        this.transiciones = new HashMap<>();
        Map<Character, String> q0map = new HashMap<>();
        q0map.put('/', "q1");
        for (Character ch : this.letras) {
            q0map.put(ch, "q4"); // cualquier letra pasa a q4
        }
        for (Character ch : this.digitos) {
            q0map.put(ch, "q5");
        }
        q0map.put('+', "q9");
        q0map.put('"', "q7");
        q0map.put('>', "q40");
        q0map.put('!', "q41");
        q0map.put('}', "q42");
        q0map.put('{', "q43");
        q0map.put('(', "q44");
        q0map.put(')', "q45");
        q0map.put(';', "q46");
        q0map.put(',', "q47");
        this.transiciones.put("q0", q0map);

        this.transiciones.put("q1", Map.of('*', "q2"));

        Map<Character, String> q2map = new HashMap<>();
        for (Character ch : this.alfabeto) {
            if (ch != '*') {
                q2map.put(ch, "q2"); // cualquier caracter menos * pasa a q3
            }
        }
        q2map.put('*', "q3");
        this.transiciones.put("q2", q2map);

        Map<Character, String> q3map = new HashMap<>();
        for (Character ch : this.alfabeto) {
            if (ch != '/') {
                q2map.put(ch, "q2"); // cualquier caracter menos / vuelve a q2
            }
        }
        q2map.put('/', "q53");
        this.transiciones.put("q3", q3map);

        Map<Character, String> q4map = new HashMap<>();
        for (Character ch : this.letras) {
            q4map.put(ch, "q4"); // cualquier letra pasa a q4
        }
        for (Character ch : this.digitos) {
            q4map.put(ch, "q4");
        }
        for (Character ch : this.alfabeto) {
            if (!q4map.containsKey(ch)) {
                q4map.put(ch, "q52"); // cualquier otro caracter pasa a q52
            }
        }
        this.transiciones.put("q4", q4map);

        Map<Character, String> q5map = new HashMap<>();
        for (Character ch : this.digitos) {
            q5map.put(ch, "q5");
        }
        q5map.put('.', "q6");
        for (Character ch : this.alfabeto) {
            if (!q5map.containsKey(ch)) {
                q5map.put(ch, "q50");
            }
        }
        this.transiciones.put("q5", q5map);

        Map<Character, String> q6map = new HashMap<>();
        for (Character ch : this.digitos) {
            q6map.put(ch, "q10");
        }
        this.transiciones.put("q6", q6map);

        Map<Character, String> q10map = new HashMap<>();
        for (Character ch : this.signos) {
            if (!q10map.containsKey(ch)) {
                q10map.put(ch, "q10");
            }
        }
        this.transiciones.put("q10", q10map);

        Map<Character, String> q51map = new HashMap<>();
        for (Character ch : this.alfabeto) {
            q51map.put(ch, "q51");
        }
        this.transiciones.put("q51", q51map);

        this.transiciones.put("q9", Map.of('=', "q9"));

        Map<Character, String> q7map = new HashMap<>();
        for (Character ch : this.alfabeto) {
            if (ch != '"') {
                q7map.put(ch, "q8");
            }
        }
        this.transiciones.put("q7", q7map);

        Map<Character, String> q8map = new HashMap<>();
        for (Character ch : this.alfabeto) {
            if (ch != '"') {
                q8map.put(ch, "q8");
            }
        }
        q8map.put('"', "q48");
        this.transiciones.put("q8", q8map);
    }


    public void procesarFichero(BufferedReader lector) throws Exception {
        String estadoActual = estadoInicial;
        int caracter, linea = 1;
        char charAscii;
        while ((caracter = lector.read()) != -1) {
            charAscii = (char) caracter;
            if (charAscii == '\n') {
                linea++;
            }
            if (!alfabeto.contains(charAscii)) {
                Main.errores.add(new Error(linea, "LEXICO", "Caracter no reconocido: " + charAscii));
                continue;
            }
            String siguienteEstado = null;
            Map<Character, String> mapa = transiciones.get(estadoActual);
            if (mapa != null && mapa.containsKey(charAscii)) {
                siguienteEstado = mapa.get(charAscii);
                accionesSemanticas(estadoActual, siguienteEstado);
                estadoActual = siguienteEstado;
            }
            else {
                Main.errores.add(new Error(linea, "LEXICO", "Caracter no reconocido: " + charAscii));
            }
        }
        Main.tokens.add(new Token("EOF", "-"));
        lector.close();
    }
    private void accionesSemanticas(String estadoAnterior, String estadoSiguiente) {
        if (estadoAnterior.equals("q0") && estadoSiguiente.equals("q1")) {

        }
    }
}