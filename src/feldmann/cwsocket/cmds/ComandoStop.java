/*

 */

package feldmann.cwsocket.cmds;

/**
 *
 * @author Carlos André Feldmann Júnior
 */
public class ComandoStop extends Comando{

    public ComandoStop() {
        super("stop");
    }

    @Override
    public void execute(String command, String[] args) {
    System.exit(0);
    }

}
