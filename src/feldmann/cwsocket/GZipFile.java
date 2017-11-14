package feldmann.cwsocket;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GZipFile {

    private String OUTPUT_GZIP_FILE;
    private String SOURCE_FILE;

    public GZipFile(String praonde, String deonde) {
        OUTPUT_GZIP_FILE = praonde;
        SOURCE_FILE = deonde;
        gzipIt();

    }

    /**
     * GZip it
     *
     * @param zipFile output GZip file location
     */
    public void gzipIt() {

        byte[] buffer = new byte[1024];

        try {

            GZIPOutputStream gzos
                    = new GZIPOutputStream(new FileOutputStream(OUTPUT_GZIP_FILE));

            FileInputStream in
                    = new FileInputStream(SOURCE_FILE);

            int len;
            while ((len = in.read(buffer)) > 0) {
                gzos.write(buffer, 0, len);
            }

            in.close();

            gzos.finish();
            gzos.close();

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
