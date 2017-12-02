/*
 */
package com.nitsoft.util;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.util.HtmlUtils;



public class SecurityFilter extends DelegatingFilterProxy {

    static class FilteredRequest extends HttpServletRequestWrapper {

        /* These are the characters allowed by the Javascript validation */
        public FilteredRequest(HttpServletRequest request) {
            super(request);
        }

        // remove bad character
        public String sanitize(String input) {
            if (input != null && !input.equals("")) {
                input = input.replaceAll("([\\ud840\\udbff\\udc00\\udfff\\ud800])", "");
            }
            return input;
        }
        
        // Escape special characters to hex code
        private static String toUnicodeEscapeString(String str) {
            
            // Check is special character
            if ( !StringUtil.containSpecialCharacter( str )) {
                return str;
            }
            
            // Modeled after the code in java.util.Properties.save()
            StringBuilder buf = new StringBuilder();
            int len = str.length();
            char ch;
            for (int i = 0; i < len; i++) {
              ch = str.charAt(i);
              
              if ( !StringUtil.containSpecialCharacter( String.valueOf(ch) )) {
                  buf.append(ch);
                  continue;
              }
              
              switch (ch) {
                case '\\': buf.append("\\\\"); break;
                case '\t': buf.append("\\t"); break;
                case '\n': buf.append("\\n"); break;
                case '\r': buf.append("\\r"); break;

                default:
                  
                    buf.append('\\');
                    buf.append('u');
                    buf.append(toHex((ch >> 12) & 0xF));
                    buf.append(toHex((ch >>  8) & 0xF));
                    buf.append(toHex((ch >>  4) & 0xF));
                    buf.append(toHex((ch >>  0) & 0xF));
              }
            }
            return buf.toString();
          }

          private static char toHex(int nibble) {
            return hexDigit[(nibble & 0xF)];
          }

          private static char[] hexDigit = {
            '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'
          };

        /**
         * Clean XSS on parameter
         *
         * @param value Parameter's value
         * @return new value
         */
        private String cleanXSS(String input) {
           
            // Unicode escape
            // String value = StringEscapeUtils.escapeJava( input );
            if ( input == null ) {
                
                return input;
            }
            
            String value = FilteredRequest.toUnicodeEscapeString( input );
                
            // HTML escape
            // value = HtmlUtils.htmlEscapeHex(input );

            // SQL injection
             value = StringEscapeUtils.escapeSql(value );
            

            // Javascript escape
            // value = StringEscapeUtils.escapeJavaScript( value );
            
//            value = value.replaceAll("eval\\((.*)\\)", "");
//            value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
//            value = value.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
//            value = value.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
//            value = value.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "");
//            value = value.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
            return value;
        }

        @Override
        public String getParameter(String paramName) {
            String value = super.getParameter(paramName);
            if (value == null) {
                return null;
            }
            value = sanitize(value);
            return cleanXSS(value);
        }

        @Override
        public String[] getParameterValues(String paramName) {
            String values[] = super.getParameterValues(paramName);
            if (values == null) {
                return null;
            }

            int count = values.length;
            String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++) {
                String v = sanitize(values[i]);
                encodedValues[i] = cleanXSS(v);
            }
            return encodedValues;
        }

        @Override
        public String getHeader(String name) {

            String value = super.getHeader(name);

            if (value == null) {
                return null;
            }

            return value;
        }
    }

    static class FilteredResponse extends HttpServletResponseWrapper {

        private static final String CONTENT_TYPE = "Content-Type";

        public FilteredResponse(HttpServletResponse response) {
            super(response);
        }

        private void setContentTypeOptions(String contentType) {
            if (isJsonContentType(contentType)) {
                doSetXConentTypeOptions();
            }
        }

        private void doSetXConentTypeOptions() {
            setHeader("X-Frame-Options", "SAMEORIGIN");
        }

        private boolean isContentType(String headerName) {
            return headerName != null && headerName.equals(CONTENT_TYPE);
        }

        @Override
        public void setHeader(String name, String value) {
            if (isContentType(name)) {
                setContentTypeOptions(value);
            }

            super.setHeader(name, value);
        }

        @Override
        public void addHeader(String name, String value) {
            if (isContentType(name)) {
                setContentTypeOptions(value);
            }
            super.addHeader(name, value);
        }

        @Override
        public void setContentType(String type) {
            // doSetXConentTypeOptions();
            // Security options
            super.setContentType(type);
        }
    }

//    @Override
//    public void init(FilterConfig config) throws ServletException {
//        encoding = config.getInitParameter("requestEncoding");
//        if (encoding == null) {
//            encoding = "UTF-8";
//        }
//    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException {
        // Respect the client-specified character encoding
        // (see HTTP specification section 3.4.1)
//        if (null == request.getCharacterEncoding()) {
//            request.setCharacterEncoding(encoding);
//        }

        // response.setContentType("text/html; charset=UTF-8");
        //response.setCharacterEncoding("UTF-8");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

//        httpServletResponse.setHeader("X-XSS-Protection", "1; mode=block");
//        httpServletResponse.setHeader("X-Content-Type-Options", "nosniff");

        next.doFilter(new FilteredRequest(httpServletRequest), response);
        //next.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // NOOP
    }

    static boolean isJsonContentType(String contentType) {
        if (contentType == null) {
            return false;
        } else {
            return contentType.matches("^application/json.*") || contentType.matches("^text/json.*");
        }
    }
}