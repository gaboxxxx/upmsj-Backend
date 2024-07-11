package upmsj.inventario.productos.excepcion;

public class DonanteNoEncontradoExcepcion  extends RuntimeException{

    public DonanteNoEncontradoExcepcion(String mensaje){
        super(mensaje);
    }
}
