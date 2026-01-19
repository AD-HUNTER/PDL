import tslib.TS_Gestor;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static TS_Gestor gestor;
    public static List<Token> tokens = new ArrayList<>();
    public static List<Error> errores = new ArrayList<>();
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
                        escritor.append(token.getAtributo());
                    }
                    // Agregar delimitador despues del token y un salto de linea
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
    private static void agregarAtributos(){
        int res;
        res=gestor.createAtributo("numero de parametros", TS_Gestor.DescripcionAtributo.NUM_PARAM,
                TS_Gestor.TipoDatoAtributo.ENTERO);
        if(res!=0){
            System.out.println("Error al definir un atributo.");
            System.exit(1);
        }
        res=gestor.createAtributo("tipo de parametros", TS_Gestor.DescripcionAtributo.TIPO_PARAM,
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
        res=gestor.createAtributo("dimension", TS_Gestor.DescripcionAtributo.OTROS, TS_Gestor.TipoDatoAtributo.ENTERO);
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
    public static void main(String[] args) throws FileNotFoundException {
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
        gestor = new TS_Gestor("Tabla de Simbolos.txt");
        //gestor.activarDebug();
        agregarAtributos();
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
        AnalizadorSintactico parser = new AnalizadorSintactico("C:/Users/asack/Documents/Grado/PDL/Proyecto/run/parse.txt");
        // Llamamos al procesador con el fichero de entrada y tratamos errores.
        try {
            lexico.procesarFichero(fichEntrada);
            escribeFichTokens(tokens);
            for (Token token : tokens) {
                parser.accion(token);
            }
            parser.end(); // Cerrar los ficheros parse.txt y pasos.txt
            escribeFichErrores(errores);
            java.io.PrintStream outOriginal = System.out;
            try (java.io.PrintStream outFile = new java.io.PrintStream(new java.io.FileOutputStream("TablaDeSimbolos.txt", true), true, java.nio.charset.StandardCharsets.UTF_8)) {
                System.setOut(outFile);
                gestor.show(TS_Gestor.Tabla.GLOBAL);
            } catch (java.io.IOException e) {
                e.printStackTrace(outOriginal);
            } finally {
                System.setOut(outOriginal);
            }
            // gestor.write(TS_Gestor.Tabla.GLOBAL);
            // gestor.destroy(TS_Gestor.Tabla.GLOBAL);
            System.out.println("El fichero fue procesado correctamente.");
            System.exit(0);
        } catch (Exception e) {
            System.err.println("Ha ocurrido un error, comprueba el fichero 'Errores'.");
            System.exit(3);
        }
    }
}
