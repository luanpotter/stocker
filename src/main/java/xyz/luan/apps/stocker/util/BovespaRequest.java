package xyz.luan.apps.stocker.util;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public class BovespaRequest {

	private static final String URL = "http://www.bmfbovespa.com.br/Pregao-Online/ExecutaAcaoAjax.asp";
	private static final String CODE = "CodigoPapel";

	public double getCurrentValue(String stock) {
		try {
			HttpClient httpclient = HttpClients.createDefault();
			HttpGet req = new HttpGet(new URIBuilder(URL).setParameter(CODE, stock).build());
			req.setConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build());

			HttpResponse response = httpclient.execute(req);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				return extractValueFromResponseEntity(entity);
			} else {
				throw new IOException("Response expected, none returned...");
			}
		} catch (IOException | IllegalStateException | JDOMException | URISyntaxException ex) {
			throw new RuntimeException(ex);
		}
	}

	private double extractValueFromResponseEntity(HttpEntity entity) throws JDOMException, IOException {
		final SAXBuilder jdomBuilder = new SAXBuilder();
		final XPathFactory xFactory = XPathFactory.instance();

		Document doc = jdomBuilder.build(entity.getContent());
		XPathExpression<Element> expr = xFactory.compile("//ComportamentoPapeis/Papel", Filters.element());
		return convertToDouble(expr.evaluate(doc).get(0).getAttribute("Ultimo").getValue());
	}

	private double convertToDouble(String value) {
		return Double.parseDouble(value.replace(',', '.'));
	}
}
