# WebHooks with RMI

## Usage

First, we need to start the producer server. In order to do that,
open a terminal at the root of the project and enter :

`advanced_middleware/ $ cd producer && java ProducerClient`

Then, in another terminal, at the root of the project enter :

`advanced_middleware/ $ cd consumer && java MainConsumerClient`

## HTTP Streaming with RMI

RMI is designed to be used in distributed applications, in order to transfer messages
between different nodes of the system.

When it comes to WebHooks, RMI is a perfectly logical choice since it works as follow :

```
Consumer
    ---> Opens a connection with the producer 
    ---> Performs the request

Producer    
    ---> Receives the request
    ---> Sends back the result of the request
    ---> Connection is closed
```

Due to that design, HTTP Streaming becomes painful and difficult to implement because RMI, by design, will close the connection
between two hosts after the request has been processed. 

In HTTP Streaming, we need to keep the connection opened even after a chunk of data has been exchanged, i.e., after a request has been processed.
Usually, we would use the `Connection: Keep-Alive` HTTP header, but RMI doesn't.

In consequence, in order to simulate HTTP Streaming we have 2 solutions :

- If the data size if inferior to the size of the buffer used by RMI to transfer data on the network, we can directly send these data instead of streaming them chunk by chunk (and simulate the streaming in the client/consumer side)
- Else, we can do the following :

    ```
    Size of data to send = Sd
    Size of the buffer used by RMI = Sb
    Chunk size = Sc = Sd/Sb
    
    Consumer
        ---> Opens a connection with the producer for streaming
    
    Producer 
        ---> Receives request and stores the consumer identifier if this is its first request, otherwise updates the associated chunk position in the list of chunks to send
        ---> Sends the next chunk of size Sc associated to the consumer
        ---> Connection is closed
    
    ...
    
    Consumer (is already _registered_ on Producer side)
        ---> Opens a connection with the producer for streaming
        
    Producer 
        ---> Receives request, updates the next chunk to send (no one left after this one)
        ---> Sends the next chunk of size Sc associated to the consumer
        ---> Connection is closed

    Consumer (is already _registered_ on Producer side)
        ---> Opens a connection with the producer for streaming
        
    Producer 
        ---> Receives request, sees that no chunks are left to be send
        ---> Sends an empty chunk of data
        ---> Connection is closed
        
    Consumer
        ---> Receives empty chunk of data ==> streaming is complete

    ```
    
Obviously, this solution is really bad because we need to re-open connections for each chunk of data, which is very slow and time consuming.

Moreover, we base the end of the consumer requests on the fact that, when the producer sends an empty chunk, the streaming is complete.
This raises a new problem caused by packet loss : if the i-th chunk is lost, then the consumer will receive no data and think that the streaming is complete, when it was not.
Another problem with this is that the producer will have updated the next chunk to send to the consumer, but the consumer didn't receive any data.
Therefore, the data received by the consumer will be corrupted.

Finally, HTTP Streaming has way better performances over the UDP protocol. Indeed, the TCP protocol (used by RMI) guarantees the data integrity.
In an ideal world, the HTTP Streaming would work fine over TCP because we can assume that packet loss would never happen.

However, in real world, there are packet losses due to multiple factors, and therefore we don't want to block the streaming if a given packet (say i-th) has not yet arrived, but the i+1-th, i+2-th ... have arrived already.

The TCP protocol is a heavy one that is fine for data integrity, etc... but is way too heavy when it comes to HTTP Streaming (it slows down the communication).

## Authors

- [mailto:clement.beal@etu.unice.fr](Béal Clément)
- [mailto:maxime.flament@etu.unice.fr](Flament Maxime)

