/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package patexplore;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
    
/**
 *
 * @author M. Reza Irvanda
 */
public class SimpleChat extends ReceiverAdapter {
    JChannel channel;
    String user_name = System.getProperty("user.name","n/a");
    
    private void start() throws Exception{
        channel = new JChannel();
        channel.setReceiver(this);
        channel.connect("ChatCluster");
        eventLoop();
        channel.close();
    }
    
    private void eventLoop(){
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            try{
                System.out.print("> ");
                System.out.flush();
                String line = in.readLine().toLowerCase();
                if(line.startsWith("quit") || line.startsWith("exit"))
                    break;
                line = "{" + user_name + "] " + line;
                Message msg = new Message(null,null,line);
                channel.send(msg);
            }
            catch(Exception e){
                
            }
        }
    }
    
    @Override
    public void viewAccepted(View new_view){
        System.out.println("** view: "+new_view);
    }
    
    @Override
    public void receive(Message msg){
        System.out.println(msg.getSrc() + ": " + msg.getObject());
    }
    public static void main(String[] args){
        try{
            new SimpleChat().start();
        }
        catch(Exception e){
            System.out.println("=== Welcome to the chat ===");
        }
    }
}
