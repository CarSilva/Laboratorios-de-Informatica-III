
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Esta classe implementa um trio constituído por um código de produto, quantidade e
 * um conjunto de clientes. Esta classe vem facilitar a implementação do projeto.
 * 
 * @author Grupo28
 */
public class TriploProdutoQuantidadeLista implements Comparable, Serializable{
    // Variáveis de instância
    /**
     * O código de produto
     */
    private String produto;
    
    /**
     * A quantidade
     */
    private int quantidade;
    
    /**
     *  Conjunto de códigos de clientes
     */
    private Set<String> clientes;

    //Construtores
    
    /**
     * Construtor por parâmetro da classe
     * 
     * @param produto O código de produto a atribui
     * @param quantidade A quantidade a atribuir
     * @param clientes O conjunto de clientes a atribuir
     */
    public TriploProdutoQuantidadeLista(String produto, int quantidade, Set<String> clientes) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.clientes = new HashSet<>();
        this.clientes.addAll(clientes);
    }
    
    /**
     * Métodos de classe
     */
    
    /**
     * Método que devolve a quantidade  
     * 
     * @return A quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    public String getProduto() {
        return produto;
    }
    
    /**
     * Método que transforma a informação da classe numa string
     * 
     * @return A string resultante
     */
    public String toString() {
        return produto + " " + quantidade + " " + this.clientes.size();
    }
    
    /**
     * Método que compara a classe por quantidade
     * 
     * @param o O objeto a comparar
     * @return 1, 0 ou -1 se for maior, igual ou menor
     */
    public int compareTo (Object o){
        TriploProdutoQuantidadeLista t = (TriploProdutoQuantidadeLista) o;
        if (this.quantidade == t.getQuantidade()){
            return this.produto.compareTo(t.getProduto());
        }
        return Math.negateExact(((Integer) quantidade).compareTo(t.getQuantidade()));
    }
    
    /**
     * Método que gera o HashCode da classe
     * @return 
     */
    public int hashCode(){
        return Arrays.hashCode(new Object[]{this.produto, this.quantidade, this.clientes});
    }
}
