package pk;

import javax.jms.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Main {

    public static void main(String[] args) {
        try {
            int count = 0;
            Context c = null;
            QueueConnectionFactory f = null;
            QueueSession qs;
            Queue q = null;
            QueueReceiver r = null;
            Mensaje m;
            ObjectMessage om;
            QueueConnection cc = null;
            
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
            qs=cc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            try {
                cc.start();
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            r = qs.createReceiver((javax.jms.Queue) q);
            
            while(count<=100){
                om=(ObjectMessage)r.receive(0);
                m=(Mensaje)om.getObject();
                if(m.op==1){
                    System.out.print(count + " + " + m.valor + " = ");
                    count += m.valor;
                    System.out.println(count);
                }
                else if(m.op==2){
                    System.out.print(count + " - " + m.valor + " = ");
                    count -= m.valor;
                    System.out.println(count);
                }else{
                    System.out.println("Wrong operation code received.");
                }
            }
            cc.close();
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
