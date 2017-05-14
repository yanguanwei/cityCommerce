package city.commerce.controller.api;

import city.commerce.controller.api.response.ApiResponse;
import city.commerce.service.search.SearchCondition;
import city.commerce.service.search.SearchResult;
import city.commerce.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author young.alway@gmail.com
 * @since 2017-03-26
 */
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/api/search", method = RequestMethod.POST)
    public ApiResponse<SearchResult> search(SearchCondition condition) {
        return ApiResponse.successOf(searchService.search(condition));
    }
}
