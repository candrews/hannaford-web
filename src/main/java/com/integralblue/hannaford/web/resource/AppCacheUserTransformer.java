package com.integralblue.hannaford.web.resource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.resource.ResourceTransformerChain;
import org.springframework.web.servlet.resource.ResourceTransformerSupport;
import org.springframework.web.servlet.resource.TransformedResource;

import com.integralblue.hannaford.web.model.PageState;
import com.integralblue.hannaford.web.service.PageService;
import com.integralblue.hannaford.web.service.PageStateParserService;

public class AppCacheUserTransformer extends ResourceTransformerSupport {
	
	@Autowired
	private PageService pageService;
	@Autowired
	private PageStateParserService pageStateParserService;

	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private final String fileExtension = "manifest";

	@Override
	public Resource transform(HttpServletRequest request, Resource resource, ResourceTransformerChain transformerChain)
			throws IOException {
		resource = transformerChain.transform(request, resource);
		if (!this.fileExtension.equals(StringUtils.getFilenameExtension(resource.getFilename()))) {
			return resource;
		}
		byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
		String content = new String(bytes, DEFAULT_CHARSET);
		Document document = pageService.getPage("/");
    	PageState pageState = pageStateParserService.getPageState(document);
		if(pageState.getUserInfo()==null){
			content = content.replaceAll("(?m)^\\#IFLOGGEDIN .*$", "");
		}else{
			content = content.replaceAll("(?m)^\\#IFLOGGEDIN ", "");
		}
		content=content.replaceAll(Pattern.quote("${CURRENTUSERID}"), pageState.getUserInfo()==null?"NONE":pageState.getUserInfo().getId());
		return new TransformedResource(resource, content.getBytes(DEFAULT_CHARSET));
	}

}
