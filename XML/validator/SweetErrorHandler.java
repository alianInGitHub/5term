package validator;

import jdk.internal.org.xml.sax.SAXParseException;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by anastasia on 05.11.16.
 */
public class SweetErrorHandler extends DefaultHandler{
    private Logger logger = Logger.getLogger(SweetErrorHandler.class);

    public void warning(SAXParseException e){
        logger.warn(getLineAdress(e) + " - " + e.getMessage());
    }
    public void error(SAXParseException e){
        logger.error(getLineAdress(e) + " - " + e.getMessage());
    }
    public void fatalError(SAXParseException e){
        logger.fatal(getLineAdress(e) + " - " + e.getMessage());
    }
    private String getLineAdress(SAXParseException e){
        return e.getLineNumber() + ":" + e.getColumnNumber();
    }
}
