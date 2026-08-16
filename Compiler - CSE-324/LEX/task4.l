%{
#include <stdio.h>
%}

%%
0[xX][0-9a-fA-F]+    { printf("Hexadecimal Number: %s\n", yytext); }
[0-9]+               { printf("Decimal Number: %s\n", yytext); }

[ \t]+               { }
\n                   { return 0; }

.                    { printf("Unknown: %s\n", yytext); }
%%

int yywrap()
{
    return 1;
}

int main()
{
    printf("Enter numbers: ");
    yylex();
    return 0;
}