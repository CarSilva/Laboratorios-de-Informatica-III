
import java.io.Serializable;

/**
 * Esta classe tem toda a informação relativa a um produto, a sua faturação ( custo),
 * a quantidade que foi vendida e o numero de vendas.
 * 
 * @author Carlos
 */
public class DadosProduto implements Serializable{
    // Variáveis de instância
    
    /**
     * Faturação de um determinado produto
     */
    private double faturacao;
    
    /**
     * Quantidade comprada de um determinado produto 
     */
    private int quantidade;
    
    /**
     * Numero de vendas de um determinado produto
     */
    private int numeroVendas;

    // Construtores
    
    /**
     * Construtor por parametro da Classe DadosProduto, atualiza as variáveis de instância
     * atraves de uma venda.
     * @param v A venda recebida
     */
    public DadosProduto(Venda v) {
        this.faturacao = v.getQuantidade() * v.getPreco();
        this.quantidade = v.getQuantidade();
        this.numeroVendas = 1;
    }
    
    /**
     * Método que devolve a faturação ( associada a um produto)
     * 
     * @return O valor da faturação
     */
    public double getFaturacao() {
        return faturacao;
    }

    /**
     * Método que devolve o número de vendas( asociadas a um produto)
     * 
     * @return O número de vendas 
     */
    public int getNumeroVendas() {
        return numeroVendas;
    }
    
    /**
     * Método que devolve a quantidade comprada( asociada a um produto)
     * 
     * @return A quantidade comprada 
     */
    public int getQuantidade() {
        return quantidade;
    }
    
    /**
     * Método que incrementa a quantidade comprada.
     * 
     * @param quantidade A quantidade a somar á existente
     */
    public void incQuantidade(int quantidade){
        this.quantidade += quantidade;
    }
    
    /**
     * Método que incrementa a faturação.
     * 
     * @param faturacao A faturaçaõ a somar á existente
     */
    public void incFaturacao(double faturacao){
        this.faturacao += faturacao;
    }
    
    /**
     * Método que incrementa a o numero de vendas.
     */
    public void incNumeroVendas(){
        this.numeroVendas++;
    }
}
