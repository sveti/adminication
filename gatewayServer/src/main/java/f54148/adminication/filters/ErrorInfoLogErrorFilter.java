package f54148.adminication.filters;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class ErrorInfoLogErrorFilter extends ZuulFilter {

	private static Logger logger = LoggerFactory.getLogger(ErrorInfoLogErrorFilter.class);
	
	@Override
	public Object run() throws ZuulException {
		RequestContext context = RequestContext.getCurrentContext();
		
		logger.info("Error Filter: " + String.format("Error response Status Code: %s", context.getResponse().getStatus()));
		
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public String filterType() {
		return "error";
	}

}