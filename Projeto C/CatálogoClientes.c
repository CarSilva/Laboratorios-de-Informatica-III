#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#include "AVL.h"
#include "CatálogoClientes.h"

#define TAM_ALFABETO 26

struct clientes{
        AVL avl[TAM_ALFABETO];
        int size[TAM_ALFABETO];
};

struct cliente{
	char cliente[8]; 
};

struct conjclientes{
	char** lista;
	int size;
};

Cliente initCliente (){
	Cliente c = malloc (sizeof (struct cliente));
}

Cliente criaCliente (Cliente c, char* s){
	strcpy (c->cliente, s);
	return c;
}

char* getCliente (Cliente c){
	return c->cliente;
}

CatClientes initCatClientes(){
        int i;
	CatClientes catC = malloc (sizeof (struct clientes));;
	for (i = 0; i < TAM_ALFABETO; i++){
		catC->avl[i] = NULL;
		catC->size[i] = 0;
	}
        return catC;
}

CatClientes insereCliente (CatClientes catC, Cliente c){
	char l = c->cliente[0];
	int i = l - 'A';
	catC->avl[i] = insereAVL (catC->avl[i], c->cliente);
	catC->size[i]++;
	return catC;
}

bool existeCliente (CatClientes catC, Cliente c){
	char l = c->cliente[0];
	int i = l - 'A';
	return existeAVL (catC->avl[i], c->cliente);
}

int totalClientes (CatClientes catC){
	int i, t;
	for (i = 0, t = 0; i < TAM_ALFABETO; i++){
		t += catC->size[i];
	}
	return t;
}

int totalClientesLetra (CatClientes catC, char letra){
	int i = letra - 'A';
	return catC->size[i];
}

void removeCatClientes (CatClientes catC){
	int i;
	if (catC){
		for (i = 0; i < TAM_ALFABETO; i++){
			freeTree(catC->avl[i]);
		}
		free(catC);
	}
}

ConjClientes initConjClientes (){
	ConjClientes conjC = malloc (sizeof (struct conjclientes));
	conjC->size = 0;
	conjC->lista = NULL;
	return conjC;
}

ConjClientes clientesPorLetra (ConjClientes conjC, CatClientes catC, char c){
	int i = c - 'A';
	int p = 0;
	conjC->size = totalClientesLetra (catC, c);
	conjC->lista = realloc (conjC, sizeof (char*) * conjC->size);
	conjC->lista = inorder (catC->avl[i], conjC->lista, &p);
	return conjC;
}

