import java.util.ArrayList;
import java.util.List;

public class Token {

    // Atributos
    private String codigo;
    private String atributo;


    // Constructor
    public Token(String codigo, String atributo) {
        this.codigo = codigo;
        this.atributo = atributo;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public String getAtributo() {
        return this.atributo;
    }
}
