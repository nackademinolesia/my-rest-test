package se.nackademin.my.rest.test;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.Random;
import java.util.UUID;

/**11111
 *
 * @author olesia
 */
public class Authors {
    private static final String BASE_URL="http://localhost:8080/librarytest/rest/";
    private Integer id;
    private String name;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
 
    public Response getAuthor(int id) {
        String resourceName = "authors/"+id;
        Response response = given().accept(ContentType.JSON).get(BASE_URL+resourceName);//.prettyPeek();
        return response;
    }

    public Response getAllAuthors() {
        String resourseName="authors";      
        Response getResponse = given().accept(ContentType.JSON).get(BASE_URL + resourseName);//.prettyPeek();
        return getResponse;   
    }

    public String prepareNewAuthorRandom() {

        //String resourseName="authors";
        name = UUID.randomUUID().toString();
        setId((Integer) new Random().nextInt(5000));
        String postBodyTemplate = ""+"{\n" +
                "\"author\":\n" +
                "  {\n" +
                "    \"name\":\"%s\",\n" +
                "    \"id\":%s\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, name, ""+id);
        return postBody;        
    }

    public String prepareNewAuthorSpec() {//refactoring!!!

        //String resourseName="authors";
        name = UUID.randomUUID().toString();
        setId((Integer) new Random().nextInt(5000));
        String postBodyTemplate = ""+
                "\"author\":\n" +
                "  {\n" +
                "    \"name\":\"%s\",\n" +
                "    \"id\":%s\n" +
                "  }";
        
        String postBody = String.format(postBodyTemplate, name, ""+id);
        return postBody;        
    }
    
    
    
    public Response createNewAuthor() {
        String resourseName="authors";
        /*name = UUID.randomUUID().toString();
        setId((Integer) new Random().nextInt(5000));
        //id = new Random().nextInt(5000);

        String postBodyTemplate = ""+"{\n" +
                "\"author\":\n" +
                "  {\n" +
                "    \"name\":\"%s\",\n" +
                "    \"id\":%s\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, name, ""+id);*/
        String postBody = prepareNewAuthorRandom();
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL + resourseName);//.prettyPeek();
        System.out.println("Status code: " + postResponse.getStatusCode());
        return postResponse;        

    }

    public Response createNewAuthorByTemplate(String nameTemplate, Integer idTemplate) {
        String resourseName="authors";
        String postBodyTemplate = ""+"{\n" +
                "\"author\":\n" +
                "  {\n" +
                "    \"name\":\"%s\",\n" +
                "    \"id\":%s\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, nameTemplate, ""+idTemplate);
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL + resourseName);//.prettyPeek();
        System.out.println("Status code: " + postResponse.getStatusCode());
        return postResponse;        

    }
    
    /*    public Response createNewAuthorByTemplate1(String nameTemplate, Integer authorIdTemplate, Integer bookIdTemplate) {
        //books/{book_id}/authors
        String resourseName="books/";
        String postBodyTemplate = ""+"{\n" +
                "\"author\":\n" +
                "  {\n" +
                "    \"name\":\"%s\",\n" +
                "    \"id\":%s\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, nameTemplate, ""+authorIdTemplate);
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL + resourseName + bookIdTemplate +"/authors");
        System.out.println("Status code: " + postResponse.getStatusCode());
        return postResponse;        

    }*/

        public Response addAdditionalAuthorToBook(Integer bookId) {
        //books/{book_id}/authors
        String resourseName="authors";
        String resourseName1="books/";
        name = UUID.randomUUID().toString();
        setId((Integer) new Random().nextInt(5000));
        //id = new Random().nextInt(5000);

        String postBodyTemplate = ""+"{\n" +
                "\"author\":\n" +
                "  {\n" +
                "    \"name\":\"%s\",\n" +
                "    \"id\":%s\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, name, ""+id);
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL + resourseName);
        System.out.println("Status code: " + postResponse.getStatusCode());
                
        postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL + resourseName1 + bookId +"/authors");
        System.out.println("Status code: " + postResponse.getStatusCode());
        return postResponse;        

    }

        public Response addAdditionalAuthorWithoutIdToBookN(Integer bookId) {//refactoring!!!
        //books/{book_id}/authors
        //String resourseName="authors";
        String resourseName1="books/";
     /*   name = UUID.randomUUID().toString();

        String postBodyTemplate = ""+"{\n" +
                "\"author\":\n" +
                "  {\n" +
                "    \"name\":\"%s\"\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, name);
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL + resourseName);
        System.out.println("Status code: " + postResponse.getStatusCode());*/
                
        String postBody = addAdditionalAuthorWithoutIdTemplate();
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL + resourseName1 + bookId +"/authors");
        System.out.println("Status code: " + postResponse.getStatusCode());
        return postResponse;        

    }

        public String addAdditionalAuthorWithoutIdTemplate() {
        //books/{book_id}/authors
        String resourseName="authors";
        //String resourseName1="books/";
        name = UUID.randomUUID().toString();

        String postBodyTemplate = ""+"{\n" +
                "\"author\":\n" +
                "  {\n" +
                "    \"name\":\"%s\"\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, name);
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL + resourseName);
        System.out.println("Status code: " + postResponse.getStatusCode());
        return postBody;       
        //postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL + resourseName1 + bookId +"/authors");
        //System.out.println("Status code: " + postResponse.getStatusCode());
        //return postResponse;        

    }
        
        
        public Response addAdditionalAuthorAlreadyUsedToBookN(Integer bookId) {
        String resourseName="authors";
        String resourseName1="books/";
        name = UUID.randomUUID().toString();
        setId((Integer) new Random().nextInt(5000));
        //id = new Random().nextInt(5000);

        String postBodyTemplate = ""+"{\n" +
                "\"author\":\n" +
                "  {\n" +
                "    \"name\":\"%s\",\n" +
                "    \"id\":%s\n" +
                "  }\n" +
                "}";
        
        String postBody = String.format(postBodyTemplate, name, ""+id);
        Response postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL + resourseName);
        System.out.println("Status code: " + postResponse.getStatusCode());
                
        postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL + resourseName1 + bookId +"/authors");
        System.out.println("Status code: " + postResponse.getStatusCode());
        
        postResponse = given().contentType(ContentType.JSON).body(postBody).post(BASE_URL + resourseName1 + bookId +"/authors");
        System.out.println("Status code: " + postResponse.getStatusCode());

        return postResponse;        

    }
        
        
    public Response deleteAuthor(int id) {
        String deleteResourseName = "authors/"+id;
        Response deleteResponse = delete(BASE_URL + deleteResourseName);
        return deleteResponse;
        
    
    }
    
}
