# Goal
Load custom servers in singleplayer.

# Parts
## Loader
Loads custom servers as "singleplayer" worlds.  These will be loaded as subprocesses of the client.  When the server loads, it will call back to the client and tell the client to connect.

### Process when in singleplayer mode
1. Client creates a server instance and boots it based of that servers config file.
2. Client opens a TCP server, so that the server and client can communicate. 
3. When the server is ready, it connects to the communication server and calls back to the client so that the client can connect.  Resource packs may be loaded in singleplayer mode if necessary.
4. After the client connects, the server sends check alive pings to the client 1 a second.
5. Once an exit condition is reached, the server shutsdown.
    - When 5 check alive pings are missed
    - When the client sends a shutdown packet to the server.
    - When the server crashes, the client will disconnect automatically.
    - When the server stops, a message is sent to have the client disconnect.

### Process when in multiplayer mode
1. The server now has the communication server.
2. The client first connects to the communication server and requires any metadata/resource packs necessary.
3. After the client is setup, it connects to the server.
4. The client is free to connect and disconnect whenever.  This will not affect whether the server stays alive or not.

## GUI
A custom GUI for Cubic would be good for creating these "singleplayer" server instances.  This would likely use FancyMenu.