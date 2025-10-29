import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Conversor {

    public static <T> Set<T> convertirASetJava(webservices.AbstractSet conjuntoWebService) {
        Set<T> conjuntoJava = new HashSet<>();
        if (conjuntoWebService != null) {
            conjuntoJava.addAll((Collection<? extends T>) conjuntoWebService);
        }
        return conjuntoJava;
    }

    public static List<String> convertirAListaJava(webservices.AbstractList listaWebService) {
        List<String> listaJava = new ArrayList<>();
        if (listaWebService != null) {
            listaJava.addAll((Collection<? extends String>) listaWebService);
        }
        return listaJava;
    }

    public static List<String> convertirAArrayListJava(webservices.ArrayList arrayListWebService) {
        List<String> arrayListJava = new ArrayList<>();
        if (arrayListWebService != null) {
            arrayListJava.addAll((Collection<? extends String>) arrayListWebService);
        }
        return arrayListJava;
    }

    public static Collection<String> convertirACollectionJava(webservices.AbstractCollection coleccionWebService) {
        Collection<String> coleccionJava = new ArrayList<>();
        if (coleccionWebService != null) {
            coleccionJava.addAll((Collection<? extends String>) coleccionWebService);
        }
        return coleccionJava;
    }
    
    public static <T> HashSet<T> convertirAHashSetJava(webservices.HashSet conjuntoWebService) {
        HashSet<T> conjuntoJava = new HashSet<>();
        if (conjuntoWebService != null) {
            conjuntoJava.addAll((Collection<? extends T>) conjuntoWebService);
        }
        return conjuntoJava;
    }
    
}
