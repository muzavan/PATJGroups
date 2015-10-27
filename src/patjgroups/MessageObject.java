/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package patjgroups;

/**
 *
 * @author M. Reza Irvanda
 * @param <T>
 */
public class MessageObject<T> {       
    public enum OperationType {
      ADD,REMOVE,CONTAINS,POP,PUSH,TOP  
    };
    private T object;
    private OperationType type;
    
    public MessageObject(T obj, OperationType typ){
        object = obj;
        type = typ;
    }
    
    public T getObject(){
        return object;
    }
    
    public void setObject(T obj){
        object = obj;
    }
    
    public OperationType getOperationType(){
        return type;
    }
    
    public void setOperationType(OperationType typ){
        type = typ;
    }
    
}

