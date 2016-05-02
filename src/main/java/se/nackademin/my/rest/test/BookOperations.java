package se.nackademin.my.rest.test;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.Random;
import java.util.UUID;

/*
 *
 * @author olesia
 */
public class BookOperations {
    private static final String BASE_URL="http://localhost:8080/librarytest/rest/";
    private Object author;
    private Integer id;
    private String description;
    private Integer nbOfPage;
    private String title;
    private String isbn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNbOfPage() {
        return nbOfPage;
    }

    public void setNbOfPage(Integer nbOfPage) {
        this.nbOfPage = nbOfPage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Object getAuthor() {
        return author;
    }

    public void setAuthor(Object author) {
        this.author = author;
    }

    public Response getBook(int id) {
        String resourceName = "books/"+id;
        Response response = given().accept(ContentType.JSON).get(BASE_URL+resourceName);
        return response;
    }

    public Response getBooksOfAuthor(Integer authorId) {
        ///books/byauthor/{author_id}
        String resourceName = "books/byauthor/"+authorId;
        Response response = given().accept(ContentType.JSON).get(BASE_URL+resourceName).prettyPeek();
        return response;
    }

    public Response getBooksAuthors(Integer bookId) {
        ///books/{book_id}/authors
        String resourceName = "books/"+bookId;
        Response response = given().accept(ContentType.JSON).get(BASE_URL+resourceName+"/authors").prettyPeek();
        return response;
    }
    
    public Response push(String booksTemplate) {
        String resourseName="books";
        Response postResponse = given().contentType(ContentType.JSON).body(booksTemplate).post(BASE_URL + resourseName);
        System.out.println("Status code: " + postResponse.getStatusCode());
        return postResponse;            
    }

    public Response put(String booksTemplate) {
        String resourseName="books";
        Response putResponse = given().contentType(ContentType.JSON).body(booksTemplate).put(BASE_URL + resourseName);
        System.out.println("Status code: " + putResponse.getStatusCode());
        return putResponse;            
    }

    public Response get(int id) {
        String resourseName="books/"+id;
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL+resourseName);
        System.out.println("Status code: " + getResponse.getStatusCode());
        return getResponse;               
    
    }

    public String createBookRandomTemplate(String authorTemplate) {
        title = UUID.randomUUID().toString();
        description = UUID.randomUUID().toString();
        isbn = UUID.randomUUID().toString();
        nbOfPage = new Random().nextInt(500);
        id = new Random().nextInt(5000);

        String postBodyTemplate = ""+"{\n" +
                "\"book\":\n" +
                "  {\n" +
                "    \"description\":\"%s\",\n" +
                "    \"isbn\":\"%s\",\n" +
                "    \"nbOfPage\":%s,\n" +
                "    \"title\":\"%s\",\n" +
                "    \"id\":%s\n" +
                "    "+authorTemplate+
                "  }\n" +
                "}";
        
        
//        String postBody = String.format(postBodyTemplate, description, isbn, ""+new Random().nextInt(500), title, ""+new Random().nextInt(5000));
        String postBody = String.format(postBodyTemplate, description, isbn, ""+nbOfPage, title, ""+getId());

        return postBody; 
    
    }
    public Response createBook(String bookTemplate) {
        String resourseName="books";
        //title = UUID.randomUUID().toString();
        //description = UUID.randomUUID().toString();
        //isbn = UUID.randomUUID().toString();
        //nbOfPage = new Random().nextInt(500);
        //id = new Random().nextInt(5000);

    /*    String postBodyTemplate = ""+"{\n" +
                "\"book\":\n" +
                "  {\n" +
                "    \"description\":\"%s\",\n" +
                "    \"isbn\":\"%s\",\n" +
                "    \"nbOfPage\":%s,\n" +
                "    \"title\":\"%s\",\n" +
                "    \"id\":%s\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, description, isbn, ""+nbOfPage, title, ""+id);
        jasonString = postBody;*/
        Response postResponse = given().contentType(ContentType.JSON).body(bookTemplate).post(BASE_URL + resourseName);//.prettyPeek();
        System.out.println("Status code: " + postResponse.getStatusCode());
        return postResponse;        
    }

    
/*    public String gatLatestJasonString() {
        return jasonString;
    }*/
    
    public Response getAllBooks() {
        String resourseName="books";      
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL + resourseName).prettyPeek();
        return getResponse;   
    }
    
    public Response deleteBook(int id) {
        String deleteResourseName = "books/"+id;
        Response deleteResponse = delete(BASE_URL + deleteResourseName);
        return deleteResponse;
            
    } 
    
}
