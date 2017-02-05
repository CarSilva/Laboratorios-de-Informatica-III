
import java.io.Serializable;

/**
 * Esta classe implementa um par constituido por uma quantidade e faturação
 * associados a um produto por definir.
 * 
 * @author Grupo28
 */
public class ParQuantidadeFaturacao implements Serializable{
    // Variáveis de instância
    
    /**
     * A quantidade comprada do produto por definir
     */
    private int quantidade;
    
    /**
     * A faturação do produto por definir
     */
    private double faturacao;

    //Construturores
    
    /**
     * Construtor por parâmetro da classe
     * 
     * @param quantidade A quantidade a atribuir
     * @param faturacao A faturação a atribuir
     */
    public ParQuantidadeFaturacao(int quantidade, double faturacao) {
        this.quantidade = quantidade;
        this.faturacao = faturacao;
    }
    
    /**
     * Métodos da classe 
     */

    /**
     * Método que devolve a quantidade comprada por um produto nao definido
     * 
     * @return A quantidade comprada
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Método que devolve a faturação de um produto nao definido
     * 
     * @return A faturação
     */
    public double getFaturacao() {
        return faturacao;
    }
    
    /**
     * Método que atualiza a quantidade
     * 
     * @param quantidade A quantidade a ser adicionada
     */
    public void incQuantidade (int quantidade){
        this.quantidade += quantidade;
    }
    
    /**
     * Método que atualiza a faturação
     * @param faturacao A faturação a ser adicionada
     */
    public void incFaturacao(double faturacao){
        this.faturacao += faturacao;
    }
}
