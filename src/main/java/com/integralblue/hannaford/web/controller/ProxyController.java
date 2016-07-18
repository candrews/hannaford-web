package com.integralblue.hannaford.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;

import com.google.common.collect.ImmutableList;
import com.integralblue.hannaford.web.SiteUrlConstants;
import com.integralblue.hannaford.web.exception.NoSearchResultsFoundException;
import com.integralblue.hannaford.web.model.Breadcrumb;
import com.integralblue.hannaford.web.model.BrowseProductPage;
import com.integralblue.hannaford.web.model.ContentPage;
import com.integralblue.hannaford.web.model.ProductPage;
import com.integralblue.hannaford.web.model.SearchPage;
import com.integralblue.hannaford.web.model.ThumbnailPage;
import com.integralblue.hannaford.web.service.BrowseProductPageService;
import com.integralblue.hannaford.web.service.ContentPageService;
import com.integralblue.hannaford.web.service.ProductPageService;
import com.integralblue.hannaford.web.service.ProxyService;
import com.integralblue.hannaford.web.service.SearchPageService;
import com.integralblue.hannaford.web.service.ThumbnailPageService;

@Controller
public class ProxyController {
	@Autowired
	private ProxyService proxyService;
	@Autowired
	private ProductPageService productPageService;
	@Autowired
	private BrowseProductPageService browseProductPageService;
	@Autowired
	private ThumbnailPageService thumbnailPageService;
	@Autowired
	private ContentPageService contentPageService;
	@Autowired
	private SearchPageService searchPageService;
	
    @RequestMapping(method=RequestMethod.GET, value={"/assets/**"})
    public ResponseEntity<byte[]> assets(HttpServletRequest request) {
    	String relativeUrl = getRelativeUrlWithQueryString(request);
    	ResponseEntity<byte[]> entity = getEntityReturnEntityOnError(relativeUrl, byte[].class);
    	MediaType contentType = entity.getHeaders().getContentType();
    	ResponseEntity<byte[]> ret = ResponseEntity.status(entity.getStatusCode()).header(HttpHeaders.CONTENT_TYPE, contentType.toString()).body(entity.getBody());
    	return ret;
    }

    @RequestMapping(method=RequestMethod.GET, value={SiteUrlConstants.BROWSE_PRODUCTS})
    public String browseProducts(HttpServletRequest request, Model model) {
    	String relativeUrl = getRelativeUrlWithQueryString(request);
    	BrowseProductPage browseProductPage = browseProductPageService.getBrowseProductPage(relativeUrl);
    	model.addAttribute("browseProductPage",browseProductPage);
    	model.addAttribute("breadcrumbs",browseProductPage.getBreadcrumbs());
    	return "browseProduct";
    }

    @RequestMapping(method=RequestMethod.GET, value={SiteUrlConstants.SEARCH})
    public String search(HttpServletRequest request, Model model) {
    	String relativeUrl = getRelativeUrlWithQueryString(request);
    	try{
    		SearchPage searchPage = searchPageService.getSearchPage(relativeUrl);
        	model.addAttribute("searchPage",searchPage);
        	model.addAttribute("breadcrumbs",searchPage.getBreadcrumbs());
        	return "search";
    	}catch(NoSearchResultsFoundException e){
        	model.addAttribute("breadcrumbs",ImmutableList.<Breadcrumb>builder()
        			.add(Breadcrumb.builder()
        					.href("/")
        					.name("Home")
        					.build())
        			.build());
    		return "noSearchResults";
    	}
    }

    @RequestMapping(method=RequestMethod.GET, value={"/thumbnail/**"})
    public String thumbnail(HttpServletRequest request, Model model) {
    	String relativeUrl = getRelativeUrlWithQueryString(request);
    	ThumbnailPage thumbnailPage = thumbnailPageService.getThumbnailPage(relativeUrl);
    	model.addAttribute("thumbnailPage",thumbnailPage);
    	model.addAttribute("breadcrumbs",thumbnailPage.getBreadcrumbs());
    	return "thumbnail";
    }

    @RequestMapping(method=RequestMethod.GET, value={"/product/**"})
    public String product(HttpServletRequest request, Model model) {
    	String relativeUrl = getRelativeUrlWithQueryString(request);
    	ProductPage productPage = productPageService.getProductPage(relativeUrl);
    	model.addAttribute("productPage",productPage);
    	model.addAttribute("breadcrumbs",productPage.getBreadcrumbs());
    	return "product";
    }
    
    @RequestMapping(method=RequestMethod.GET, value={SiteUrlConstants.CONTENT})
    public String content(HttpServletRequest request, Model model) {
    	String relativeUrl = getRelativeUrlWithQueryString(request);
    	ContentPage contentPage = contentPageService.getContentPage(relativeUrl);
    	model.addAttribute("contentPage",contentPage);
    	model.addAttribute("breadcrumbs",contentPage.getBreadcrumbs());
        return "content";
    }
    
    @RequestMapping(method=RequestMethod.GET, value={"/","/home.jsp"})
    public String home(HttpServletRequest request, Model model) {
    	ContentPage contentPage = contentPageService.getHomePage();
    	model.addAttribute("contentPage",contentPage);
    	model.addAttribute("breadcrumbs",contentPage.getBreadcrumbs());
        return "content";
    }
    
    @RequestMapping(method=RequestMethod.GET, value={SiteUrlConstants.CAMERA})
    public String camera(HttpServletRequest request, Model model) {
    	model.addAttribute("breadcrumbs",ImmutableList.<Breadcrumb>builder()
    			.add(Breadcrumb.builder()
    					.href("/")
    					.name("Home")
    					.build())
    			.build());
        return "camera";
    }
    
    private static String getRelativeUrlWithQueryString(HttpServletRequest request){
    	String relativeUrl = request.getRequestURI();
    	if(request.getQueryString()!=null){
    		relativeUrl+="?" + request.getQueryString();
    	}
    	return relativeUrl;
    }
    
    @SuppressWarnings("unchecked")
	private <T> ResponseEntity<T> getEntityReturnEntityOnError(String relativeUrl, Class<T> clazz){
		try {
			return proxyService.getEntity(relativeUrl, clazz);
		} catch (HttpClientErrorException e) {
			BodyBuilder bodyBuilder = ResponseEntity.status(e.getStatusCode()).headers(e.getResponseHeaders());
			if (clazz == byte[].class) {
				return (ResponseEntity<T>) bodyBuilder.body(e.getResponseBodyAsByteArray());
			} else if (clazz == String.class) {
				return (ResponseEntity<T>) bodyBuilder.body(e.getResponseBodyAsString());
			}else{
				return bodyBuilder.body(null);
			}
		}
    }
    
}
