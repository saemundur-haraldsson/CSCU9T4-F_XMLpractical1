package XML;

import java.io.*;               // import input-output

import javax.xml.XMLConstants;
import javax.xml.parsers.*;         // import parsers
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.*;           // import XPath
import javax.xml.validation.*;      // import validators
import javax.xml.transform.*;       // import DOM source classes

//import com.sun.xml.internal.bind.marshaller.NioEscapeHandler;
import org.w3c.dom.*;               // import DOM
import org.xml.sax.SAXParseException;

/**
  DOM handler to read XML information, to create this, and to print it.

  @author   CSCU9T4, University of Stirling
  @version  11/03/20
*/
public class DOMMenu {

  /** Document builder */
  private static DocumentBuilder builder = null;

  /** XML document */
  private static Document document = null;

  /** XPath expression */
  private static XPath path = null;

  /** XML Schema for validation */
  private static Schema schema = null;

  /*----------------------------- General Methods ----------------------------*/

  /**
    Main program to call DOM parser.

    @param args         command-line arguments
   * @throws InterruptedException
  */
  public static void main(String[] args) throws InterruptedException  {
    // load XML file into "document"
    loadDocument(args[0] , args[1]);
    validateDocument(args[1]);
    // print staff.xml using DOM methods and XPath queries
    printNodes();

   
  }

  /**
    Set global document by reading the given file.

    @param filename     XML file to read
  */
  private static void loadDocument(String filename , String schemaPath) {
    try {
      // create a document builder
      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
      builder = builderFactory.newDocumentBuilder();

      // create an XPath expression
      XPathFactory xpathFactory = XPathFactory.newInstance();
      path = xpathFactory.newXPath();

      // parse the document for later searching
      document = builder.parse(new File(filename));

      SchemaFactory schemaFac = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schema = schemaFac.newSchema(new File(schemaPath));

      

    }
    catch (Exception exception) {
      System.err.println("could not load document " + exception);
    }
  }

  /*-------------------------- DOM and XPath Methods -------------------------*/
  /**
   Validate the document given a schema file
   @param filename XSD file to read
  */
  private static Boolean validateDocument(String filename)  {
    try {
      String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
      SchemaFactory factory = SchemaFactory.newInstance(language);
      schema = factory.newSchema(new File(filename));
      Validator validator = schema.newValidator();
      validator.validate(new DOMSource(document));
      return true;
    } catch (SAXParseException spe){
      System.err.println(spe);
      System.err.println("Could not load schema or validate");

      return false;
    }
    catch (Exception e)
      {
        System.err.println(e);
        System.err.println("Could not load schema or validate");
        return false;
      }
  }
  /**
    Print nodes using DOM methods and XPath queries.
   * @throws InterruptedException
  */
  private static void printNodes() throws InterruptedException {

    NodeList items = document.getElementsByTagName("item");

    if(items.item(0).hasAttributes() == true)
      {
        System.out.println(items.item(0).get);
      }


  }

  /**
    Get result of XPath query.

    @param query        XPath query
    @return         result of query
  */
  private static String query(String query) {
    String result = "";
    try {
      result = path.evaluate(query, document);
    }
    catch (Exception exception) {
      System.err.println("could not perform query - " + exception);
    }
    return(result);
  }
}
