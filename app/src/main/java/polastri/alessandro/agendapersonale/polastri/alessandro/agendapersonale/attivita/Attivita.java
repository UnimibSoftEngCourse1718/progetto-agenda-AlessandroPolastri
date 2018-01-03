package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.attivita;

public class Attivita {

    static final String NOME_TABELLA = "attivita";
    static final String CAMPO_ID = "_id";
    static final String CAMPO_OGGETTO = "oggetto";
    static final String CAMPO_INIZIO = "inizio";
    static final String CAMPO_FINE = "fine";
    static final String CAMPO_PRIORITA = "priorita";

    private Attivita(){

        throw new IllegalStateException("Utility class");
    }
}
