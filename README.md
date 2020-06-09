# hacker-news-crawler
A maven-bundled Spring application that crawls public Hacker News API to process and return Top stories, Top comments and Past History as requested by a REST client.

List of APIs exposed:

1. /top-stories:
This API returns the top 10 stories ranked by score in the last 10 minutes, each containing title, url, score, time of submission, and the user who submitted it.

2. /comments:
This API returns the top 10 parent comments on a given story ID, sorted by the total number of comments (including child comments).

3. /past-stories:
This API returns all the past top stories that were served previously.

# Prerequisites

1. JDK 1.8.x
2. Maven 3.x.x
3. Redis 4.x.x
4. Docker (Optional)

# How to Run the application?

Run without Docker:

1. Clone the repository.
2. Navigate to the root directory of the project.
3. Run the maven build command: *mvn clean install -s settings.xml*
4. Ensure that redis-server in your system is up and running. If not, start the service using: *redis-server*
5. Run the maven run command: *mvn spring-boot:run -s settings.xml*
6. Start experimenting with the APIs:
a. http://localhost:8080/top-stories
b. http://localhost:8080/comments?storyId=
c. http://localhost:8080/past-stories

Run with Docker:

1. Clone the repository.
2. Navigate to the root directory of the project.
3. Run the maven build command: *mvn clean install -s settings.xml*
4. Ensure that redis-server in your system is up and running. If not, start the service using: *redis-server*
5. Build docker using: *docker-compose build*
6. Run docker using: *docker-compose up*
7. Start experimenting with the APIs:
a. http://localhost:8080/top-stories
b. http://localhost:8080/comments?storyId=
c. http://localhost:8080/past-stories
