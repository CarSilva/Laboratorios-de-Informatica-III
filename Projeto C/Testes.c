#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "Venda.h"
#include "Testes.h"
#include "CatálogoClientes.h"
#include "CatálogoProdutos.h"

DATA initData(){
        DATA d = malloc (sizeof (struct dados));
        d->erroCliente = 0;
        d->erroProduto = 0;
        d->erroComum = 0;
        d->totalInvalido = 0;
        d->totalValido = 0;
        d->total = 0;
        return d;
}

void dataTest (SALE s, CatClientes ccs, CatProdutos cps, DATA d){
        bool r = true;
        if (!existeProduto (cps, s->produto)){
                r = false;
                d->erroProduto++;
        }
	if (!existeCliente (ccs, s->cliente)){
                if (r == false)
                        d->erroComum++;
                r = false;
                d->erroCliente++;
        }
        if (s->preco < 0 || s->preco > 999.99)
                r = false;
        if (s->quantidade < 1 || s->quantidade > 200)
                r = false;
        if (s->tipo == 'P' && s->tipo == 'N')
                r = false;
        if (s->mes < 1 || s->mes > 12)
                r = false;
        if (s->filial < 1 || s->filial > 3)
                r = false;
        if (r == false)
                d->totalInvalido++;
        else d->totalValido++;
	d->total++;
}

void divideTest (char* linha, SALE s){
        char* token;
        token = strtok (linha," ");
        s->produto = criaProduto (token);
        token = strtok (NULL, " ");
        s->preco = atof (token);
        token = strtok (NULL, " ");
        s->quantidade = atoi (token);
        token = strtok (NULL, " ");
        s->tipo = token[0];
        token = strtok (NULL, " ");
        s->cliente =  criaCliente (token);
        token = strtok (NULL, " ");
        s->mes = atoi (token);
        token = strtok (NULL, "\r\n");
        s->filial = atoi (token);
}

void testGeralUM (CatClientes ccs, CatProdutos cps){
	char linha[MAX_BUF_VEN];
        FILE *f;
	DATA d = initData();
        SALE s = malloc (sizeof (struct venda));
        f = fopen("Vendas_1M.txt","r");
        while (fgets (linha, MAX_BUF_VEN, f)){
                divideTest (linha, s);
                dataTest (s, ccs, cps, d);
        }
        fclose (f);

	printf ("Total de Clientes Registados: %d\n", totalClientes(ccs));
        printf ("Total de Códigos de Produtos: %d\n", totalProdutos(cps));
        printf ("Linhas de Vendas: %d\n", d->total);
        printf ("Vendas com códigos de cliente errados: %d\n", d->erroCliente);
        printf ("Vendas com códigos de produtos errados: %d\n", d->erroProduto);
        printf ("Vendas válidas: %d\n", d->totalValido);
        printf ("Vendas inválidas : %d\n", d->totalInvalido);
        printf ("Vendas com códigos de cliente e produtos errados: %d\n", d->erroComum);
}

TESTE initTeste(){
        TESTE t = malloc (sizeof (struct teste));
        t->faturacao = 0;
        t->unidades = 0;
        t->filial[0] = 0;
        t->filial[1] = 0;
        t->filial[2] = 0;
        t->zero = 0;
}

void testGeralDOIS(){
        char linha[MAX_BUF_VEN];
        SALE s = malloc (sizeof (struct venda));
        TESTE t = initTeste();
        FILE *f;
        f = fopen("Vendas_1MValidas.txt","r");
	CatClientes ccs = initCatClientes();
	CatProdutos cps = initCatProdutos();
        while (fgets (linha, MAX_BUF_VEN, f)){
                divideTest (linha, s);
		if (!existeCliente (ccs, s->cliente))
			ccs = insereCliente (ccs, s->cliente);
		if (!existeProduto (cps, s->produto))
			cps = insereProduto (cps, s->produto);
                t->faturacao += s->preco * s->quantidade;
                t->unidades += s->quantidade;
                t->filial[s->filial - 1]++;
                if (s->preco == 0) t->zero++;
        }

	printf ("Produtos comprados: %d\n", totalProdutos(cps));
	printf ("Clientes compradores: %d\n", totalClientes(ccs));
        printf ("Faturado total: %f\n", t->faturacao);
        printf ("Unidades vendidas: %d\n", t->unidades);
        printf ("Filial 1: %d; Filial 2: %d; Filial 3: %d\n", t->filial[0], t->filial[1], t->filial[2]);
        printf ("Vendas de preço zero: %d\n", t->zero);
}

CLIENT initClient (){
	int i;
	CLIENT c = malloc (sizeof (struct cliente));
	c->registos = 0;
	c->quantidade = 0;
	c->pagamento = 0;
	for(i = 0; i < 12; i++)
		c->compras[i] = 0;
	return c;
}

void testClient (){
	int i;
	char cliente[10], linha[MAX_BUF_VEN];
	FILE *f;
	CLIENT c = initClient();
	SALE s = malloc (sizeof (struct venda));
	printf("Introduza cliente: ");
	fgets (cliente, MAX_BUF_CAT, stdin);
        cliente[5] = 0;
	f = fopen ("Vendas_1MValidas.txt", "r");
	while (fgets (linha, MAX_BUF_VEN, f)){
		divideTest (linha, s);
		if (!strcmp(getCliente(s->cliente), cliente)){
			c->registos++;
        		c->quantidade += s->quantidade;
        		c->pagamento += s->quantidade * s->preco;
        		c->compras[s->mes - 1]++;
		}		
	}

	printf("Cliente: %s\n", cliente);
	printf("Total de Registos de Compras do Cliente: %d\n", c->registos);
	printf("Quantidade Total de Produtos comprados: %d\n", c->quantidade);
	printf("Total pago pelo cliente: %f\n", c->pagamento);
	for (i = 0; i < 12; i++){
		printf("Mes %d Compras %d\n", i+1, c->compras[i]);
	}
}

PRODUCT initProduct(){
	int i;
	PRODUCT p = malloc (sizeof (struct produto));
	for (i=0; i < 12; i++){
		p->vendasN[i] = p->faturacaoN[i] = p->vendasP[i] = p->faturacaoP[i] = 0;
	}
	return p;
}

void testProduct (){
	int i, vendas;
	double faturacao;
	char produto[10], linha[MAX_BUF_VEN];
	FILE *f;
	PRODUCT p = initProduct();
	SALE s = malloc (sizeof (struct venda));
	CatClientes ccs = initCatClientes();
	printf("Introduza produto: ");
	fgets (produto, MAX_BUF_CAT, stdin);
	produto[6] = 0;
	f = fopen ("Vendas_1MValidas.txt", "r");
	while (fgets (linha, MAX_BUF_VEN, f)){
		divideTest (linha, s);
		if (!strcmp (getProduto(s->produto), produto)){
			if (!existeCliente (ccs, s->cliente))
				ccs = insereCliente (ccs, s->cliente);
			if (s->tipo == 'N'){
				p->vendasN[s->mes - 1] += s->quantidade;
				p->faturacaoN[s->mes - 1] += s->quantidade * s->preco;
			}
			if (s->tipo == 'P'){
				p->vendasP[s->mes - 1] += s->quantidade;
				p->faturacaoP[s->mes - 1] += s->quantidade * s->preco;
			}
		}
	}
	
	printf ("Número de clientes: %d\n", totalClientes(ccs));
	for (i = vendas = faturacao = 0; i < 12; i++){
		if (p->vendasN[i])
			printf ("Mes: %d Vendas N = %d Faturação N = %f\n", i+1, p->vendasN[i], p->faturacaoN[i]);
		if (p->vendasP[i])
			printf ("Mes: %d Vendas P = %d Faturação P = %f\n", i+1, p->vendasP[i], p->faturacaoP[i]);
		vendas += p->vendasN[i] + p->vendasP[i];
		faturacao += p->faturacaoN[i] + p->faturacaoP[i];
	}
	printf ("Total de vendas: %d\n", vendas);
	printf ("Faturação total: %f\n", faturacao);
}
