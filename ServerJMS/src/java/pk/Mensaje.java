package pk;

import java.io.Serializable;

public class Mensaje implements Serializable{
    int op, valor;
    
    public Mensaje(int op, int valor){
        this.op=op;
        this.valor=valor;
    }
}

