%{
#include <stdio.h>

int upper = 0;
int lower = 0;
%}

%%
[A-Z]              { upper++; }
[a-z]              { lower++; }
[0-9]              { }
[ \t]              { }
\n                 { return 0; }

[^A-Za-z0-9 \t\n]  { printf("Special Character: %s\n", yytext); }
%%

int yywrap()
{
    return 1;
}

int main()
{
    printf("Enter input: ");
    yylex();

    printf("\nUppercase Characters: %d\n", upper);
    printf("Lowercase Characters: %d\n", lower);

    return 0;
}