/*

 */
package feldmann.cwsocket;

import feldmann.cwsocket.Cliente;
import feldmann.cwsocket.Log;
import feldmann.cwsocket.InterServerSocket;
import feldmann.cwsocket.cmds.Comando;
import feldmann.cwsocket.cmds.ComandoGC;
import feldmann.cwsocket.cmds.ComandoStatus;
import feldmann.cwsocket.cmds.ComandoStop;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Carlos André Feldmann Júnior
 */
public class CardWarsSocketServer {

    public static String charset = "UTF-16";

    public static void startCmds(){
        new ComandoGC();
        new ComandoStatus();
        new ComandoStop();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        startCmds();
        Log.start();
        int porta = 5000;
        try {
            Log.sendLog("Server aberto na porta " + porta + " !");
            InterServerSocket sv = new InterServerSocket(porta);
            Scanner s = new Scanner(System.in);

            while (s.hasNext()) {
                String next = s.nextLine();
                String cmd;
                if (next.contains(" ")) {
                    cmd = next.split(" ")[0];
                } else {
                    cmd = next;
                }
                String[] argumentos = next.split(" ");
                argumentos = Arrays.copyOfRange(argumentos, 1, argumentos.length);
                cmd = cmd.toLowerCase();
                if (Comando.cmds.containsKey(cmd)) {
                    Comando.cmds.get(cmd).execute(cmd, argumentos);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
