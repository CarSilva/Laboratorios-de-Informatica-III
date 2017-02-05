
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.System.out;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Esta classe implementa a execução da aplicação, agregando todos os métodos e classes
 * desenvolvidas.
 * 
 * @author Grupo28
 */
public class Main {
    //Variáveis de instância
    
    /**
     * A classe hipermecado (geral)
     */
    private static Hipermercado hipermercado;
    
    /**
     * Classe que implementa o diferente tipo de menus
     */
    private static Menu menuPrincipal, menuLeituraGravacao, menuEstatisticas, menuQueries;
    
    /**
     * Função main, executa todas as leituras, estatisticas e queries.
     * 
     * @param args 
     */
    public static void main (String[] args){
        carregarMenus();
        hipermercado = null;
        boolean continuar = true;
        while (continuar){
            clear();
            menuPrincipal.executa();
            int next = menuPrincipal.getOpcao();
            switch (next){
                case 1:
                    leituragravacao();
                    break;
                case 2:
                    estatisticas();
                    break;
                case 3:
                    queries();
                    break;
                case 0:
                    continuar = false;
            }
        }
    }
    
    /**
     * Chama todos os menus para apresentação.
     */
    private static void carregarMenus(){
        carregarMenuPrincipal();
        carregarMenuLeituraGravacao();
        carregarMenuEstatisticas();
        carregarMenuQueries();
    }
    
    /**
     * Implementa a apresentação do menu principal
     */
    private static void carregarMenuPrincipal(){
        String[] opcoes = {"GEREVENDAS",
                           "Leitura/Gravação dos dados",
                           "Estatísticas",
                           "Queries"};
        menuPrincipal = new Menu(opcoes);
    }
    
    /**
     * Implementa a apresentação do menu de leituras e gravações.
     */
    private static void carregarMenuLeituraGravacao(){
        String[] opcoes = {"LEITURA - GRAVAÇÃO",
                           "Ler dados de ficheiros",
                           "Gravar em ficheiro objeto",
                           "Ler de ficheiro objeto"};
        menuLeituraGravacao = new Menu(opcoes);
    }
    
    /**
     * Implementa a apresentação do menu referente as estatisticas.
     */
    private static void carregarMenuEstatisticas(){
        String[] opcoes = {"ESTATÍSTICAS",
                           "Dados referentes ao último ficheiro de vendas lido",
                           "Numeros gerais respeitantes aos dados atuais"};
        menuEstatisticas = new Menu(opcoes);
    }
    
    /**
     * Implementa a apresentação do menu referente ás queries.
     */
    private static void carregarMenuQueries(){
        String[] opcoes = {"QUERIES",
                           "Códigos de produtos nunca comprados",
                           "Total de vendas realizadas e número total de clientes que as fizeram de um dado mês",
                           "Dado um código de cliente determinar quantas compras fez, quantos produtos distintos comprou e quanto gastou no total",
                           "Dado um código de produto determinar quantas vezes foi comprado, por quantos clientes diferentes e o total faturado",
                           "Dado o código de um cliente determinar a lista de códigos de produtos que mais comprou",
                           "Determinar o conjunto dos produtos mais vendidos em todo o ano (quantidade)",
                           "Determinar a lista dos três maiores compradores (faturacao)",
                           "Determinar os códigos de clientes que compraram mais produtos diferentes",
                           "Dado um código de produto determinar o conjunto de clientes que mais o compraram e, para cada um, o valor gasto"};
        menuQueries = new Menu(opcoes);
    }
    
    /**
     * Lê ou grava os ficheiros/objetos
     */
    private static void leituragravacao(){
        clear();
        menuLeituraGravacao.executa();
        int next = menuLeituraGravacao.getOpcao();
        switch(next){
            case 1:
                leituraFicheiros();
                break;
            case 2:
                gravarObjecto();
                break;
            case 3:
                lerObjecto();
                break;
            default:
                return;
        }
        Input.lerString();
    }
    
    /**
     * Lê os ficheiros de vendas, produtos e clientes.
     */
    private static void leituraFicheiros (){
        clear();
        hipermercado = new Hipermercado();
        try{    
            leituraClientes();
            leituraProdutos();
            out.print("Ficheiro vendas a ler: ");
            String fich = Input.lerString();
            leituraVendas(fich);
        }
        catch(IOException e){
                
            out.println(e.getMessage());
            hipermercado = null;
        }
        out.println("Concluído");
    }
    
    /**
     * Lê os códigos de clientes
     * @throws IOException 
     */
    private static void leituraClientes() throws IOException{
        BufferedReader inStream = null; 
        String linha = null;
        int i = 0;
        inStream = new BufferedReader(new FileReader("Clientes.txt"));
        Crono.start();
        while( (linha = inStream.readLine()) != null ){
            hipermercado.insereCliente(linha);
            i++;
        }
        out.println ("Clientes lidos: " + i + " em " + Crono.print() + " s");
    }
    
    /**
     * Lê os códigos de produtos
     * @throws IOException 
     */
    private static void leituraProdutos() throws IOException{
        BufferedReader inStream = null; 
        String linha = null;
        int i = 0;
        inStream = new BufferedReader(new FileReader("Produtos.txt"));
        Crono.start();
        while( (linha = inStream.readLine()) != null ){
            hipermercado.insereProduto(linha);
            i++;
        }
        out.println ("Produtos lidos: " + i + " em " + Crono.print() + " s");
    }
    
    /**
     * Lê as linhas de vendas do ficheiro de vendas passado
     * @param fich O nome do ficheiro a lêr
     * @throws IOException 
     */
    private static void leituraVendas(String fich) throws IOException{
        BufferedReader inStream = null; 
        String linha = null;
        int total = 0;
        inStream = new BufferedReader(new FileReader(fich));
        hipermercado.setNomeFich(fich);
        Crono.start();
        while( (linha = inStream.readLine()) != null ){
            hipermercado.insereVenda(parseLinhaVenda(linha));
            total++;
        }
        out.println ("Vendas lidas: " + total + "; Vendas validadas: " + (total-hipermercado.getInvalidas()) + " em " + Crono.print() + " s");
    }
    
    /**
     * Divide um linha do ficheiro vendas
     * @param linha A linha a dividir
     * @return Uma venda já com todos os campos preenchidos
     */
    private static Venda parseLinhaVenda(String linha){
        String[] campos = linha.split(" ");
        String cProduto, tipo, cCliente;
        double preco;
        int quantidade, mes, filial;
        try{
            cProduto = campos[0].trim();
            preco = Double.parseDouble (campos[1].trim());
            quantidade = Integer.parseInt (campos[2].trim()); 
            tipo = campos[3].trim();
            cCliente = campos[4].trim();
            mes = Integer.parseInt(campos[5].trim());
            filial = Integer.parseInt(campos[6].trim());
        }
        catch (NullPointerException | NumberFormatException e){
            out.println (e.getMessage());
            return null;
        }
        return new Venda(cProduto, preco, quantidade, tipo, cCliente, mes, filial);
    }
    
    /**
     * Grava a informação da classe hipermecado
     */
    private static void gravarObjecto (){
        clear();
        out.print("Nome do ficheiro objeto a gravar: ");
        String fich = Input.lerString();
        Crono.start();
        try{
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fich));
            os.writeObject(hipermercado);
            os.flush();
            os.close();
        }
        catch (IOException e){
            out.println (e.getMessage());
        }
        out.println("Gravação concluídae em " + Crono.print() + " s");
    }
    
    /**
     * Lê a ultima gravação
     */
    private static void lerObjecto (){
        clear();
        out.print("Nome do ficheiro objeto a ler: ");
        String fich = Input.lerString();
        Crono.start();
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream (fich));
            hipermercado = (Hipermercado) is.readObject();
            is.close();
        }
        catch (IOException | ClassNotFoundException e){
            out.println (e.getMessage());
        }
        out.println("Leitura concluída em " + Crono.print() + " s");
    }
    
    /**
     * Função que executa as estatisticas
     */
    private static void estatisticas(){
        if (hipermercado == null){
            out.println("Leitura necessária");
            Input.lerString();
            return;
        }
        clear();
        menuEstatisticas.executa();
        int next = menuEstatisticas.getOpcao();
        switch(next){
            case 1:
                estatisticas1();
                break;
            case 2:
                estatisticas2();
        }
        out.println("Concluído");
        Input.lerString();
    }
    
    /**
     * Estatisticas sobre os dados referentes ao último ficheiro de vendas lido
     */
    private static void estatisticas1(){
        clear();
        int totalProdutos = hipermercado.getTotalProdutos();
        int produtosDiferentesComprados = hipermercado.produtosDiferentesComprados();
        int totalClientes = hipermercado.getTotalClientes();
        int clientesCompraram = hipermercado.clientesCompraram();
        out.println("Nome do ficheiro de vendas: " + hipermercado.getNomeFich());
        out.println("Número total de registos de venda errados: " + hipermercado.getInvalidas());
        out.println("Número total de produtos: " + totalProdutos);
        out.println("Total de produtos diferentes comprados: " + produtosDiferentesComprados);
        out.println("Total de produtos não comprados: " + (totalProdutos - produtosDiferentesComprados));
        out.println("Número total de clientes: " + totalClientes);
        out.println("Número total de clientes que realizaram compras: " + clientesCompraram);
        out.println("Total de clientes que nada compraram: "+ (totalClientes - clientesCompraram));
        out.println("Total de compras de valor total igual a 0.0: " + hipermercado.vendasZero());
        out.println("Faturação total: " + hipermercado.faturacao());
    }
    
    /**
     * Estatisticas sobre os números gerais referentes aos dados actuais já registados nas estruturas
     */
    private static void estatisticas2(){
        int i = 1;
        while (i != 0){
            clear();
            out.println("\nMÊS " + i);
            out.println("Número total de compras: " + hipermercado.numeroCompras(i));
            for (int j = 1; j <= hipermercado.getNumFiliais(); j++){
                out.println("Faturação filial " + j + ": " + hipermercado.faturacao(i,j));
            }
            out.println("Faturação total: " + hipermercado.faturacao(i));
            out.println("Número de distintos clientes: " + hipermercado.clientesDiferentes(i));
            
            out.print("\nMês (Sair - 0): ");
            i = Input.lerInt();
            if (i < 0) i = 1;
            if (i > 12) i = 12;
        }
    }
    
    /**
     * Função que chama e executa todas as 9 queries
     */
    private static void queries(){
        if (hipermercado == null){
            out.println("Leitura necessária");
            Input.lerString();
            return;
        }
        boolean continuar = true;
        while(continuar){
            clear();
            menuQueries.executa();
            int next = menuQueries.getOpcao();
            switch(next){
                case 1:
                    query1();
                    break;
                case 2:
                    query2();
                    break;
                case 3:
                    query3();
                    break;
                case 4:
                    query4();
                    break;
                case 5:
                    query5();
                    break;
                case 6:
                    query6();
                    break;
                case 7:
                    query7();
                    break;
                case 8:
                    query8();
                    break;
                case 9:
                    query9();
                    break;
                case 0:
                    continuar = false;
            }
            out.println("Concluído");
            Input.lerString();
        }
    }
    
    /**
     * Query 1
     */
    private static void query1() {
        Crono.start();
        List<String> pnc = hipermercado.produtosNuncaComprados();
        double tempo = Crono.stop();
        int pag = 1;
        int nPag = (int) Math.ceil((double) pnc.size() / 10);
        while(pag != 0){
            clear();
            out.println("\nTempo: " + tempo + " s");
            out.println("Número de produtos nunca comprados: " + pnc.size());
            out.println("Página " + pag + " de " + nPag + " páginas");
            for (int j = 0; j < 10 && (pag-1)*10+j < pnc.size(); j++){
                out.println(pnc.get((pag-1)*10 + j));
            }
            out.print("\nPágina (Sair - 0): ");
            pag = Input.lerInt();
            if (pag < 0) pag = 1;
            if (pag > nPag) pag = nPag;
        }
    }
    
    /**
     * Query 2
     */
    private static void query2(){
        clear();
        out.print("Digite o mês pretendido: ");
        int mes = Input.lerInt();
        Crono.start();
        int numeroCompras = hipermercado.numeroCompras(mes);
        int clientesDiferentes = hipermercado.clientesDiferentes(mes);
        out.println("\nTempo: " + Crono.print() + " s");        
        out.println("MÊS: " + mes);
        out.println("Número total de vendas: " + numeroCompras);
        out.println("Número total de clientes distintos: " + clientesDiferentes);
    }
    
    /**
     * Query 3
     */
    private static void query3(){
        out.print("Digite o código de cliente pretendido: ");
        String cliente = Input.lerString();
        int i = 1;
        while (i != 0){
            clear();
            Crono.start();
            int numeroCompras = hipermercado.numeroCompras(i, cliente);
            int produtosDiferentes = hipermercado.produtosDiferentes(i, cliente);
            double totalGasto = hipermercado.totalGasto(i, cliente);
            out.println("\nTempo: " + Crono.print() + " s");
            out.println("MÊS " + i);
            out.println("Número de compras: " + numeroCompras);
            out.println("Número de produtos distintos: " + produtosDiferentes);
            out.println("Total gasto: " + totalGasto);
            
            out.print("\nMês (Sair - 0): ");
            i = Input.lerInt();
            if (i < 0) i = 1;
            if (i > 12) i = 12;
        }
    }
    
    /**
     * Query 4
     */
    private static void query4(){
        out.print("Digite o código de produto pretendido: ");
        String produto = Input.lerString();
        int i = 1;
        while (i != 0){
            clear();
            Crono.start();
            int vezesComprado = hipermercado.vezesComprado(i, produto);
            int clientesDiferentes = hipermercado.clientesDiferentes(i, produto);
            double faturacao = hipermercado.faturacao(i, produto);
            out.println("\nTempo: " + Crono.print() + " s");
            out.println("MÊS " + i);
            out.println("Número de vezes comprado: " + vezesComprado);
            out.println("Número de clientes diferentes: " + clientesDiferentes);
            out.println("Total faturado: " + faturacao);
            
            out.print("\nMês (Sair - 0): ");
            i = Input.lerInt();
            if (i < 0) i = 1;
            if (i > 12) i = 12;
        }
    }
    
    /**
     * Query 5
     */
    private static void query5(){
        out.print("Digite o código de cliente pretendido: ");
        String cliente = Input.lerString();
        Crono.start();
        List<ParProdutoQuantidade> pmc = hipermercado.produtosMaisComprados(cliente);
        double tempo = Crono.stop();
        int pag = 1;
        int nPag = (int) Math.ceil((double)pmc.size() / 10);
        while (pag != 0){
            clear();
            out.println("\nTempo: " + tempo + " s");
            out.println("Página " + pag + " de " + nPag + " páginas");
            out.println("Produto - Quantidade");
            for (int j = 0; j < 10 && (pag-1)*10+j < pmc.size(); j++){
                out.println(pmc.get((pag-1)*10 + j).toString());
            }
            out.print("\nPágina (Sair - 0): ");
            pag = Input.lerInt();
            if (pag < 0) pag = 1;
            if (pag > nPag) pag = nPag;
        }
    }
    
    /**
     * Query 6
     */
    private static void query6(){
        clear();
        out.print("Digite quantos produtos quer ver: ");
        int x = Input.lerInt();
        Crono.start();
        Set<TriploProdutoQuantidadeLista> pmv = hipermercado.produtosMaisVendidos();
        out.println("\nTempo: " + Crono.print() + " s");
        Iterator<TriploProdutoQuantidadeLista> it = pmv.iterator();
        out.println("Produto - Quantidade - Numero de clientes");
        for (int i = 0; i < x && it.hasNext(); i++){
            out.println(it.next().toString());
        }
    }
    
    /**
     * Query 7
     */
    private static void query7(){
        int i = 1;
        while (i != 0){
            clear();
            Crono.start();
            Set<ParClienteFaturacao> mc = hipermercado.maioresCompradores(i);
            out.println("Tempo: " + Crono.print() + " s");
            out.println("FILIAL " + i);
            out.println("Cliente - Faturação");
            Iterator<ParClienteFaturacao> it = mc.iterator();
            for (int j = 0; j < hipermercado.getNumFiliais() && it.hasNext(); j++){
                out.println(it.next().toString());
            }
            
            out.print("\nFilial (Sair - 0): ");
            i = Input.lerInt();
            if (i < 0) i = 1;
            if (i > hipermercado.getNumFiliais()) i = hipermercado.getNumFiliais();
        }
    }
    
    /**
     * Query 8
     */
    private static void query8(){
        clear();
        out.print("Digite quantos produtos quer ver: ");
        int x = Input.lerInt();
        Crono.start();
        Set<ParClienteLista> mpd = hipermercado.maisProdutosDiferentes();
        out.println("\nTempo: " + Crono.print() + " s");
        Iterator<ParClienteLista> it = mpd.iterator();
        out.println("Cliente - Numero de produtos diferentes");
        for (int i = 0; i < x && it.hasNext(); i++){
            out.println(it.next().toString());
        }
    }
    
    /**
     * Query 9
     */
    private static void query9(){
        clear();
        out.print("Digite o código de produto pretendido: ");
        String produto = Input.lerString();
        out.print("Determine quantos clientes quer ver: ");
        int x = Input.lerInt();
        Crono.start();
        Set<TriploClienteQuantidadeFaturacao> mc = hipermercado.maioresCompradores(produto);
        out.println("\nTempo: " + Crono.print() + " s");
        Iterator<TriploClienteQuantidadeFaturacao> it = mc.iterator();
        out.println("Cliente - Quantidade - Valor gasto");
        for (int i = 0; i < x && it.hasNext(); i++){
            out.println(it.next().toString());
        }   
    }
    
    /**
     * Limpa o ecrã
     */
    private static void clear(){
        for (int i = 0; i < 40; i++){
            out.println();
        }
    }
}
