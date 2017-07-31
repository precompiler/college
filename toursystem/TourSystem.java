import java.io.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
class InitMatrix{  //初始化矩阵
	byte []buf=new byte[50]; //缓存字节数组
	String temp;
	int row=0,col=0,i=0,chi;
	InitMatrix(){
	}
	public void loadMatrix(int [][]e,int vn,String path) throws FileNotFoundException,IOException{//读入矩阵数据
		File mf=new File(path); //创建文件对象
		FileInputStream fin=new FileInputStream(mf);//文件输入流对象
		while((chi=fin.read())!=-1){ //读入数据
			if(chi!=32){ //不为空格
				buf[i]=(byte)chi;//存入缓存数组
				i++;
			}
			else{
				temp=new String(buf,0,i);//用缓存数组构造字符串
				i=0;
				e[row][col]=(Integer.decode(temp)).intValue();//将字符串值转换为int型并存入邻接矩阵
				col++;
				if(col==vn){
					row++;
					col=0;
				}
			}
		}
		fin.close();//关闭文件输入流
	}
}
class InitVnum{  //初始化顶点个数
	int chi,i=0;
	char []buf=new char[20];//缓存数组
	InitVnum(){//构造函数
	}
	public int loadVnum(String path) throws FileNotFoundException,IOException{//读入景点个数
		File vf=new File(path);
		FileInputStream fin=new FileInputStream(vf);
		while((chi=fin.read())!=-1){
			buf[i]=(char)chi; //读取数据，存入缓存
			i++;
		}
		fin.close();//关闭文件输入流
		String temp=new String(buf,0,i);
		return (Integer.decode(temp)).intValue();//返回景点个数
	}
}
class InitList{  //初始化下拉菜单
	File lf;
	public void loadList(Choice ch,String dbpath) throws FileNotFoundException,IOException{//读取景点名称创建下拉菜单
		lf=new File(dbpath);
		FileInputStream fin=new FileInputStream(lf);
		String temp;
		byte []buf=new byte[50]; //缓存读入数据
		int i=0,chi;
		while((chi=fin.read())!=-1){
			if(chi!=32){
				buf[i]=(byte)chi;
				i++;
			}
			else{
				temp=new String(buf,0,i,"GBK"); //转换为字符串
				i=0;
				ch.add(temp); //加入下拉菜单
			}
		}
		fin.close(); //关闭文件输入流
	}
}
		
class InitPosition{//初始绘图坐标
	byte []buf=new byte[50];
	int i,chi;
	String temp;
	InitPosition(){
	}
	public void loadPositionX(int []px,String pathx) throws FileNotFoundException,IOException{//读取景点横坐标
		File pfx=new File(pathx);
		FileInputStream finx=new FileInputStream(pfx);
		i=0;
		int index=0;
		while((chi=finx.read())!=-1){
			if(chi!=32){
				buf[i]=(byte)chi;
				i++;
			}
			else{
				temp=new String(buf,0,i);
				i=0;
				px[index]=(Integer.decode(temp)).intValue();//将横坐标存入数组px中
				index++;
			}
		}
		finx.close();
	}
	public void loadPositionY(int []py,String pathy) throws FileNotFoundException,IOException{//读取景点纵坐标
		File pfy=new File(pathy);
		FileInputStream finy=new FileInputStream(pfy);
		i=0;
		int index=0;
		while((chi=finy.read())!=-1){
			if(chi!=32){
				buf[i]=(byte)chi;
				i++;
			}
			else{
				temp=new String(buf,0,i);
				i=0;
				py[index]=(Integer.decode(temp)).intValue();
				index++;
			}
		}
		finy.close();
	}
}
class Guide{//计算最短路径和所有路径
	int []visited; //标记某个点是否被访问过
	int []p;//存放求所有路径时途经的各个点
 	int vn; //景点个数
	Guide(int v){//构造函数，初始景点个数
		vn=v;
		visited=new int[vn];
		p=new int[200];
	}
	private void path(int [][]e,int i,int j,int k,Choice cc,TextArea tx){//回溯法求两景点间的所有路径
		int s;int flag=0;
		if(p[k]==j){ //找到一条路径
			for(int x=0;x<k;x++){
				for(int y=x+1;y<k;y++){//检查是否有回路
					if(p[x]==p[y]){
					  flag=1; //存在回路
					  break;
					}
				}
				if(flag==1) //退出for循环
					  break;
			}
			if(flag==0){
			    for(s=0;s<=k;s++){
			    tx.append(cc.getItem(p[s])+" ");//在文本框中输出途经的景点名称
			    }
			    tx.append("\n");//换行，准备输出下一条路径（如果存在）
		  }
		  else
		      ; //有回路的路径不输出
		}
		else{// 还没找到终点
			s=0;
			while(s<vn){
				if(e[p[k]][s]!=32767&&visited[s]==0){//如果p[k]点和s点之间有路径且s没被访问过
					visited[s]=1; //将s标记为访问过的点
					p[k+1]=s; //将s纳入途经景点数组
					path(e,i,j,k+1,cc,tx); //递归访问下个点
					visited[s]=0; //回溯
				}
				s++;
			}
		}
	}
	public void findAllPath(int [][]e,int i,int j,Choice cc,TextArea tx){
		int k;
		p[0]=i;//将起点i纳入途经路径数组
		tx.setText("从 "+cc.getItem(i)+" 到 "+cc.getItem(j)+" 的所有路径\n\n");
		for(k=0;k<vn;k++)
		 visited[i]=0; //初始化
		this.path(e,i,j,0,cc,tx);
	}
	
	
	private void ppath(int []path,int i,int v0,TextArea tx,int []rem,int index,Choice cc){
	  int k;
	  k=path[i];
	  if(k==v0)
		 return;
	  ppath(path,k,v0,tx,rem,index+1,cc);
	  tx.append(cc.getItem(k)+" ");
	  rem[index]=k;//printf("%d,",k);k存入数组
  }
  private void DisPath(int []dist,int []path,int []st,int n,int v0,int e,TextArea tx,int []rem,int index,Choice cc){
		  if((st[e]==1)&&(e!=v0)){//如果e被纳入路径并且e与v0不是同一点
		  	tx.setText("");
			  tx.append("从 "+cc.getItem(v0)+" 到 "+cc.getItem(e)+" 最短距离"+Integer.toString(dist[e])+"米\n\n路径："+cc.getItem(v0)+" ");
			  ppath(path,e,v0,tx,rem,index,cc);//递归显示出最短路径途经的各点
			  tx.append(cc.getItem(e)+"\n");  
		  }
		  else{
			  tx.setText(" 无路径\n");//否则显示无路径
			}
  }
  public void findShortestPath(int [][]e,int v0,int end,TextArea tx,int []rem,int index,Choice cc){//狄杰斯特拉算法
	  int []dist=new int[vn];
	  int []path=new int[vn];
	  int []s=new int[vn];
	  int mindis,i,j,u,n=vn;
	  for(int m=0;m<n+2;m++)
	    rem[m]=-1;
	  for(i=0;i<n;i++){
		  dist[i]=e[v0][i];
		  s[i]=0;
		  if(e[v0][i]<32767)
			  path[i]=v0;
		  else
			  path[i]=-1;
	  }
	  s[v0]=1;
	  path[v0]=0;
	  for(i=0;i<n;i++){
		  mindis=32767;
		  u=v0;
		  for(j=0;j<n;j++)
			  if((s[j]==0)&&(dist[j]<mindis)){
				  u=j;
				  mindis=dist[j];
			  }
			s[u]=1;
			for(j=0;j<n;j++)
				if(s[j]==0)
					if((e[u][j]<32767)&&(dist[u]+e[u][j]<dist[j])){
						dist[j]=dist[u]+e[u][j];
						path[j]=u;
					}
	  }
	  rem[vn]=v0;
	  rem[vn+1]=end;
	  DisPath(dist,path,s,n,v0,end,tx,rem,index,cc);
  }
}
class InitInfo{ //初始化景点信息
	File mf;
	public void loadInfo(String []des,String dbpath) throws IOException,FileNotFoundException{
		mf=new File(dbpath);
		FileInputStream fin=new FileInputStream(mf);
		String temp;
		byte []buf=new byte[100];
		int i=0,chi,index=0;
		while((chi=fin.read())!=-1){
			if(chi!=32){
				buf[i]=(byte)chi;
				i++;
			}
			else{
				temp=new String(buf,0,i,"GBK");
				i=0;
				des[index]=new String(temp);
				index++;
			}
		}
	}
}
class Modifywnd extends Frame implements WindowListener,ActionListener{//修改信息窗口类
	Label title,vn,vnm,vi,vm,vpx,vpy;
	TextField vertexNum;
	TextArea vertexInfo,matrix,xposition,yposition,pname;
	Button save,cancel,yes,no;
	Dialog confirm; //确认保存对话框
	String temp;
	File mf;
	FileInputStream fin;
	FileOutputStream fout;
	int chi,i=0;
	byte []buf=new byte[800];
	Modifywnd() throws FileNotFoundException,IOException{
		super("添加/删除景点");
		GridBagLayout gbLayout=new GridBagLayout();//采用GridBagLayout布局
		GridBagConstraints gbc=new GridBagConstraints();
		setLayout(gbLayout);
		title=new Label("添加/删除景点");
	  vn=new Label("景点个数");
	  vnm=new Label("景点名称");
	  vi=new Label("景点介绍");
	  vm=new Label("邻接矩阵");
	  vpx=new Label("景点横坐标");
	  vpy=new Label("景点纵坐标");
		
		gbc.gridx=1;gbc.gridy=1;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbc.gridheight=1;
		gbc.insets=new Insets(3,6,2,6);
		gbLayout.setConstraints(title,gbc);
		this.add(title);
		
		gbc.gridx=1;gbc.gridy=2;
		gbc.gridheight=1;
		gbc.gridwidth=1;
		gbLayout.setConstraints(vn,gbc);
		this.add(vn);
		
		vertexNum=new TextField(4);
		vertexNum.setBackground(new Color(236,228,209));
		gbc.gridx=2;gbc.gridy=2;
		gbc.gridheight=1;
		gbc.gridwidth=1;
		gbLayout.setConstraints(vertexNum,gbc);
		this.add(vertexNum);
		
		gbc.gridx=1;gbc.gridy=3;
		gbc.gridheight=1;
		gbc.gridwidth=1;
		gbLayout.setConstraints(vnm,gbc);
		this.add(vnm);
		
		pname=new TextArea("",4,40,TextArea.SCROLLBARS_NONE);
		pname.setBackground(new Color(236,228,209));
		gbc.gridx=2;gbc.gridy=3;
		gbc.gridheight=3;gbc.gridwidth=8;
		gbLayout.setConstraints(pname,gbc);
		this.add(pname);
		
		gbc.gridx=1;gbc.gridy=6;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbLayout.setConstraints(vi,gbc);
		this.add(vi);
		
		vertexInfo=new TextArea("",4,40,TextArea.SCROLLBARS_NONE);
		vertexInfo.setBackground(new Color(236,228,209));
		gbc.gridx=2;gbc.gridy=6;
		gbc.gridwidth=8;gbc.gridheight=3;
		gbLayout.setConstraints(vertexInfo,gbc);
		this.add(vertexInfo);
		
		
		gbc.gridx=1;gbc.gridy=9;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbLayout.setConstraints(vm,gbc);
		this.add(vm);
		
		matrix=new TextArea("",4,40,TextArea.SCROLLBARS_NONE);
		matrix.setBackground(new Color(236,228,209));
		gbc.gridx=2;gbc.gridy=9;
		gbc.gridheight=3;gbc.gridwidth=8;
		gbLayout.setConstraints(matrix,gbc);
		this.add(matrix);
		
		gbc.gridx=1;gbc.gridy=12;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbLayout.setConstraints(vpx,gbc);
		this.add(vpx);
		
		xposition=new TextArea("",4,40,TextArea.SCROLLBARS_NONE);
		xposition.setBackground(new Color(236,228,209));
		gbc.gridx=2;gbc.gridy=12;
		gbc.gridheight=2;gbc.gridwidth=8;
		gbLayout.setConstraints(xposition,gbc);
		this.add(xposition);
		
		gbc.gridx=1;gbc.gridy=14;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbLayout.setConstraints(vpy,gbc);
		this.add(vpy);
		
		yposition=new TextArea("",4,40,TextArea.SCROLLBARS_NONE);
		yposition.setBackground(new Color(236,228,209));
		gbc.gridx=2;gbc.gridy=14;
		gbc.gridheight=2;gbc.gridwidth=8;
		gbLayout.setConstraints(yposition,gbc);
		this.add(yposition);
		
		save=new Button("保存修改");
		save.setBackground(new Color(236,228,209));
		gbc.gridx=2;gbc.gridy=16;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbLayout.setConstraints(save,gbc);
		save.addActionListener(this);
		this.add(save);
		cancel=new Button("取  消");
		cancel.setBackground(new Color(236,228,209));
		gbc.gridx=3;gbc.gridy=16;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbLayout.setConstraints(cancel,gbc);
		cancel.addActionListener(this);
		this.add(cancel);
		yes=new Button("是");
		yes.setBackground(new Color(236,228,209));
		yes.addActionListener(this);
		no=new Button("否");
		no.setBackground(new Color(236,228,209));
		no.addActionListener(this);
		this.addWindowListener(this);
		
	
		mf=new File("database\\PointNum.txt");
		fin=new FileInputStream(mf);
		while((chi=fin.read())!=-1){
			buf[i]=(byte)chi;
			i++;
		}
		fin.close();
		String temp=new String(buf,0,i);
		vertexNum.setText(temp);
		i=0;
		mf=new File("database\\ListItem.txt");
		fin=new FileInputStream(mf);
		while((chi=fin.read())!=-1){//读入数据信息在相应的文本区域显示，以便修改
			buf[i]=(byte)chi;
			i++;
		}
		fin.close();
		temp=new String(buf,0,i,"GBK");
		pname.setText(temp);
		i=0;
		mf=new File("database\\VertexInfo.txt");
		fin=new FileInputStream(mf);
		while((chi=fin.read())!=-1){//读入数据信息在相应的文本区域显示，以便修改
			buf[i]=(byte)chi;
			i++;
		}
		fin.close();
		temp=new String(buf,0,i,"GBK");
		vertexInfo.setText(temp);
		i=0;
		mf=new File("database\\Matrix.txt");
		fin=new FileInputStream(mf);
		while((chi=fin.read())!=-1){//读入数据信息在相应的文本区域显示，以便修改
			buf[i]=(byte)chi;
			i++;
		}
		fin.close();
		temp=new String(buf,0,i);
		matrix.setText(temp);
		i=0;
		mf=new File("database\\Positionx.txt");
		fin=new FileInputStream(mf);
		while((chi=fin.read())!=-1){//读入数据信息在相应的文本区域显示，以便修改
			buf[i]=(byte)chi;
		  i++;
		}
		fin.close();
		temp=new String(buf,0,i);
		xposition.setText(temp);
		i=0;
		mf=new File("database\\Positiony.txt");
		fin=new FileInputStream(mf);
		while((chi=fin.read())!=-1){//读入数据信息在相应的文本区域显示，以便修改
			buf[i]=(byte)chi;
			i++;
		}
		fin.close();
		temp=new String(buf,0,i);
		yposition.setText(temp);
		this.setSize(400,600);
		this.setBackground(new Color(236,228,209));
		this.setLocation(100,100);//pack();
		show();
	}
		public void windowOpened(WindowEvent e){       /*   实现           */
	  }
	  public void windowClosing(WindowEvent e){      /*  WindowListener3 */
		 this.dispose();                               /*                  */
	  }
	  public void windowClosed(WindowEvent e){       /*     中的         */
	  }
	  public void windowIconified(WindowEvent e){    /*                  */
	  }
	  public void windowDeiconified(WindowEvent e){  /*                  */
	  }
	  public void windowActivated(WindowEvent e){    /*   抽象           */
	  }
	  public void windowDeactivated(WindowEvent e){  /*   方法           */
	  }
	  public void actionPerformed(ActionEvent et){   /* 实现ActionListener中的抽象方法*/
	  	if(et.getSource()==cancel){ //点击取消按钮
	  		this.dispose();//关闭该窗口
	  	}
	  	if(et.getSource()==yes){//点击“是”按钮
	  		confirm.dispose();//关闭确认保存对话框
	  		try{//将修改信息写入对应文本文件
	  		  mf=new File("database\\PointNum.txt");
	  		  fout=new FileOutputStream(mf);
	  		  temp=new String(vertexNum.getText());
	  		  fout.write(temp.getBytes());
	  		  mf=new File("database\\VertexInfo.txt");
	  		  fout=new FileOutputStream(mf);
	  		  temp=new String(vertexInfo.getText());
	  		  fout.write(temp.getBytes("GBK"));
	  		  mf=new File("database\\Matrix.txt");
	  		  fout=new FileOutputStream(mf);
	  		  temp=new String(matrix.getText());
	  		  fout.write(temp.getBytes());
	  		  mf=new File("database\\Positionx.txt");
	  		  fout=new FileOutputStream(mf);
	  		  temp=new String(xposition.getText());
	  		  fout.write(temp.getBytes());
	  		  mf=new File("database\\Positiony.txt");
	  		  fout=new FileOutputStream(mf);
	  		  temp=new String(yposition.getText());
	  		  fout.write(temp.getBytes());
	  		  mf=new File("database\\ListItem.txt");
	  		  fout=new FileOutputStream(mf);
	  		  temp=new String(pname.getText());
	  		  fout.write(temp.getBytes("GBK"));
	  		  fout.close();
	  		}
	  		catch(FileNotFoundException e){}
	  		catch(IOException e){} //捕获异常
	  	}
	  	if(et.getSource()==no){ //点击“否”按钮
	  		confirm.dispose();//关闭确认保存对话框
	  	}
	  	if(et.getSource()==save){ //点击“保存修改”按钮
	  		confirm=new Dialog(this,"",true); //创建对话框
	  		Panel p1=new Panel();
	  		p1.add("Center",new Label("确定要保存修改吗?"));
	  		Panel p2=new Panel();
	  		p2.add(yes);
	  		p2.add(no);
	  		confirm.add("Center",p1);//添加面板
	  		confirm.add("South",p2);
	  		confirm.setSize(200,100);
	  		confirm.setLocation(200,200);
	  		confirm.setResizable(false);
	  		confirm.setBackground(new Color(236,228,209));
	  		confirm.show();
	  	}
	  }
}	
class AppWnd extends Frame implements WindowListener,ActionListener{
	int vertexNum;  //景点个数
	int [][]edges;  //存放邻接矩阵
	int []positionX;//存放景点横坐标
	int []positionY;//存放景点纵坐标
	int []pv;//存放某景点是否为最短路径的途径点
	String []vertexInfo;//存放景点信息
	Image img;
	Choice list1; //下拉菜单
	Button viewIntro;
	TextArea displayInfo;
	Label from;
	Choice start;
	Label to;
	Choice end;
	Button checkShortest;
	Button checkAll;
	Button admin;
	TextArea displayPath;
	Dialog login;//登陆对话框
	Label user;
	Label password;
	Label wrong;
	TextField uname;//输入用户名区域
	TextField upwd;//输入密码区域
	Button confirm;
	Button cancel;
	InitList initObj;
	InitInfo infObj;
	InitVnum ivObj;
	InitPosition ipObj;
	InitMatrix imObj;
	Guide gdObj;
  AppWnd() throws IOException,FileNotFoundException{
  	super("校园导游咨询系统");
  	this.addWindowListener(this);
  	setLayout(new FlowLayout());
  	img=getToolkit().getImage("imgs\\4.gif");
  	list1=new Choice();
  	list1.setBackground(new Color(236,228,209));
  	this.add(list1);
  	
  	viewIntro=new Button("察看景点信息");
  	viewIntro.addActionListener(this);
  	viewIntro.setBackground(new Color(236,228,209));
  	this.add(viewIntro);
  	
  	displayInfo=new TextArea("",5,90,TextArea.SCROLLBARS_NONE);
  	displayInfo.setEditable(false);
  	displayInfo.setBackground(new Color(236,228,209));
  	this.add(displayInfo);

  	ivObj=new InitVnum();
  	vertexNum=ivObj.loadVnum("database\\PointNum.txt");//读取景点个数
  	edges=new int[vertexNum][vertexNum];
  	imObj=new InitMatrix();
  	imObj.loadMatrix(edges,vertexNum,"database\\Matrix.txt");//读取邻接矩阵
  	gdObj=new Guide(vertexNum);
  	vertexInfo=new String [vertexNum];
  	pv=new int[vertexNum+2];
  	for(int t=0;t<pv.length;t++)
  	  pv[t]=-1;//所有点都不是最短路径途经点
  	positionX=new int[vertexNum];
    positionY=new int[vertexNum];
    initObj=new InitList();
  	initObj.loadList(list1,"database\\ListItem.txt");//初始化下拉菜单
  	infObj=new InitInfo();
  	ipObj=new InitPosition();
  	ipObj.loadPositionX(positionX,"database\\Positionx.txt");//初始化各景点横坐标
  	ipObj.loadPositionY(positionY,"database\\Positiony.txt");//初始化各景点纵坐标
  	infObj.loadInfo(vertexInfo,"database\\VertexInfo.txt");//初始化景点信息
  	
  	from=new Label("起始点");
  	this.add(from);
  	
  	start=new Choice();
  	start.setBackground(new Color(236,228,209));
  	initObj.loadList(start,"database\\ListItem.txt");
  	this.add(start);
  	
  	to=new Label("目的地");
  	this.add(to);
  	
  	end=new Choice();
  	end.setBackground(new Color(236,228,209));
  	initObj.loadList(end,"database\\ListItem.txt");
  	this.add(end);
  	
  	checkShortest=new Button("察看最短路径");
  	checkShortest.setBackground(new Color(236,228,209));
  	checkShortest.addActionListener(this);
  	this.add(checkShortest);
  	
  	checkAll=new Button("察看所有路径");
  	checkAll.setBackground(new Color(236,228,209));
  	checkAll.addActionListener(this);
  	this.add(checkAll);
  	
  	displayPath=new TextArea("",10,90,TextArea.SCROLLBARS_NONE);
  	displayPath.setEditable(false);
  	displayPath.setBackground(new Color(236,228,209));
  	this.add(displayPath);
  	
  	admin=new Button("管理员登陆");
  	admin.setBackground(new Color(236,228,209));
  	admin.addActionListener(this);
  	this.add(admin);
    this.setLocation(10,10);	
  	this.setSize(700,680);
  	this.setIconImage(img);
  	this.setBackground(new Color(236,228,209));
  	this.setResizable(false);
  	show();
  }
  public void paint(Graphics g){//绘制路线图
  	Color cr=new Color(213,125,54);//是最短路径途经点的颜色
  	Color cr2=new Color(0,0,0);//不是最短路径途经点的颜色
  	boolean flag=false;//标记某个景点是否为最短路径途经点
  	for(int cur=0;cur<vertexNum;cur++){
  		for(int p=0;p<pv.length;p++){
  			if(pv[p]==cur){//是途经点
  			  flag=true;
  			  break;
  			}
  			else
  			  flag=false;
  		}
  		if(flag){//是途经点用cr颜色画实心圆点
  			g.setColor(cr);
  	    g.fillOval(positionX[cur],positionY[cur],10,10);//取出cur景点的横纵座标画圆点
  	    g.drawString(list1.getItem(cur),positionX[cur],positionY[cur]);
  	  }
  	  else{
  	  	g.setColor(cr2);
  	  	g.drawOval(positionX[cur],positionY[cur],10,10);
  	  	g.drawString(list1.getItem(cur),positionX[cur],positionY[cur]);
  	  }
  	}
  	g.setColor(cr2);
  	for(int i=0;i<vertexNum;i++){//将图中的点连线
  		for(int j=0;j<i;j++){
  			if(edges[i][j]!=32767){
  			  g.drawLine(positionX[i],positionY[i],positionX[j],positionY[j]);
  		  }
  	  }
    }
  }
  public void windowOpened(WindowEvent e){
	}
	public void windowClosing(WindowEvent e){
		System.exit(0);
	}
	public void windowClosed(WindowEvent e){
	}
	public void windowIconified(WindowEvent e){
	}
	public void windowDeiconified(WindowEvent e){
	}
	public void windowActivated(WindowEvent e){
	}
	public void windowDeactivated(WindowEvent e){
	}
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==viewIntro){  //按下“察看景点信息”按钮
			displayInfo.setText(vertexInfo[list1.getSelectedIndex()]); //显示景点信息
		}
		if(ae.getSource()==checkShortest){  //按下“察看最短路径”按钮
			int ifrom,ito;
			ifrom=start.getSelectedIndex();  //取得所选项目的标号
			ito=end.getSelectedIndex();
			gdObj.findShortestPath(edges,ifrom,ito,displayPath,pv,0,list1);
			repaint(); //重绘路线图，标出最短路径途经点
		}
		if(ae.getSource()==checkAll){//按下“察看所有路径”按钮
			int s,e;
			for(int k=0;k<pv.length;k++)
			  pv[k]=-1;
			s=start.getSelectedIndex();
			e=end.getSelectedIndex();
			gdObj.findAllPath(edges,s,e,list1,displayPath);
			repaint();
		}
		if(ae.getSource()==admin){//按下”管理员登陆”
			login=new Dialog(this,"管理员登陆",true);//创建登陆对话框
			login.setResizable(false);//大小不可调整
			Panel p1=new Panel();
			wrong=new Label("                            ");
			confirm=new Button("登陆");
			confirm.setBackground(new Color(236,228,209));
			confirm.addActionListener(this);
			cancel=new Button("取消");
			cancel.setBackground(new Color(236,228,209));
			cancel.addActionListener(this);
			user=new Label("用户名");
			password=new Label("密码");
			uname=new TextField(8);
			uname.setBackground(new Color(236,228,209));
			upwd=new TextField(8);
			upwd.setBackground(new Color(236,228,209));
			upwd.setEchoChar('*');
			p1.add(user);
			p1.add(uname);
			p1.add(password);
			p1.add(upwd);
			login.add("North",p1);
			Panel p2=new Panel();
			Panel p3=new Panel();
			p2.add(confirm);
			p2.add(cancel);
			p3.add(wrong);
			login.add("South",p2);
			login.add("Center",p3);
			login.setSize(300,120);
			login.setLocation(300,300);
			login.setBackground(new Color(236,228,209));
			login.show();
	  }
	  if(ae.getSource()==confirm){//点击登陆按钮
	  	String un=new String(uname.getText());
	  	String up=new String(upwd.getText());
	  	if(un.compareTo("admin")==0&&up.compareTo("admin")==0){//如果密码用户名都正确
	  		login.dispose();//关闭登陆对话框
	  		try{
	  		  Modifywnd mw=new Modifywnd();//启动修改信息界面
	  		}
	  		catch(FileNotFoundException e){}
	  		catch(IOException e){}//捕获异常
	  	}
	  	else
	  	  wrong.setText("用户名或密码错");//如果密码或用户名不正确
	  }
	  if(ae.getSource()==cancel){//点击”取消“按钮
	  	login.dispose();//关闭登陆对话框
	  }
	}
}
public class TourSystem{ //公共类，创建程序主窗口
	public static void main(String []args) throws IOException,FileNotFoundException{//主函数，抛出异常
		AppWnd myapp=new AppWnd();//创建AppWnd类对象
	}
}