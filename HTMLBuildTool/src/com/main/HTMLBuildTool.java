package com.main;
import static java.lang.System.out;
import static javax.swing.JOptionPane.YES_NO_OPTION;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.*;


//实现接口ActionListener
public class HTMLBuildTool implements ActionListener {

    JFrame jf;
    JPanel jpanel;
    JButton jb1, jb2, jb3,jb4;
    JTextArea jta = null;
    JScrollPane jscrollPane;
    String middleValue = "0";

    public HTMLBuildTool() {
        jf = new JFrame("JSP 生成工具");
        Container contentPane = jf.getContentPane();
        contentPane.setLayout(new BorderLayout());

        jta = new JTextArea(10, 15);
        jta.setTabSize(4);
        jta.setFont(new Font("标楷体", Font.BOLD, 16));
        jta.setLineWrap(true);// 激活自动换行功能
        jta.setWrapStyleWord(true);// 激活断行不断字功能
        //jta.setBackground(Color.pink);

        jscrollPane = new JScrollPane(jta);
        jpanel = new JPanel();
        jpanel.setLayout(new GridLayout(1, 3));

        jb1 = new JButton("复制");
        jb1.addActionListener(this);
        jb2 = new JButton("粘贴");
        jb2.addActionListener(this);
        jb3 = new JButton("清空");
        jb3.addActionListener(this);
        jb4 = new JButton("生成 JSP");
        jb4.addActionListener(this);

        jpanel.add(jb1);
        jpanel.add(jb2);
        jpanel.add(jb3);
        jpanel.add(jb4);


        contentPane.add(jscrollPane, BorderLayout.CENTER);
        contentPane.add(jpanel, BorderLayout.SOUTH);

        jf.setSize(550, 450);
        jf.setLocation(350, 150);
        jf.setVisible(true);

        jf.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    // 覆盖接口ActionListener的方法actionPerformed
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb1) {
            jta.copy();
        } else if (e.getSource() == jb2) {
            jta.paste();
        } else if (e.getSource() == jb3) {
            jta.setText("");
        } else if (e.getSource() == jb4) {
        	if(jta.getText()==""||jta.getText().isEmpty()||jta.getText()==null){
                JOptionPane.showMessageDialog(null, "文本不能为空！","生成失败",  JOptionPane.ERROR_MESSAGE);
        	}else{
        		String textData[] = jta.getText().split("\n");
            	String[] datas = new String[textData.length];
        		for(int i=0;i<textData.length;i++){
//        			String data = textData[i]+"<br>";
        			String data = textData[i];
        			datas[i] = data.toString();
        		}      		
        		//命名文件
        		String htmlName = JOptionPane.showInputDialog("输入 JSP 文件名:",middleValue);
        		middleValue = htmlName;
        		if(htmlName.isEmpty()||htmlName.length()==0||htmlName==null){
                    JOptionPane.showMessageDialog(null, "名称不能为空！","请输入名称",  JOptionPane.ERROR_MESSAGE);
        		}else{
//            		txt2html(strArr2str(datas),htmlName);//文本生成  html 网页
        			txt2jsp(datas,htmlName);
        			int result = JOptionPane.showConfirmDialog(null,"是否预览 JSP 文件?\n", "生成成功",  YES_NO_OPTION );
                    if (result==0){
                        try {
                            Desktop.getDesktop().open(new File("D:\\tool\\data\\"+htmlName+".jsp"));
                        }catch(IOException ieo) {

                        }
                    }
        		}
        	}
            
        }
    }

    
    public String strArr2str(String strArr[]){//String数组转换为String
    	StringBuffer dataBuffer = new StringBuffer();
		for(int i = 0; i < strArr.length; i++)
		{ 
			dataBuffer.append(strArr[i]);
		}	
		String contentData = dataBuffer.toString();
    	return contentData;
    }
    
    
    public void txt2jsp(String datass[],String htmlName){
    	String filePath = "D:\\tool\\template.jsp";
        String disrPath = "D:\\tool\\data\\";
        String fileName = htmlName;
    	 try {
             String templateContent = "";
             FileInputStream fileinputstream = new FileInputStream(filePath);// 读取模板文件
             int lenght = fileinputstream.available();
             byte bytes[] = new byte[lenght];
             fileinputstream.read(bytes);
             fileinputstream.close();
             templateContent = new String(bytes);
//             System.out.print(templateContent);
             if(datass.length>=1) {
            	 templateContent = templateContent.replaceAll("###audioname###", datass[0]);
//            	 String audioname = datass[0];
//            	 String num =audioname.substring(audioname.length()-3,audioname.length());
//            	 templateContent = templateContent.replaceAll("###num###", num);
//                 templateContent = templateContent.replaceAll("###hotelname###", datass[1]);
             }
             System.out.print(templateContent);
             
             String fileame = fileName + ".jsp";
             fileame = disrPath+"/" + fileame;
             FileOutputStream fileoutputstream = new FileOutputStream(fileame);// 建立文件输出流
//             System.out.print("文件输出路径:");
//             System.out.print(fileame);
             byte tag_bytes[] = templateContent.getBytes();
             fileoutputstream.write(tag_bytes);
             fileoutputstream.close();
         } catch (Exception e) {
             System.out.print(e.toString());
         }
    	
    }
  
    public void txt2html(String datass,String htmlName){
//    	String filePath = "D:\\tool\\template.html";
    	String filePath = "D:\\tool\\template.jsp";
        String disrPath = "D:\\tool\\data\\";
        String fileName = htmlName;
    	 try {
             String templateContent = "";
             FileInputStream fileinputstream = new FileInputStream(filePath);// 读取模板文件
             int lenght = fileinputstream.available();
             byte bytes[] = new byte[lenght];
             fileinputstream.read(bytes);
             fileinputstream.close();
             templateContent = new String(bytes);
//             System.out.print(templateContent);
             templateContent = templateContent.replaceAll("###content###", datass);
             System.out.print(templateContent);
             
             String fileame = fileName + ".jsp";
             fileame = disrPath+"/" + fileame;// 生成的html文件保存路径。
             FileOutputStream fileoutputstream = new FileOutputStream(fileame);// 建立文件输出流
//             System.out.print("文件输出路径:");
//             System.out.print(fileame);
             byte tag_bytes[] = templateContent.getBytes();
             fileoutputstream.write(tag_bytes);
             fileoutputstream.close();
         } catch (Exception e) {
             System.out.print(e.toString());
         }
    	
    }
    

    public static void main(String[] args) {
        new HTMLBuildTool();
    }
}