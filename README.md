# Sending Messages with Java Message Service (JMS)

I manually create (using the Glassfish resource management tool) a message queue connection factory and a message queue. The connection factory should be located inside the application through a directory service (JNDI). Through the connection factory we will be able to create a connection, which in turn will allow us to create a session. 

Sessions are used for several purposes in JMS, in particular they allow us to create transactions, i.e. I can group a series of sends and receives to ensure atomicity. For a session we can already create one or more message producers/consumers, which can send and receive messages from the message queue.
