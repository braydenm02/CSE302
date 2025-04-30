%{
#include <iostream>
#include <stdlib.h>
#include <string.h>
#include "PtoCparser.tab.h"
extern "C" int yylex();
%}
digit   [0-9]
NUMBER  (0|-?[1-9]{digit}*)
%%
.		{std::cout << "SYNTAX ERROR: " << yytext << " IS ILLEGAL" << std::endl;}
%%