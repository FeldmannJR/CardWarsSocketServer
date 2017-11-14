/*

 */
package feldmann.cwsocket;

import feldmann.cwsocket.GZipFile;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.text.DateFormatter;

/**
 *
 * @author Carlos André Feldmann Júnior
 */
public class Log {

    public static Logger logg = null;

    public static void start() {

        try {

            logg = Logger.getLogger("cardwarssocketserver");
            FileHandler fh = new FileHandler("lasted.log");
            logg.setUseParentHandlers(false);
            logg.addHandler(fh);

            Formatter fr = new Formatter() {

                @Override
                public String format(LogRecord record) {

                    String horario = new SimpleDateFormat("kk:mm:ss").format(new Date());
                    return "[" + horario + "] " + record.getLevel().getName() + " : " + record.getMessage() + "\n";
                }
            };
            fh.setFormatter(fr);
            ConsoleHandler con = new ConsoleHandler();
            con.setFormatter(fr);
            logg.addHandler(con);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void sendLog(String log) {
        logg.info(log);
    }

    public static void close() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String data = sdf.format(date);

        int atual = 1;
       new File("logs").mkdir();;
       
        File f = new File("./logs/" + data + "-1.log.gz");
        
        while (f.exists()) {
            f = new File("logs/" + data + "-" + atual + ".log.gz");
            atual++;
        }
        new GZipFile(f.getPath(), "lasted.log");
    }
}
