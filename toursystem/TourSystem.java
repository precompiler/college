import java.io.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
class InitMatrix{  //��ʼ������
	byte []buf=new byte[50]; //�����ֽ�����
	String temp;
	int row=0,col=0,i=0,chi;
	InitMatrix(){
	}
	public void loadMatrix(int [][]e,int vn,String path) throws FileNotFoundException,IOException{//�����������
		File mf=new File(path); //�����ļ�����
		FileInputStream fin=new FileInputStream(mf);//�ļ�����������
		while((chi=fin.read())!=-1){ //��������
			if(chi!=32){ //��Ϊ�ո�
				buf[i]=(byte)chi;//���뻺������
				i++;
			}
			else{
				temp=new String(buf,0,i);//�û������鹹���ַ���
				i=0;
				e[row][col]=(Integer.decode(temp)).intValue();//���ַ���ֵת��Ϊint�Ͳ������ڽӾ���
				col++;
				if(col==vn){
					row++;
					col=0;
				}
			}
		}
		fin.close();//�ر��ļ�������
	}
}
class InitVnum{  //��ʼ���������
	int chi,i=0;
	char []buf=new char[20];//��������
	InitVnum(){//���캯��
	}
	public int loadVnum(String path) throws FileNotFoundException,IOException{//���뾰�����
		File vf=new File(path);
		FileInputStream fin=new FileInputStream(vf);
		while((chi=fin.read())!=-1){
			buf[i]=(char)chi; //��ȡ���ݣ����뻺��
			i++;
		}
		fin.close();//�ر��ļ�������
		String temp=new String(buf,0,i);
		return (Integer.decode(temp)).intValue();//���ؾ������
	}
}
class InitList{  //��ʼ�������˵�
	File lf;
	public void loadList(Choice ch,String dbpath) throws FileNotFoundException,IOException{//��ȡ�������ƴ��������˵�
		lf=new File(dbpath);
		FileInputStream fin=new FileInputStream(lf);
		String temp;
		byte []buf=new byte[50]; //�����������
		int i=0,chi;
		while((chi=fin.read())!=-1){
			if(chi!=32){
				buf[i]=(byte)chi;
				i++;
			}
			else{
				temp=new String(buf,0,i,"GBK"); //ת��Ϊ�ַ���
				i=0;
				ch.add(temp); //���������˵�
			}
		}
		fin.close(); //�ر��ļ�������
	}
}
		
class InitPosition{//��ʼ��ͼ����
	byte []buf=new byte[50];
	int i,chi;
	String temp;
	InitPosition(){
	}
	public void loadPositionX(int []px,String pathx) throws FileNotFoundException,IOException{//��ȡ���������
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
				px[index]=(Integer.decode(temp)).intValue();//���������������px��
				index++;
			}
		}
		finx.close();
	}
	public void loadPositionY(int []py,String pathy) throws FileNotFoundException,IOException{//��ȡ����������
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
class Guide{//�������·��������·��
	int []visited; //���ĳ�����Ƿ񱻷��ʹ�
	int []p;//���������·��ʱ;���ĸ�����
 	int vn; //�������
	Guide(int v){//���캯������ʼ�������
		vn=v;
		visited=new int[vn];
		p=new int[200];
	}
	private void path(int [][]e,int i,int j,int k,Choice cc,TextArea tx){//���ݷ���������������·��
		int s;int flag=0;
		if(p[k]==j){ //�ҵ�һ��·��
			for(int x=0;x<k;x++){
				for(int y=x+1;y<k;y++){//����Ƿ��л�·
					if(p[x]==p[y]){
					  flag=1; //���ڻ�·
					  break;
					}
				}
				if(flag==1) //�˳�forѭ��
					  break;
			}
			if(flag==0){
			    for(s=0;s<=k;s++){
			    tx.append(cc.getItem(p[s])+" ");//���ı��������;���ľ�������
			    }
			    tx.append("\n");//���У�׼�������һ��·����������ڣ�
		  }
		  else
		      ; //�л�·��·�������
		}
		else{// ��û�ҵ��յ�
			s=0;
			while(s<vn){
				if(e[p[k]][s]!=32767&&visited[s]==0){//���p[k]���s��֮����·����sû�����ʹ�
					visited[s]=1; //��s���Ϊ���ʹ��ĵ�
					p[k+1]=s; //��s����;����������
					path(e,i,j,k+1,cc,tx); //�ݹ�����¸���
					visited[s]=0; //����
				}
				s++;
			}
		}
	}
	public void findAllPath(int [][]e,int i,int j,Choice cc,TextArea tx){
		int k;
		p[0]=i;//�����i����;��·������
		tx.setText("�� "+cc.getItem(i)+" �� "+cc.getItem(j)+" ������·��\n\n");
		for(k=0;k<vn;k++)
		 visited[i]=0; //��ʼ��
		this.path(e,i,j,0,cc,tx);
	}
	
	
	private void ppath(int []path,int i,int v0,TextArea tx,int []rem,int index,Choice cc){
	  int k;
	  k=path[i];
	  if(k==v0)
		 return;
	  ppath(path,k,v0,tx,rem,index+1,cc);
	  tx.append(cc.getItem(k)+" ");
	  rem[index]=k;//printf("%d,",k);k��������
  }
  private void DisPath(int []dist,int []path,int []st,int n,int v0,int e,TextArea tx,int []rem,int index,Choice cc){
		  if((st[e]==1)&&(e!=v0)){//���e������·������e��v0����ͬһ��
		  	tx.setText("");
			  tx.append("�� "+cc.getItem(v0)+" �� "+cc.getItem(e)+" ��̾���"+Integer.toString(dist[e])+"��\n\n·����"+cc.getItem(v0)+" ");
			  ppath(path,e,v0,tx,rem,index,cc);//�ݹ���ʾ�����·��;���ĸ���
			  tx.append(cc.getItem(e)+"\n");  
		  }
		  else{
			  tx.setText(" ��·��\n");//������ʾ��·��
			}
  }
  public void findShortestPath(int [][]e,int v0,int end,TextArea tx,int []rem,int index,Choice cc){//�ҽ�˹�����㷨
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
class InitInfo{ //��ʼ��������Ϣ
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
class Modifywnd extends Frame implements WindowListener,ActionListener{//�޸���Ϣ������
	Label title,vn,vnm,vi,vm,vpx,vpy;
	TextField vertexNum;
	TextArea vertexInfo,matrix,xposition,yposition,pname;
	Button save,cancel,yes,no;
	Dialog confirm; //ȷ�ϱ���Ի���
	String temp;
	File mf;
	FileInputStream fin;
	FileOutputStream fout;
	int chi,i=0;
	byte []buf=new byte[800];
	Modifywnd() throws FileNotFoundException,IOException{
		super("���/ɾ������");
		GridBagLayout gbLayout=new GridBagLayout();//����GridBagLayout����
		GridBagConstraints gbc=new GridBagConstraints();
		setLayout(gbLayout);
		title=new Label("���/ɾ������");
	  vn=new Label("�������");
	  vnm=new Label("��������");
	  vi=new Label("�������");
	  vm=new Label("�ڽӾ���");
	  vpx=new Label("���������");
	  vpy=new Label("����������");
		
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
		
		save=new Button("�����޸�");
		save.setBackground(new Color(236,228,209));
		gbc.gridx=2;gbc.gridy=16;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbLayout.setConstraints(save,gbc);
		save.addActionListener(this);
		this.add(save);
		cancel=new Button("ȡ  ��");
		cancel.setBackground(new Color(236,228,209));
		gbc.gridx=3;gbc.gridy=16;
		gbc.gridheight=1;gbc.gridwidth=1;
		gbLayout.setConstraints(cancel,gbc);
		cancel.addActionListener(this);
		this.add(cancel);
		yes=new Button("��");
		yes.setBackground(new Color(236,228,209));
		yes.addActionListener(this);
		no=new Button("��");
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
		while((chi=fin.read())!=-1){//����������Ϣ����Ӧ���ı�������ʾ���Ա��޸�
			buf[i]=(byte)chi;
			i++;
		}
		fin.close();
		temp=new String(buf,0,i,"GBK");
		pname.setText(temp);
		i=0;
		mf=new File("database\\VertexInfo.txt");
		fin=new FileInputStream(mf);
		while((chi=fin.read())!=-1){//����������Ϣ����Ӧ���ı�������ʾ���Ա��޸�
			buf[i]=(byte)chi;
			i++;
		}
		fin.close();
		temp=new String(buf,0,i,"GBK");
		vertexInfo.setText(temp);
		i=0;
		mf=new File("database\\Matrix.txt");
		fin=new FileInputStream(mf);
		while((chi=fin.read())!=-1){//����������Ϣ����Ӧ���ı�������ʾ���Ա��޸�
			buf[i]=(byte)chi;
			i++;
		}
		fin.close();
		temp=new String(buf,0,i);
		matrix.setText(temp);
		i=0;
		mf=new File("database\\Positionx.txt");
		fin=new FileInputStream(mf);
		while((chi=fin.read())!=-1){//����������Ϣ����Ӧ���ı�������ʾ���Ա��޸�
			buf[i]=(byte)chi;
		  i++;
		}
		fin.close();
		temp=new String(buf,0,i);
		xposition.setText(temp);
		i=0;
		mf=new File("database\\Positiony.txt");
		fin=new FileInputStream(mf);
		while((chi=fin.read())!=-1){//����������Ϣ����Ӧ���ı�������ʾ���Ա��޸�
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
		public void windowOpened(WindowEvent e){       /*   ʵ��           */
	  }
	  public void windowClosing(WindowEvent e){      /*  WindowListener3 */
		 this.dispose();                               /*                  */
	  }
	  public void windowClosed(WindowEvent e){       /*     �е�         */
	  }
	  public void windowIconified(WindowEvent e){    /*                  */
	  }
	  public void windowDeiconified(WindowEvent e){  /*                  */
	  }
	  public void windowActivated(WindowEvent e){    /*   ����           */
	  }
	  public void windowDeactivated(WindowEvent e){  /*   ����           */
	  }
	  public void actionPerformed(ActionEvent et){   /* ʵ��ActionListener�еĳ��󷽷�*/
	  	if(et.getSource()==cancel){ //���ȡ����ť
	  		this.dispose();//�رոô���
	  	}
	  	if(et.getSource()==yes){//������ǡ���ť
	  		confirm.dispose();//�ر�ȷ�ϱ���Ի���
	  		try{//���޸���Ϣд���Ӧ�ı��ļ�
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
	  		catch(IOException e){} //�����쳣
	  	}
	  	if(et.getSource()==no){ //������񡱰�ť
	  		confirm.dispose();//�ر�ȷ�ϱ���Ի���
	  	}
	  	if(et.getSource()==save){ //����������޸ġ���ť
	  		confirm=new Dialog(this,"",true); //�����Ի���
	  		Panel p1=new Panel();
	  		p1.add("Center",new Label("ȷ��Ҫ�����޸���?"));
	  		Panel p2=new Panel();
	  		p2.add(yes);
	  		p2.add(no);
	  		confirm.add("Center",p1);//������
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
	int vertexNum;  //�������
	int [][]edges;  //����ڽӾ���
	int []positionX;//��ž��������
	int []positionY;//��ž���������
	int []pv;//���ĳ�����Ƿ�Ϊ���·����;����
	String []vertexInfo;//��ž�����Ϣ
	Image img;
	Choice list1; //�����˵�
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
	Dialog login;//��½�Ի���
	Label user;
	Label password;
	Label wrong;
	TextField uname;//�����û�������
	TextField upwd;//������������
	Button confirm;
	Button cancel;
	InitList initObj;
	InitInfo infObj;
	InitVnum ivObj;
	InitPosition ipObj;
	InitMatrix imObj;
	Guide gdObj;
  AppWnd() throws IOException,FileNotFoundException{
  	super("У԰������ѯϵͳ");
  	this.addWindowListener(this);
  	setLayout(new FlowLayout());
  	img=getToolkit().getImage("imgs\\4.gif");
  	list1=new Choice();
  	list1.setBackground(new Color(236,228,209));
  	this.add(list1);
  	
  	viewIntro=new Button("�쿴������Ϣ");
  	viewIntro.addActionListener(this);
  	viewIntro.setBackground(new Color(236,228,209));
  	this.add(viewIntro);
  	
  	displayInfo=new TextArea("",5,90,TextArea.SCROLLBARS_NONE);
  	displayInfo.setEditable(false);
  	displayInfo.setBackground(new Color(236,228,209));
  	this.add(displayInfo);

  	ivObj=new InitVnum();
  	vertexNum=ivObj.loadVnum("database\\PointNum.txt");//��ȡ�������
  	edges=new int[vertexNum][vertexNum];
  	imObj=new InitMatrix();
  	imObj.loadMatrix(edges,vertexNum,"database\\Matrix.txt");//��ȡ�ڽӾ���
  	gdObj=new Guide(vertexNum);
  	vertexInfo=new String [vertexNum];
  	pv=new int[vertexNum+2];
  	for(int t=0;t<pv.length;t++)
  	  pv[t]=-1;//���е㶼�������·��;����
  	positionX=new int[vertexNum];
    positionY=new int[vertexNum];
    initObj=new InitList();
  	initObj.loadList(list1,"database\\ListItem.txt");//��ʼ�������˵�
  	infObj=new InitInfo();
  	ipObj=new InitPosition();
  	ipObj.loadPositionX(positionX,"database\\Positionx.txt");//��ʼ�������������
  	ipObj.loadPositionY(positionY,"database\\Positiony.txt");//��ʼ��������������
  	infObj.loadInfo(vertexInfo,"database\\VertexInfo.txt");//��ʼ��������Ϣ
  	
  	from=new Label("��ʼ��");
  	this.add(from);
  	
  	start=new Choice();
  	start.setBackground(new Color(236,228,209));
  	initObj.loadList(start,"database\\ListItem.txt");
  	this.add(start);
  	
  	to=new Label("Ŀ�ĵ�");
  	this.add(to);
  	
  	end=new Choice();
  	end.setBackground(new Color(236,228,209));
  	initObj.loadList(end,"database\\ListItem.txt");
  	this.add(end);
  	
  	checkShortest=new Button("�쿴���·��");
  	checkShortest.setBackground(new Color(236,228,209));
  	checkShortest.addActionListener(this);
  	this.add(checkShortest);
  	
  	checkAll=new Button("�쿴����·��");
  	checkAll.setBackground(new Color(236,228,209));
  	checkAll.addActionListener(this);
  	this.add(checkAll);
  	
  	displayPath=new TextArea("",10,90,TextArea.SCROLLBARS_NONE);
  	displayPath.setEditable(false);
  	displayPath.setBackground(new Color(236,228,209));
  	this.add(displayPath);
  	
  	admin=new Button("����Ա��½");
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
  public void paint(Graphics g){//����·��ͼ
  	Color cr=new Color(213,125,54);//�����·��;�������ɫ
  	Color cr2=new Color(0,0,0);//�������·��;�������ɫ
  	boolean flag=false;//���ĳ�������Ƿ�Ϊ���·��;����
  	for(int cur=0;cur<vertexNum;cur++){
  		for(int p=0;p<pv.length;p++){
  			if(pv[p]==cur){//��;����
  			  flag=true;
  			  break;
  			}
  			else
  			  flag=false;
  		}
  		if(flag){//��;������cr��ɫ��ʵ��Բ��
  			g.setColor(cr);
  	    g.fillOval(positionX[cur],positionY[cur],10,10);//ȡ��cur����ĺ������껭Բ��
  	    g.drawString(list1.getItem(cur),positionX[cur],positionY[cur]);
  	  }
  	  else{
  	  	g.setColor(cr2);
  	  	g.drawOval(positionX[cur],positionY[cur],10,10);
  	  	g.drawString(list1.getItem(cur),positionX[cur],positionY[cur]);
  	  }
  	}
  	g.setColor(cr2);
  	for(int i=0;i<vertexNum;i++){//��ͼ�еĵ�����
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
		if(ae.getSource()==viewIntro){  //���¡��쿴������Ϣ����ť
			displayInfo.setText(vertexInfo[list1.getSelectedIndex()]); //��ʾ������Ϣ
		}
		if(ae.getSource()==checkShortest){  //���¡��쿴���·������ť
			int ifrom,ito;
			ifrom=start.getSelectedIndex();  //ȡ����ѡ��Ŀ�ı��
			ito=end.getSelectedIndex();
			gdObj.findShortestPath(edges,ifrom,ito,displayPath,pv,0,list1);
			repaint(); //�ػ�·��ͼ��������·��;����
		}
		if(ae.getSource()==checkAll){//���¡��쿴����·������ť
			int s,e;
			for(int k=0;k<pv.length;k++)
			  pv[k]=-1;
			s=start.getSelectedIndex();
			e=end.getSelectedIndex();
			gdObj.findAllPath(edges,s,e,list1,displayPath);
			repaint();
		}
		if(ae.getSource()==admin){//���¡�����Ա��½��
			login=new Dialog(this,"����Ա��½",true);//������½�Ի���
			login.setResizable(false);//��С���ɵ���
			Panel p1=new Panel();
			wrong=new Label("                            ");
			confirm=new Button("��½");
			confirm.setBackground(new Color(236,228,209));
			confirm.addActionListener(this);
			cancel=new Button("ȡ��");
			cancel.setBackground(new Color(236,228,209));
			cancel.addActionListener(this);
			user=new Label("�û���");
			password=new Label("����");
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
	  if(ae.getSource()==confirm){//�����½��ť
	  	String un=new String(uname.getText());
	  	String up=new String(upwd.getText());
	  	if(un.compareTo("admin")==0&&up.compareTo("admin")==0){//��������û�������ȷ
	  		login.dispose();//�رյ�½�Ի���
	  		try{
	  		  Modifywnd mw=new Modifywnd();//�����޸���Ϣ����
	  		}
	  		catch(FileNotFoundException e){}
	  		catch(IOException e){}//�����쳣
	  	}
	  	else
	  	  wrong.setText("�û����������");//���������û�������ȷ
	  }
	  if(ae.getSource()==cancel){//�����ȡ������ť
	  	login.dispose();//�رյ�½�Ի���
	  }
	}
}
public class TourSystem{ //�����࣬��������������
	public static void main(String []args) throws IOException,FileNotFoundException{//���������׳��쳣
		AppWnd myapp=new AppWnd();//����AppWnd�����
	}
}