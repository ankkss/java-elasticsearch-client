# java-elasticsearch-client
Simple elasticsearch java client application with ES multimach search rest API example.

It's a spring boot application.

Configuration file-
src/main/resources/application.properties

Execute-
Run App.java as java application

Sample search API request-
Type: POST
URL: http://localhost:8080/search
Body: 
{
  "index": "test",      # ES index
  "fields": ["field"],  #fields search will apply
  "string": "search"    #searched term
}
