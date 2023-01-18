package pk;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Main {

    public static void main(String[] args) {
        Context c = null;
        QueueConnectionFactory f = null;
        QueueSession qs = null;
        Queue q = null;
        QueueSender s = null;
        Mensaje m = null;
        QueueConnection cc = null;
        ObjectMessage om = null;
        int op=3, valor;
        
        try {
            c=new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            f=(QueueConnectionFactory)c.lookup("factoriaConexiones");
        } catch (NamingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            q=(Queue)c.lookup("cola");
        } catch (NamingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            cc = f.createQueueConnection();
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            qs=cc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            cc.start();
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            s=qs.createSender(q);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Scanner entrada=new Scanner(System.in);
        
        do{
            System.out.println("Enter the operation code");
            try{
                op=entrada.nextInt();
                if(op==1 || op==2){
                    System.out.println("Enter the value");
                    try{
                        valor=entrada.nextInt();
                        m = new Mensaje(op, valor);
                        try {
                            om=qs.createObjectMessage(m);
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            s.send(om);
                            System.out.println("Message sent");
                        } catch (JMSException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }catch (InputMismatchException ex) {
                        System.out.println("Only integers are allowed");
                        entrada.nextLine();
                    }
                }
                else if(op==0){
                    System.out.println("Program completed");
                }
                else{
                    System.out.println("Invalid operation code");
                }
            }catch (InputMismatchException ex) {
                System.out.println("Only integers are allowed");
                entrada.nextLine(); 
            }
        }while(op!=0);
        try {
            cc.close();
        }catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
