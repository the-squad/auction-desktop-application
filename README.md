# Auctions desktop app
> Sell and buy items right away from your desktop. Build with JavaFX and CSS

## Installing / Getting started
* Using Xampp or any other localhost navigate to localhost/phpmyadmin
* Create a new database and give it a name
* Navigate to the new database and click on import and select [database.sql](database.sql)
* Download [mySql connector](https://dev.mysql.com/downloads/connector/j/5.1.html)
* Now add the mySql to your editor:

### NetBeans IDE
* Right click on libraries and select Add Jar File
* Navigate to the downloaded .Jar and select it

### IntellJ IDE
* In the title menu File > Project Structure > Libraries
* Click (+) icon and navigate to the downloaded .Jar and select it

**In order to config the database copy [Config.example](Config.example) to src > app > models and 
rename it to Config.java**

```Java
//  Database localhost
public static final String DB_URL = "jdbc:mysql://localhost/[database name]";
//  Database credentials
public static final String USER = "root";
public static final String PASS = "";
```
