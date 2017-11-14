/*

 */
package feldmann.cwsocket;

import static feldmann.cwsocket.CardWarsSocketServer.charset;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Carlos André Feldmann Júnior
 */
public class Cliente {

    private Socket sock;
    private String svname;
    public static String separador = "✲";
    boolean connected = true; 
    
    public Cliente(final Socket sock, String svname) {
        this.sock = sock;
        this.svname = svname;

        final Cliente cl = this;
        final String nomesv = svname;
        Runnable r = new Runnable() {

            @Override
            public void run() {
                while (connected) {
                    try {

                        byte[] bytes = InterServerSocket.readBytes(sock);
                        if(bytes==null){
                            continue;
                        }
                       
                        String bruta = new String(bytes, charset);

                        if (bruta.contains(separador)) {
                            String praquem = bruta.split(separador)[0];
                            String channel = bruta.split(separador)[1];
                            String msg = bruta.split(separador)[2];

                         //   Log.sendLog("[MSG] " + nomesv + " mandou " + channel + " mensagem para " + praquem + " com a msg: '" + msg + "'");

                            if (praquem.equalsIgnoreCase("all")) {
                                for (Cliente c : InterServerSocket.ListaClientes.values()) {
                                    if (c != cl) {
                                        c.sendMessage(channel, msg);
                                    }
                                }
                            } else if (praquem.equalsIgnoreCase("lobbys")) {
                                for (Cliente clif : InterServerSocket.ListaClientes.values()) {
                                    if (clif.svname.contains("Lobby") && !clif.svname.equals(nomesv)) {
                                        clif.sendMessage(channel, msg);
                                    }
                                }
                            } else {

                                if (InterServerSocket.ListaClientes.containsKey(praquem)) {

                                    InterServerSocket.ListaClientes.get(praquem).sendMessage(channel, msg);
                                }
                            }
                        }

                    } catch (IOException ex) {
                        disconect();
                        break;
                    }

                }
            }
        };
        new Thread(r).start();
    }

    public String getServerName() {
        return svname;
    }

    public void disconect() {
        try {
            Log.sendLog("[Conexão] Cliente " + svname + " desconectado!");
            connected = false;
            sock.close();
            InterServerSocket.ListaClientes.remove(svname);
            for (Cliente c : InterServerSocket.ListaClientes.values()) {

                c.sendMessage("dc", svname);

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Socket getSocket() {
        return sock;
    }

    public void sendMessage(String channel, String msg) {
        try {
            String info = channel + separador + msg;
            byte[] msgbytes = info.getBytes(charset);
            InterServerSocket.sendBytes(msgbytes, 0, msgbytes.length, sock);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
