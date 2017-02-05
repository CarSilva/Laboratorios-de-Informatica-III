#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#include "AVL.h"
#include "CatálogoProdutos.h"

#define TAM_ALFABETO 26

struct produtos{
        AVL avl[TAM_ALFABETO];
        int size[TAM_ALFABETO];
};

struct produto{
	char produto[8]; 
};

struct conjprodutos{
	char** lista;
	int size;
};

Produto initProduto (){
	Produto p = malloc (sizeof (struct produto));
	return p;
}

Produto criaProduto (Produto p, char* s){
	strcpy (p->produto, s);
	return p;
}

char* getProduto (Produto p){
	return p->produto;
}

CatProdutos initCatProdutos(){
        int i;
	CatProdutos catP = malloc (sizeof (struct produtos));;
	for (i = 0; i < TAM_ALFABETO; i++){
		catP->avl[i] = NULL;
		catP->size[i] = 0;
	}
        return catP;
}

CatProdutos insereProduto (CatProdutos catP, Produto p){
	char l = p->produto[0];
	int i = l - 'A';
	catP->avl[i] = insereAVL (catP->avl[i], p->produto);
	catP->size[i]++;
	return catP;
}

bool existeProduto (CatProdutos catP, Produto p){
	char l = p->produto[0];
	int i = l - 'A';
	return existeAVL (catP->avl[i], p->produto);
}

int totalProdutos (CatProdutos catP){
	int i, t;
	for (i = 0, t = 0; i < TAM_ALFABETO; i++){
		t += catP->size[i];
	}
	return t;
}

int totalProdutosLetra (CatProdutos catP, char letra){
	int i = letra - 'A';
	return catP->size[i];
}

void removeCatProdutos (CatProdutos catP){
	int i;
	if (catP){
		for (i = 0; i < TAM_ALFABETO; i++){
			freeTree(catP->avl[i]);
		}
		free(catP);
	}
}

ConjProdutos initConjProdutos (){
	ConjProdutos conjP = malloc (sizeof (struct conjprodutos));
	conjP->size = 0;
	conjP->lista = NULL;
	return conjP;
}

ConjProdutos produtosPorLetra (ConjProdutos conjP, CatProdutos catP, char c){
	int i = c - 'A';
	int p = 0;
	conjP->size = totalProdutosLetra (catP, c);
	conjP->lista = realloc (conjP, sizeof (char*) * conjP->size);
	conjP->lista = inorder (catP->avl[i], conjP->lista, &p);
	return conjP;
}

