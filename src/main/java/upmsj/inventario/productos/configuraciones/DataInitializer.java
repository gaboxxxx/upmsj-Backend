package upmsj.inventario.productos.configuraciones;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import upmsj.inventario.productos.modelo.Usuario;
import upmsj.inventario.productos.respositorio.UsuarioRepository;

@Component
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;

    public DataInitializer(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostConstruct
    public void init() {
        
    }

}