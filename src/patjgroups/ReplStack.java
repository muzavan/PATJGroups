/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package patjgroups;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Stack;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

/**
 *
 * @author M. Reza Irvanda
 * @param <T>
 */
public class ReplStack<T> extends ReceiverAdapter {
    Stack<T> set = new Stack<T>();
    JChannel channel;
    
    public ReplStack(String name) throws Exception{
        channel = new JChannel();
        channel.setReceiver(this);
        channel.connect(name);
        channel.getState(null,10000);
    }
    
    public void close(){
        channel.close();
    }
    
    @Override
    public void viewAccepted(View new_view){
        System.out.println("New : "+new_view);
    }
    
    @Override
    public void receive(Message msg){
        MessageObject<T> obj;
        try {
            obj = (MessageObject<T>) deserialize(msg.getBuffer());
            synchronized(set){
            switch(obj.getOperationType()){
                case PUSH:
                    set.push(obj.getObject());
                    System.out.println("'"+obj.getObject()+"' pushed");
                    break;
                case POP:
                    System.out.println("'"+set.pop()+"' poped");
                    break;
                case TOP:
                    System.out.println("'"+set.peek()+"' on top");
                    break;
                default :
                    break;
            }
        }
        } catch (IOException ex) {
            Logger.getLogger(ReplStack.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReplStack.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void getState(OutputStream out) throws Exception{
        synchronized(set){
            Util.objectToStream(set, new DataOutputStream(out));
        }
    }
    
    @Override
    public void setState(InputStream input) throws Exception{
        Stack<T> s;
        s = (Stack<T>) Util.objectFromStream(new DataInputStream(input));
        synchronized(set){
            for(T obj : s){
                set.add(obj);
                System.out.println(obj.toString() + " added to set");
            }
        }
    }
    
    public boolean push(T obj) throws Exception{
        MessageObject<T> messageobject = new MessageObject<>(obj,MessageObject.OperationType.PUSH);
        Message message = new Message(null,null,serialize(messageobject));
        channel.send(message);
        return true;
    }
    
    public T top() throws Exception{
        MessageObject<T> messageobject = new MessageObject<>(null,MessageObject.OperationType.TOP);
        Message message = new Message(null,null,serialize(messageobject));
        channel.send(message);
        return set.peek();
    }
    
    public boolean pop() throws Exception{
        MessageObject<T> messageobject = new MessageObject<>(null,MessageObject.OperationType.POP);
        Message message = new Message(null,null,serialize(messageobject));
        channel.send(message);
        return true;
    }
    
    protected Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException{
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        return objectStream.readObject();
    }
    
    protected byte[] serialize(Object obj) throws IOException{
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(obj);
        return byteStream.toByteArray();
    }
    
}
