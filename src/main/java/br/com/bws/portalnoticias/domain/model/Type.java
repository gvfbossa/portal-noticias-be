package br.com.bws.portalnoticias.domain.model;

public enum Type {
    HIGHLIGHT("Destaque"),
    COMMON("Comum");

    private final String displayName;

    Type(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return displayName;
    }
}
