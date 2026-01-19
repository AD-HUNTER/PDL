import java.util.Stack;
import java.io.*;
import java.util.*;

public class AnalizadorSintactico {

    public enum EstadosSintactico{
        I0,I1,I2,I3,I4,I5,I6,I7,I8,I9,
        I10,I11,I12,I13,I14,I15,I16,I17,I18,I19,
        I20,I21,I22,I23,I24,I25,I26,I27,I28,I29,
        I30,I31,I32,I33,I34,I35,I36,I37,I38,I39,
        I40,I41,I42,I43,I44,I45,I46,I47,I48,I49,
        I50,I51,I52,I53,I54,I55,I56,I57,I58,I59,
        I60,I61,I62,I63,I64,I65,I66,I67,I68,I69,
        I70,I71,I72,I73,I74,I75,I76,I77,I78,I79,
        I80,I81,I82,I83,I84,I85,I86,I87,I88,I89,
        I90,I91,I92,I93,I94,I95,I96,I97,I98,I99,
        I100,I101,I102,I103,I104,I105,I106,I107,I108,I109,
        I110,I111,I112,I113,I114,I115,I116,I117,I118,I119,
        I120,I121,I122,I123,I124,I125,I126,I127,I128,I129,
        I130,I131,I132,I133,I134,I135,I136,I137,I138,I139,
        I140,I141,I142,I143,I144,I145,I146,I147,I148,I149,
        I150,I151,I152,I153,I154,I155,I156,I157,I158,I159,
        I160,I161,I162,I163,I164,I165,I166,I167,I168,I169,
        I170,I171,I172,I173,I174,I175,I176,I177,I178,I179,
        I180,I181,I182,I183,I184,I185,I186,I187,I188,I189,
        I190,I191,I192,I193,I194,I195,I196,I197;
    }

    public enum Simbolos{
        P,B,G1,G2,M,T,S,L,Q,X,E,E1,R,U,U1,V,F,F1,F2,F3,A,K,C;
    }

    public enum Reglas{
        R1,R2,R3,R4,R5,R6,R7,R8,R9,
        R10,R11,R12,R13,R14,R15,R16,
        R17,R18,R19,R20,R21,R22,R23,
        R24,R25,R26,R27,R28,R29,R30,
        R31,R32,R33,R34,R35,R36,R37,
        R38,R39,R40,R41,R42,R43,R44,
        R45,R46,R47,R48,R49,R50,R51,
        R52,R53,R54;
    }
    // TODO
    private Stack<Object> pila;
    protected Token token_Recibido;
    private EstadosSintactico estadoActual;
    private String[] simb;
    protected boolean aceptado;
    protected boolean error;
    protected PrintWriter parser;
    protected PrintWriter pasos;
    private List<String> l;

    //	private Pair p1;
    //	private Pair p2;
// TODO
    public AnalizadorSintactico(String path_salida_sintactico) throws FileNotFoundException {
        //this.token_Recibido = token;
        this.estadoActual = EstadosSintactico.I0;
        this.pila = new Stack<Object>();
        this.pila.push(estadoActual);//Inicializas la pila y la pones el estado inicial, el cero
        this.simb = new String[51];
        this.simb = llenarSimbolos();
        this.aceptado = false;
        this.error = false;
        this.parser = new PrintWriter(path_salida_sintactico);
        parser.write("Ascendente ");
        this.pasos = new PrintWriter("C:/Users/asack/Documents/Grado/PDL/Proyecto/run/pasos.txt");
        this.l= new ArrayList<String>();
        l.add("I0");
    }
    public String[] llenarSimbolos() {//Falta P' , no creo que lo ncesite
        String[] simb = new String[51];
        simb[0] = "boolean";
        simb[1] = "float";
        simb[2] = "for";
        simb[3] = "function";
        simb[4] = "if";
        simb[5] = "int";
        simb[6] = "let";
        simb[7] = "read";
        simb[8] = "return";
        simb[9] = "string";
        simb[10] = "void";
        simb[11] = "write";
        simb[12] = "real";
        simb[13] = "entero";
        simb[14] = "cadena";
        simb[15] = "id";
        simb[16] = "+=";
        simb[17] = "=";
        simb[18] = ",";
        simb[19] = ";";
        simb[20] = "(";
        simb[21] = ")";
        simb[22] = "{";
        simb[23] = "}";
        simb[24] = "-";
        simb[25] = "!";
        simb[26] = ">";
        simb[27] = "$";
        simb[28] = "P";
        simb[29] = "F";
        simb[30] = "F1";
        simb[31] = "F2";
        simb[32] = "F3";
        simb[33] = "U1";
        simb[34] = "A";
        simb[35] = "K";
        simb[36] = "C";
        simb[37] = "T";
        simb[38] = "B";
        simb[39] = "S";
        simb[40] = "L";
        simb[41] = "Q";
        simb[42] = "X";
        simb[43] = "E";
        simb[44] = "R";
        simb[45] = "U";
        simb[46] = "V";
        simb[47] = "M";
        simb[48] = "G1";
        simb[49] = "G2";
        simb[50] = "E1";
        return simb;
    }
    public int posTokenenString(Token t) {
        String codigo = t.getCodigo();
        String atributo;
        if(codigo.equals("Real"))
            return 12;
        else if(codigo.equals("Entero"))
            return 13;
        else if(codigo.equals("Cad"))
            return 14;
        else if(codigo.equals("Id"))
            return 15;
        else if(codigo.equals("Inc"))
            return 16;
        else if(codigo.equals("Igual"))
            return 17;
        else if(codigo.equals("Coma"))
            return 18;
        else if(codigo.equals("Pcoma"))
            return 19;
        else if(codigo.equals("AbrePar"))
            return 20;
        else if(codigo.equals("CierraPar"))
            return 21;
        else if(codigo.equals("AbreCorch"))
            return 22;
        else if(codigo.equals("CierraCorch"))
            return 23;
        else if(codigo.equals("Menos"))
            return 24;
        else if(codigo.equals("Not"))
            return 25;
        else if(codigo.equals("Mayor"))
            return 26;
        else if (codigo.equals("EOF"))
            return 27;
        else if(codigo.equals("PalRes")) {
            atributo=t.getAtributo();
            return Integer.parseInt(atributo)-1;
        }
        return -1;
    }
    public boolean desplazamiento(String s, EstadosSintactico e) {
        pasos.write("desplazamiento "+ s + " al estado "+ e.toString()+"\n");
        pasos.write("\n");
        pasos.write("\n");
        pila.push(s);
        pila.push(e);
        l.add(s);
        l.add(e.toString());
        return true;
    }
    public void reduccion(String s, Reglas r) {
        pasos.write("reduccion " + r.toString()+ "\n");
        pasos.write("\n");
        pasos.write("\n");
        int j = simbolosporregla(r);
        EstadosSintactico e;
        String antecedente = "";
        EstadosSintactico f;
        String regla = numRegla(r) + " ";
        parser.write(regla);

        for(int i = 0; i < j; i++) {
            pila.pop();//estado
            pila.pop();//simbolo
            l.remove(l.size()-1);
            l.remove(l.size()-1);
        }
        antecedente = antecedente(r);
        e = (EstadosSintactico) pila.peek();
        f = tablaGoto(antecedente, e);// estado con el que hacer la desplazamiento
        desplazamiento(antecedente, f);
        //Consigo el antecedente
        //peek de lo que queda encima en la pila, que es el estado al que se debe ir
        //tablagoto(antecedente,peek)
    }
    // TODO
    public void error() {
        System.out.println("Error");
        error = true;
    }
    //Devuelve el antecedente de R
    public String antecedente(Reglas r) {
        String s = "";
        switch(r) {
            case R1:
                s = "P";
                break;
            case R2:
                s = "P";
                break;
            case R3:
                s = "P";
                break;
            case R4:
                s = "B";
                break;
            case R5:
                s = "B";
                break;
            case R6:
                s = "B";
                break;
            case R7:
                s = "B";
                break;
            case R8:
                s = "G1";
                break;
            case R9:
                s = "G1";
                break;
            case R10:
                s = "G2";
                break;
            case R11:
                s = "G2";
                break;
            case R12:
                s = "G2";
                break;
            case R13:
                s = "M";
                break;
            case R14:
                s = "T";
                break;
            case R15:
                s = "T";
                break;
            case R16:
                s = "T";
                break;
            case R17:
                s = "T";
                break;
            case R18:
                s = "S";
                break;
            case R19:
                s = "S";
                break;
            case R20:
                s = "S";
                break;
            case R21:
                s = "S";
                break;
            case R22:
                s = "S";
                break;
            case R23:
                s = "S";
                break;
            case R24:
                s = "L";
                break;
            case R25:
                s = "L";
                break;
            case R26:
                s = "Q";
                break;
            case R27:
                s = "Q";
                break;
            case R28:
                s = "X";
                break;
            case R29:
                s = "X";
                break;
            case R30:
                s = "E";
                break;
            case R31:
                s = "E1";
                break;
            case R32:
                s = "E1";
                break;
            case R33:
                s = "R";
                break;
            case R34:
                s = "R";
                break;
            case R35:
                s = "U";
                break;
            case R36:
                s = "U1";
                break;
            case R37:
                s = "U1";
                break;
            case R38:
                s = "V";
                break;
            case R39:
                s = "V";
                break;
            case R40:
                s = "V";
                break;
            case R41:
                s = "V";
                break;
            case R42:
                s = "V";
                break;
            case R43:
                s = "V";
                break;
            case R44:
                s = "F";
                break;
            case R45:
                s = "F1";
                break;
            case R46:
                s = "F1";
                break;
            case R47:
                s = "F2";
                break;
            case R48:
                s = "F3";
                break;
            case R49:
                s = "A";
                break;
            case R50:
                s = "A";
                break;
            case R51:
                s = "K";
                break;
            case R52:
                s = "K";
                break;
            case R53:
                s = "C";
                break;
            case R54:
                s = "C";
                break;
        }
        return s;
    }
    public String numRegla(Reglas r) {
        String s = "";
        switch(r) {
            case R1:
                s = "1";
                break;
            case R2:
                s = "2";
                break;
            case R3:
                s = "3";
                break;
            case R4:
                s = "4";
                break;
            case R5:
                s = "5";
                break;
            case R6:
                s = "6";
                break;
            case R7:
                s = "7";
                break;
            case R8:
                s = "8";
                break;
            case R9:
                s = "9";
                break;
            case R10:
                s = "10";
                break;
            case R11:
                s = "11";
                break;
            case R12:
                s = "12";
                break;
            case R13:
                s = "13";
                break;
            case R14:
                s = "14";
                break;
            case R15:
                s = "15";
                break;
            case R16:
                s = "16";
                break;
            case R17:
                s = "17";
                break;
            case R18:
                s = "18";
                break;
            case R19:
                s = "19";
                break;
            case R20:
                s = "20";
                break;
            case R21:
                s = "21";
                break;
            case R22:
                s = "22";
                break;
            case R23:
                s = "23";
                break;
            case R24:
                s = "24";
                break;
            case R25:
                s = "25";
                break;
            case R26:
                s = "26";
                break;
            case R27:
                s = "27";
                break;
            case R28:
                s = "28";
                break;
            case R29:
                s = "29";
                break;
            case R30:
                s = "30";
                break;
            case R31:
                s = "31";
                break;
            case R32:
                s = "32";
                break;
            case R33:
                s = "33";
                break;
            case R34:
                s = "34";
                break;
            case R35:
                s = "35";
                break;
            case R36:
                s = "36";
                break;
            case R37:
                s = "37";
                break;
            case R38:
                s = "38";
                break;
            case R39:
                s = "39";
                break;
            case R40:
                s = "40";
                break;
            case R41:
                s = "41";
                break;
            case R42:
                s = "42";
                break;
            case R43:
                s = "43";
                break;
            case R44:
                s = "44";
                break;
            case R45:
                s = "45";
                break;
            case R46:
                s = "46";
                break;
            case R47:
                s = "47";
                break;
            case R48:
                s = "48";
                break;
            case R49:
                s = "49";
                break;
            case R50:
                s = "50";
                break;
            case R51:
                s = "51";
                break;
            case R52:
                s = "52";
                break;
            case R53:
                s = "53";
                break;
            case R54:
                s = "54";
                break;
        }
        return s;
    }
    //Con este metodo sabemos cuantas simbolos en el consecuente tiene cada regla
    public int simbolosporregla(Reglas r) {
        int i = 0;
        switch(r) {
            case R1:
                i=2;
                break;
            case R2:
                i=2;
                break;
            case R3:
                i=1;
                break;
            case R4:
                i=5;
                break;
            case R5:
                i=5;
                break;
            case R6:
                i=9;
                break;
            case R7:
                i=1;
                break;
            case R8:
                i=3;
                break;
            case R9:
                i = 0;
                break;
            case R10:
                i=3;
                break;
            case R11:
                i=3;
                break;
            case R12:
                i=0;
                break;
            case R13:
                i=0;
                break;
            case R14:
                i=1;
                break;
            case R15:
                i=1;
                break;
            case R16:
                i=1;
                break;
            case R17:
                i=1;
                break;
            case R18:
                i=4;
                break;
            case R19:
                i=4;
                break;
            case R20:
                i=5;
                break;
            case R21:
                i=3;
                break;
            case R22:
                i=3;
                break;
            case R23:
                i=3;
                break;
            case R24:
                i=2;
                break;
            case R25:
                i=0;
                break;
            case R26:
                i=3;
                break;
            case R27:
                i=0;
                break;
            case R28:
                i=1;
                break;
            case R29:
                i=0;
                break;
            case R30:
                i=2;
                break;
            case R31:
                i=3;
                break;
            case R32:
                i=0;
                break;
            case R33:
                i=2;
                break;
            case R34:
                i=1;
                break;
            case R35:
                i=2;
                break;
            case R36:
                i=3;
                break;
            case R37:
                i=0;
                break;
            case R38:
                i=1;
                break;
            case R39:
                i=3;
                break;
            case R40:
                i=4;
                break;
            case R41:
                i=1;
                break;
            case R42:
                i=1;
                break;
            case R43:
                i=1;
                break;
            case R44:
                i=7;
                break;
            case R45:
                i=1;
                break;
            case R46:
                i=1;
                break;
            case R47:
                i=1;
                break;
            case R48:
                i=3;
                break;
            case R49:
                i=3;
                break;
            case R50:
                i=1;
                break;
            case R51:
                i=4;
                break;
            case R52:
                i=0;
                break;
            case R53:
                i=2;
                break;
            case R54:
                i=0;
                break;
        }
        return i;
    }
    public EstadosSintactico tablaGoto(String s, EstadosSintactico e) {
        EstadosSintactico respuesta = EstadosSintactico.I0;//Por inicializar a cualquier valor
        switch(e) {
            case I0:
                if(s.equals("P")) {
                    respuesta = EstadosSintactico.I10;
                }
                else if(s.equals("B")) {
                    respuesta = EstadosSintactico.I11;
                }
                else if(s.equals("S")) {
                    respuesta = EstadosSintactico.I12;
                }
                else if(s.equals("F")) {
                    respuesta = EstadosSintactico.I13;
                }
                else {
                    error();
                }
                break;
            case I2:
                if(s.equals("T")) {
                    respuesta = EstadosSintactico.I20;
                }
                else if(s.equals("F1")) {
                    respuesta = EstadosSintactico.I21;
                }
                else
                    error();
                break;
            case I4:
                if(s.equals("M")) {
                    respuesta = EstadosSintactico.I23;
                }
                else
                    error();
                break;
            case I6:
                if(s.equals("X")) {
                    respuesta = EstadosSintactico.I31;
                }
                else if(s.equals("E")) {
                    respuesta = EstadosSintactico.I32;
                }
                else if(s.equals("R")) {
                    respuesta = EstadosSintactico.I33;
                }
                else if(s.equals("U")) {
                    respuesta = EstadosSintactico.I34;
                }
                else if(s.equals("V")) {
                    respuesta = EstadosSintactico.I35;
                }
                else {
                    error();
                }
                break;
            case I7:
                if(s.equals("E")) {
                    respuesta = EstadosSintactico.I36;
                }
                else if(s.equals("R")) {
                    respuesta = EstadosSintactico.I33;
                }
                else if(s.equals("U")) {
                    respuesta = EstadosSintactico.I34;
                }
                else if(s.equals("V")) {
                    respuesta = EstadosSintactico.I35;
                }
                else {
                    error();
                }
                break;
            case I11:
                if(s.equals("P")) {
                    respuesta = EstadosSintactico.I41;
                }
                else if(s.equals("B")) {
                    respuesta = EstadosSintactico.I11;
                }
                else if(s.equals("S")) {
                    respuesta = EstadosSintactico.I12;
                }
                else if(s.equals("F")) {
                    respuesta = EstadosSintactico.I13;
                }
                else
                    error();
                break;
            case I13:
                if(s.equals("P")) {
                    respuesta = EstadosSintactico.I42;
                }
                else if(s.equals("B")) {
                    respuesta = EstadosSintactico.I11;
                }
                else if(s.equals("S")) {
                    respuesta = EstadosSintactico.I12;
                }
                else if(s.equals("F")) {
                    respuesta = EstadosSintactico.I13;
                }
                else
                    error();
                break;
            case I14:
                if(s.equals("G1")) {
                    respuesta = EstadosSintactico.I44;
                }
                else
                    error();
                break;
            case I21:
                if (s.equals("F2")) respuesta = EstadosSintactico.I46;
                else error();
                break;
            case I22:
                if (s.equals("E")) respuesta = EstadosSintactico.I47;
                else if (s.equals("R")) respuesta = EstadosSintactico.I118;
                else if (s.equals("U")) respuesta = EstadosSintactico.I119;
                else if (s.equals("V")) respuesta = EstadosSintactico.I120;
                else error();
                break;
            case I23:
                if (s.equals("T")) respuesta = EstadosSintactico.I48;
                else error();
                break;
            case I29:
                if (s.equals("E")) respuesta = EstadosSintactico.I51;
                else if (s.equals("R")) respuesta = EstadosSintactico.I118;
                else if (s.equals("U")) respuesta = EstadosSintactico.I119;
                else if (s.equals("V")) respuesta = EstadosSintactico.I120;
                else error();
                break;
            case I30:
                if (s.equals("R")) respuesta = EstadosSintactico.I52;
                else if (s.equals("U")) respuesta = EstadosSintactico.I34;
                else if (s.equals("V")) respuesta = EstadosSintactico.I35;
                else error();
                break;
            case I33:
                if (s.equals("E1")) respuesta = EstadosSintactico.I55;
                else error();
                break;
            case I35:
                if (s.equals("U1")) respuesta = EstadosSintactico.I57;
                else error();
                break;

            case I37:
                if (s.equals("E")) respuesta = EstadosSintactico.I59;
                else if (s.equals("R")) respuesta = EstadosSintactico.I33;
                else if (s.equals("U")) respuesta = EstadosSintactico.I34;
                else if (s.equals("V")) respuesta = EstadosSintactico.I35;
                else error();
                break;

            case I38:
                if (s.equals("E")) respuesta = EstadosSintactico.I60;
                else if (s.equals("R")) respuesta = EstadosSintactico.I33;
                else if (s.equals("U")) respuesta = EstadosSintactico.I34;
                else if (s.equals("V")) respuesta = EstadosSintactico.I35;
                else error();
                break;

            case I39:
                if (s.equals("L")) respuesta = EstadosSintactico.I61;
                else if (s.equals("E")) respuesta = EstadosSintactico.I62;
                else if (s.equals("R")) respuesta = EstadosSintactico.I127;
                else if (s.equals("U")) respuesta = EstadosSintactico.I128;
                else if (s.equals("V")) respuesta = EstadosSintactico.I129;
                else error();
                break;

            case I46:
                if (s.equals("F3")) respuesta = EstadosSintactico.I66;
                else error();
                break;

            case I50:
                if (s.equals("L")) respuesta = EstadosSintactico.I69;
                else if (s.equals("E")) respuesta = EstadosSintactico.I62;
                else if (s.equals("R")) respuesta = EstadosSintactico.I127;
                else if (s.equals("U")) respuesta = EstadosSintactico.I128;
                else if (s.equals("V")) respuesta = EstadosSintactico.I129;
                else error();
                break;

            case I54:
                if (s.equals("R")) respuesta = EstadosSintactico.I71;
                else if (s.equals("U")) respuesta = EstadosSintactico.I34;
                else if (s.equals("V")) respuesta = EstadosSintactico.I35;
                else error();
                break;

            case I56:
                if (s.equals("V")) respuesta = EstadosSintactico.I72;
                else error();
                break;

            case I62:
                if (s.equals("Q")) respuesta = EstadosSintactico.I77;
                else error();
                break;

            case I63:
                if (s.equals("E")) respuesta = EstadosSintactico.I78;
                else if (s.equals("R")) respuesta = EstadosSintactico.I33;
                else if (s.equals("U")) respuesta = EstadosSintactico.I34;
                else if (s.equals("V")) respuesta = EstadosSintactico.I35;
                else error();
                break;

            case I64:
                if (s.equals("E")) respuesta = EstadosSintactico.I79;
                else if (s.equals("R")) respuesta = EstadosSintactico.I33;
                else if (s.equals("U")) respuesta = EstadosSintactico.I34;
                else if (s.equals("V")) respuesta = EstadosSintactico.I35;
                else error();
                break;

            case I65:
                if (s.equals("A")) respuesta = EstadosSintactico.I82;
                else if (s.equals("T")) respuesta = EstadosSintactico.I81;
                else error();
                break;

            case I67:
                if (s.equals("S")) respuesta = EstadosSintactico.I84;
                else error();
                break;

            case I71:
                if (s.equals("E1")) respuesta = EstadosSintactico.I87;
                else error();
                break;

            case I72:
                if (s.equals("U1")) respuesta = EstadosSintactico.I88;
                else error();
                break;

            case I76:
                if (s.equals("E")) respuesta = EstadosSintactico.I90;
                else if (s.equals("R")) respuesta = EstadosSintactico.I127;
                else if (s.equals("U")) respuesta = EstadosSintactico.I128;
                else if (s.equals("V")) respuesta = EstadosSintactico.I129;
                else error();
                break;

            case I83:
                if (s.equals("B")) respuesta = EstadosSintactico.I94;
                else if (s.equals("S")) respuesta = EstadosSintactico.I137;
                else if (s.equals("C")) respuesta = EstadosSintactico.I95;
                else error();
                break;

            case I90:
                if(s.equals("Q")) {
                    respuesta = EstadosSintactico.I96;
                }
                else
                    error();
                break;

            case I91:
                if(s.equals("G2")) {
                    respuesta = EstadosSintactico.I98;
                }
                else
                    error();
                break;
            case I92:
                if(s.equals("K")) respuesta = EstadosSintactico.I100;
                else error();
                break;
            case I94:
                if(s.equals("B")) respuesta = EstadosSintactico.I94;
                else if(s.equals("S")) respuesta = EstadosSintactico.I137;
                else if(s.equals("C")) respuesta = EstadosSintactico.I101;
                else error();
                break;
            case I99:
                if(s.equals("T")) respuesta = EstadosSintactico.I106;
                else error();
                break;
            case I103:
                if(s.equals("E")) respuesta = EstadosSintactico.I107;
                else if(s.equals("R")) respuesta = EstadosSintactico.I118;
                else if(s.equals("U")) respuesta = EstadosSintactico.I119;
                else if(s.equals("V")) respuesta = EstadosSintactico.I120;
                else error();
                break;
            case I104:
                if(s.equals("E")) respuesta = EstadosSintactico.I108;
                else if(s.equals("R")) respuesta = EstadosSintactico.I118;
                else if(s.equals("U")) respuesta = EstadosSintactico.I119;
                else if(s.equals("V")) respuesta = EstadosSintactico.I120;
                else error();
                break;
            case I105:
                if(s.equals("S")) respuesta = EstadosSintactico.I109;
                else error();
                break;
            case I110:
                if(s.equals("K")) respuesta = EstadosSintactico.I111;
                else error();
                break;
            case I116:
                if(s.equals("E")) respuesta = EstadosSintactico.I139;
                else if(s.equals("R")) respuesta = EstadosSintactico.I118;
                else if(s.equals("U")) respuesta = EstadosSintactico.I119;
                else if(s.equals("V")) respuesta = EstadosSintactico.I120;
                else error();
                break;
            case I117:
                if(s.equals("R")) respuesta = EstadosSintactico.I140;
                else if(s.equals("U")) respuesta = EstadosSintactico.I119;
                else if(s.equals("V")) respuesta = EstadosSintactico.I120;
                else error();
                break;
            case I118:
                if(s.equals("E1")) respuesta = EstadosSintactico.I142;
                else error();
                break;
            case I120:
                if(s.equals("U1")) respuesta = EstadosSintactico.I144;
                else error();
                break;
            case I125:
                if(s.equals("E")) respuesta = EstadosSintactico.I146;
                else if(s.equals("R")) respuesta = EstadosSintactico.I118;
                else if(s.equals("U")) respuesta = EstadosSintactico.I119;
                else if(s.equals("V")) respuesta = EstadosSintactico.I120;
                else error();
                break;
            case I126:
                if(s.equals("R")) respuesta = EstadosSintactico.I147;
                else if(s.equals("U")) respuesta = EstadosSintactico.I128;
                else if(s.equals("V")) respuesta = EstadosSintactico.I129;
                else error();
                break;
            case I127:
                if(s.equals("E1")) respuesta = EstadosSintactico.I149;
                else error();
                break;
            case I129:
                if(s.equals("U1")) respuesta = EstadosSintactico.I151;
                else error();
                break;
            case I132:
                if(s.equals("M")) respuesta = EstadosSintactico.I154;
                else error();
                break;
            case I134:
                if(s.equals("X")) respuesta = EstadosSintactico.I156;
                else if(s.equals("E")) respuesta = EstadosSintactico.I32;
                else if(s.equals("R")) respuesta = EstadosSintactico.I33;
                else if(s.equals("U")) respuesta = EstadosSintactico.I34;
                else if(s.equals("V")) respuesta = EstadosSintactico.I35;
                else error();
                break;
            case I135:
                if(s.equals("E")) respuesta = EstadosSintactico.I157;
                else if(s.equals("R")) respuesta = EstadosSintactico.I33;
                else if(s.equals("U")) respuesta = EstadosSintactico.I34;
                else if(s.equals("V")) respuesta = EstadosSintactico.I35;
                else error();
                break;
            case I138:
                if(s.equals("L")) respuesta = EstadosSintactico.I161;
                else if(s.equals("E")) respuesta = EstadosSintactico.I62;
                else if(s.equals("R")) respuesta = EstadosSintactico.I127;
                else if(s.equals("U")) respuesta = EstadosSintactico.I128;
                else if(s.equals("V")) respuesta = EstadosSintactico.I129;
                else error();
                break;
            case I141:
                if(s.equals("R")) respuesta = EstadosSintactico.I163;
                else if(s.equals("U")) respuesta = EstadosSintactico.I119;
                else if(s.equals("V")) respuesta = EstadosSintactico.I120;
                else error();
                break;
            case I143:
                if(s.equals("V")) respuesta = EstadosSintactico.I164;
                else error();
                break;
            case I145:
                if(s.equals("L")) respuesta = EstadosSintactico.I165;
                else if(s.equals("E")) respuesta = EstadosSintactico.I62;
                else if(s.equals("R")) respuesta = EstadosSintactico.I127;
                else if(s.equals("U")) respuesta = EstadosSintactico.I128;
                else if(s.equals("V")) respuesta = EstadosSintactico.I129;
                else error();
                break;
            case I148:
                if(s.equals("R")) respuesta = EstadosSintactico.I167;
                else if(s.equals("U")) respuesta = EstadosSintactico.I128;
                else if(s.equals("V")) respuesta = EstadosSintactico.I129;
                else error();
                break;
            case I150:
                if(s.equals("V")) respuesta = EstadosSintactico.I168;
                else error();
                break;
            case I152:
                if(s.equals("G1")) respuesta = EstadosSintactico.I169;
                else error();
                break;
            case I153:
                if(s.equals("E")) respuesta = EstadosSintactico.I170;
                else if(s.equals("R")) respuesta = EstadosSintactico.I118;
                else if(s.equals("U")) respuesta = EstadosSintactico.I119;
                else if(s.equals("V")) respuesta = EstadosSintactico.I120;
                else error();
                break;
            case I154:
                if(s.equals("T")) respuesta = EstadosSintactico.I171;
                else error();
                break;
            case I158:
                if(s.equals("E")) respuesta = EstadosSintactico.I175;
                else if(s.equals("R")) respuesta = EstadosSintactico.I33;
                else if(s.equals("U")) respuesta = EstadosSintactico.I34;
                else if(s.equals("V")) respuesta = EstadosSintactico.I35;
                else error();
                break;
            case I159:
                if(s.equals("E")) respuesta = EstadosSintactico.I176;
                else if(s.equals("R")) respuesta = EstadosSintactico.I33;
                else if(s.equals("U")) respuesta = EstadosSintactico.I34;
                else if(s.equals("V")) respuesta = EstadosSintactico.I35;
                else error();
                break;
            case I160:
                if(s.equals("L")) respuesta = EstadosSintactico.I177;
                else if(s.equals("E")) respuesta = EstadosSintactico.I62;
                else if(s.equals("R")) respuesta = EstadosSintactico.I127;
                else if(s.equals("U")) respuesta = EstadosSintactico.I128;
                else if(s.equals("V")) respuesta = EstadosSintactico.I129;
                else error();
                break;
            case I163:
                if(s.equals("E1")) respuesta = EstadosSintactico.I179;
                else error();
                break;
            case I164:
                if(s.equals("U1")) respuesta = EstadosSintactico.I180;
                else error();
                break;
            case I167:
                if(s.equals("E1")) respuesta = EstadosSintactico.I182;
                else error();
                break;
            case I168:
                if(s.equals("U1")) respuesta = EstadosSintactico.I183;
                else error();
                break;
            case I184:
                if(s.equals("E")) respuesta = EstadosSintactico.I190;
                else if(s.equals("R")) respuesta = EstadosSintactico.I33;
                else if(s.equals("U")) respuesta = EstadosSintactico.I34;
                else if(s.equals("V")) respuesta = EstadosSintactico.I35;
                else error();
                break;
            case I185:
                if(s.equals("S")) respuesta = EstadosSintactico.I191;
                else error();
                break;
            case I194:
                if(s.equals("G2")) respuesta = EstadosSintactico.I195;
                else error();
                break;
            case I196:
                if(s.equals("S")) respuesta = EstadosSintactico.I197;
                else error();
                break;
            default: break;

        }
        return respuesta;
    }
    // TODO
    public void accion(Token token) {//Tabla de accion
        boolean desplazamiento = false;
        int posTokenenString = posTokenenString(token);// Tengo la pos del token recibido en el array de posibles simbolos
        String s = simb[posTokenenString];
        EstadosSintactico estadoDesplazado = EstadosSintactico.I0;

        while(!desplazamiento) {

            pasos.write(s);
            pasos.write("\n");
            pasos.write(mostrar_lista(l));
            pasos.write("\n");


            estadoActual = (EstadosSintactico) pila.peek();//Lo que este encima de la pila ser� el estado en que se este
            switch (estadoActual) {
                    case I0:
                        if (s.equals("for")) desplazamiento= desplazamiento(s, EstadosSintactico.I1);
                        else if (s.equals("function")) desplazamiento= desplazamiento(s, EstadosSintactico.I2);
                        else if (s.equals("if")) desplazamiento= desplazamiento(s, EstadosSintactico.I3);
                        else if (s.equals("let")) desplazamiento= desplazamiento(s, EstadosSintactico.I4);
                        else if (s.equals("read")) desplazamiento= desplazamiento(s, EstadosSintactico.I5);
                        else if (s.equals("return")) desplazamiento= desplazamiento(s, EstadosSintactico.I6);
                        else if (s.equals("write")) desplazamiento= desplazamiento(s, EstadosSintactico.I7);
                        else if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I8);
                        else if (s.equals("$")) desplazamiento(s, EstadosSintactico.I9);
                        break;

                    case I1:
                        if (s.equals("(")) desplazamiento= desplazamiento(s, EstadosSintactico.I14);
                        break;

                    case I2:
                        if (s.equals("boolean")) desplazamiento= desplazamiento(s, EstadosSintactico.I15);
                        else if (s.equals("float")) desplazamiento= desplazamiento(s, EstadosSintactico.I16);
                        else if (s.equals("int")) desplazamiento= desplazamiento(s, EstadosSintactico.I17);
                        else if (s.equals("string")) desplazamiento= desplazamiento(s, EstadosSintactico.I18);
                        else if (s.equals("void")) desplazamiento= desplazamiento(s, EstadosSintactico.I19);
                        break;

                    case I3:
                        if (s.equals("(")) desplazamiento= desplazamiento(s, EstadosSintactico.I22);
                        break;

                    case I4:
                        if (s.equals("boolean") || s.equals("float") || s.equals("int") || s.equals("string")) {
                            reduccion(s, Reglas.R13); // M -> ε
                        }
                        break;

                    case I5:
                        if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I24);
                        break;

                    case I6:
                        if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I25);
                        else if (s.equals("entero")) desplazamiento= desplazamiento(s, EstadosSintactico.I26);
                        else if (s.equals("real")) desplazamiento= desplazamiento(s, EstadosSintactico.I27);
                        else if (s.equals("cadena")) desplazamiento= desplazamiento(s, EstadosSintactico.I28);
                        else if (s.equals("(")) desplazamiento= desplazamiento(s, EstadosSintactico.I29);
                        else if (s.equals("!")) desplazamiento= desplazamiento(s, EstadosSintactico.I30);
                        else if (s.equals(";")) {
                            reduccion(s, Reglas.R29); // X -> ε
                        }
                        break;

                    case I7:
                        if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I25);
                        else if (s.equals("entero")) desplazamiento= desplazamiento(s, EstadosSintactico.I26);
                        else if (s.equals("real")) desplazamiento= desplazamiento(s, EstadosSintactico.I27);
                        else if (s.equals("cadena")) desplazamiento= desplazamiento(s, EstadosSintactico.I28);
                        else if (s.equals("(")) desplazamiento= desplazamiento(s, EstadosSintactico.I29);
                        else if (s.equals("!")) desplazamiento= desplazamiento(s, EstadosSintactico.I30);
                        break;

                    case I8:
                        if (s.equals("+=")) desplazamiento= desplazamiento(s, EstadosSintactico.I37);
                        else if (s.equals("=")) desplazamiento= desplazamiento(s, EstadosSintactico.I38);
                        else if (s.equals("(")) desplazamiento= desplazamiento(s, EstadosSintactico.I39);
                        break;

                    case I9:
                        if (s.equals("$")) {
                            reduccion(s, Reglas.R3); // P -> EOF
                        }
                        break;

                    case I10:
                        if (s.equals("$")) desplazamiento= desplazamiento(s, EstadosSintactico.I40);
                        break;

                    case I11:
                        if (s.equals("for")) desplazamiento= desplazamiento(s, EstadosSintactico.I1);
                        else if (s.equals("function")) desplazamiento= desplazamiento(s, EstadosSintactico.I2);
                        else if (s.equals("if")) desplazamiento= desplazamiento(s, EstadosSintactico.I3);
                        else if (s.equals("let")) desplazamiento= desplazamiento(s, EstadosSintactico.I4);
                        else if (s.equals("read")) desplazamiento= desplazamiento(s, EstadosSintactico.I5);
                        else if (s.equals("return")) desplazamiento= desplazamiento(s, EstadosSintactico.I6);
                        else if (s.equals("write")) desplazamiento= desplazamiento(s, EstadosSintactico.I7);
                        else if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I8);
                        else if (s.equals("$")) desplazamiento(s, EstadosSintactico.I9);
                        break;

                    case I12:
                        if (s.equals("for") || s.equals("function") || s.equals("if") ||
                                s.equals("let") || s.equals("read") || s.equals("return") ||
                                s.equals("write") || s.equals("id") || s.equals("$")) {
                            reduccion(s, Reglas.R7); // B -> S
                        }
                        break;

                    case I13:
                        if (s.equals("for")) desplazamiento= desplazamiento(s, EstadosSintactico.I1);
                        else if (s.equals("function")) desplazamiento= desplazamiento(s, EstadosSintactico.I2);
                        else if (s.equals("if")) desplazamiento= desplazamiento(s, EstadosSintactico.I3);
                        else if (s.equals("let")) desplazamiento= desplazamiento(s, EstadosSintactico.I4);
                        else if (s.equals("read")) desplazamiento= desplazamiento(s, EstadosSintactico.I5);
                        else if (s.equals("return")) desplazamiento= desplazamiento(s, EstadosSintactico.I6);
                        else if (s.equals("write")) desplazamiento= desplazamiento(s, EstadosSintactico.I7);
                        else if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I8);
                        else if (s.equals("$")) desplazamiento(s, EstadosSintactico.I9);
                        break;

                    case I14:
                        if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I43);
                        else if (s.equals(";")) {
                            reduccion(s, Reglas.R9); // G1 -> ε
                        }
                        break;

                    case I15:
                        if (s.equals("id")) {
                            reduccion(s, Reglas.R16); // T -> boolean
                        }
                        break;

                    case I16:
                        if (s.equals("id")) {
                            reduccion(s, Reglas.R15); // T -> float
                        }
                        break;

                    case I17:
                        if (s.equals("id")) {
                            reduccion(s, Reglas.R14); // T -> int
                        }
                        break;

                    case I18:
                        if (s.equals("id")) {
                            reduccion(s, Reglas.R17); // T -> string
                        }
                        break;

                    case I19:
                        if (s.equals("id")) {
                            reduccion(s, Reglas.R46); // F1 -> void
                        }
                        break;

                    case I20:
                        if (s.equals("id")) {
                            reduccion(s, Reglas.R45); // F1 -> T
                        }
                        break;

                    case I21:
                        if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I45);
                        break;

                    case I22:
                        if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I112);
                        else if (s.equals("entero")) desplazamiento= desplazamiento(s, EstadosSintactico.I113);
                        else if (s.equals("real")) desplazamiento= desplazamiento(s, EstadosSintactico.I114);
                        else if (s.equals("cadena")) desplazamiento= desplazamiento(s, EstadosSintactico.I115);
                        else if (s.equals("(")) desplazamiento= desplazamiento(s, EstadosSintactico.I116);
                        else if (s.equals("!")) desplazamiento= desplazamiento(s, EstadosSintactico.I117);
                        break;

                    case I23:
                        if (s.equals("boolean")) desplazamiento= desplazamiento(s, EstadosSintactico.I15);
                        else if (s.equals("float")) desplazamiento= desplazamiento(s, EstadosSintactico.I16);
                        else if (s.equals("int")) desplazamiento= desplazamiento(s, EstadosSintactico.I17);
                        else if (s.equals("string")) desplazamiento= desplazamiento(s, EstadosSintactico.I18);
                        break;

                    case I24:
                        if (s.equals(";")) desplazamiento= desplazamiento(s, EstadosSintactico.I49);
                        break;

                    case I25:
                        if (s.equals("(")) desplazamiento= desplazamiento(s, EstadosSintactico.I50);
                        else if (s.equals(";") || s.equals("-") || s.equals(">")) {
                            reduccion(s, Reglas.R38); // V -> ID
                        }
                        break;

                    case I26:
                        if (s.equals(";") || s.equals("-") || s.equals(">")) {
                            reduccion(s, Reglas.R39); // V -> ENTERO
                        }
                        break;

                    case I27:
                        if (s.equals(";") || s.equals("-") || s.equals(">")) {
                            reduccion(s, Reglas.R40); // V -> REAL
                        }
                        break;

                    case I28:
                        if (s.equals(";") || s.equals("-") || s.equals(">")) {
                            reduccion(s, Reglas.R41); // V -> CADENA
                        }
                        break;

                    case I29:
                        if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I112);
                        else if (s.equals("entero")) desplazamiento= desplazamiento(s, EstadosSintactico.I113);
                        else if (s.equals("real")) desplazamiento= desplazamiento(s, EstadosSintactico.I114);
                        else if (s.equals("cadena")) desplazamiento= desplazamiento(s, EstadosSintactico.I115);
                        else if (s.equals("(")) desplazamiento= desplazamiento(s, EstadosSintactico.I116);
                        else if (s.equals("!")) desplazamiento= desplazamiento(s, EstadosSintactico.I117);
                        break;

                    case I30:
                        if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I25);
                        else if (s.equals("entero")) desplazamiento= desplazamiento(s, EstadosSintactico.I26);
                        else if (s.equals("real")) desplazamiento= desplazamiento(s, EstadosSintactico.I27);
                        else if (s.equals("cadena")) desplazamiento= desplazamiento(s, EstadosSintactico.I28);
                        else if (s.equals("(")) desplazamiento= desplazamiento(s, EstadosSintactico.I29);
                        else if (s.equals("!")) desplazamiento= desplazamiento(s, EstadosSintactico.I30);
                        break;

                    case I31:
                        if (s.equals(";")) {
                            desplazamiento = desplazamiento(s, EstadosSintactico.I53);
                        }
                        break;

                    case I32:
                        if (s.equals(";")) {
                            reduccion(s, Reglas.R28);
                        }
                        break;

                    case I33:
                        if (s.equals(">")) desplazamiento= desplazamiento(s, EstadosSintactico.I54);
                        else if (s.equals(";")) {
                            reduccion(s, Reglas.R32);
                        }
                        break;

                    case I34:
                        if (s.equals(";") || s.equals(">")) {
                            reduccion(s, Reglas.R34);
                        }
                        break;

                    case I35:
                        if (s.equals("-")) desplazamiento= desplazamiento(s, EstadosSintactico.I56);
                        else if (s.equals(";") || s.equals(">")) {
                            reduccion(s, Reglas.R37);
                        }
                        break;

                    case I36:
                        if (s.equals(";")) desplazamiento= desplazamiento(s, EstadosSintactico.I58);
                        break;

                    case I37:
                        if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I25);
                        else if (s.equals("entero")) desplazamiento= desplazamiento(s, EstadosSintactico.I26);
                        else if (s.equals("real")) desplazamiento= desplazamiento(s, EstadosSintactico.I27);
                        else if (s.equals("cadena")) desplazamiento= desplazamiento(s, EstadosSintactico.I28);
                        else if (s.equals("(")) desplazamiento= desplazamiento(s, EstadosSintactico.I29);
                        else if (s.equals("!")) desplazamiento= desplazamiento(s, EstadosSintactico.I30);
                        break;

                    case I38:
                        if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I25);
                        else if (s.equals("entero")) desplazamiento= desplazamiento(s, EstadosSintactico.I26);
                        else if (s.equals("real")) desplazamiento= desplazamiento(s, EstadosSintactico.I27);
                        else if (s.equals("cadena")) desplazamiento= desplazamiento(s, EstadosSintactico.I28);
                        else if (s.equals("(")) desplazamiento= desplazamiento(s, EstadosSintactico.I29);
                        else if (s.equals("!")) desplazamiento= desplazamiento(s, EstadosSintactico.I30);
                        break;

                    case I39:
                        if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I121);
                        else if (s.equals("entero")) desplazamiento= desplazamiento(s, EstadosSintactico.I122);
                        else if (s.equals("real")) desplazamiento= desplazamiento(s, EstadosSintactico.I123);
                        else if (s.equals("cadena")) desplazamiento= desplazamiento(s, EstadosSintactico.I124);
                        else if (s.equals("(")) desplazamiento= desplazamiento(s, EstadosSintactico.I125);
                        else if (s.equals("!")) desplazamiento= desplazamiento(s, EstadosSintactico.I126);
                        else if (s.equals(")")) {
                            reduccion(s, Reglas.R25);
                        }
                        break;

                    case I40:
                        if(s.equals("$")) {
                            aceptado = true;
                            desplazamiento = true;
                        }
                        break;
                    case I41:
                        if(s.equals("$")) {
                            reduccion(s, Reglas.R1);
                        }
                        break;

                    case I42:
                        if(s.equals("$")) {
                            reduccion(s, Reglas.R2);
                        }
                        break;

                    case I43:
                        if (s.equals("=")) desplazamiento= desplazamiento(s, EstadosSintactico.I63);
                        break;

                    case I44:
                        if (s.equals(";")) desplazamiento= desplazamiento(s, EstadosSintactico.I64);
                        break;

                    case I45:
                        if (s.equals("(")) {
                            reduccion(s, Reglas.R47);
                        }
                        break;

                    case I46:
                        if (s.equals("(")) desplazamiento= desplazamiento(s, EstadosSintactico.I65);
                        break;

                    case I47:
                        if (s.equals(")")) desplazamiento= desplazamiento(s, EstadosSintactico.I67);
                        break;

                    case I48:
                        if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I68);
                        break;

                    case I49:
                        if (s.equals("for") || s.equals("function") || s.equals("if")
                                || s.equals("let") || s.equals("read") || s.equals("return")
                                || s.equals("write") || s.equals("id") || s.equals("$")) {
                            reduccion(s, Reglas.R22);
                        }
                        break;

                    case I50:
                        if (s.equals("id")) desplazamiento = desplazamiento(s, EstadosSintactico.I121);
                        else if (s.equals("entero")) desplazamiento = desplazamiento(s, EstadosSintactico.I122);
                        else if (s.equals("real")) desplazamiento = desplazamiento(s, EstadosSintactico.I123);
                        else if (s.equals("cadena")) desplazamiento = desplazamiento(s, EstadosSintactico.I124);
                        else if (s.equals("(")) desplazamiento = desplazamiento(s, EstadosSintactico.I125);
                        else if (s.equals("!")) desplazamiento = desplazamiento(s, EstadosSintactico.I126);
                        else if (s.equals(")")) {
                            reduccion(s, Reglas.R25);
                        }
                        break;
                case I51:
                    if (s.equals(")")) desplazamiento = desplazamiento(s, EstadosSintactico.I70);
                    break;

                case I52:
                    if (s.equals(";") || s.equals(">")) {
                        reduccion(s, Reglas.R33);
                    }
                    break;

                case I53:
                    if (s.equals("for") || s.equals("function") || s.equals("if")
                            || s.equals("let") || s.equals("read") || s.equals("return")
                            || s.equals("write") || s.equals("id") || s.equals("$")) {
                        reduccion(s, Reglas.R23);
                    }
                    break;

                case I54:
                    if (s.equals("id")) desplazamiento = desplazamiento(s, EstadosSintactico.I25);
                    else if (s.equals("entero")) desplazamiento = desplazamiento(s, EstadosSintactico.I26);
                    else if (s.equals("real")) desplazamiento = desplazamiento(s, EstadosSintactico.I27);
                    else if (s.equals("cadena")) desplazamiento = desplazamiento(s, EstadosSintactico.I28);
                    else if (s.equals("(")) desplazamiento = desplazamiento(s, EstadosSintactico.I29);
                    else if (s.equals("!")) desplazamiento = desplazamiento(s, EstadosSintactico.I30);
                    break;

                case I55:
                    if (s.equals(";")) {
                        reduccion(s, Reglas.R30);
                    }
                    break;

                case I56:
                    if (s.equals("id")) desplazamiento = desplazamiento(s, EstadosSintactico.I25);
                    else if (s.equals("entero")) desplazamiento = desplazamiento(s, EstadosSintactico.I26);
                    else if (s.equals("real")) desplazamiento = desplazamiento(s, EstadosSintactico.I27);
                    else if (s.equals("cadena")) desplazamiento = desplazamiento(s, EstadosSintactico.I28);
                    else if (s.equals("(")) desplazamiento = desplazamiento(s, EstadosSintactico.I29);
                    break;

                case I57:
                    if (s.equals(";") || s.equals(">")) {
                        reduccion(s, Reglas.R35);
                    }
                    break;

                case I58:
                    if (s.equals("for")|| (s.equals("function")) || (s.equals("if")) ||
                            (s.equals("let")) || (s.equals("read")) || (s.equals("return")) ||
                            (s.equals("write")) || (s.equals("id")) || (s.equals("$"))) {
                        reduccion(s, Reglas.R21); // A -> B A1
                    }
                    break;

                case I59:
                    if (s.equals(";")) desplazamiento = desplazamiento(s, EstadosSintactico.I73);
                    break;

                case I60:
                    if (s.equals(";")) desplazamiento = desplazamiento(s, EstadosSintactico.I74);
                    break;

                case I61:
                    if (s.equals(")")) desplazamiento = desplazamiento(s, EstadosSintactico.I75);
                    break;

                case I62:
                    if (s.equals(",")) desplazamiento = desplazamiento(s, EstadosSintactico.I76);
                    else if (s.equals(")")) {
                        reduccion(s, Reglas.R27);
                    }
                    break;

                case I63:
                    if (s.equals("id")) desplazamiento = desplazamiento(s, EstadosSintactico.I25);
                    else if (s.equals("entero")) desplazamiento = desplazamiento(s, EstadosSintactico.I26);
                    else if (s.equals("real")) desplazamiento = desplazamiento(s, EstadosSintactico.I27);
                    else if (s.equals("cadena")) desplazamiento = desplazamiento(s, EstadosSintactico.I28);
                    else if (s.equals("(")) desplazamiento = desplazamiento(s, EstadosSintactico.I29);
                    else if (s.equals("!")) desplazamiento = desplazamiento(s, EstadosSintactico.I30);
                    break;

                case I64:
                    if (s.equals("id")) desplazamiento = desplazamiento(s, EstadosSintactico.I25);
                    else if (s.equals("entero")) desplazamiento = desplazamiento(s, EstadosSintactico.I26);
                    else if (s.equals("real")) desplazamiento = desplazamiento(s, EstadosSintactico.I27);
                    else if (s.equals("cadena")) desplazamiento = desplazamiento(s, EstadosSintactico.I28);
                    else if (s.equals("(")) desplazamiento = desplazamiento(s, EstadosSintactico.I29);
                    else if (s.equals("!")) desplazamiento = desplazamiento(s, EstadosSintactico.I30);
                    break;

                case I65:
                    if (s.equals("boolean")) desplazamiento = desplazamiento(s, EstadosSintactico.I15);
                    else if (s.equals("float")) desplazamiento = desplazamiento(s, EstadosSintactico.I16);
                    else if (s.equals("int")) desplazamiento = desplazamiento(s, EstadosSintactico.I17);
                    else if (s.equals("string")) desplazamiento = desplazamiento(s, EstadosSintactico.I18);
                    else if (s.equals("void")) desplazamiento = desplazamiento(s, EstadosSintactico.I80);
                    break;

                case I66:
                    if (s.equals("{")) desplazamiento = desplazamiento(s, EstadosSintactico.I83);
                    break;

                case I67:
                    if (s.equals("read")) desplazamiento= desplazamiento(s, EstadosSintactico.I5);
                    else if (s.equals("return")) desplazamiento= desplazamiento(s, EstadosSintactico.I6);
                    else if (s.equals("write")) desplazamiento= desplazamiento(s, EstadosSintactico.I7);
                    else if (s.equals("id")) desplazamiento= desplazamiento(s, EstadosSintactico.I8);
                    break;

                case I68:
                    if (s.equals(";")) desplazamiento = desplazamiento(s, EstadosSintactico.I85);
                    break;

                case I69:
                    if (s.equals(")")) desplazamiento = desplazamiento(s, EstadosSintactico.I86);
                    break;

                case I70:
                    if (s.equals(";") || s.equals("-") || s.equals(">")) {
                        reduccion(s, Reglas.R42);
                    }
                    break;

                case I71:
                    if (s.equals(">")) desplazamiento = desplazamiento(s, EstadosSintactico.I54);
                    else if (s.equals(";")) {
                        reduccion(s, Reglas.R32);
                    }
                    break;

                case I72:
                    if (s.equals("-")) desplazamiento = desplazamiento(s, EstadosSintactico.I56);
                    else if (s.equals(";") || s.equals(">")) {
                        reduccion(s, Reglas.R37);
                    }
                    break;

                case I73:
                    if (s.equals("for") || s.equals("function") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("$")) {
                        reduccion(s, Reglas.R19);
                    }
                    break;

                case I74:
                    if (s.equals("for") || s.equals("function") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("$")) {
                        reduccion(s, Reglas.R18);
                    }
                    break;

                case I75:
                    if (s.equals(";")) desplazamiento = desplazamiento(s, EstadosSintactico.I89);
                    break;

                case I76:
                    if (s.equals("id")) desplazamiento = desplazamiento(s, EstadosSintactico.I121);
                    else if (s.equals("entero")) desplazamiento = desplazamiento(s, EstadosSintactico.I122);
                    else if (s.equals("real")) desplazamiento = desplazamiento(s, EstadosSintactico.I123);
                    else if (s.equals("cadena")) desplazamiento = desplazamiento(s, EstadosSintactico.I124);
                    else if (s.equals("(")) desplazamiento = desplazamiento(s, EstadosSintactico.I125);
                    else if (s.equals("!")) desplazamiento = desplazamiento(s, EstadosSintactico.I126);
                    break;

                case I77:
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R24);
                    }
                    break;

                case I78:
                    if (s.equals(";")) {
                        reduccion(s, Reglas.R8);
                    }
                    break;

                case I79:
                    if (s.equals(";")) desplazamiento = desplazamiento(s, EstadosSintactico.I91);
                    break;

                case I80:
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R50);
                    }
                    break;

                case I81:
                    if (s.equals("id")) desplazamiento = desplazamiento(s, EstadosSintactico.I92);
                    break;

                case I82:
                    if (s.equals(")")) desplazamiento = desplazamiento(s, EstadosSintactico.I93);
                    break;

                case I83:
                    if (s.equals("for"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I130);
                    if (s.equals("if"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I131);
                    if (s.equals("let"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I132);
                    if (s.equals("read"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I133);
                    if (s.equals("return"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I134);
                    if (s.equals("write"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I135);
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I136);
                    if (s.equals("}")) {
                        reduccion(s, Reglas.R54); // C -> ε
                    }
                    break;

                case I84:
                    if (s.equals("for") || s.equals("function") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("$")) {
                        reduccion(s, Reglas.R4);
                    }
                    break;

                case I85:
                    if (s.equals("for") || s.equals("function") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("$")) {
                        reduccion(s, Reglas.R5);
                    }
                    break;

                case I86:
                    if (s.equals(";") || s.equals("-") || s.equals(">")) {
                        reduccion(s, Reglas.R43);
                    }
                    break;

                case I87:
                    if (s.equals(";")) {
                        reduccion(s, Reglas.R31);
                    }
                    break;

                case I88:
                    if (s.equals(";") || s.equals(">")) {
                        reduccion(s, Reglas.R36);
                    }
                    break;

                case I89:
                    if (s.equals("for") || s.equals("function") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("$")) {
                        reduccion(s, Reglas.R20);
                    }
                    break;

                case I90:
                    if (s.equals(","))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I76);
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R27);
                    }
                    break;

                case I91:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I97);
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R12);
                    }
                    break;

                case I92:
                    if (s.equals(","))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I99);
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R52);

                    }
                    break;

                case I93:
                    if (s.equals("{")) {
                        reduccion(s, Reglas.R48);
                    }
                    break;

                case I94:
                    if (s.equals("for"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I130);
                    if (s.equals("if"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I131);
                    if (s.equals("let"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I132);
                    if (s.equals("read"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I133);
                    if (s.equals("return"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I134);
                    if (s.equals("write"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I135);
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I136);
                    if (s.equals("}")) {
                        reduccion(s, Reglas.R54);
                    }
                    break;

                case I95:
                    if (s.equals("}"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I102);
                    break;

                case I96:
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R26);
                    }
                    break;

                case I97:
                    if (s.equals("+="))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I103);
                    if (s.equals("="))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I104);
                    break;

                case I98:
                    if (s.equals(")"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I105);
                    break;

                case I99:
                    if (s.equals("boolean"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I15);
                    if (s.equals("float"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I16);
                    if (s.equals("int"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I17);
                    if (s.equals("string"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I18);
                    break;

                case I100:
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R49);
                    }
                    break;

                case I101:
                    if (s.equals("}")) {
                        reduccion(s, Reglas.R53);
                    }
                    break;

                case I102:
                    if (s.equals("for") || s.equals("function") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("$")) {
                        reduccion(s, Reglas.R44);
                    }
                    break;

                case I103:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I112);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I113);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I114);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I115);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I116);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I117);
                    break;
                case I104:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I112);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I113);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I114);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I115);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I116);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I117);
                    break;

                case I105:
                    if (s.equals("read"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I5);
                    if (s.equals("return"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I6);
                    if (s.equals("write"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I7);
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I8);
                    break;

                case I106:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I110);
                    break;

                case I107:
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R11);
                    }
                    break;

                case I108:
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R10);
                    }
                    break;

                case I109:
                    if (s.equals("for") || s.equals("function") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("$")) {
                        reduccion(s, Reglas.R6);
                    }
                    break;

                case I110:
                    if (s.equals(","))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I99);
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R52);
                    }
                    break;

                case I111:
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R51);
                    }
                    break;

                case I112:
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I138);
                    if (s.equals(")") || s.equals("-") || s.equals(">")) {
                        reduccion(s, Reglas.R38);
                    }
                    break;

                case I113:
                    if (s.equals(")") || s.equals("-") || s.equals(">")) {
                        reduccion(s, Reglas.R39);
                    }
                    break;

                case I114:
                    if (s.equals(")") || s.equals("-") || s.equals(">")) {
                        reduccion(s, Reglas.R40);
                    }
                    break;

                case I115:
                    if (s.equals(")") || s.equals("-") || s.equals(">")) {
                        reduccion(s, Reglas.R41);
                    }
                    break;

                case I116:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I112);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I113);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I114);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I115);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I116);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I117);
                    break;
                case I117:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I112);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I113);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I114);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I115);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I116);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I117);
                    break;
                case I118:
                    if (s.equals(">")) desplazamiento = desplazamiento(s, EstadosSintactico.I141);
                    else if (s.equals(")")) {
                        reduccion(s, Reglas.R32);
                    }
                    break;
                case I119:
                    if (s.equals(")") || s.equals(">")) {
                        reduccion(s, Reglas.R34);
                    }
                    break;
                case I120:
                    if (s.equals("-")) desplazamiento = desplazamiento(s, EstadosSintactico.I143);
                    else if (s.equals(")") || s.equals(">")) {
                        reduccion(s, Reglas.R37);
                    }
                break;
                case I121:
                    if (s.equals("(")) desplazamiento = desplazamiento(s, EstadosSintactico.I145);
                    else if (s.equals(")") || s.equals(">") || s.equals("-") || s.equals(",")) {
                        reduccion(s, Reglas.R38);
                    }
                    break;
                case I122:
                    if (s.equals(")") || s.equals(">") || s.equals("-") || s.equals(",")) {
                        reduccion(s, Reglas.R39);
                    }
                    break;
                case I123:
                    if (s.equals(")") || s.equals(">") || s.equals("-") || s.equals(",")) {
                        reduccion(s, Reglas.R40);
                    }
                    break;
                case I124:
                    if (s.equals(")") || s.equals(">") || s.equals("-") || s.equals(",")) {
                        reduccion(s, Reglas.R41);
                    }
                    break;
                case I125:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I112);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I113);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I114);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I115);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I116);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I117);
                    break;
                case I126:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I121);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I122);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I123);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I124);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I125);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I126);
                    break;
                case I127:
                    if (s.equals(">")) desplazamiento = desplazamiento(s, EstadosSintactico.I148);
                    else if (s.equals(")") || s.equals(",")) {
                        reduccion(s, Reglas.R32);
                    }
                    break;
                case I128:
                    if (s.equals(")") || s.equals(">") || s.equals(",")) {
                        reduccion(s, Reglas.R34);
                    }
                    break;
                case I129:
                    if (s.equals("-")) desplazamiento = desplazamiento(s, EstadosSintactico.I150);
                    else if (s.equals(")") || s.equals(",") || s.equals(">")) {
                        reduccion(s, Reglas.R37);
                    }
                    break;
                case I130:
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I152);
                    break;
                case I131:
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I153);
                    break;

                case I132:
                    if (s.equals("boolean") || s.equals("float") || s.equals("int") || s.equals("string")) {
                        reduccion(s, Reglas.R13);
                    }
                    break;
                case I133:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I155);
                    break;

                case I134:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I25);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I26);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I27);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I28);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I29);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I30);
                    if (s.equals(";")) {
                        reduccion(s, Reglas.R29);
                    }
                    break;

                case I135:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I25);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I26);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I27);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I28);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I29);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I30);
                    break;

                case I136:
                    if (s.equals("+="))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I158);
                    if (s.equals("="))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I159);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I160);
                    break;

                case I137:
                    if (s.equals("for") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("}")) {
                        reduccion(s, Reglas.R7);
                    }
                    break;

                case I138:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I121);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I122);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I123);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I124);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I125);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I126);
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R25);
                    }
                    break;

                case I139:
                    if (s.equals(")"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I162);
                    break;

                case I140:
                    if (s.equals(")") || s.equals(">")) {
                        reduccion(s, Reglas.R33);
                    }
                    break;

                case I141:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I112);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I113);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I114);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I115);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I116);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I117);
                    break;

                case I142:
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R30);
                    }
                    break;

                case I143:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I112);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I113);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I114);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I115);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I116);
                    break;

                case I144:
                    if (s.equals(")") || s.equals(">")) {
                        reduccion(s, Reglas.R35);
                    }
                    break;

                case I145:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I121);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I122);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I123);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I124);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I125);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I126);
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R25);
                    }
                    break;

                case I146:
                    if (s.equals(")"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I166);
                    break;

                case I147:
                    if (s.equals(",") || s.equals(")") || s.equals(">")) {
                        reduccion(s, Reglas.R33);
                    }
                    break;

                case I148:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I121);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I122);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I123);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I124);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I125);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I126);
                    break;

                case I149:
                    if (s.equals(",") || s.equals(")")) {
                        reduccion(s, Reglas.R30);
                    }
                    break;

                case I150:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I121);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I122);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I123);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I124);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I125);
                    break;

                case I151:
                    if (s.equals(",") || s.equals(")") || s.equals(">")) {
                        reduccion(s, Reglas.R35);
                    }
                    break;

                case I152:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I43);
                    if (s.equals(";")) {
                        reduccion(s, Reglas.R9);
                    }
                    break;

                case I153:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I112);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I113);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I114);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I115);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I116);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I117);
                    break;

                case I154:
                    if (s.equals("boolean"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I15);
                    if (s.equals("float"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I16);
                    if (s.equals("int"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I17);
                    if (s.equals("string"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I18);
                    break;

                case I155:
                    if (s.equals(";"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I172);
                    break;

                case I156:
                    if (s.equals(";"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I173);
                    break;

                case I157:
                    if (s.equals(";"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I174);
                    break;

                case I158:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I25);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I26);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I27);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I28);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I29);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I30);
                    break;

                case I159:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I25);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I26);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I27);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I28);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I29);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I30);
                    break;

                case I160:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I121);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I122);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I123);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I124);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I125);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I126);
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R25);
                    }
                    break;

                case I161:
                    if (s.equals(")"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I178);
                    break;

                case I162:
                    if (s.equals(")") || s.equals("-") || s.equals(">")) {
                        reduccion(s, Reglas.R42);
                    }
                    break;

                case I163:
                    if (s.equals(">"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I141);
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R32);
                    }
                    break;

                case I164:
                    if (s.equals("-")) desplazamiento = desplazamiento(s, EstadosSintactico.I143);
                    if (s.equals(")") || s.equals(">")) {
                        reduccion(s, Reglas.R37);
                    }
                    break;

                case I165:
                    if (s.equals(")"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I181);
                    break;

                case I166:
                    if (s.equals(",") || s.equals(")") || s.equals("-") || s.equals(">")) {
                        reduccion(s, Reglas.R42);
                    }
                    break;

                case I167:
                    if (s.equals(">"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I148);
                    if (s.equals(",") || s.equals(")")) {
                        reduccion(s, Reglas.R32);
                    }
                    break;

                case I168:
                    if (s.equals("-"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I150);
                    if (s.equals(",") || s.equals(")") || s.equals(">")) {
                        reduccion(s, Reglas.R37);
                    }
                    break;

                case I169:
                    if (s.equals(";"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I184);
                    break;

                case I170:
                    if (s.equals(")"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I185);
                    break;

                case I171:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I186);
                    break;

                case I172:
                    if (s.equals("for") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("}")) {
                        reduccion(s, Reglas.R22);
                    }
                    break;

                case I173:
                    if (s.equals("for") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("}")) {
                        reduccion(s, Reglas.R23);
                    }
                    break;

                case I174:
                    if (s.equals("for") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("}")) {
                        reduccion(s, Reglas.R21);
                    }
                    break;

                case I175:
                    if (s.equals(";"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I187);
                    break;

                case I176:
                    if (s.equals(";"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I188);
                    break;

                case I177:
                    if (s.equals(")"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I189);
                    break;

                case I178:
                    if (s.equals(")") || s.equals("-") || s.equals(">")) {
                        reduccion(s, Reglas.R43);
                    }
                    break;

                case I179:
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R31);
                    }
                    break;

                case I180:
                    if (s.equals(")") || s.equals(">")) {
                        reduccion(s, Reglas.R36);
                    }
                    break;

                case I181:
                    if (s.equals(",") || s.equals(")") || s.equals("-") || s.equals(">")) {
                        reduccion(s, Reglas.R43);
                    }
                    break;

                case I182:
                    if (s.equals(",") || s.equals(")")) {
                        reduccion(s, Reglas.R31);
                    }
                    break;

                case I183:
                    if (s.equals(",") || s.equals(")") || s.equals(">")) {
                        reduccion(s, Reglas.R36);
                    }
                    break;

                case I184:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I25);
                    if (s.equals("entero"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I26);
                    if (s.equals("real"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I27);
                    if (s.equals("cadena"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I28);
                    if (s.equals("("))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I29);
                    if (s.equals("!"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I30);
                    break;

                case I185:
                    if (s.equals("read"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I133);
                    if (s.equals("return"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I134);
                    if (s.equals("write"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I135);
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I136);
                    break;

                case I186:
                    if (s.equals(";"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I192);
                    break;

                case I187:
                    if (s.equals("for") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("}")) {
                        reduccion(s, Reglas.R19);
                    }
                    break;

                case I188:
                    if (s.equals("for") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("}")) {
                        reduccion(s, Reglas.R18);
                    }
                    break;

                case I189:
                    if (s.equals(";"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I193);
                    break;

                case I190:
                    if (s.equals(";"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I194);
                    break;

                case I191:
                    if (s.equals("for") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("}")) {
                        reduccion(s, Reglas.R4);
                    }
                    break;

                case I192:
                    if (s.equals("for") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("}")) {
                        reduccion(s, Reglas.R5);
                    }
                    break;

                case I193:
                    if (s.equals("for") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("}")) {
                        reduccion(s, Reglas.R20);
                    }
                    break;

                case I194:
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I97);
                    if (s.equals(")")) {
                        reduccion(s, Reglas.R12);
                    }
                    break;

                case I195:
                    if (s.equals(")"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I196);
                    break;

                case I196:
                    if (s.equals("read"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I133);
                    if (s.equals("return"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I134);
                    if (s.equals("write"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I135);
                    if (s.equals("id"))
                        desplazamiento = desplazamiento(s, EstadosSintactico.I136);
                    break;

                case I197:
                    if (s.equals("for") || s.equals("if") ||
                            s.equals("let") || s.equals("read") || s.equals("return") ||
                            s.equals("write") || s.equals("id") || s.equals("}")) {
                        reduccion(s, Reglas.R6);
                    }
                    break;
            }//fin switch
        }//fin while
    }//fin comprobar_Token
    // TODO
    public void end() {
        parser.close();
        pasos.close();
    }
    // TODO
    public String mostrar_lista (List l) {
        String s = "";
        for (int i = 0; i < l.size(); i++) {
            s = s + l.get(i)+", ";
        }
        return s;
    }
}
