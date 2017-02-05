import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;

/**
 * Esta classe implementa o Catálogo de Clientes
 *
 * @author Grupo28
 */
public class CatalogoClientes implements Serializable{
    //Variáveis de instância
    
    /**
     * Map que associa uma letra(p.e "A"), a um conjunto de códigos de cliente que começam por essa letra
     */
    private Map <String, Set<String>> clientes; 
    
    //Construtures
    
    /**
     * Construtor vazio da classe CatalogoClientes que inicializa, devidamente as variaveis de instância
     */
    public CatalogoClientes(){
        clientes = new HashMap<>();
        for(int i = 65; i <= 90; i++){
            clientes.put(Character.toString((char)i), new TreeSet<>());
        }
    }
    
    /**
     * Construtor da classe CatalogoClientes que a partir de um Map compatível com a classe, atribui todas as informações desse Map
     * à classe.
     * 
     * @param cli O Map recebido, que associa letras do alfabeto a codigos de cliente
     */
    public CatalogoClientes(Map <String, Set<String>> cli){
        this.clientes = new HashMap<>();
        for (String s : cli.keySet()){
            Set<String> ts = new TreeSet<>();
            for (String ss : cli.get(s)){
                ts.add(ss);
            }
            clientes.put(s, ts);
        }
    }
    
    /**
     * Construtor por cópia da classe CatalogoClientes
     * 
     * @param cc O CatalogoClientes recebido
     */
    public CatalogoClientes(CatalogoClientes cc){
        this.clientes = cc.getClientes();
    }
    
    /**
     * Devolve todos os clientes que existem ou estao registados no CatalogoClientes
     * 
     * @return Os clientes todos que existem
     */
    public Map<String, Set<String>> getClientes() {
        Map<String, Set<String>> cli = new HashMap<>();
        for (String s : clientes.keySet()){
            Set<String> ts = new TreeSet<>();
            for (String ss : clientes.get(s)){
                ts.add(ss);
            }
            cli.put(s, ts);
        }
        return cli;
    }
    
    /**
     * Determina o quantos clientes existem
     * 
     * @return O número de clientes existentes
     */
    public int sizeTotal(){
        int size = 0;
        for(Set<String> me : clientes.values()){
            size += me.size();
        }
        return size;
    }
    
    /**
     * Determina quantos clientes existem começados por determinada letra
     * 
     * @param letra A letra que vamos analisar
     * @return O tamanho dos clientes
     */
    public int sizeTotalLetra(String letra){
        return clientes.get(letra).size();
    }
    
    /**
     * Verifica se um cliente existe
     * 
     * @param cliente O cliente que queremos testar
     * @return true ou false, consoante exista ou não
     */
    public boolean existe(String cliente){
        return clientes.get(Character.toString(cliente.charAt(0))).contains(cliente);
    }
    
    /**
     * Adiciona um cliente ao CatalogoClientes
     * 
     * @param cliente O clientes a adicionar
     */
    public void add(String cliente){
        String s = Character.toString(cliente.charAt(0));
        clientes.get(s).add(cliente);
    }
    
    /**
     * Método que clona um objecto da classe
     * 
     * @return Um clone da classe
     */
    public CatalogoClientes clone(){
        return new CatalogoClientes(this);
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
        CatalogoClientes cc = (CatalogoClientes) o;
        return this.clientes.equals(cc.getClientes());
    }
}
    

