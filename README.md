# search-autocomplete
 Auto complete API, provides recommendation for type ahead.
 
 App provides two api one for adding sample strings to cache, one for getting recommedations from the search.
 
 one can use below curl api for loading sample data to cache
 
 ```
 curl --location --request POST 'http://localhost:8080/autocomplete/populate-queries' \
--header 'Content-Type: application/json' \
--data-raw '{
    "queries":["Jurassic Park","Notting Hill","Clueless","The Hunger Games","The King'\''s Speech","The Queen","The Wizard of Oz"]
}'
 ```

use following api to get recommendations

```
curl --location --request GET 'http://localhost:8080/autocomplete/recommendations/the'
```

Load test is done with 400K words for loading to cache, the application took 600ms. The recommendation api is used to to get all the words starting with "s",
which took 25ms.
