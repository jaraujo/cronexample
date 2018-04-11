package org.openshift;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ExecScript.
 *
 * @author ja17702@imcnam.ssmb.com
 * @author mm23459@imcnam.ssmb.com (AKA "the resource")
 *
 */
@WebServlet("/ExecScript")
public class ExecScript extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ExecScript.class.getCanonicalName());

    private static final long serialVersionUID = 1L;

    private static final String OS = System.getProperty("os.name").toLowerCase();


    /**
     * Determine if we are running on Windows, or not.
     *
     * @return true if running on Windows.
     */
    public static boolean isWindows() {
        return (ExecScript.OS.indexOf("win") >= 0);
    }


    /**
     * Command line test.
     *
     * @param args
     *            The path to the script to execute.
     */
    public static void main(final String[] args) {
        ExecScript.runScript(args[0]);
    }


    /**
     * Call the script and return the output.
     *
     * @param script
     *            The path to the script to execute.
     * @return The output of the executed script.
     */
    public static String runScript(final String script) {


        ExecScript.LOG.info("In the RunScript method");
        String[] cmd = null;

	/** 
	** For security, only enable the calling of a known script in a known location.
	** otherwise uncomment the below handle custom script names/paths accordingly with your own security restrictions.
	**/

        if (ExecScript.isWindows()) {
		cmd = new String[] {"myScript.bat"};
            // cmd = new String[] { script };

        } else {
		cmd = new String[] {"sh", "myScript.sh"};
            //cmd = new String[] { "sh", script };
        }


        
	final StringBuffer o = new StringBuffer();
        try {

            ExecScript.LOG.info("Calling the script");
            final ProcessBuilder pb = new ProcessBuilder(cmd);
            final Process p = pb.start();
            final InputStream is = p.getInputStream();
            final InputStreamReader isr = new InputStreamReader(is);
            final BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                o.append(line);
                o.append(System.getProperty("line.separator"));
                ExecScript.LOG.info("Added a line to the output buffer: " + line);
            }

            p.waitFor();

            ExecScript.LOG.info("Returned from calling the script");

            br.close();
            isr.close();
            is.close();

        } catch (final Exception e) {
            ExecScript.LOG.severe(e.getMessage());
        }

        ExecScript.LOG.info("Returning the response to the caller: " + o.toString());
        return o.toString();

    }

    /**
     * Default constructor.
     */
    public ExecScript() {
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String script = request.getParameter("script");
        response.getWriter().append(ExecScript.runScript(script));
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

}
	