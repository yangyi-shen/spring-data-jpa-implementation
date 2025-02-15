# spring-data-jpa-implementation

This is an instructional implementation of Spring Data JPA, mainly intended to improve my fluency with Spring Boot in general. The goal is to be able to perform CRUD operations on the JPA repository by GETting select url paths (e.g. /write/pizza adds string "pizza" to the repository). GET / will return a summary of the entire database.

# todo-list

- [x] Initialize Spring Boot project
- [x] Create JPA repository
- [ ] Create controller w/ methods for CRUD operations
- - [ ] /api/create?text={text}: Create new item based on text parameter
- - [x] /api/read?id={id}: Return content of item with specified ID
- - [x] /api/read?id=random: Get random database item
- - [x] /api/read:id=all: Get every item in database
- - [ ] /api/update?id={id}&text={text}: Update text of item with specified ID
- - [ ] /api/delete?id={id}: Delete item with specified ID
- [ ] Create method to get database summary and tie to GET /
- [ ] Put on finishing touches