
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Esta classe contem os dados que um cliente deve ter, os produtos comprados associados,
 * a quantos comprou e quanto gastou neles
 * 
 * @author Grupo28
 */
public class DadosCliente implements Serializable{
    //Variáveis de instância
    
    /**
     * Map com o código de produto e os dados associados ao mesmo,
     * a sua faturação, quantidade comprada e número de vendas.
     */
    private Map<String, DadosProduto> dados;
    
    //Construtores
    
    /**
     * Construtor vazio da classe DadosCliente, inicializa as variáveis de instância.
     */
    public DadosCliente(){
        dados = new HashMap<>();
    }
    
    /**
     * Construtos por paramentros da classe DadosClientes, recebe uma venda e atualiza a classe com ela.
     * 
     * @param v 
     */
    public DadosCliente(Venda v){
        this();
        insere(v);
    }
    
    
    /**
     * Atualiza as variaveis de instância ao inserir uma venda 
     * 
     * @param v A venda a inserir
     */
    public void insere(Venda v){
        DadosProduto p = dados.get(v.getProduto());
        if (p == null){
            dados.put(v.getProduto(), new DadosProduto(v));
        }
        else {
            p.incFaturacao(v.getPreco() * v.getQuantidade());
            p.incQuantidade(v.getQuantidade());
        }
    }
    
    /**
     * Testa se um produto existe
     * @param produto O produto que vai ser testado
     * @return true ou false consoante exista ou não
     */
    public boolean contem(String produto){
        return dados.containsKey(produto);
    }
    
    /**
     * Determina o total gasto na compra dos produtos presentes na variavel dados(as keys)
     * 
     * @return O total gastos nos produtos
     */
    public double totalGasto(){
        double g = 0;
        for (DadosProduto p: dados.values()){
            g += p.getFaturacao();
        }
        return g;
    }
    
    /**
     * Determina a quantidade comprada de um determinado produto.
     * 
     * @return Um Map com o código de produto e a quantidade comprada
     */
    public Map<String, Integer> mapeamentoProdutoQuantidade(){
        Map<String, Integer> m = new HashMap<>();
        for (String produto: dados.keySet()){
            m.put(produto, dados.get(produto).getQuantidade());
        }
        return m;
    }
    
    /**
     * Devolve todos os produtos que foram comprados( por determinado produto).
     * 
     * @return Todos os produtos que foram comprados
     */
    public Set<String> getProdutos(){
        return dados.keySet();
    }
    
    /**
     * Determina o número de compras que foram efetuadas( por um cliente).
     * 
     * @return O numero total de compras efetuadas
     */
    public int getNumeroCompras(){
        int q = 0;
        for(DadosProduto d: dados.values()){
            q += d.getNumeroVendas();
        }
        return q;
    }
    
    /**
     * Determina a quantidade e a faturação de um determinado produto( relativo a um cliente)
     * @param produto O produto a determinar
     * @return A quantidade e a faturação do produto
     */
    public ParQuantidadeFaturacao getQuantidadeFaturacao (String produto){
        DadosProduto d = dados.get(produto);
        try{
            return new ParQuantidadeFaturacao(d.getQuantidade(), d.getFaturacao());
        }
        catch(NullPointerException e){
            return null;
        }
    }
    

}