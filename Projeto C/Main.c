#include <stdio.h> 
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>

#include "Venda.h"
#include "CatálogoClientes.h"
#include "CatálogoProdutos.h"
#include "Testes.h"

#define MAX_BUF 64
#define MAX_BUF_CAT 32
#define MAX_BUF_VEN 128

CatProdutos lerProdutos (CatProdutos catP, char* fileName){
	char linha[MAX_BUF_CAT];
	char* t;
	FILE* f;
	Produto p = initProduto();
	f = fopen (fileName, "r");
	while (fgets (linha, MAX_BUF_CAT, f)){
		t = strtok (linha, "\r\n");
		p = criaProduto (p, t);
		catP = insereProduto (catP, p);
	}
	fclose(f);
	return catP;
}

CatClientes lerClientes (CatClientes catC, char* fileName){
	char linha[MAX_BUF_CAT];
	char* t;
	FILE *f;
	Cliente c = initCliente();
	f = fopen (fileName, "r");
	while (fgets (linha, MAX_BUF_CAT, f)){
		t = strtok (linha, "\r\n");
		c = criaCliente (c, t);
		catC = insereCliente (catC, c);
	}
	fclose(f);
	return catC;
}

bool validar (Venda v, CatClientes catC, CatProdutos catP){
	if (!existeProduto (catP, v->produto))
		return false;
	if (v->preco < 0 || v->preco > 999.99)
		return false;
	if (v->quantidade < 1 || v->quantidade > 200)
		return false;
	if (v->tipo == 'P' && v->tipo == 'N')
		return false;
	if (!existeCliente (catC, v->cliente))
		return false;
	if (v->mes < 1 || v->mes > 12)
		return false;
	if (v->filial < 1 || v->filial > 3)
		return false;
	return true;
}

void dividir (char* linha, Venda v){
	char* token;
	token = strtok (linha," ");
	v->produto = criaProduto (v->produto, token);
	token = strtok (NULL, " ");
	v->preco = atof (token);
	token = strtok (NULL, " ");
	v->quantidade = atoi (token);
	token = strtok (NULL, " ");
	v->tipo = token[0];
	token = strtok (NULL, " ");
	v->cliente = criaCliente (v->cliente, token);
	token = strtok (NULL, " ");
	v->mes = atoi (token);
	token = strtok (NULL, "\r\n");
	v->filial = atoi (token);
}

void lerVendas (CatClientes catC, CatProdutos catP, char* fileName){
	char linha[MAX_BUF_VEN];
	FILE *f1, *f2;
	Venda v = initVenda();
	f1 = fopen(fileName,"r");
	f2 = fopen ("Vendas_1MValidas.txt","w");
	while (fgets (linha, MAX_BUF_VEN, f1)){
		dividir (linha, v);
		if (validar (v, catC, catP))
			fprintf (f2, "%s %.2f %d %c %s %d %d\n", 
				getProduto(v->produto), v->preco, v->quantidade, 
				v->tipo, getCliente(v->cliente), v->mes, v->filial); 
	}
	fclose (f2);
	fclose (f1);
}

char** defaultFileName (char** f){
	f[0] = realloc (f[0], sizeof (char) * 16);
	strcpy (f[0], "Clientes.txt");
	f[1] = realloc (f[1], sizeof (char) * 16);
	strcpy (f[1], "Produtos.txt");
	f[2] = realloc (f[2], sizeof (char) * 16);
	strcpy (f[2], "Vendas_1M.txt");
	return f;
}

char** initFileName (){
	int i;
	char** f = malloc (sizeof (char*) * 3);
	for (i = 0; i < 3; i++)
		f[i] = NULL;
	f = defaultFileName (f);
	return f;
}

void preQuerie1 (CatClientes catC, CatProdutos catP, char* t, char** f){
	int i;
	for (i = 0; i < 3; i++){
		t = strtok (NULL, " \r\n");
		if (t){
			f[i] = realloc (f[i], sizeof (char) * strlen(t) + 1);
			strcpy (f[i], t);
		}
	}
	removeCatClientes (catC);
	removeCatProdutos (catP);
}

char** posQuerie1 (CatClientes catC, CatProdutos catP, char** f){
	printf ("%s: Lidas = %d\n", f[0], totalClientes (catC));
	printf ("%s: Lidas = %d\n", f[1], totalProdutos (catP));
	printf ("%s: Lidas = ; Validadas = \n", f[2]);
	return defaultFileName (f);
}

int main (){
	int i;
	char linha[MAX_BUF];
	char* t;
	char** f = initFileName ();
	CatClientes catC = NULL;
	CatProdutos catP = NULL;
	printf ("Introduza valor: ");
	while (fgets(linha, MAX_BUF, stdin)){
		t = strtok (linha, " \r\n");
		switch (t[0]){
			case '1':
				preQuerie1 (catC, catP, t, f);
				catC = initCatClientes();
				catP = initCatProdutos();
				catC = lerClientes (catC, f[0]);
				catP = lerProdutos (catP, f[1]);
				lerVendas (catC, catP, f[2]);
				f = posQuerie1 (catC, catP, f);
		}
		printf ("Introduza valor: ");
	}
	printf ("\n");
	/*testGeralUM (catC, catV);*/
	/*testGeralDOIS ();*/
	/*testClient();*/
	/*testProduct();*/
	return 0;
}
