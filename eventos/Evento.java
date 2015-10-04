package eventos;

public class Evento {
   private String mensagem;

   public Evento (String mensagem) {
        this.mensagem = mensagem;   
   }

   public String getMensagem() {
        return this.mensagem;
   }
}

