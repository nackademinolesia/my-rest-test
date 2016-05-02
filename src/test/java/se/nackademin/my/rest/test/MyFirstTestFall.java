package se.nackademin.my.rest.test;

/*import org.junit.Test;
import static org.junit.Assert.*;
import static com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;*/

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.Random;
import java.util.UUID;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;



/**
 *
 * @author olesia
 */
public class MyFirstTestFall {
    private static final String BASE_URL="http://localhost:8080/librarytest/rest/";  
    public MyFirstTestFall() {
    }
 
    @Test /*test #1*/
    public void testPostAuthor() {
        //Create a new author.
        Authors author = new Authors();
        Response response = author.createNewAuthor();
        System.out.println("test #1 Status code: " + response.getStatusCode());
        assertEquals("Status code should be 201", 201, response.statusCode());
        
    }

    @Test /*test #1.1*/
    public void testPostAuthorNegativ() {
        //400 The author's id was already in the database.
        Authors author1 = new Authors();
        Authors author2 = new Authors();

        Response response1 = author1.createNewAuthor();
        assertEquals("Status code should be 201", 201, response1.statusCode());
        int idAuthor1 = author1.getId();
        Response response2 = author2.createNewAuthorByTemplate(UUID.randomUUID().toString(), idAuthor1);
        //posmotret' chto vnutri response1 =  given().accept(ContentType.JSON).get(BASE_URL+"authors/"+idAuthor1).prettyPeek();

        
        System.out.println("Status code: " + response2.getStatusCode());
        assertEquals("Post response should have status code 400", 400, response2.getStatusCode());
    
    }
    
    @Test /*test #2*//*!!!!!!!!!!!!!dosnt work
    public void testPutAuthor() {
        //Update a author with new data.
        Authors author = new Authors();
        Response response = author.createNewAuthor();
        System.out.println("test #1 Status code: " + response.getStatusCode());
        assertEquals("Status code should be 201", 201, response.statusCode());
        int t = author.getId();
        author.setName("Olesia");
        System.out.println(t);

//        Response putResponse = given().contentType(ContentType.JSON).queryParam(BASE_URL+"authors/id", t).body(author).put(BASE_URL+"authors");
        Response putResponse = given().contentType(ContentType.JSON).body(author).put(BASE_URL+"authors");

        Response response1 =  given().accept(ContentType.JSON).get(BASE_URL+"authors/"+t).prettyPeek();
 
    
    }

    @Test /*test #2.1*/
    public void testPutAuthorNegativ() {
        //404 The author was not found.
    
    }
    
    
    @Test /*test #3*/
    public void testGetAllAuthors() {
        //Get all the books from the database
        Authors author = new Authors();
        Response response = author.getAllAuthors();
        System.out.println("test #3 Status code: " + response.getStatusCode());
        assertEquals("Status code should be 200", 200, response.statusCode());
    
    }

    
    @Test /*test #4*/
      public void testGetAuthorById() {
        //Get the author with the specified id.
        Authors author = new Authors();
        
        Response response = author.getAuthor(1);
        System.out.println("test #4 Status code: " + response.getStatusCode());
        assertEquals("Status code should be 200", 200, response.statusCode());
    
    }

    @Test /*test #4.1*/
      public void testGetAuthorByIdNegativ() {
        //Get the author with the specified id, if the author was not found.
        Authors author = new Authors();
        
        Response response = author.getAuthor(999999);
        System.out.println("test #4.1 Status code: " + response.getStatusCode());
        assertEquals("Status code should be 404 if the author was not found", 404, response.statusCode());
    
    }

    @Test /*test #5*/
       public void testDeleteAuthorById() {
        //Delete the author with the specified id.
        Authors author = new Authors();
        Response response = author.createNewAuthor();
        assertEquals("Status code should be 201", 201, response.statusCode());
        int t = author.getId();
        Response deleteResponse = author.deleteAuthor(t);
        assertEquals("Delet method should return 204", 204, deleteResponse.getStatusCode());
        System.out.println("test #5 Status code: " + deleteResponse.getStatusCode());
   
    }
      
    @Test /*test #5.1*/
       public void testDeleteAuthorByIdNegativ() {
        //404 The author was not found.
        Authors author = new Authors();
        Response response = author.createNewAuthor();
        assertEquals("Status code should be 201", 201, response.statusCode());
        int t = author.getId();
        Response deleteResponse = author.deleteAuthor(t);
        assertEquals("Delet method should return 204", 204, deleteResponse.getStatusCode());
        deleteResponse = author.deleteAuthor(t);
        assertEquals("Delet method should return 404", 404, deleteResponse.getStatusCode());
        System.out.println("test #5.1 Status code: " + deleteResponse.getStatusCode());
 
    }

       
    @Test /*test #6*/
      public void testBookPost() {
        String resourseName="books";
        BookOperations bookOperations = new BookOperations();
        String bookTemplate = bookOperations.createBookRandomTemplate("");
        Response postResponse = bookOperations.createBook(bookTemplate);
        assertEquals("Post response should have status code 201", 201, postResponse.statusCode());
    
    }
      
   @Test /*test #6.1*/
      public void testBookPostNegativ1() {
        //The book's id was already in the database
        BookOperations bookOperations = new BookOperations();
        String bookTemplate = bookOperations.createBookRandomTemplate("");
        Response postResponse = bookOperations.createBook(bookTemplate);
        System.out.println("Status code: " + postResponse.getStatusCode());
        assertEquals("Post response should have status code 201", 201, postResponse.statusCode());

        postResponse = bookOperations.createBook(bookTemplate);
        System.out.println("Status code: " + postResponse.getStatusCode());
        assertEquals("Post response should have status code 400", 400, postResponse.getStatusCode());
    
    }

   @Test /*test #6.2*/
      public void testBookPostNegativ2() {
        //the book contained an author that had no id field set
        Authors additionalAuthor = new Authors();
//        String authorTemplate = additionalAuthor.prepareNewAuthorRandom();
        String authorTemplate = additionalAuthor.prepareNewAuthorSpec();
        BookOperations bookOperations = new BookOperations();
        String bookTemplate = bookOperations.createBookRandomTemplate(authorTemplate+"\n");
        Response response = bookOperations.createBook(bookTemplate);
        System.out.println("test #6.2 Status code: " + response.getStatusCode());
        assertEquals("Status code should be 400", 400, response.statusCode());
    
    }
      
   @Test /*test #6.3*/
      public void testBookPostNegativ3() {
        //the book contained an author that didn't exist in the database.
        Authors additionalAuthor = new Authors();
        String authorTemplate = additionalAuthor.prepareNewAuthorSpec();
        BookOperations bookOperations = new BookOperations();
        String bookTemplate = bookOperations.createBookRandomTemplate(authorTemplate+"\n");
        Response postResponse = bookOperations.createBook(bookTemplate);
        System.out.println("test #6.3 Status code: " + postResponse.getStatusCode());
        assertEquals("Status code should be 400", 400, postResponse.statusCode());


    }
      
   /*@Test /*test #7*//*!!!!!!!!!!!!!!!!!!dosnt work*/
/*      public void testBookPut() {
      //The book was updated.    
        BookOperations bookOperations = new BookOperations();
        String bookTemplate = bookOperations.createBookRandomTemplate();
        Response postResponse = bookOperations.createBook(bookTemplate);
        int t = bookOperations.getId();
        bookOperations.setDescription("Hej!Hej!");
        bookOperations.setIsbn("11111111111");
        //Response putResponse = bookOperations.put(bookTemplate);
        //String bookT = bookOperations.createBookRandomTemplate();

        //Response putResponse = given().contentType(ContentType.JSON).body(bookT).put(BASE_URL+"books");
        
        //int fetchedId = getResponse.jsonPath().getInt("books.book[0].id");

        Response putResponse = given().contentType(ContentType.JSON).queryParam(BASE_URL+"books/id", t).body(bookOperations).put(BASE_URL+"books");
        System.out.println("test #7 Status code: " + putResponse.getStatusCode());
        
        //SingleBook singleBook = new SingleBook(bookOperations);
        //Response response = given().contentType(ContentType.JSON).body(singleBook).log().all().post(BASE_URL+"books").prettyPeek();
        //System.out.println("Status code: " + response.getStatusCode());
     
      }
      
   @Test /*test #7.1*/
/*      public void testBookPutNegativ1() {
      //400 The book contained an author with no id field set or the book contained an author that didn't exist in the database.
      
      }

   @Test /*test #7.2*/
/*      public void testBookPutNegativ2() {
      //404 The book was not found.
      
      }  */


    @Test /*test #8*/
      public void testGetAllBooks() {
        //Get all the books from the database
        BookOperations bookOperations = new BookOperations();
        Response response = bookOperations.getAllBooks();
        System.out.println("test #8 Status code: " + response.getStatusCode());
        assertEquals("Status code should be 200", 200, response.statusCode());

    }

      
    @Test /*test #9*/
      public void testGetBookByNumber() {
        BookOperations bookOperations = new BookOperations();
        String bookTemplate = bookOperations.createBookRandomTemplate("");
        Response postResponse = bookOperations.createBook(bookTemplate);
        int t = bookOperations.getId();
        Response response = bookOperations.get(t);
        System.out.println("test #9 Status code: " + response.getStatusCode());
        assertEquals("Status code should be 200", 200, response.statusCode());

    }
      
    @Test /*test #9.1*/
      public void testGetBookByNumberNegativ() {
        //404 The book was not found.
        BookOperations bookOperations = new BookOperations();
        Response response = bookOperations.get(999999999);
        System.out.println("test #9.1 Status code: " + response.getStatusCode());
        assertEquals("Status code should be 404", 404, response.statusCode());

    } 
      
    @Test /*test #10*/
      public void testDeleteBookByNumber() {
      //The book was deleted.
        BookOperations bookOperations = new BookOperations();
        String bookTemplate = bookOperations.createBookRandomTemplate("");
        Response postResponse = bookOperations.createBook(bookTemplate);
        assertEquals("Post response should have status code 201", 201, postResponse.statusCode());
        int t = bookOperations.getId();
        Response deleteResponse = new BookOperations().deleteBook(t);
        assertEquals("Delet method should return 204", 204, deleteResponse.getStatusCode());
        System.out.println("test #10 Status code: " + deleteResponse.getStatusCode());
      }
      
    @Test /*test #10.1*/
      public void testDeleteBookByNumberNegativ() {
      //404 The book was not found.
        BookOperations bookOperations = new BookOperations();
        String bookTemplate = bookOperations.createBookRandomTemplate("");
        Response postResponse = bookOperations.createBook(bookTemplate);
        assertEquals("Post response should have status code 201", 201, postResponse.statusCode());
        int t = bookOperations.getId();
        Response deleteResponse = new BookOperations().deleteBook(t);
        assertEquals("Delet method should return 204", 204, deleteResponse.getStatusCode());
        Response getDeletedBookResponse = new BookOperations().getBook(t);
        assertEquals("Fetching deleted book should return 404", 404, getDeletedBookResponse.getStatusCode());
        System.out.println("test #10.1 Status code: " + getDeletedBookResponse.getStatusCode());

      }
      
    @Test /*test #11*/
      public void testGetBooksOfAuthor() {
        //Get all the books of the asked author from the database.
        ///books/byauthor/{author_id}
        BookOperations bookOperations = new BookOperations();
        Response response = bookOperations.getBooksOfAuthor(2);
        System.out.println("test #11 Status code: " + response.getStatusCode());
        assertEquals("Status code should be 200", 200, response.statusCode());
          
      }

    @Test /*test #11.1*/
      public void testGetBooksOfAuthorNegativ() {
        //Try to get all the books of the asked author from the database, when there is no such author.
        ///books/byauthor/{author_id}
        BookOperations bookOperations = new BookOperations();
        Response response = bookOperations.getBooksOfAuthor(555555);
        System.out.println("test #11.1 Status code: " + response.getStatusCode());
        assertEquals("Status code should be 200", 200, response.statusCode());
          
      }
      
      
    @Test /*test #12*/
      public void testGetAuthersOfBook() {
        //200 Get the authors of the specified book.
        BookOperations bookOperations = new BookOperations();
        Response getResponse = bookOperations.getBooksAuthors(4);
        System.out.println("test #12 Status code: " + getResponse.getStatusCode());
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
          
      }
      
    @Test /*test #12.1*/
      public void testGetAuthersOfBookNegativ() {
        //404 The book was not 
        BookOperations bookOperations = new BookOperations();
        Response getResponse = bookOperations.getBooksAuthors(99999);
        System.out.println("test #12.1 Status code: " + getResponse.getStatusCode());
        assertEquals("Status code should be 404", 404, getResponse.statusCode());
          
      }

    @Test /*test #13*/
      public void testAddAutherToBook() {
        //200 Add an author to the specified book. 
        ///books/{book_id}/authors
        Authors additionalAuthor = new Authors();
        Response response = additionalAuthor.addAdditionalAuthorToBook(3);
        System.out.println("test #13 Status code: " + response.getStatusCode());
        assertEquals("Status code should be 200", 200, response.statusCode());

      }

    @Test /*test #13.1*/
      public void testAddAutherToBookNegativ1() {
        //400 The author did not have the id field set
        ///books/{book_id}/authors
        Authors additionalAuthor = new Authors();
        String postBody = additionalAuthor.addAdditionalAuthorWithoutIdTemplate();
        Response response = additionalAuthor.addAdditionalAuthorWithoutIdToBookN(1);
        System.out.println("test #13.1 Status code: " + response.getStatusCode());
        assertEquals("Status code should be 400", 400, response.statusCode());

      }

    @Test /*test #13.2*/
      public void testAddAutherToBookNegativ2() {
        //400 The author was already an author of this book. 
        ///books/{book_id}/authors
        Authors additionalAuthor = new Authors();
        Response response = additionalAuthor.addAdditionalAuthorAlreadyUsedToBookN(2);
        System.out.println("test #13.2 Status code: " + response.getStatusCode());
        assertEquals("Status code should be 400", 400, response.statusCode());

      }
      
    @Test /*test #13.3*/
      public void testAddAutherToBookNegativ3() {
        //404 The book was not found. 
        ///books/{book_id}/authors
        Authors additionalAuthor = new Authors();
        Response response = additionalAuthor.addAdditionalAuthorToBook(99999);
        System.out.println("test #13.3 Status code: " + response.getStatusCode());
        assertEquals("Status code should be 404", 404, response.statusCode());

      }

    
}
