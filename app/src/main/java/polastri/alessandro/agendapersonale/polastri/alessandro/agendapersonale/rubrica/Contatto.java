package polastri.alessandro.agendapersonale.polastri.alessandro.agendapersonale.rubrica;

class Contatto {

    static final String NOME_TABELLA = "contatti";
    static final String CAMPO_ID = "_id";
    static final String CAMPO_NOME = "nome";
    static final String CAMPO_COGNOME = "cognome";
    static final String CAMPO_TELEFONO = "telefono";
    static final String CAMPO_EMAIL = "email";
    static final String CAMPO_TIPO = "tipo";

    private Contatto() {

        throw new IllegalStateException("Utility class");
    }
}
