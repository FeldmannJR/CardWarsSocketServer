/*

 */

package feldmann.cwsocket.cmds;

import java.util.HashMap;

/**
 *
 * @author Carlos André Feldmann Júnior
 */
public abstract class Comando{

    public static HashMap<String,Comando> cmds= new HashMap();
   
    String cmd;

    public Comando(String cmd) {
        this.cmd = cmd;
        cmds.put(cmd, this);
    }    
  
    public abstract void execute(String command,String[] args);
        
    
   
}
