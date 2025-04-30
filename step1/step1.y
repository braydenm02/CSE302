%{
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <string>
#include <iostream>
#include <fstream>
#include <utility>
#include <cmath>

extern "C" int yylex();
extern "C" int yyparse();
extern FILE * yyin;

int line_no = 0;

void error(const char []);
void yyerror(const char []);
%}
%union {
	int ival;
	char * sval;
	float fval;
}
%token programEnd begin end forward turn NUMBER
%type <ival> NUMBER
%%

program : {line_no++;} block {line_no++;}
	;

block : begin statement_list end
	;

statement_list : statement statement_list
	| statement
	;

statement : command
	;

command : forward distance
	| turn angle
	;

distance : NUMBER
	;

angle : NUMBER
	;

%%

int main( int argc, char **argv )
{
	char * loc;
	if( argc != 2 )
		std::cout << "Usage: PtoC inputfile\n";
	else
	{
		std::string srcFilePath = argv[1];
		yyin = fopen( *++argv, "r" );
		if( yyin == NULL )
		{
			std::cout << "SP: Couldn't open " << *argv << std::endl;
			exit( 1 );
		}
		std::string fileName = "";
		
		yyparse();
		fclose( yyin );
	}
}

void error( const char msg[] )
{
	std::cout << "LINE " << line_no << " : " << msg << std::endl;
	exit( -1 );
}

void yyerror(const char s[]) {
  std::cout << "EEK, parse error!  Message: " << s << std::endl;
  // might as well halt now:
  exit(-1);
}

void nextToken() {

}

void hasNext() {

}
