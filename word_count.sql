CREATE TABLE docs (line STRING);
-- Change the path to the docs you need
LOAD DATA INPATH '/path/to/doc'  OVERWRITE INTO TABLE docs;

CREATE TABLE word_couts AS

SELECT word, count(word) AS count FROM

   (SELECT explode(split(line,  ‘\s’)) AS word FROM docs) w

GROUP BY word

ORDER BY word;


