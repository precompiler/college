#include<stdio.h>
#include<malloc.h>

struct Elm
{int el[100];
 int belong[100];
 }element;
 
void display(int *code,int n)                      /*This function is used to print the result*/
 {int i,j,max;
   for(i=1;i<=n;i++)                               /*initialize the struct Elm*/
    {element.el[i]=i;
     element.belong[i]=*(code+i-1);
    }
   max=element.belong[1];
   for(i=2;i<=n;i++)                               /*find the biggest number of syndrome*/
     if(max<element.belong[i])
       max=element.belong[i];
   printf("{");                                    /*begin to print*/
   for(i=1;i<=max;i++)
      {printf("{");
	  for(j=1;j<=n;j++)                        /*print all the elements that belong to one syndrome*/
	    {if(element.belong[j]==i)
	      printf("X%d," ,element.el[j]);
	    }
       printf("\b}");                              /*remove the last comma(,) and print a }*/
      }
	    printf("}\n");                         /*finish one division*/
 }

void set_partition(int n)             
{int *code,*max;
 int i,j;
 code=(int *)malloc(sizeof(int)*n);                /*create memory*/
 max=(int *)malloc(sizeof(int)*n);
  for(i=0;i<n;i++)                                 /*initialize arrays*/
    {code[i]=1;
     max[i]=2;
    }
  while(1)
   {display(code,n);                               /*call function display() and print out result*/
    for(i=n-1;code[i]==max[i];i--)                 /*find next increasible*/
     ;
    if(i>0)                                        /*found?*/
     {code[i]++;                                   /*refresh*/
       for(j=i+1;j<n;j++)
         {code[j]=1;
          max[j]=max[i]+((code[i]==max[i])? 1:0) ;
         }
     }
    else break;                                  /*not found,break*/
   }
   free(code);          /*free memory*/
   free(max);  
}
   main()            /*main start from here*/
   {int n;
    printf("Input the value of n:\n");
    scanf("%d",&n);
    set_partition(n);
    getch();
   }
