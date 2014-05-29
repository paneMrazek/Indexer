package main.java.indexer.server;

import java.io.IOException;
import java.net.HttpURLConnection;

import main.java.indexer.shared.communication.params.SubmitBatch_Params;
import main.java.indexer.shared.communication.results.GetProjects_Result;
import main.java.indexer.shared.communication.results.SubmitBatch_Result;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


@SuppressWarnings("unused")
public class SubmitBatchHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Facade facade = new Facade();
		XStream xmlStream = new XStream(new DomDriver());
		String auth = exchange.getRequestHeaders().getFirst("authorization");
		SubmitBatch_Params params = (SubmitBatch_Params) xmlStream.fromXML(exchange.getRequestBody());

		SubmitBatch_Result result = facade.submitBatch(auth,params);

		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result,exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}