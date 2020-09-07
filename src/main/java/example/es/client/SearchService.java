package example.es.client;

import java.io.IOException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.es.client.model.Search;

@Service
public class SearchService {
	
	private static final Logger LOG = LoggerFactory.getLogger(SearchService.class);

	@Autowired
	private ElasticHighClient elasticHighClient;

	public SearchResponse search(Search searchRequest) throws IOException {
		QueryBuilder query = QueryBuilders.multiMatchQuery(searchRequest.getString(),
				searchRequest.getFields().toArray(new String[] {}));

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(10);
		searchSourceBuilder.query(query);
		
		LOG.info("search query-{}", searchSourceBuilder);

		return elasticHighClient.search(searchRequest.getIndex(), searchSourceBuilder);
	}
}
