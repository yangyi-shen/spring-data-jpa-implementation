# spring-data-jpa-implementation

This is an instructional implementation of Spring Data JPA, mainly intended to improve my fluency with Spring Boot in general. Once you've started the project, you can perform CRUD operations on the JPA repository by GETting select url paths. Available paths are listed below:

- **/:** Get summary of entire database, accompanied by some **✨fancy✨** text!
- **/api/create?text={text}:** Create new item based on text parameter
- **/api/read?id={id}:** Return content of item with specified ID
- **/api/read?id=random:** Get random database item
- **/api/read:id=all:** Get every item in database
- **/api/update?id={id}&text={text}:** Update text of item with specified ID
- **/api/delete?id={id}:** Delete item with specified ID

# how to run:
1. Download this repository
2. Enter the project, from whatever directory you've stored it in
3. Run ```mvn spring-boot:run```
4. Behold, my glorious Spring Data JPA implementation!