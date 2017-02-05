
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Esta classe implementa um par que contém um cliente e um conjunto de produtos
 * 
 * @author Grupo28
 */
public class ParClienteLista implements Comparable, Serializable{
    //Variáveis de instância
    
    /**
     * O código de cliente
     */
    private String cliente;
    
    /**
     * O conjunto de produtos
     */
    private Set<String> produtos;

    //Construtores
    
    /**
     * Construtor por parâmetros da classe
     * 
     * @param cliente O cliente a atribuir
     * @param l O conjunto a atribuir
     */
    public ParClienteLista(String cliente, Set<String> l) {
        this.cliente = cliente;
        this.produtos = new HashSet<>(l);
    }

    /**
     * Métodos da classe
     */
    
    /**
     * Método que devolve o cliente
     * 
     * @return A string com o código de cliente
     */
    public String getCliente() {
        return cliente;
    }

    public Set<String> getProdutos() {
        Set<String> s = new HashSet<>(produtos);
        return s;
    }
    
    /**
     * Método que determina o tamanho do conjunto de produtos
     * 
     * @return Quantos produtos existem
     */
    public int getSize(){
        return produtos.size();
    }

    /**
     * Método que implementa a apresentação da informção da classe
     * 
     * @return A string resultante
     */
    public String toString() {
        return cliente + " " + produtos.size();
    }
    
    /**
     * Método que adiciona um produto ao conjunto dos produtos existentes
     * 
     * @param produto O produto a adicionar
     */
    public void addProduto(String produto){
        produtos.add(produto);
    }

    /**
     * Método que compara a classe com prioridade no tamanho do conjunto dos produtos
     * 
     * @param o O objeto a comparar 
     * @return 1, 0 ou -1, caso o tamanho seja maior, igual ou menor
     */
    public int compareTo (Object o){
        ParClienteLista p = (ParClienteLista) o;
        if (this.produtos.size() == p.getSize()){
            return this.cliente.compareTo(p.getCliente());
        }
        return (Math.negateExact(((Integer) this.produtos.size()).compareTo(p.getSize())));
    }

    
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
        ParClienteLista other = (ParClienteLista) obj;
        
        return this.cliente.equals(other.getCliente()) && this.produtos.equals(other.getProdutos());
    }
    
    /**
     * Método que gera o HashCode
     * @return O hascode
     */
    public int hashCode(){
        return Arrays.hashCode(new Object[]{this.cliente, this.produtos});
    }
   
}
