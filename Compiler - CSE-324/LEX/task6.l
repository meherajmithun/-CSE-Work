%{
#include <stdio.h>

int characters = 0;
int words = 0;
int lines = 0;
%}

%%
\n                  { lines++; characters++; return 0; }
[ \t]+              { characters += yyleng; }
[A-Za-z0-9]+        { words++; characters += yyleng; }
.                   { characters++; }
%%

int yywrap()
{
    return 1;
}

int main()
{
    printf("Enter input: ");
    yylex();

    printf("\nNumber of Characters: %d\n", characters);
    printf("Number of Words: %d\n", words);
    printf("Number of Lines: %d\n", lines);

    return 0;
}