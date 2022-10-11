package it.sisca.dunkest.dunkestsimulator;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(fluent = true)
@Builder
@Getter
public class Giocatore {

    private String nome;
    private String squadra;
    private String ruolo;
    private Double crediti;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Giocatore giocatore)) return false;
        return nome.equals(giocatore.nome) && squadra.equals(giocatore.squadra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, squadra);
    }

    @Override
    public String toString() {
        return nome;
    }
}
