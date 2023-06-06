package searchengine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpServer;

/**
 * This class is responsible for creating an an HTTP server and serves files in
 * response to HTTP requests.
 */
public class WebServer {
  private static final int BACKLOG = 0;
  String configFile = Files.readString(Paths.get("config.txt")).strip();
  HttpServer server;

  /**
   * This method is responsible for creating a web server.
   *
   * @param port The port from which the server will look for a connection, and
   *             set up the web server
   * @throws IOException if an I/O error occurs when reading a file
   */

  WebServer(int port) throws IOException {
    server = HttpServer.create(new InetSocketAddress(port), BACKLOG);
    server.createContext("/", io -> ResponseManager.respond(io, 200, "text/html", getFile("web/index.html")));
    server.createContext("/search", io -> QueryManager.search(io));
    server.createContext(
        "/favicon.ico", io -> ResponseManager.respond(io, 200, "image/x-icon", getFile("web/favicon.ico")));
    server.createContext(
        "/code.js", io -> ResponseManager.respond(io, 200, "application/javascript", getFile("web/code.js")));
    server.createContext(
        "/style.css", io -> ResponseManager.respond(io, 200, "text/css", getFile("web/style.css")));
    server.start();
    String msg = " WebServer running on http://localhost:" + port + " ";
    System.out.println("╭" + "─".repeat(msg.length()) + "╮");
    System.out.println("│" + msg + "│");
    System.out.println("╰" + "─".repeat(msg.length()) + "╯");
  }

  /**
   * Reads the contents of a file and turns it into a byte array.
   *
   * @param filename the path to the file to read
   * @return the contents of the file as an array of bytes
   */
  byte[] getFile(String filename) {
    try {
      return Files.readAllBytes(Paths.get(filename));
    } catch (IOException exception) {
      exception.printStackTrace();
      return new byte[0];
    }
  }

}