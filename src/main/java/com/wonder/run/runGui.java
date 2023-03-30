package com.wonder.run;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class runGui extends JFrame implements ActionListener
{

    JLabel labelData  = new JLabel("", JLabel.CENTER);
    JTextField textFieldData;
    JLabel labelUrl  = new JLabel("", JLabel.CENTER);
    JTextField textFieldUrl;
    JLabel labelPort  = new JLabel("", JLabel.CENTER);
    JTextField JTextFieldPort;
    JLabel labelUserName  = new JLabel("", JLabel.CENTER);
    JTextField JTextFieldPortUsername;
    JLabel labelPassword  = new JLabel("", JLabel.CENTER);
    JTextField JTextFieldPortPassword;
    JButton submitButton = new JButton("Submit");

    public runGui()
    {
        JPanel myPanel = new JPanel();
        add(myPanel);
        textFieldUrl  =new JTextField(10);
        JTextFieldPort=new JTextField(10);
        textFieldData = new JTextField(10);
        JTextFieldPortUsername = new JTextField(10);
        JTextFieldPortPassword = new JTextField(10);

        labelUrl.setText("数据库地址");
        myPanel.add(labelUrl);
        textFieldUrl.setText("127.0.0.1");
        myPanel.add(textFieldUrl);

        labelPort.setText("数据端口");
        myPanel.add(labelPort);
        JTextFieldPort.setText("3306");
        myPanel.add(JTextFieldPort);

        labelData.setText("数据库名");
        myPanel.add(labelData);
        textFieldData.setText("test_sql");
        myPanel.add(textFieldData);

        labelUserName.setText("用户名");
        myPanel.add(labelUserName);
        JTextFieldPortUsername.setText("root");
        myPanel.add(JTextFieldPortUsername);

        labelPassword.setText("密码");
        myPanel.add(labelPassword);
        JTextFieldPortPassword.setText("123456");
        myPanel.add(JTextFieldPortPassword);

        myPanel.add(submitButton);
        submitButton.addActionListener(this);


    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        JDialog frame = new JDialog();//构造一个新的JFrame，作为新窗口。
        frame.setBounds(// 让新窗口与SwingTest窗口示例错开50像素。
                new Rectangle(
                        (int) this.getBounds().getX() + 50,
                        (int) this.getBounds().getY() + 50,
                        (int) this.getBounds().getWidth(),
                        (int) this.getBounds().getHeight()
                )
        );

        if (actionEvent.getSource() == submitButton) {
            String url = textFieldUrl.getText();
            String port = JTextFieldPort.getText();
            String data = textFieldData.getText();
            String username = JTextFieldPortUsername.getText();
            String password = JTextFieldPortPassword.getText();
            DatebaseTable datebaseTable = new DatebaseTable();
            try {
                List<String> resList = datebaseTable.tableName(data,url,port,username,password);
                //System.out.println(resList.size());
                if(resList.size() ==0){
                    System.out.println("没有这个数据库");
                    JOptionPane.showMessageDialog(null,"没有这个数据库！","没有这个数据库！",JOptionPane.WARNING_MESSAGE);	//消息对话框
                }else {
                    frame.setLayout(new BorderLayout());
                    frame.setLayout(new GridLayout(4,4,5,5));
                    JCheckBox[] boxs = new JCheckBox[resList.size()];// 创建控件数组
                      for (int i = 0; i < resList.size(); i++) {
                          //System.out.println(resList.get(i));
                          boxs[i] = new JCheckBox (resList.get(i));// 初始化数组中的复选框组件
                          frame.add(boxs[i]);
                      }
                    JButton submitButtonCode = new JButton("生成代码");
                    frame.add(submitButtonCode);
                    submitButtonCode.addActionListener(new ActionListener(){// 增加监听动作
                        public void actionPerformed(ActionEvent e){// 满足 动作 事件 的选择后触发以下事件
                            //System.out.println(e.getSource());
                            List<String> addList=new ArrayList<String>();
                            for (int i = 0; i <boxs.length ; i++) {
                                //System.out.println(boxs[i].getText() + " 是否选中: " + boxs[i].isSelected());
                                if(boxs[i].isSelected()){
                                    //System.out.println(boxs[i].getText());
                                    addList.add(boxs[i].getText());
                                }
                            }
                            System.out.println(addList);
                            if (addList.size()==0) {
                                System.out.println("没有这个数据库");
                                JOptionPane.showMessageDialog(null,"哥，你还没有选表。。。","你还没有选表！",JOptionPane.WARNING_MESSAGE);	//消息对话框
                            }else {
                                CodeGenerator.makeCoder(url+":"+port+"/"+data,username,password,addList);
                                JOptionPane.showMessageDialog(frame,"代码已经生成，关闭gui窗口查看");
                                frame.setVisible(false);
                            }

                        }
                    });
                    frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);    // 设置模式类型。
                    frame.setVisible(true);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }


    public static void main(String args[])
    {
        runGui g = new runGui();
        g.setLocation(200, 250);
        g.setSize(181, 200);
        g.setVisible(true);
    }
}