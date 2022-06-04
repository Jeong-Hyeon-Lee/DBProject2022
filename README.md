# DBProject2022 ( PT Reservation System ) 

#### `TrainerWithGUI`  
|__ `TrainerMenuJDBC.java`   :  JDBC related functions for the trainer query  
|__ `TrainerJDialogGUI.java` :  GUI files for the trainer menu page   
|__ `TrainerMenuJTable.java` :  Trainer Menu Files and connecting to the sql functions  
|__ `ServiceStartScreen.java` : Test file for start screen  
|__ `TrainerJoinScreen.java`  : Test file for trainer joining screen  
|__ `TrainerLoginScreen.java` : Test file for trainer login screen  
  
### 회원
* **java file**  
|__ `M_MainScreen.java` : Menu page for member    
|__ `M_myPage.java` : Check the account info   
|__ `M_searchGYM.java` : Search GYM (with location, with gym name, recommend gym with member's location, enroll/change gym)  
|__ `M_searchTrainer.java` : Search trainer (with belonging gym name, with trainer name, show all trainer in member's gym, enroll/change trainer)    
|__ `M_enrollMembership.java` : Enroll/change membership     
|__ `M_totalLeft.java` : query to check the number of remaining classes (for changing gym or trainer)   
* **In addition,**  
  * Gym and trainer must be enrolled first before check the account info.
  * Gym, trainer, and membership can only be changed when the number of remaining classes is zero.
  * The order of enrollment is as follows : Gym -> Trainer -> Membership
