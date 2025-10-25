import java.util.ArrayList;
import java.util.List;

public class Error{

    // Atributos
    private int linea;
    private String tipo;
    private String mensaje;

    // Constructor
    public Error(int linea, String tipo, String mensaje) {
        this.linea = linea;
        this.tipo = tipo;
        this.mensaje = mensaje;
    }

    public int getLinea() {
        return this.linea;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getMensaje() {
        return this.mensaje;
    }
    @Override
    public String toString() {
        return "Linea " + this.linea + " " + this.tipo + ": " + this.mensaje + "\n";
    }

}