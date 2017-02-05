
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Esta classe implementa a estrutura de uma filial do hipermecado
 * 
 * @author Grupo28
 */

public class Filial implements Serializable{
    /**
     * Lista com 12 componentes, contendo cada componente um map com o código de cliente
     * e os dados desse clientes, numero de compras, quantidade comprada, etc.
     */
    private List<Map<String, DadosCliente>> clientesPorMes;
    
    /**
     * Construtor vazio da classe. Incializa os 12 campos da variável de instância
     */
    public Filial(){
        clientesPorMes = new ArrayList<>(12);
        for(int i = 0; i < 12; i++){
            clientesPorMes.add(new HashMap<>());
        }
    }
    
    /**
     * Adiciona a informação pertinente á classe através de uma venda recebida como parâmetro
     * 
     * @param v A venda recebida
     */
    public void insere(Venda v){
        Map<String, DadosCliente> m = clientesPorMes.get(v.getMes() - 1);
        DadosCliente d = m.get(v.getCliente());
        if (d == null){
            d = new DadosCliente(v);
            m.put(v.getCliente(), d);
        }
        else {
            d.insere(v);
        }
    }
    
    /**
     * Determina o total de produtos diferentes comprados por determinado cliente,
     * em determinado mês.
     * 
     * @param mes O mês que vamos analisar
     * @param cliente O cliente que queremos obter resultado
     * @return Um conjunto com os produtos diferentes
     */
    public Set<String> produtosDiferentes(int mes, String cliente){
        try{
            return this.clientesPorMes.get(mes-1).get(cliente).getProdutos();
        }
        catch(NullPointerException e){
            return null;
        }
        /*
        Set<String> pdc = new TreeSet<>();
        Map<String, ParQuantidadeFaturacao> p = this.clientesPorMes.get(mes-1).get(cliente).getProdutos();
        for(String s : p.keySet()){
            pdc.add(s);
        }
        return pdc;*/
    }
    
    /**
     * Método que determina o número de compras num determinado mês de um dado cliente
     * 
     * @param mes O mês a analisar
     * @param cliente O cliente que queremos obter resultados
     * @return O numero de compras 
     */
    public int numeroCompras(int mes, String cliente){
        try{
            return this.clientesPorMes.get(mes-1).get(cliente).getNumeroCompras();
        }
        catch(NullPointerException e){
            return 0;
        }
        /*
        int quantidadeT = 0;
        for(ParQuantidadeFaturacao pqf : this.clientesPorMes.get(mes-1).get(cliente).getDados().values()){
            quantidadeT += pqf.getQuantidade();
        }
        return quantidadeT;*/
    }
    
    /**
     * Método que determina o total gasto por um cliente num mês
     * 
     * @param mes O mês que queremos analisar
     * @param cliente O cliente que queremos obter resultados
     * @return O total gasto pelo cliente no mês em causa
     */
    public double totalGasto(int mes, String cliente){
        try{
            return clientesPorMes.get(mes-1).get(cliente).totalGasto();
        }
        catch(NullPointerException e){
            return 0;
        }
        
        
        /*
        for(ParQuantidadeFaturacao pqf : this.clientes.get(mes-1).get(cliente).getProdFat().values()){
           gasto += pqf.getFaturacao();
        }
        return gasto;*/
    }
    
    /**
     * Método que determina os produtos mais comprados de um determinado cliente.
     * 
     * @param cliente O cliente sobre o qual queremos obter resultado
     * @return Um map com os codigos de produtos e as quantidas compradas dos mesmos
     */
    public Map<String, Integer> produtosMaisComprados(String cliente){
        Map<String, Integer> m = new HashMap<>();
        for (int i = 0; i < 12; i++){
            DadosCliente d = clientesPorMes.get(i).get(cliente);
            try{
                Map<String, Integer> m2 = d.mapeamentoProdutoQuantidade();
                for (String produto: m2.keySet()){
                    Integer quantidade = m.get(produto);
                    if (quantidade == null){
                        m.put(produto, m2.get(produto));
                    }
                    else {
                        m.put(produto, quantidade + m2.get(produto));
                    }
                }
            }
            catch(NullPointerException e){}
        }
        return m;
        
        /*
        Set<ParProdutoQuantidade> pmc = new TreeSet<>(new ComparatorParProdutoQuantidade());
        Map<String, ParQuantidadeFaturacao> p = new HashMap<>();
        for(int i = 0; i < 12; i++){
            p = this.clientes.get(i).get(cliente).getProdFat();
            for(Map.Entry<String, ParQuantidadeFaturacao> ps : p.entrySet()){
                pmc.add(new ParProdutoQuantidade(ps.getKey(), ps.getValue().getQuantidade()));
            }
        }
        return pmc;*/
    }
    
    /**
     * Método que determina os clientes diferentes que compraram num dado mês
     * 
     * @param mes O mês a analisar
     * @return Um conjunto com todos os clientes diferentes que compraram
     */
    public Set<String> clientesDiferentes(int mes){
        return this.clientesPorMes.get(mes-1).keySet();
    }
    
    /**
     * Método que determina os clientes diferentes que comprarem determinado produto.
     * num dado mês.
     * 
     * @param mes O mês a analisar
     * @param produto O produto sobre o qual queremos resultados
     * @return Um conjunto com todos os clientes diferentes que compraram o produto
     */
    public Set<String> clientesDiferentes(int mes, String produto){
        Set<String> cd = new HashSet<>();
        Map<String, DadosCliente> m = clientesPorMes.get(mes-1);
        for (String s: m.keySet()){
            if (m.get(s).contem(produto)){
                cd.add(s);
            }
        }
        return cd;
        /*
        for(Map.Entry<String, QtdFatMes> c : clientes.get(mes-1).entrySet()){
            if(c.getValue().getProdFat().containsKey(produto))
                cd.add(c.getKey());
        }
        return cd;*/
    }
    
    /**
     * Método que determina os clientes que compraram globalmente.
     * 
     * @return O conjunto com os códigos de clientes que compraram
     */
    public Set<String> clientesCompraram(){
        Set<String> s = new HashSet<>();
        for (int i = 0; i < 12; i++){
            s.addAll(clientesPorMes.get(i).keySet());
        }
        return s;
    }
    
    /**
     * Método que determina os clientes mais compradadores, associando cada cliente
     * ao valor total gasto
     * 
     * @return Um conjunto com o código de cliente e a seus gastos
     */
    public Set<ParClienteFaturacao> maioresCompradores(){
        Map<String, Double> m = new HashMap<>();
        for (int i = 0; i < 12; i++){
            Map<String, DadosCliente> m2 = this.clientesPorMes.get(i);
            for (String cliente: m2.keySet()){
                Double faturacao = m.get(cliente);
                if (faturacao == null){
                    m.put(cliente, m2.get(cliente).totalGasto());
                }
                else {
                    m.put(cliente, faturacao + m2.get(cliente).totalGasto());
                }
            }
        }
        Set<ParClienteFaturacao> s = new TreeSet<>();
        for (String cliente: m.keySet()){
            s.add (new ParClienteFaturacao(cliente, m.get(cliente)));
        }
        return s;
    }
    
    /**
     * Método que determina os clientes mais compradores de um determinado produto
     * 
     * @param produto O produto sobre o qual queremos resultados
     * @return Um conjunto com os clientes e a quantidade e o total gasto do cliente, num determinado produto
     */
    public Map<String, ParQuantidadeFaturacao> maioresCompradores(String produto){
        Map<String, ParQuantidadeFaturacao> m = new HashMap<>();
        for (int i = 0; i < 12; i++){
            Map<String, DadosCliente> m2 = this.clientesPorMes.get(i);
            for (String cliente: m2.keySet()){
                ParQuantidadeFaturacao p = m.get(cliente);
                ParQuantidadeFaturacao p2 = m2.get(cliente).getQuantidadeFaturacao(produto);
                if (p2 != null){
                    if (p == null){
                        m.put(cliente, p2);
                    }
                    else{
                        p.incFaturacao(p2.getFaturacao());
                        p.incQuantidade(p2.getQuantidade());
                    }
                }
            }
        }
        return m;
    }
    
    /**
     * Método que determina para cada cliente os produtos diferentes comprados
     * 
     * @return Um Map com os códigos de clientes e um conjunto de códigos de produtos diferentes comprados por cada cliente
     */
    public Map<String, Set<String>> maisProdutosDiferentes(){
        Map<String, Set<String>> m = new HashMap<>();
        for (int i = 0; i < 12; i++){
            Map<String, DadosCliente> m2 = this.clientesPorMes.get(i);
            for (String cliente: m2.keySet()){
                Set<String> s = m.get(cliente);
                Set<String> s2 = m2.get(cliente).getProdutos();
                if (s == null){
                    s = new HashSet<>();
                    s.addAll(s2);
                    m.put(cliente, s);
                }
                else{
                    s.addAll(s2);
                }
            }
        }
        return m;
    }
    
    /**
     * Método que determina os produtos mais vendidos, ou seja mais comprados
     * 
     * @return Um map com as quantidade e os clientes que compraram cada produto
     */
    public Map<String, ParQuantidadeLista> produtosMaisVendidos(){
        Map<String, ParQuantidadeLista> m = new HashMap<>();
        for (int i = 0; i < 12; i++){
            Map<String, DadosCliente> m2 = clientesPorMes.get(i);
            for(String cliente: m2.keySet()){
                DadosCliente d = m2.get(cliente);
                Map<String, Integer> m3 = d.mapeamentoProdutoQuantidade();
                for (String produto: m3.keySet()){
                    ParQuantidadeLista p = m.get(produto);
                    if (p == null){
                        m.put(produto, new ParQuantidadeLista(m3.get(produto), cliente));
                    }
                    else{
                        p.incQuantidade(m3.get(produto));
                        p.addLista(cliente);
                    }
                }
            }
        }
        return m;
    }
}
