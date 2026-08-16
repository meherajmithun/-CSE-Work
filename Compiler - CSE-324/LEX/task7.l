%{
#include <stdio.h>
%}

%x COMMENT

%%
"//".*          { printf("Single Line Comment: %s\n", yytext); }

"/*"            { 
                    printf("Multi Line Comment: %s", yytext);
                    BEGIN(COMMENT);
                }

<COMMENT>"*/"   { 
                    printf("%s\n", yytext);
                    BEGIN(INITIAL);
                }

<COMMENT>\n     { printf("\n"); }

<COMMENT>.      { printf("%s", yytext); }

.|\n            { }
%%

int yywrap()
{
    return 1;
}

int main()
{
    FILE *fp;

    fp = fopen("input.txt", "r");

    if (fp == NULL)
    {
        printf("File not found!\n");
        return 1;
    }

    yyin = fp;
    yylex();

    fclose(fp);

    return 0;
}