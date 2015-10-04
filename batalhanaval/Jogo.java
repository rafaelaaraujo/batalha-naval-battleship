package batalhanaval;

import java.io.Serializable;
import java.util.ArrayList;

import enuns.Estado;
import eventos.Evento;

public class Jogo implements Serializable {

    private static final long serialVersionUID = 1L;


    private Jogador[] jogadores;

    private Estado estado;

    // Fila de eventos
    private ArrayList<Evento> eventos;

    public Jogo() {
        jogadores = new Jogador[2];
        jogadores[0] = new Jogador(this);
        jogadores[1] = new Robo(this);

        eventos = new ArrayList<Evento>();

		setEstado(Estado.POSICIONANDO_NAVIOS);
    }

    public void setEstado(Estado estado) {
        if (estado == Estado.POSICIONANDO_NAVIOS) {
            this.addEvento("Prepare-se! A batalha vai começar!");
        } else if (estado == Estado.TERMINADO) {
            if (this.getVencedor() == jogadores[0])
                addEvento("A batalha terminou! Você foi derrotado!");
            else
                addEvento("A batalha terminou! Você venceu!");
        }

        this.estado = estado;
    }

    
    public Jogador getJogador(int i) {
        return jogadores[i];
    }
    
    public Estado getEstado() {
        return estado;
    }

    public Jogador getVencedor () {
        if (this.estado != Estado.TERMINADO)
            return null;

        return this.jogadores[0].getFrotaRestante().size() == 0
            ? jogadores[1]
            : jogadores[0];           
    }

    /**
     * Adiciona um evento ao final da fila.
     *
     * @param mensagem A mensagem do evento
     */
    public void addEvento (String mensagem) {
        this.eventos.add(new Evento(mensagem));
    }

    /**
     * Remove e retorna o primeiro evento da fila.
     *
     */
    public Evento getEvento () {
        if (eventos.size() > 0)
            return eventos.remove(0);
        else
            return null;
    }
}
