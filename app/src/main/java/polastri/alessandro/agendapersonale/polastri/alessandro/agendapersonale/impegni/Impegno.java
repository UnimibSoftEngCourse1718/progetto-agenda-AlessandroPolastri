package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.impegni;

class Impegno {

    static final String NOME_TABELLA = "impegni";
    static final String CAMPO_ID = "_id";
    static final String CAMPO_OGGETTO = "oggetto";
    static final String CAMPO_DATA = "data";
    static final String CAMPO_ORA_INIZIO = "ora_iniziale";
    static final String CAMPO_ORA_FINALE = "ora_finale";
    static final String CAMPO_RIPETIZIONE = "ripetizione";
    static final String CAMPO_ALLARME = "allarme";
    static final String CAMPO_NOTE = "note";
    static final String CAMPO_TIPO = "tipo";

    private Impegno() {

        throw new IllegalStateException("Utility class");
    }
}
