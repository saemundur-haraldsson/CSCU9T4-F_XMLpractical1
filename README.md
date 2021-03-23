# CSCU9T4-F_XMLpractical1
This repository holds the starter code for the first XML practical exercise in CSCU9T4 and F
	
	For this practical I used DOM

a) I have added the validation of the document in the main method, after the loadDocument call.
	I have added System.exit() in the loadDocument method to terminate the program if the document cannot be loaded or found

b) I have modified the validateDocument method
 	I have added another catch block in the validateDocument method, which catches a SAXParseException and provides the user with 
	a useful message.

c) I have modified the printNodes method
    	First I am creating nodes for the menu, item, name, price and description elements.
	Then I am creating a NodeList, populated with "item" elements
	Then with the help of a for loop, I am initialising the nodes with their current values and printing the output

PS. I have provided some descriptive comments in the code :)
