Author: 
  - James Vo - 40021967 
  - James Nguyen 
  - Benson Chan  
------------------------------
===================================================================

Part A - The Data Access Layer
-----------------------------
In the previous assignment the repository class was implemented in the core project. In
this revision we want to use a separate database layer. Create a separate project called
data access layer and implement the data access using a pattern of your choice (Table
Data Gateway or Active Record).
Move the database configuration that you implemented in the repository core to this layer
(database connection string, user id, and password).
Patterns: Table Data Gateway or Active Record

Part B - The Repository Core
-----------------------------
Keep the Business Core Objects, as designed in assignment 2. Remove all database
implementations. Instead, use the newly Data Access Layer you created in part A.
Patterns: Domain Model (as specified in assignment 2)


Part C - The Web Presentation Layer
-----------------------------
In the Presentation Layer, refactor the “View book details” page by applying a Template
View pattern. Implement two different versions of the view for display the book
information. One: with cover page, One: without the cover page, with an option to edit
the book info (the link to the edit info brings the user to the edit page).
Patterns: Template View (2 implementations)

Part D - The Service Layer
-----------------------------
In this part, you are simulating the possibility of having a separate service layer on top of
the business core. For this part, create a separate web application project that hosts a
servlet, namely books, for simulating the functionality of a service layer. The servlet
responds to a GET request and returns book information in json format. It is specified as
follows:
- Calling servlet with no parameter:
servlet lists all books in json format. No cover image information is given.
- Calling servlet with id=…
servlet returns the book details in json format. Since the image cover data is a byte
array, it won’t be included in the response. However, include a field indicating
whether the book has a cover image or not (i.e. "has-image" : "yes").
In case of any errors, the error must be caught and reported in json format. Include the
exception message in the response.
Note that since the repository core is a secure, the service would not work without the
client being authenticated. Hence, implement two additional servlets for authentication:
 login servlet (user=…&pwd=…)
 logout servlet (no parameter)
It is assumed that the client uses cookies to store the session. Note that this is not a REST
implementation.
Patterns: Service Layer

Part E - The System Model
-----------------------------
Display the system architecture by providing updated UML diagrams and showing the
above implementations. 
3
For each project (layer), display the class diagram, as well as showing one sequence
diagram for a scenario of your choice. Total of diagrams: 8.
For each sequence diagrams, only list classes within the project layer and only the top
level class(es) from the underlying layer, if applicable
