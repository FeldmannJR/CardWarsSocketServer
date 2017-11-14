/*

 */
package feldmann.cwsocket;

import static feldmann.cwsocket.CardWarsSocketServer.charset;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author Carlos André Feldmann Júnior
 */
public class InterServerSocket extends ServerSocket {

    public static HashMap<String, Cliente> ListaClientes = new HashMap(); //Lista estatica dos clientes conectados

    public static void sendBytes(byte[] myByteArray, int start, int len, Socket sock) throws IOException {
        if (len < 0) {
            throw new IllegalArgumentException("Negative length not allowed");
        }
        if (start < 0 || start >= myByteArray.length) {
            throw new IndexOutOfBoundsException("Out of bounds: " + start);
        }

        OutputStream out = sock.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);

        dos.writeInt(len);
        if (len > 0) {
            dos.write(myByteArray, start, len);
        }
    }

    public static byte[] readBytes(Socket sock) throws IOException {

        InputStream in = sock.getInputStream();
        DataInputStream dis = new DataInputStream(in);

        int len = dis.readInt();

        byte[] data = null;
        if (len > 0 && len < 2000) {
            data = new byte[len];
            dis.readFully(data);

        }
        dis = null;
        return data;
    }

    public InterServerSocket(int port) throws IOException {
        super(port);
        Thread n = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Socket svsock = accept();
                        byte[] svtobyte = readBytes(svsock);
                        if (svtobyte != null) {
                            String svname = new String(svtobyte, charset);
                            Cliente c = new Cliente(svsock, svname);
                            ListaClientes.put(svname, c);
                            Log.sendLog("[Conexao] Novo cliente " + svname + " na porta " + svsock.getPort());

                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        n.start();

    }
}
