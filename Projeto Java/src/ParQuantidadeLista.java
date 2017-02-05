
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Esta classe implementa um par que é constituído por uma quantidade e um conjunto
 * de clientes que compraram determinado produto.
 * 
 * @author Grupo28
 */
public class ParQuantidadeLista implements Serializable{
    // Variáveis de instância
    
    /**
     * A quantidade comprada do produto
     */
    private int quantidade;
    
    /**
     * O conjunto de clientes que compraram
     */
    private Set<String> clientes;

    //Contrutores
    
    /**
     * Construtor por parâmetro da classe.
     * 
     * @param quantidade A quantidade de um produto
     * @param cliente O cliente que vamos adicionar
     */
    public ParQuantidadeLista(int quantidade, String cliente) {
        this.quantidade = quantidade;
        this.clientes = new HashSet<>();
        this.clientes.add(cliente);
    }

    /**
     * Métodos de classe
     */
    
    /**
     * Método que retorna a quantidade comprada de um produto
     * 
     * @return O total da quantidade 
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Método que retorna a lista de clientes que compraram determiando produto
     * 
     * @return Um conjunto de códigos de clientes
     */
    public Set<String> getLista() {
        Set<String> s = new HashSet<>();
        s.addAll(clientes);
        return s;
    }
    
    /**
     * Método que atualiza a quantidade
     * 
     * @param quantidade A quantidade a atualizar
     */
    public void incQuantidade(int quantidade){
        this.quantidade += quantidade;
    }
    
    /**
     * Método que adiciona um cliente ao conjunto de clientes
     * 
     * @param cliente O cliente a adicionar
     */
    public void addLista(String cliente){
        this.clientes.add(cliente);
    }
    
    /**
     * Método que adiciona ao conjunto de clientes ao conjunto de clientes existente
     * 
     * @param clientes O conjunto de clientes
     */
    public void addLista(Set<String> clientes){
        this.clientes.addAll(clientes);
    }
    
}
