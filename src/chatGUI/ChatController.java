/*Nama : Windy Israniati Jihan
  NIM  : 1301144309
  Kelas: IF-38-09*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javaChat.ClientConnection;

/**
 *
 * @author TOSHIBA
 */
public class ChatController implements ActionListener {
    private ChatView view;
    private ClientConnection client = null;

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source == view.getTxFieldChat()){
            if(client==null){
                try {
                client = new ClientConnection();
                String ip = view.getStringChat();
                client.connect(ip);
                WriteOutput w = new WriteOutput();
                w.start();
                } catch (Exception e){
                    System.out.println("Error : " + e);
                }
            }
            else{
                String input = view.getStringChat();
                client.writeStream(input);
                view.setTxFieldChat("");
            }
        }
    }
    public ChatController(){
        view = new ChatView();
        view.setVisible(true);
        view.addListener(this);
        client = null;
    }
    public class WriteOutput extends Thread{
        @Override
        public void run(){
            try{
                String inputan;
                while((inputan = client.readStream()) != null){
                    view.setTxAreaChat(inputan);
                }
            }catch (Exception e){
                System.out.println("Error : " + e);
            }
        }
    }
}
