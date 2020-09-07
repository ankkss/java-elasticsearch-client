package example.es.client;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ElasticHighClient {

	private RestHighLevelClient client = null;

	@Value("#{'${elastic.host}'.split(',')}")
	private List<String> hosts;

	@Value("${elastic.port}")
	private int port;

	private String user;

	private String password;

	public ElasticHighClient() {}

	@PostConstruct
	private void initialize() {
		List<HttpHost> httpsHosts = hosts.stream()
				.map(s -> new HttpHost(s, port))
				.collect(Collectors.toList());

		RestClientBuilder builder = RestClient.builder(httpsHosts.toArray(new HttpHost[] {}));
		
		//If x-pack security enabled
		if (user != null && password != null) {
			final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("user", "password"));

			builder.setHttpClientConfigCallback(
					httpClientBuilder -> httpClientBuilder
					.setDefaultCredentialsProvider(credentialsProvider));
		}

		client = new RestHighLevelClient(builder);
	}
	
	public SearchResponse search(String index, SearchSourceBuilder sourceBuilder) throws IOException {
		SearchRequest searchRequest = new SearchRequest(index.split(","));
		searchRequest.source(sourceBuilder);
		
		return client.search(searchRequest, RequestOptions.DEFAULT);
	}

}
