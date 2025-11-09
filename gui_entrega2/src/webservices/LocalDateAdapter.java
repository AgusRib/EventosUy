package webservices;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adaptador para convertir entre String y java.time.LocalDate
 * para el correcto funcionamiento con JAX-WS
 */
public class LocalDateAdapter extends XmlAdapter<String, java.time.LocalDate> {

    @Override
    public java.time.LocalDate unmarshal(String dateString) throws Exception {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        return java.time.LocalDate.parse(dateString);
    }

    @Override
    public String marshal(java.time.LocalDate localDate) throws Exception {
        if (localDate == null) {
            return null;
        }
        return localDate.toString();
    }
}