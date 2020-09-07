package example.es.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import example.es.client.model.Search;

@RestController
public class SearchController {
	
	@Autowired
	private SearchService searchService;

	@PostMapping("/search")
	public ResponseEntity<String> search(@RequestBody Search searchRequest) {
		try {
			return ResponseEntity.ok(searchService.search(searchRequest).toString());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
}
