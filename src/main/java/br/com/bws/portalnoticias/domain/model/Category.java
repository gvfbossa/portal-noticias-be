package br.com.bws.portalnoticias.domain.model;

public enum Category {
    GERAL("Geral"),
    POLITICA("Política"),
    POLICIAL("Policial"),
    ESPORTES("Esportes"),
    CULTURA("Cultura"),
    EMPREGO("Emprego"),
    ACHADOS_E_PERDIDOS("Achados e Perdidos"),
    PRODUTOS_SERVICOS("Produtos e Serviços"),
    RECLAMACOES("Reclamações");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return displayName;
    }
}
