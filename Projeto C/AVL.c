#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#include "AVL.h"

int max (int a, int b){
	return (a > b ? a : b);
}

int altura (AVL a){
	if (a)
		return a->altura;
	return 0;
}

int diferenca (AVL a, AVL b){
	return altura (a) - altura (b);
}

AVL rotacaoEsq (AVL a){
	AVL aux = a->dir;
	a->dir = aux->esq;
	aux->esq = a;
	return aux;
}

AVL rotacaoDir (AVL a){
	AVL aux = a->esq;
	a->esq = aux->dir;
	aux->dir = a;
	return aux;
}

AVL balanceDir (AVL a){
	AVL aux = a->dir;
	if (diferenca (aux->dir, aux->esq) > 0){
		a = rotacaoEsq (a);
		aux = a->esq;
		aux->altura = max (altura(aux->dir), altura(aux->esq)) + 1;
		return a;
	}
	a->dir = rotacaoDir (a->dir);
	a = rotacaoEsq (a);
	aux = a->esq;
	aux->altura = max (altura(aux->dir), altura(aux->esq)) + 1;
	aux = a->dir;
	aux->altura = max (altura(aux->dir), altura(aux->esq)) + 1;
	return a;
}

AVL balanceEsq (AVL a){
	AVL aux = a->esq;
	if (diferenca (aux->esq, aux->dir) > 0){
		a = rotacaoDir (a);
		aux = a->dir;
		aux->altura = max (altura(aux->esq), altura(aux->dir)) + 1;
		return a;
	}
	a->esq = rotacaoEsq (a->esq);
	a = rotacaoDir (a);
	aux = a->dir;
	aux->altura = max (altura(aux->esq), altura(aux->dir)) + 1;
	aux = a->esq;
	aux->altura = max (altura(aux->esq), altura(aux->dir)) + 1;
	return a;
}

AVL insereDir (AVL a, char* codigo){
	a->dir = insereAVL (a->dir, codigo);
	if (diferenca (a->dir, a->esq) == 2){
		a = balanceDir (a);
	}
	a->altura = max (altura(a->dir), altura(a->esq)) + 1;
	return a;
}

AVL insereEsq (AVL a, char* codigo){
	a->esq = insereAVL (a->esq, codigo);
	if (diferenca (a->esq, a->dir) == 2){
		a = balanceEsq (a);
	}
	a->altura = max (altura(a->esq), altura(a->dir)) + 1;
	return a;
}

AVL insereAVL (AVL a, char* codigo){
	if (!a){
		a = malloc (sizeof (struct avl));
		a->codigo = malloc (sizeof (char) * 10);
		strcpy (a->codigo, codigo);
		a->esq = a->dir = NULL;
		a->altura = 1;
		return a;
	}
	if (strcmp (codigo, a->codigo) > 0)
		return insereDir (a, codigo);
	return insereEsq (a, codigo);
}

bool existeAVL (AVL a, char* codigo){
        AVL aux = a;
        int i;
        while (aux){
                i = strcmp (codigo, aux->codigo);
                if (i == 0)
                        return true;
                if (i > 0)
                        aux = aux->dir;
                else
                        aux = aux->esq;
        }
        return false;
}

void freeTree (AVL a){
        if (a){
                freeTree (a->esq);
                freeTree (a->dir);
                free (a);
        }
}

char** inorder (AVL a, char** l, int* i){
        int p;
	if (a){
		l = inorder (a->esq, l, i);
		p = *i;
                l[p] = malloc (sizeof (char) * 10);
		strcpy (l[p], a->codigo);
		(*i)++;
                l = inorder (a->dir, l, i);
        }
        return l;
}
