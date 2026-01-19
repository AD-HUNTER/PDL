import tslib.TS_Gestor;

import javax.swing.plaf.DesktopIconUI;
import java.io.BufferedReader;
import java.io.PushbackReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.FileReader;

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
    private StringBuilder lexema;
    private Integer valor;
    private int ndecimales;
    private int numParam;
    private double valorFloat;
    private boolean zonaFuncion;
    private boolean zonaDec;
    private boolean faltanParametros;
    private int posTSG_de_func;
    private boolean esUltimoParam;

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
        this.lexema = new StringBuilder();
        this.valor = null;
        this.ndecimales = 0;
        this.valorFloat = 0.0;
        this.estados = Set.of("q0", "q1", "q2", "q3", "q4", "q5", "q6", "q7", "q8", "q9", "q10",
                "q40", "q41", "q42", "q43", "q44", "q45", "q46", "q47", "q48", "q49", "q50", "q51", "q52", "q53","q54");
        this.alfabeto = Set.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '-','_', '=', '!', '>', '.', ',', ';', '(',
                ')', '{', '}', '+', ' ', '/', '*', '"', '\t', '\n', '\r', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        this.letras = Set.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z');
        this.signos = Set.of('-', '=', '!', '>', ',', ';', '(', ')', '{', '}', '+','"');
        this.digitos = Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        this.estadoInicial = "q0";
        this.estadosFinales = Set.of("q40", "q41", "q42", "q43", "q44", "q45", "q46", "q47", "q48", "q49", "q50", "q51", "q52", "q53","q54");
        this.zonaFuncion = false;
        this.zonaDec = false;
        this.faltanParametros = false;
        this.numParam = 0;
        this.transiciones = new HashMap<>();
        this.esUltimoParam = false;
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
        q0map.put(' ', "q0");
        q0map.put('\t', "q0");
        q0map.put('\n', "q0");
        q0map.put('\r', "q0");
        q0map.put('_', "q4");
        q0map.put('=', "q54");
        q0map.put('-', "q53");
        this.transiciones.put("q0", q0map);

        this.transiciones.put("q1", Map.of('*', "q2"));

        Map<Character, String> q2map = new HashMap<>();
        for (Character ch : this.alfabeto) {
            if (ch != '*') {
                q2map.put(ch, "q2"); // cualquier caracter menos * pasa a q2
            }
        }
        q2map.put('*', "q3");
        this.transiciones.put("q2", q2map);

        Map<Character, String> q3map = new HashMap<>();
        for (Character ch : this.alfabeto) {
            if (ch != '/' && ch != '*') {
                q3map.put(ch, "q2"); // cualquier caracter menos / vuelve a q2
            }
        }
        q3map.put('/', "q0");
        q3map.put('*', "q3");
        this.transiciones.put("q3", q3map);

        Map<Character, String> q4map = new HashMap<>();
        for (Character ch : this.letras) {
            q4map.put(ch, "q4"); // cualquier letra pasa a q4
        }
        for (Character ch : this.digitos) {
            q4map.put(ch, "q4");
        }
        q4map.put('_', "q4");
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
        for (Character ch : this.digitos) {
                q10map.put(ch, "q10");
        }
        // si llega cualquier otro caracter, pasar a q51 (fin del real)
        for (Character ch : this.alfabeto) {
            if (!q10map.containsKey(ch)) {
                q10map.put(ch, "q51");
            }
        }
        this.transiciones.put("q10", q10map);

        this.transiciones.put("q9", Map.of('=', "q49"));

        Map<Character, String> q7map = new HashMap<>();
        Map<Character, String> q8map = new HashMap<>();
        for (int i = 32; i <= 126; i++) { // rango ASCII imprimible
            char ch = (char) i;
            if (ch != '"' && ch != '\n' && ch != '\r') {
                q7map.put(ch, "q8");
                q8map.put(ch, "q8");
            }
        }
        q8map.put('"', "q48");
        this.transiciones.put("q7", q7map);
        this.transiciones.put("q8", q8map);
    }

    public void procesarFichero(String FichEntrada) throws Exception {
        PushbackReader lector = new PushbackReader(new BufferedReader(new FileReader(FichEntrada)));
        String estadoActual = estadoInicial;
        String siguienteEstado = null;
        int caracter;
        int linea = 1;
        char charAscii;
        // Estados finales que requieren devolver el caracter al flujo
        Set<String> estadosConPushback = Set.of("q50", "q51", "q52");
        while ((caracter = lector.read()) != -1) {
            charAscii = (char) caracter;
            if (charAscii == '\n') {
                linea++;
            }
            if (!alfabeto.contains(charAscii)) {
                Main.errores.add(new Error(linea, "LEXICO", "Caracter no reconocido: " + charAscii));
                estadoActual = estadoInicial;
                siguienteEstado = null;
                lexema.setLength(0);
                valor = null;
                valorFloat = 0.0;
                ndecimales = 0;
                continue;
            }
            Map<Character, String> mapa = transiciones.get(estadoActual);
            if (mapa == null || !mapa.containsKey(charAscii)) {
                Main.errores.add(new Error(linea, "LEXICO", "Caracter no reconocido: " + charAscii));
                estadoActual = estadoInicial;
                siguienteEstado = null;
                lexema.setLength(0);
                valor = null;
                valorFloat = 0.0;
                ndecimales = 0;
                continue;
            }
            siguienteEstado = mapa.get(charAscii);
            accionesSemanticas(estadoActual, siguienteEstado, charAscii, linea);
            estadoActual = siguienteEstado;
            if (estadosFinales.contains(estadoActual)) {
                // Si el estado final requiere pushback, devolver el caracter al flujo
                if (estadosConPushback.contains(estadoActual)) {
                    lector.unread(caracter);
                    // Si era un salto de linea, decrementar el contador
                    if (charAscii == '\n') {
                        linea--;
                    }
                }
                estadoActual = estadoInicial;
            }
        }
        if (caracter == -1){
        Main.tokens.add(new Token("EOF", "-"));
        lector.close();
        }
    }
    private void accionesSemanticas(String estadoAnterior, String estadoSiguiente, char c, int linea) {
        String transicion = estadoAnterior + "->" + estadoSiguiente;
        switch (transicion) {
            case "q0->q40":
                Main.tokens.add(new Token("Mayor", "-"));
                break;
            case "q0->q41":
                Main.tokens.add(new Token("Not", "-"));
                break;
            case "q0->q42":
                Main.tokens.add(new Token("CierraCorch", "-"));
                zonaFuncion = false;
                break;
            case "q0->q43":
                Main.tokens.add(new Token("AbreCorch", "-"));
                faltanParametros = false;
                break;
            case "q0->q44":
                Main.tokens.add(new Token("AbrePar", "-"));
                break;
            case "q0->q45":
                Main.tokens.add(new Token("CierraPar", "-"));
                if (zonaFuncion && numParam != 0) {
                    esUltimoParam = true;
                }
                break;
            case "q0->q46":
                Main.tokens.add(new Token("Pcoma", "-"));
                break;
            case "q0->q47":
                Main.tokens.add(new Token("Coma", "-"));
                break;
            case "q0->q53":
                Main.tokens.add(new Token("Menos", "-"));
                break;
            case "q0->q54":
                Main.tokens.add(new Token("Igual", "-"));
            case "q0->q7":
                lexema.setLength(0);
                break;
            case "q7->q8":
                lexema.append(c);
                break;
            case "q8->q8":
                lexema.append(c);
                break;
            case "q8->q48":
                if (lexema.length() < 64){
                    Main.tokens.add(new Token("Cad", lexema.toString()));
                    lexema.setLength(0);
                } else {
                    Main.errores.add(new Error(linea, "LEXICO", "Cadena de caracteres muy larga."));
                }
                break;
            case "q9->q49":
                Main.tokens.add(new Token("Inc", "-"));
                break;
            case "q0->q5":
                valor = Character.getNumericValue(c);
                break;
            case "q5->q5":
                valor = valor*10 + Character.getNumericValue(c);
                break;
            case "q5->q50":
                if (valor <= 32767) {
                    Main.tokens.add(new Token("Entero", valor.toString()));

                }else{
                    Main.errores.add(new Error(linea, "LEXICO", "El valor entero excede el limite permitido."));
                }
                valor = null;
                break;
            case "q6->q10":
                ndecimales = 1;
                valorFloat = (double) valor + Character.getNumericValue(c) * Math.pow(10, -ndecimales);
                ndecimales++;
                break;
            case "q10->q10":
                valorFloat = valorFloat + (double) Character.getNumericValue(c) * Math.pow(10, -ndecimales);
                ndecimales++;
                break;
            case "q10->q51":
                if (valorFloat <= 117549436.0) {
                    Main.tokens.add(new Token("Real", Double.toString(valorFloat)));
                }
                else{
                    Main.errores.add(new Error(linea, "LEXICO", "El valor decimal excede el limite permitido."));
                }
                valorFloat = 0.0;
                valor = null;
                ndecimales = 0;
                break;
            case "q0->q4":
                lexema.setLength(0);
                lexema.append(c);
                break;
            case "q4->q4":
                lexema.append(c);
                break;
            case "q4->q52":
                String lex = lexema.toString();
                if (Main.gestor.getEntradaTPalabrasReservadas(lex) != 0) {
                    // Es palabra reservada
                    switch (lex) {
                        case "boolean" -> Main.tokens.add(new Token("PalRes", "1"));
                        case "for" -> Main.tokens.add(new Token("PalRes", "3"));
                        case "function" -> {
                            Main.tokens.add(new Token("PalRes", "4"));
                            zonaFuncion = true;
                        }
                        case "if" -> Main.tokens.add(new Token("PalRes", "5"));
                        case "let" -> {
                            Main.tokens.add(new Token("PalRes", "7"));
                            zonaDec = true;
                        }
                        case "read" -> Main.tokens.add(new Token("PalRes", "8"));
                        case "write" -> Main.tokens.add(new Token("PalRes", "12"));
                        case "void" -> Main.tokens.add(new Token("PalRes", "11"));
                        case "string" -> Main.tokens.add(new Token("PalRes", "10"));
                        case "return" -> Main.tokens.add(new Token("PalRes", "9"));
                        case "int" -> Main.tokens.add(new Token("PalRes", "6"));
                        case "float" -> Main.tokens.add(new Token("PalRes", "2"));
                    }
                } else {
                    // Es un identificador
                    int posTSG = Main.gestor.getEntradaTSGlobal(lex);

                    if (zonaFuncion && faltanParametros) {
                        // Estamos declarando parámetros de una función
                        Token ultimoToken = Main.tokens.getLast();
                        if (numParam == 0) {
                            // Primer parámetro - crear tabla local
                            posTSG_de_func = Integer.parseInt(Main.tokens.get(Main.tokens.size() - 3).getAtributo());
                            Main.gestor.createTSLocal();
                        }
                        int posTSL = Main.gestor.addEntradaTSLocal(lex);
                        String tipo = "";
                        switch (ultimoToken.getAtributo()) {
                            case "10": tipo = "string"; break;
                            case "6": tipo = "int"; break;
                            case "2": tipo = "float"; break;
                            case "1": tipo = "boolean"; break;
                        }
                        Main.gestor.setTipo(posTSL, tipo);
                        Main.tokens.add(new Token("Id", String.valueOf(posTSL)));
                        numParam++;

                    } else if (zonaFuncion && !faltanParametros) {
                        // Estamos en la declaración de la función (justo después de function tipo)
                        Token ultimoToken = Main.tokens.getLast();
                        if (ultimoToken.getAtributo().equals("11") || ultimoToken.getAtributo().equals("10") ||
                                ultimoToken.getAtributo().equals("6") || ultimoToken.getAtributo().equals("2") ||
                                ultimoToken.getAtributo().equals("1")) {
                            // Es el nombre de la función
                            if (posTSG != 0) {
                                Main.errores.add(new Error(linea, "SEMANTICO", "Error de declaracion: La funcion '" + lex + "' ya existe en la tabla global."));
                            } else {
                                int pos = Main.gestor.addEntradaTSGlobal(lex);  // ← GLOBAL, no LOCAL
                                Main.tokens.add(new Token("Id", String.valueOf(pos)));
                                String tipoDev = "";
                                switch (ultimoToken.getAtributo()) {
                                    case "11": tipoDev = "void"; break;
                                    case "10": tipoDev = "string"; faltanParametros = true; break;
                                    case "6": tipoDev = "int"; faltanParametros = true; break;
                                    case "2": tipoDev = "float"; faltanParametros = true; break;
                                    case "1": tipoDev = "boolean"; faltanParametros = true; break;
                                }
                                Main.gestor.setTipo(pos, "Funcion");
                                Main.gestor.setValorAtributoCad(pos, "tipo de retorno", tipoDev);
                                Main.gestor.setValorAtributoCad(pos, "etiqueta", "func_" + lex);
                                faltanParametros = true;  // Esperamos parámetros
                            }
                        } else {
                            // Es un ID usado dentro del cuerpo de la función
                            int pos = Main.gestor.getEntradaTSGlobal(lex);
                            if (pos == 0) {
                                Main.errores.add(new Error(linea, "SEMANTICO", "Variable '" + lex + "' no declarada."));
                                pos = Main.gestor.addEntradaTSGlobal(lex); // añadir para evitar más errores
                            }
                            Main.tokens.add(new Token("Id", String.valueOf(pos)));
                        }

                    } else if (zonaDec) {
                        // Declaración de variable global con let
                        Token ultimoToken = Main.tokens.getLast();
                        if (ultimoToken.getAtributo().equals("10") || ultimoToken.getAtributo().equals("6")
                                || ultimoToken.getAtributo().equals("2") || ultimoToken.getAtributo().equals("1")) {
                            if (posTSG != 0) {
                                Main.errores.add(new Error(linea, "SEMANTICO", "Error de declaracion: La variable '" + lex + "' ya existe en la tabla global."));
                            } else {
                                int pos = Main.gestor.addEntradaTSGlobal(lex);  // ← GLOBAL
                                Main.tokens.add(new Token("Id", String.valueOf(pos)));
                                String tipo = "";
                                switch (ultimoToken.getAtributo()) {
                                    case "10": tipo = "string"; break;
                                    case "6": tipo = "int"; break;
                                    case "2": tipo = "float"; break;
                                    case "1": tipo = "boolean"; break;
                                }
                                Main.gestor.setTipo(pos, tipo);
                            }
                            zonaDec = false;
                        } else {
                            // Uso de variable después de let tipo var; (ej: a = 5;)
                            int pos = Main.gestor.getEntradaTSGlobal(lex);
                            if (pos == 0) {
                                Main.errores.add(new Error(linea, "SEMANTICO", "Variable '" + lex + "' no declarada."));
                                pos = Main.gestor.addEntradaTSGlobal(lex);
                            }
                            Main.tokens.add(new Token("Id", String.valueOf(pos)));
                        }

                    } else {
                        // Uso normal de un ID (fuera de declaraciones)
                        int pos = Main.gestor.getEntradaTSGlobal(lex);
                        if (pos == 0) {
                            Main.errores.add(new Error(linea, "SEMANTICO", "Variable '" + lex + "' no declarada."));
                            pos = Main.gestor.addEntradaTSGlobal(lex);
                        }
                        Main.tokens.add(new Token("Id", String.valueOf(pos)));
                    }
                }
                lexema.setLength(0);
                break;

            default:
                break;
        }
    }
}