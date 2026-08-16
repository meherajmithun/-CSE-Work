%{
#include <stdio.h>
%}

%%
[AEIOUaeiou]    { printf("%s is a Vowel\n", yytext); }
[A-Za-z]        { printf("%s is not a Vowel\n", yytext); }

[ \t]+          { }
\n              { return 0; }

.               { printf("%s is not a Letter\n", yytext); }
%%

int yywrap()
{
    return 1;
}

int main()
{
    printf("Enter a letter: ");
    yylex();
    return 0;
}