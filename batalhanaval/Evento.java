package batalhanaval;

/**
 * Representa um evento do jogo.
 *
 * @author Darlan P. de Campos
 * @version 1.0
 *
 */

public class Evento {
   private String mensagem;

   public Evento (String mensagem) {
        this.mensagem = mensagem;   
   }

   public String getMensagem() {
        return this.mensagem;
   }
}

