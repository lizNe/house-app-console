<h1 align="center">
  Housing Agent Application
  </h1>

<img src="https://user-images.githubusercontent.com/78036222/166112179-af256f72-90aa-4ff7-a43e-2283cee1db3e.gif" height="250" width="1000"/>
<hr>
<br>
<h2 align="center">
  Introduction
</h2>
This Housing agent application allows users to load and save multiple houses and properties created by the user to the system. A user can add various houses/properties, delete houses, update houses and list all houses/ properties on the system along with being able to save all these houses to the system to be successfully loaded back up again when the program ends. This applicatio is written completely in Kotlin Programming Lanuguage using Gradle builing tool.
Once the program starts the user is introduced to a skelton menu as shown below and can choose any of the 10 options listed to start using the Housing Agent App ! When option 2 is selected <em> List All Houses </em> a Sub-Menu appears with 3 more options: 

- List All Houses()
- List All Sold Houses()
- List All unSold Houses()

  <br>
  <h2 align="center">
  Skeleton Menu and Sub-Menu for List All Houses 
</h2>                                                             
  <img src="https://user-images.githubusercontent.com/78036222/166113812-9a5e3aa9-1f22-46d6-9d25-7e4cc7c7793b.jpg" height="350" width="400" align="left"/><img src="https://user-images.githubusercontent.com/78036222/166114316-b01bca95-4904-4c96-95f4-de411d9458ba.jpg" height="350" width="400" align="right"/> 
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  
Each houses will contain various fields such as: 
 
 ```kotlin
    var houseCategory: String,
    var houseCost: Double,
    var houseLocation: String,
    var isAvailableFrom: String,
    var isSold: Boolean,
    var numberOfBedrooms: Int,
    var numberOfBathrooms: Double,
    var houseSqFoot: Int
 
 ```
 
 ## Features
- Using CRUD to create, read, update and delete notes that are stored in the program
- A skeleton menu has been added and a more advance menu will be added at a later date
- A utility package containg a Scanner class is user to read user input rather than import java Scanner 
- Throw and catches are used to catch an error and return a message without allowing the program to completely crash
- A Kotlin-Logger is added to the program to allow for logger information to be log onto the screen.
- JUnit testing of all features in v2.0.
- Persistence such as XML and JSON have been added to load and save files
- Search Notes by Title functionality added
- Sub-Menu added for the option - list all notes that can list all notes, - all archived notes and - list all active notes.
- Using LAMBDAS to refactor code for more effective programming
  
  
   


  
  
  
  
  
  
  
  
