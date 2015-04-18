<p>
    The aim of the project is to implement a distributed Remote Desktop/VNC style application geared towards remote 'cloud' gaming.
</p>
<h1>
    Contents
</h1>
<p>
    This document is split into two parts:
</p>
<ul>
    <li>
        Implementation details
    </li>
    <li>
        Running Instructions
    </li>
</ul>
<h1>
    Implementation
</h1>
<p>
    The deployment scenario of the project is shown below:
</p>
<p>
    <img width="480" height="405" src="file:///C:/Users/ALIARS~1/AppData/Local/Temp/msohtmlclip1/01/clip_image002.jpg"/>
</p>
<p>
    <img width="480" height="370" src="file:///C:/Users/ALIARS~1/AppData/Local/Temp/msohtmlclip1/01/clip_image004.jpg"/>
</p>
<p>
    <img width="480" height="388" src="file:///C:/Users/ALIARS~1/AppData/Local/Temp/msohtmlclip1/01/clip_image006.jpg"/>
</p>
<h2>
    Central Server
</h2>
<p>
    The central server acts like a directory service for the p2p network. Workers (ESPs) first connect to it when they wish to join the network and the server
    adds them to its database. When a user wishes to engage in a remote session it first contacts the central server which responds with a list of free
    workers. The user then forms a direct p2p link with the worker.
</p>
<p>
    The central server communicates using the TCP protocol and is fully fault tolerant. This is implemented using a backup method which periodically saves the
    servers state to a file. Whenever the server is started (i.e. in the event of failure) it first checks whether a previously saved state exists and attempts
    to start from that.
</p>
<h2>
    ESP Worker
</h2>
<p>
    This is the main end point server from which users stream sessions/games. It makes three concurrent connections using different sockets:
</p>
<p>
    · A connection to receive keyboard and mouse input from the client
</p>
<ul>
    <li>
        A connection to stream video to the client
    </li>
    <li>
        A persistent connection the central server
    </li>
</ul>
<p>
    Java's robot class is used to apply received keyboard and mouse inputs from the client. A comma separated protocol has been defined for this. A push
    mechanism is used where the client pushes IO updates as soon as it receives them rather then polling for them.
</p>
<p>
    Video is captured as screenshots at about 10 fps and send over the network as soon as taken.
</p>
<h2>
    Client
</h2>
<p>
    The client comprises of a frontend GUI made from JavaFX and a backend which handles the communications with the servers. Unlike the ESP worker and central
    server which are both command line applications, the server is completely GUI based. Input from keyboard and mouse within a JavaFX frame is captures and
    sent to the worker in real time using a dedicated socket. Similarly, video is streamed from the worker using a socket and displayed in the frame. A
    screenshot of the client is shown below:
</p>
<p>
    <img width="639" height="458" src="file:///C:/Users/ALIARS~1/AppData/Local/Temp/msohtmlclip1/01/clip_image008.jpg"/>
</p>
<p>
    Instructions to run:
</p>
<p>
    Due to the distributed nature of the application, the endpoint servers (workers) must be run in different virtual physical machine on the same network.
</p>
<p>
    First, compile and run the central server as follows:
</p>
<ul type="disc">
    <li>
        javac -cp kryonet.jar Main.java NetworkMain.java Client.java
    </li>
    <li>
        java -cp .:kryonet.jar Main
    </li>
</ul>
<p>
    Remember to note down the address of the central server as it will be the main bootstrapping point.
</p>
<p>
    Second, Compile and/or run the workers on as many virtual/physical machines as needed as follows:
</p>
<ul type="disc">
    <li>
        javac -cp kryonet.jar Test.java
    </li>
    <li>
        java -cp .:kryonet.jar Test
    </li>
</ul>
<p>
    The worker will prompt for the central servers address.
</p>
<p>
    Third, launch the client by navigating to javafxmainapp folder and launching terminal with:
</p>
<ul type="disc">
    <li>
        javac -cp kryonet.jar Main.java
    </li>
    <li>
        java -cp .:kryonet.jar Main
    </li>
</ul>
<p>
    Now enter the central servers address in the field in the GUI and click connect. The status should update; if so, you may connect video and IO through
    respective buttons.
</p>
