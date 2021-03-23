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
  */
  public static void main(String[] args)  {
    // load XML file into "document"
    loadDocument(args[0]);
    // print staff.xml using DOM methods and XPath queries
    if (args.length < 2)																											// checks if there are 2 arguments in args
    {
    	System.out.println("Please provide 2 files: XML doument and a schema");
    }
    else if(validateDocument(args[1]) == true && args[1].equals("small_menu.xsd")) 	// validates the document if the right file is provided
    {
        printNodes();
    }


  
   
  }

  /**
    Set global document by reading the given file.

    @param filename     XML file to read
  */
  private static void loadDocument(String filename) {
    try {
      // create a document builder
      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
      builder = builderFactory.newDocumentBuilder();

      // create an XPath expression
      XPathFactory xpathFactory = XPathFactory.newInstance();
      path = xpathFactory.newXPath();

      // parse the document for later searching
      document = builder.parse(new File(filename));
    }
    catch (Exception exception) {
      System.out.println("Could not load document, The system cannot find the file specified ");
      System.exit(0);																	// terminates the program
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
    }
     catch (SAXParseException e) {
    	System.out.println("The XML file and the XML schema do not match " + e);			// a useful message for the user
      	return false;
      }
     catch (Exception e){
      System.err.println(e);
      System.err.println("Could not load schema or validate");
      return false;
    }
  }
  /**
    Print nodes using DOM methods and XPath queries.
  */
  private static void printNodes() {
    Node menuItem_1 = document.getFirstChild();
    Node base = null;												//sets the node "item" to null
    Node name = null;												//sets the node  "name" to null
    Node price = null;												//sets the node  "price" to null
    Node description = null;									//sets the node  "description" to null

    NodeList items = document.getElementsByTagName("item");	//creating a NodeList, populated with "item" elements

    for (int i = 0; i < items.getLength();i ++) {										// looping through the elements in the NodeList
    	base = items.item(i);																	// setting base to be equal to the i'th item in the list
    	name = base.getFirstChild().getNextSibling();							// setting the var to be equal to the i'th item's name
        price = name.getNextSibling().getNextSibling();						// setting the var to be equal to the i'th item's price
        description = price.getNextSibling().getNextSibling();			// setting the var to be equal to the i'th item's description
        double p = Double.parseDouble(price.getTextContent());		// parsing the price to be a double (for future computations)

        System.out.printf("%-13s %-8.2f %s%n",name.getTextContent(), p, description.getTextContent() );			// printing the output
    	
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
