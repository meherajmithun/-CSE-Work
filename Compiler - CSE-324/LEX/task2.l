%{
#include <stdio.h>
%}

%%
"+"|"-"|"*"|"/"|"%"              { printf("Arithmetic Operator: %s\n", yytext); }
"<"|">"|"<="|">="|"=="|"!="      { printf("Relational Operator: %s\n", yytext); }
"&&"|"||"|"!"                    { printf("Logical Operator: %s\n", yytext); }

[ \t]+                           { }
\n                               { return 0; }

.                                { printf("Unknown: %s\n", yytext); }
%%

int yywrap()
{
    return 0;
}

int main()
{
    printf("Enter operators: ");
    yylex();
    return 0;
}