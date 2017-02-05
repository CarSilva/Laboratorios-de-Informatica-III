
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Esta classe implementa um trio constituído por um código de cliente, quantidade e
 * faturação. Tudo isto associado a um produto. Esta classe vem facilitar
 * a implementação do projeto.
 * 
 * @author Grupo28
 */
public class TriploClienteQuantidadeFaturacao implements Comparable, Serializable{
    //Variáveis de instância
    
    /**
     * O código de cliente
     */
    private String cliente;
    
    /**
     * A quantidade
     */
    private int quantidade;
    
    /**
     * A faturação
     */
    private double faturacao;

    // Construtores
    
    /**
     * Construtor por parâmetro da classe
     * 
     * @param cliente O código de cliente a atribuir
     * @param quantidade A quantidade a atribuir
     * @param faturacao A faturação a atribuir
     */
    public TriploClienteQuantidadeFaturacao(String cliente, int quantidade, double faturacao) {
        this.cliente = cliente;
        this.quantidade = quantidade;
        this.faturacao = faturacao;
    }

    /**
     * Métodos de classe 
     */
    
    /**
     * Método que devolve um cliente
     * 
     * @return A string com o código de cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * Método que devolve a quantidade comprada
     * 
     * @return A quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Método que devolve a faturaçao
     * 
     * @return O valor da faturação
     */
    public double getFaturacao() {
        return faturacao;
    }

    /**
     * Método que transforma a informação da classe numa string
     * 
     * @return A string resultante
     */
    public String toString() {
        return cliente + " " +quantidade + " " + faturacao;
    }
    
    /**
     * Método que atualiza a quantidade
     * 
     * @param quantidade A quantidade a ser adicionada
     */
    public void incQuantidade(int quantidade){
        this.quantidade += quantidade;
    }
    
    /**
     * Método que atualiza a faturação 
     * 
     * @param faturacao A faturação a ser adicionada
     */
    public void incFaturacao(double faturacao){
        this.faturacao += faturacao;
    }
    
    /**
     * Método que compara a classe, com prioridade para a quantidade e depois para o código de cliente
     * 
     * @param o O objeto a ser comparado
     * @return 1, 0 ou -1 caso seja maior, igual ou menor
     */
    public int compareTo (Object o){
        TriploClienteQuantidadeFaturacao t = (TriploClienteQuantidadeFaturacao) o;
        if (this.quantidade == t.getQuantidade()){
            return cliente.compareTo(t.getCliente());
        }
        return (Math.negateExact(((Integer) this.quantidade).compareTo(t.getQuantidade())));
    }
    
    /**
     * Método que gera o HashCode da classe
     * 
     * @return O hashcode
     */
    public int hashCode(){
        return Arrays.hashCode(new Object[]{this.cliente, this.quantidade, this.faturacao});
    }

    /**
     * Método comparador da classe
     * 
     * @param obj O objeto a comparar
     * @return true se for igual e false caso contrário
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TriploClienteQuantidadeFaturacao other = (TriploClienteQuantidadeFaturacao) obj;
        
        return this.cliente.equals(other.getCliente()) && this.quantidade == other.getQuantidade() && this.faturacao == other.getFaturacao();
    }
    
    
}
