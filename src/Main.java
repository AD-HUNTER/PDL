import tslib.TS_Gestor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static TS_Gestor gestor;
    public static List<Token> tokens = new ArrayList<>();
    public static List<Error> errores = new ArrayList<>();
    public static boolean zonaFuncion=false;

    private static void escribeFichTokens(List <Token> tokens) {
        // Escribe los tokens en el archivo de salida con el formato.
        try {
            FileWriter escritor = new FileWriter("Tokens.txt");
            if(tokens.isEmpty()) {
                escritor.append("No se han creado tokens.");
                escritor.close();
            }
            else {
                for (Token token : tokens) {
                    escritor.append(" < " + token.getCodigo() + " , ");
                    // Escribir el atributo si existe
                    if (token.getAtributo() != null) {
                        //escritor.append("[");
                        escritor.append(token.getAtributo());
                        //escritor.append("]");
                    }
                    // Agregar delimitador después del token y un salto de línea
                    escritor.append(" > \n");
                }
                escritor.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void escribeFichErrores(List <Error> errores) {
        // Escribe los errores en el archivo de salida.
        try {
            FileWriter escritor = new FileWriter("Errores.txt");
            int numErr = 0;
            if(errores.isEmpty()) { // no hay errores
                escritor.append("No se han encontrado errores.");
                escritor.close();
            }
            else { // hay errores
                for (Error error : errores) {
                    numErr++;
                    escritor.append(error.toString());
                }
                escritor.append("Numero de errores: " + numErr +".\n");
                escritor.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  static void añadirAtributos(){
        int res;
        res=gestor.createAtributo("desp", TS_Gestor.DescripcionAtributo.DIR, TS_Gestor.TipoDatoAtributo.ENTERO);
        if(res!=0){
            System.out.println("Error al definir un atributo.");
            System.exit(1);
        }
        res=gestor.createAtributo("número de parámetros", TS_Gestor.DescripcionAtributo.NUM_PARAM,
                TS_Gestor.TipoDatoAtributo.ENTERO);
        if(res!=0){
            System.out.println("Error al definir un atributo.");
            System.exit(1);
        }
        res=gestor.createAtributo("tipo de parámetros", TS_Gestor.DescripcionAtributo.TIPO_PARAM,
                TS_Gestor.TipoDatoAtributo.LISTA);
        if(res!=0){
            System.out.println("Error al definir un atributo.");
            System.exit(1);
        }
        res=gestor.createAtributo("modo de parámetros", TS_Gestor.DescripcionAtributo.MODO_PARAM,
                TS_Gestor.TipoDatoAtributo.LISTA);
        if(res!=0){
            System.out.println("Error al definir un atributo.");
            System.exit(1);
        }
        res=gestor.createAtributo("tipo de retorno", TS_Gestor.DescripcionAtributo.TIPO_RET,
                TS_Gestor.TipoDatoAtributo.CADENA);
        if(res!=0){
            System.out.println("Error al definir un atributo.");
            System.exit(1);
        }
        res=gestor.createAtributo("etiqueta", TS_Gestor.DescripcionAtributo.ETIQUETA, TS_Gestor.TipoDatoAtributo.CADENA);
        if(res!=0){
            System.out.println("Error al definir un atributo.");
            System.exit(1);
        }
        res=gestor.createAtributo("parámetro", TS_Gestor.DescripcionAtributo.PARAM, TS_Gestor.TipoDatoAtributo.ENTERO);
        if(res!=0){
            System.out.println("Error al definir un atributo.");
            System.exit(1);
        }
        res=gestor.createAtributo("dimensión", TS_Gestor.DescripcionAtributo.OTROS, TS_Gestor.TipoDatoAtributo.ENTERO);
        if(res!=0){
            System.out.println("Error al definir un atributo.");
            System.exit(1);
        }
        res=gestor.createAtributo("elem", TS_Gestor.DescripcionAtributo.OTROS, TS_Gestor.TipoDatoAtributo.CADENA);
        if(res!=0){
            System.out.println("Error al definir un atributo.");
            System.exit(1);
        }
    }

    public static void main(String[] args) {

        String fichEntrada = null; // Fichero de entrada.
        //Comprobamos que se ha pasado un fichero valido
        if(args.length == 1) {
            fichEntrada = args[0]; // Nombre del archivo de entrada.
        }
        else {
            System.err.println("Error: Se debe proporcionar un fichero de codigo a procesar.");
            System.exit(1);
        }
        // Comprobamos la extension del fichero
        if(!fichEntrada.endsWith(".txt") && !fichEntrada.endsWith(".javascript")) {
            System.err.println("Error: El archivo proporcionado no es valido. Debe ser un archivo .txt o .javascript.");
            System.exit(2);
        }
        java.io.File archivo = new java.io.File(fichEntrada);
        if (!archivo.exists()) {
            System.err.println("Error: El archivo '" + fichEntrada + "' no se encuentra.");
            System.err.println("Ruta absoluta buscada: " + archivo.getAbsolutePath());
            System.err.println("Directorio de trabajo actual: " + System.getProperty("user.dir"));
            System.exit(4);
        }
        if (!archivo.canRead()) {
            System.err.println("Error: No se puede leer el archivo '" + fichEntrada + "'.");
            System.exit(5);
        }
        // Creamos un nuevo procesador de lenguaje
        AnalizadorLexico lexico = new AnalizadorLexico();
        //AnalizadorSintactico parser = new AnalizadorSintactico();
        //AnalizadorSemantico semantico = new AnalizadorSemantico();
        gestor = new TS_Gestor("Tabla de Simbolos.txt");
        gestor.activarDebug();
        añadirAtributos();
        gestor.createTPalabrasReservadas();
        gestor.createTSGlobal();
        gestor.addEntradaTPalabrasReservadas("boolean");
        gestor.addEntradaTPalabrasReservadas("float");
        gestor.addEntradaTPalabrasReservadas("for");
        gestor.addEntradaTPalabrasReservadas("function");
        gestor.addEntradaTPalabrasReservadas("if");
        gestor.addEntradaTPalabrasReservadas("int");
        gestor.addEntradaTPalabrasReservadas("return");
        gestor.addEntradaTPalabrasReservadas("string");
        gestor.addEntradaTPalabrasReservadas("void");
        gestor.addEntradaTPalabrasReservadas("let");
        gestor.addEntradaTPalabrasReservadas("read");
        gestor.addEntradaTPalabrasReservadas("write");

        // Llamamos al procesador con el fichero de entrada y tratamos errores.
        try {

            lexico.procesarFichero(fichEntrada);
            escribeFichTokens(tokens);
            escribeFichErrores(errores);
            gestor.write(TS_Gestor.Tabla.GLOBAL);
            System.out.println("El fichero fue procesado correctamente.");
            System.exit(0);
        } catch (Exception e) {
            System.err.println("Ha ocurrido un error, comprueba el fichero 'Errores'.");
            System.exit(3);
        }
    }
}