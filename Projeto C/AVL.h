#ifndef avl_h

#define avl_h

typedef struct avl{
        int altura;
	char* codigo;
        struct avl* esq;
        struct avl* dir;
} *AVL;

AVL insereAVL (AVL, char*);

bool existeAVL (AVL, char*);

void freeTree (AVL);

char** inorder (AVL, char**, int*);

#endif
