package feldmann.cwsocket.cmds;

import feldmann.cwsocket.CardWarsSocketServer;
import feldmann.cwsocket.Log;

/**
 *
 * @author NeT32
 */
public class ComandoStatus extends Comando {

    public ComandoStatus() {
        super("status");
    }

    @Override
    public void execute(String command, String[] args) {
        Runtime rt = Runtime.getRuntime();
        Log.sendLog(String.format("Sistema: %s %s (%s)", new Object[]{
            System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch")
        }));
        Log.sendLog(String.format("Java: %s %s (%s)", new Object[]{
            System.getProperty("java.vendor"), System.getProperty("java.version"), System.getProperty("java.vendor.url")
        }));
        Log.sendLog(String.format("JVM: %s %s %s", new Object[]{
            System.getProperty("java.vm.vendor"), System.getProperty("java.vm.name"), System.getProperty("java.vm.version")
        }));
        Log.sendLog("Quantidade de CPUS: " + rt.availableProcessors());
        Log.sendLog("Memoria total: " + Math.floor(rt.maxMemory() / 1024.0D / 1024.0D) + " MB");
        Log.sendLog("Memoria alocada da JVM: " + Math.floor(rt.totalMemory() / 1024.0D / 1024.0D) + " MB");
        Log.sendLog("Memoria alocada limpa: " + Math.floor(rt.freeMemory() / 1024.0D / 1024.0D) + " MB");
    }

}
