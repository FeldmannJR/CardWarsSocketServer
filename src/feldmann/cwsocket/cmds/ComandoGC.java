package feldmann.cwsocket.cmds;

import feldmann.cwsocket.Log;

/**
 *
 * @author NeT32
 */
public class ComandoGC extends Comando {

    public ComandoGC() {
        super("gc");
    }

    @Override
    public void execute(String command, String[] args) {
        Runtime r = Runtime.getRuntime();
        long oldMem = r.freeMemory() / 1048576L;
        Log.sendLog("Running Java Garbage Collector...");
        r.gc();
        int processors = r.availableProcessors();
        long maxMem = r.maxMemory() / 1048576L;
        long newMem = r.freeMemory() / 1048576L;
        if (r.maxMemory() < 0L) {
            Log.sendLog("Não tem necessidade!");
        }
        Log.sendLog("Used memory before: " + (maxMem - oldMem) + " MB");
        Log.sendLog("Current memory: " + (maxMem - newMem) + " MB" + "/" + maxMem + " MB");
        Log.sendLog("Memory freed: " + (newMem - oldMem) + " MB");
        Log.sendLog("Processors available to Java: " + processors);
    }

}
