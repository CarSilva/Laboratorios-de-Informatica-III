
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Esta classe implementa uma associação entre um cliente e a faturação  
 * 
 * @author Grupo28
 */

public class ParClienteFaturacao implements Comparable, Serializable {
    //Variáveis de instância
    
    /**
     * O cliente 
     */
    private String cliente;
    
    /**
     * A faturação
     */
    private double faturacao;

    //Construtores
    
    /**
     * Construtor por parâmetro da classe, atualiza os campos da classe
     * 
     * @param cliente O cliente a atribui
     * @param faturacao A faturação a atribuir
     */
    public ParClienteFaturacao(String cliente, double faturacao) {
        this.cliente = cliente;
        this.faturacao = faturacao;
    }
    
    public ParClienteFaturacao(ParClienteFaturacao p){
        this.cliente = p.getCliente();
        this.faturacao = p.getFaturacao();
    }
    
    /**
     * Método que devolve o cliente
     * 
     * @return A string com o código de cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * Método que devolve a faturação associada a um cliente (e um produto)
     * 
     * @return A faturação
     */
    public double getFaturacao() {
        return faturacao;
    }
    
    /**
     * Método que atualiza a faturação
     * 
     * @param faturacao A faturação a somar á existente
     */
    public void incFaturacao(double faturacao){
        this.faturacao += faturacao;
    }

    /**
     * Método que implementa a passagem para string dos campos da classe
     * 
     * @return A string resultante
     */
    public String toString() {
        return cliente + " " + faturacao;
    }
    
    /**
     * Método que compara a classe com outra, dando prioridade á faturação
     * 
     * @param o O objeto a comparar
     * @return 1, 0 ou -1 se a faturação for maior, igual ou menor
     */
    public int compareTo (Object o){
        ParClienteFaturacao p = (ParClienteFaturacao) o;
        if (this.faturacao == p.getFaturacao()){
            return this.cliente.compareTo(p.getCliente());
        }
        return (Math.negateExact(((Double) this.faturacao).compareTo(p.getFaturacao())));
    }

    /**
     * Método que gera o HashCode
     * 
     * @return o hashcode
     */
    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.cliente, this.faturacao});
    }

    /**
     * Método comparador da classe
     * 
     * @param obj  O objeto a comparar
     * @return true se for igual, false caso contrário
     */
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
        ParClienteFaturacao other = (ParClienteFaturacao) obj;
        
        return this.cliente.equals(other.getCliente()) && this.faturacao == other.getFaturacao();
    }
    
    /**
     * Método que gera um clone da classe 
     * 
     * @return Um clone da classe
     */
    public ParClienteFaturacao clone(){
        return new ParClienteFaturacao(this);
    }
}
