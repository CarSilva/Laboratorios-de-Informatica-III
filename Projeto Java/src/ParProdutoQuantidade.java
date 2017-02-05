
import java.io.Serializable;
import java.util.Arrays;

/**
 * Esta classe implementa um par constituído por um produto e por uma quantidade,
 * associada a ele.
 * 
 * @author Grupo28
 */

public class ParProdutoQuantidade implements Comparable, Serializable{
    //Variáveis de instância
    
    /**
     * O código de produto
     */
    private String produto;
    
    /**
     * A quantidade comprada do produto 
     */
    private int quantidade;

    //Construtores
    
    /**
     * Construtor por parâmetro da clase
     * 
     * @param produto O produto a atribuir
     * @param quantidade A quantidade a atribuir
     */
    public ParProdutoQuantidade(String produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }
    
    /**
     * Construtor por cópia da classe
     * @param p 
     */
    public ParProdutoQuantidade(ParProdutoQuantidade p){
        this(p.getProduto(), p.getQuantidade());
    }

    /**
     * Métodos da classe 
     */
    
    /**
     * Método que devolve o código de produto
     * 
     * @return Uma string com o código de cliente
     */
    public String getProduto() {
        return produto;
    }
    
    /**
     * Método que devolve a quantidade comprada do produto
     * 
     * @return A quantidade do produto
     */
    public int getQuantidade() {
        return quantidade;
    }
    
    /**
     * Método que atualiza a quantidade
     * 
     * @param quantidade A quantidade a adicionar
     */
    public void incQuantidade (int quantidade){
        this.quantidade += quantidade;
    }

    /**
     * Método que implementa a transformação da informação da classe numa string
     * 
     * @return A string resultante
     */
    public String toString() {
        return produto + " " + quantidade;
    }
    
    /**
     * Método que compara a classe, dando prioriadade á quantidade e depois ao código de produto
     * 
     * @param o O objeto a comparar
     * @return 1, 0 ou -1 se for maior, igual ou menor
     */
    public int compareTo (Object o){
        ParProdutoQuantidade p = (ParProdutoQuantidade) o;
        if (this.quantidade == p.getQuantidade()){
            return produto.compareTo(p.getProduto());
        }
        return (Math.negateExact(((Integer) this.quantidade).compareTo(p.getQuantidade())));
    }
    
    /**
     * Método que ger o HashCode da classe
     * @return O valor do hashcode
     */
    public int hashCode(){
        return Arrays.hashCode(new Object[]{this.quantidade, this.produto});
    }
    
    /**
     * Método comparador da classe
     * 
     * @param obj O objeto a comparar
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
        ParProdutoQuantidade other = (ParProdutoQuantidade) obj;
        
        return this.quantidade == other.getQuantidade() && this.produto.equals(other.getProduto());
    }
    
    /**
     * Método que devolve um clone da classe
     * @return Um clone da classe
     */
    public ParProdutoQuantidade clone(){
        return new ParProdutoQuantidade(this);
    }
    
}
