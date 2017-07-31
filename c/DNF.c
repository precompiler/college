#include <stdio.h>
#include <ctype.h>
#include<conio.h>
#include<string.h>
struct operator
{char opr[20];
 int top;
}oprStack;
struct value
{int val[20];
 int top;
}valStack;
struct operator *p_opr;
struct value *p_val;
int PriorityinStack(char);
void pushopr(char x);
void pushval(int x);
char popopr();
int popval();
void display();
long fun(int);
int PriorityoutStack(char);
void introduction();
void initArray();
void totalAtom();
int deal(int,int,char);
int final(char*,int*,int);
int f=0;
int atomnum=0;
char line[40];
int flag=0;
int temp;
int bin[40];
int tem[40];
int *pt;
void start();
char *string=line;
char *p=line;
char *check=line;
int length=0;
int i,j,z,n;
int next=0;
int x;
char l;
int *binary;
int y;
int s=0;
int test;
int opr=0;
char *out;
 main()
{
p_opr=&oprStack;
p_val=&valStack;
clrscr();
  introduction();
  printf("Input the formular:\n");
gets(string);
   totalAtom();
  initArray();
  start();
getch();
}
int final(char *p,int *binary,int next)
{int val1;
 int val2;
char *scan=p;
char top;
char top1;
p_opr->top=0;
p_val->top=0;
pushopr('#');
   while(*scan!='\0')
      {if(isalpha(*scan))
           pushval(binary[next++]);
       else if(*scan=='&'||*scan=='|'||*scan=='('||*scan==')'||*scan=='~')
           {top=popopr();
            pushopr(top);
             if(*scan==')')
                {top=popopr();
                     while(top!='(')
                        { if(top=='~')
{val1=popval();
                               pushval(!val1);
top=popopr();
}
                          else
{ val1=popval();
                               val2=popval();
                               pushval(deal(val1,val2,top));
                               top=popopr();}
                              }
                        }
       else if(PriorityoutStack(*scan)>PriorityinStack(top))
               pushopr(*scan);
       else if(PriorityoutStack(*scan)<PriorityinStack(top))
             { top=popopr();
                while(PriorityoutStack(*scan)<PriorityinStack(top))
                     { if(top=='~')
                         { val1=popval();
                           pushval(!val1);
                           top=popopr();}
	                   else {  val1=popval();
                              val2=popval();
                              pushval(deal(val1,val2,top));
                              top=popopr();}
                           }
                       pushopr(top);
                       pushopr(*scan);
                    }
             }
    else{printf("Illegal operator %c\n",*scan);
        getch();
        exit(1);
        }
     scan++;
 }
top=popopr();
while(top!='#')
{if(top=='~')
         {val1=popval();
          pushval(!val1);
          top=popopr();
}
 else{ val1=popval();
          val2=popval();
          pushval(deal(val1,val2,top));
          top=popopr();
}
}
return(popval());
}
void pushopr(char x)
{p_opr->opr[p_opr->top++]=x;}
void pushval(int y)
{p_val->val[p_val->top++]=y;}
int popval()
{return(p_val->val[--p_val->top]);
}
char popopr()
{return(p_opr->opr[--p_opr->top]);
}
int PriorityinStack(char operator)
{ switch(operator)
         {case '#':return -1;
          case '&':return 4;
          case '|':return 2;
          case '(':return 0;
          case '~':return 5;
         }
    }
int PriorityoutStack(char operator)
{ switch(operator)
         {case '#':return -1;
         case '&':return 3;
         case '|':return 1;
         case '(':return 8;
         case '~':return 7;
 }
    }
int deal(int val1,int val2,char operator)
{ switch(operator)
        {case '&':return(val1&&val2);
         case '|':return(val1||val2);
}
}
void start()
    {for(z=0;z<temp;z++)
        {out=line;
           if(flag==0)
              goto loop;
           else while(*pt!=0)
              {*pt=0;
                pt--;
              }
         *pt=1;
          pt=tem+39;
	      loop: for(y=40-atomnum;y<40;y++)
			      { bin[s++]=tem[y];
                  }
          s=0;
flag=1;
          x=final(string,binary,next);
          if(x==1)
             display();
          f=0;
}
printf("\b \n");
printf("Press any key to continue...\n");}
void initArray()
   { for(test=0;test<40;test++)
        {bin[test]=0;
          tem[test]=0;
	}
	temp=fun(atomnum);
binary=bin;
  pt=tem+39;}
long fun(int k)
{long m=1,i;
      for(i=0;i<k;i++)
        m=m*2;
       return m;
    }
void totalAtom()
{ for(;*p!='\0';p++)
        { if(isalpha(*p))
	      atomnum++ ;
          if(*p=='&'||*p=='|')
             opr++;
        }
     if(atomnum==0)
     {printf("No atom in formular\n");
      getch();
      exit(1);
     }
    if(opr==0)
     {printf("Illegal input\n");
       getch();
     exit(1);
     }
  while(*check!='\0')
    {length++;
     check++;
    }
  if(length==atomnum)
    {printf("No operator in formular\n");
     getch();
     exit(1);
    } 
}
void display()
{printf("(");
      for(;*out!='\0';out++)
         {if(isalpha(*out))
             {if(binary[f++]==0)
                 printf("~%c&",*out);
              else
                 printf("%c&",*out);
             }
         }
      printf("\b)|");
     }
void introduction()
{printf("********************************************************************************\n");
       printf("                                  INTRODUCTION\n");
       printf("This program can recognize 5 operators: \n");
       textcolor(GREEN);
       cprintf(" Not:");
printf(" '~'");
cprintf(" AND:");
printf(" '&'");
cprintf(" OR:");
printf(" '|'");
cprintf(" LEFT_Par:");
printf(" '('");
cprintf(" RIGHT_Par:");
printf(" ')'");
        printf("\n");textcolor(WHITE);
 printf("********************************************************************************\n");
} 
