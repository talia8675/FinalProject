package flow.bgu.ac.il;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import com.mxgraph.io.mxCodec;
import org.w3c.dom.svg.SVGDocument;

public class RunServlet extends HttpServlet {

	final static Logger LOG = LoggerFactory.getLogger(RunServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -1598336877581962216L;

	// A hack that only works if one program is running at a time!

	private static Thread thread;

	/**
	 * Handles save request and prints XML.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Extract the XML fro the message
		BufferedReader br = request.getReader();
		mxCodec codec = new mxCodec();
		String xml = IOUtils.toString(br);

		DOMImplementation impl = GenericDOMImplementation.getDOMImplementation();
		Document doc = impl.createDocument(xml, "svg", null);
		SVGDocument svg = (SVGDocument)doc;

		response.getOutputStream().println("Succesfully deployed:");
		response.setStatus(HttpServletResponse.SC_OK);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Cache-control", "private, no-cache, no-store");
		response.setHeader("Expires", "0");

		// response.getWriter().println(createGraph(request));
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
