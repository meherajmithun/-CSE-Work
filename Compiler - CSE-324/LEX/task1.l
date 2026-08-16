%{
#include <stdio.h>
%}

%%
int|float|if|then|else|for|printf|switch|case|return   { printf("Keyword: %s\n", yytext); }
[0-9]+'.'[0-9]+  { printf("Float: %s\n", yytext); }
[0-9]+   { printf("Integer: %s\n", yytext); }
[ \t]+   { }
\n       { return 0; }
.        { printf("Unknown: %s\n", yytext); }
%%

int yywrap()
{
    return 1;
}

int main()
{
    printf("Enter one line: ");
    yylex();
    return 0;
}