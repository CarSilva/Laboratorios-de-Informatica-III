import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;

/**
 * Esta classe implementa o Catálogo de Produtos
 * 
 * @author Grupo28
 */
public class CatalogoProdutos implements Serializable{
    //Variáveis de instância
    
    /**
     * Map que associa uma letra(p.e "A"), a um conjunto de códigos de produto que começam por essa letra
     */
    private Map <String, Set<String>> produtos;
    
    //Construtures
    
    /**
     * Construtor vazio da classe CatalogoProdutos que inicializa, devidamente as variaveis de instância
     */
    public CatalogoProdutos(){
        produtos = new HashMap<>();
        for(int i = 65; i <= 90; i++){
            produtos.put(Character.toString((char)i), new TreeSet<>());
        }
    }
    
    /**
     * Construtor da classe CatalogoProdutos que a partir de um Map compatível com a classe,
     * atribui todas as informações desse Map à classe.
     * 
     * @param prod O Map recebido, que associa letras do alfabeto a codigos de cliente
     */
    public CatalogoProdutos(Map <String, Set<String>> prod){
        this.produtos = new HashMap<>();
        for (String s : prod.keySet()){
            Set<String> ts = new TreeSet<>();
            for (String ss : prod.get(s)){
                ts.add(ss);
            }
            produtos.put(s, ts);
        }
    }
    
    /**
     * Construtor por cópia da classe CatalogoProdutos
     * 
     * @param cc O CatalogoProdutos recebido
     */
    public CatalogoProdutos(CatalogoProdutos cc){
        Set<String> ss = cc.getProdutos();
        this.produtos = new HashMap<>();
        for (String s: ss){
            add(s);
        }
    }
    
    /**
     * Devolve todos os produtos que sao validos, ou seja, 
     * que existem ou estao registados no CatalogoProdutos
     * 
     * @return Todos os produtos que existem 
     */
    public Set<String> getProdutos() {
        Set<String> prod = new TreeSet<>();
        for (Set<String> s : produtos.values()){
            prod.addAll(s);
        }
        return prod;
    }
    
    /**
     * Determina quantos produtos existem
     * 
     * @return O número de produtos existentes
     */
    public int sizeTotal(){
        int size = 0;
        for(Set<String> me : produtos.values()){
            size += me.size();
        }
        return size;
    }
    
    /**
     * Determina quantos produtos existem começados por uma dada letra.
     * 
     * @param letra A letra que pretendemos analisar
     * @return A quantidade de produtos dessa letra
     */
    public int sizeTotalLetra(String letra){
        return produtos.get(letra).size();
    }
    
    /**
     * Vê se um dado produto existe.
     * 
     * @param produto O produto a analizar
     * @return true se o produto existir, false caso contrário
     */
    public boolean existe(String produto){
        return produtos.get(Character.toString(produto.charAt(0))).contains(produto);
    }
    
    /**
     * Adiciona um produto.
     * 
     * @param produto O produto que queremo adicionar
     */
    public void add(String produto){
        String s = Character.toString(produto.charAt(0));
        produtos.get(s).add(produto);
    }
    
    /**
     * Método que clona um objecto da classe
     * 
     * @return Um clone da classe
     */  
    public CatalogoProdutos clone(){
        return new CatalogoProdutos(this);
    }
    
    /**
     * Método que testa se uma classe é igual a outra.
     * Testa todos os seus campos 
     * 
     * @param o O Objecto que vamos comparar
     * @return true se for igual, false caso contrário
     */
    public boolean equals (Object o){
        if(o == this)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        CatalogoProdutos cc = (CatalogoProdutos) o;
        return this.produtos.equals(cc.getProdutos());
    }
}
    

