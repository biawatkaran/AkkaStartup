# AkkaStartup
Trying things on Akka

Module word-count 

This basically reads a file provided as an argument. Then it breaks down the process to count the number of words in a file by

1. by sending to individual actor, FileReaderActor reads line by line from a file and passes to next actor LineReaderActor
2. LineReaderActor receives line, which splits based on words.
3. It passed to next actor which sums the word, once all the lines read. It passes back the result to main actor (caller).

